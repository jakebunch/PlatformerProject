package game.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.Handler;
import game.gfx.Animation;
import game.gfx.Assets;
import game.tiles.Tile;

public class Player extends Actor {

	//STATIC VALUES
	
	public static final int PLAYERWIDTH = 128, PLAYERHEIGHT = 128;
	
	public static final float RUNSPEED = 12;
	public static final float RUNACCEL = 1.5f;
	public static final float RUNDECEL = 0.5f;
	
	public static final float AIRSPEED = 12;
	public static final float AIRACCEL = 1.6f;
	public static final float AIRDECEL = 0.8f;
	
	public static final float JUMPSPEED = 22;
	public static final float JUMPWINDOW = 400;
	public static final float GRAVITY = 1.2f;
	
	public static final float VAULTSPEED = 25;
	public static final float VAULTDRIFT = 6;
	public static final float VAULTTIME = 200;
	public static final int VAULTRANGE = 64;
	
	public static final float DEATHTIME = 200;
	public static final float DEATHSPEED = 8;
	
	//MOVEMENT VALUES
	
	private float xSpeed = 0;
	private float ySpeed = 0;
	
	private boolean grounded = false;
	private boolean direction = true; //true = right, false = left
	
	private boolean dying = false;
	private long deathTimer = 0;
	private float deathXSpeed = 0;
	private float deathYSpeed = 0;
	
	private boolean canJump = false;
	private boolean jumping = false;
	private long jumpTimer = 0;
	private float gravity = 0.9f;
	
	private boolean canVault = true;
	private boolean vaulting = false;
	private long vaultTimer = 0;
	private Solid vaultSolid = null;
	
	//STATES
	
	enum State {
		st_standing,
		st_running,
		//st_ducking,
		st_jumping,
		st_falling,
		st_vaulting,
		st_flipping
		//st_dying
	};
	
	State currentState = State.st_standing;
	State lastState;
	
	enum Direction {
		RIGHT,
		LEFT
	};
	
	Direction dir = Direction.RIGHT;
	
	private int coyoteTime = 3;
	private int coyoteTimer = coyoteTime;
	
	private int vaultTime = 0;
	private int vaultTimeMax = 12;
	
	//INPUT
	
	@SuppressWarnings("unused")
	private boolean up, down, left, right, space, leftArrow, rightArrow;
	
	//ANIMATIONS
	
	private Animation playerIdleR = new Animation(80, Assets.playerIdleR);
	private Animation playerIdleL = new Animation(80, Assets.playerIdleL);
	private Animation playerRunningR = new Animation(50, Assets.playerRunningR);
	private Animation playerRunningL = new Animation(50, Assets.playerRunningL);
	private Animation playerJumpingR = new Animation(80, Assets.playerJumpingR);
	private Animation playerJumpingL = new Animation(80, Assets.playerJumpingL);
	private Animation playerFallingR = new Animation(50, Assets.playerFallingR);
	private Animation playerFallingL = new Animation(50, Assets.playerFallingL);
	private Animation playerPreVaultR = new Animation(80, Assets.playerPreVaultR);
	private Animation playerPreVaultL = new Animation(80, Assets.playerPreVaultL);
	private Animation playerVaultingR = new Animation(50, Assets.playerVaultingR);
	private Animation playerVaultingL = new Animation(50, Assets.playerVaultingL);
	private Animation playerFlippingR = new Animation(50, Assets.playerFlippingR);
	private Animation playerFlippingL = new Animation(50, Assets.playerFlippingL);
	private Animation playerTurningR = new Animation(50, Assets.playerTurningR);
	private Animation playerTurningL = new Animation(50, Assets.playerTurningL);
	
	private Animation lastSprite = playerIdleR;
	private Animation currentSprite = playerIdleR;
	
	
	//CLASS
	
	public Player(Handler handler, int x, int y) {
		super(handler, x, y, PLAYERWIDTH, PLAYERHEIGHT);
		
		hitbox.width = 60;
		hitbox.height = 95;
	}

	@Override
	public void tick() {
		
		//Animation
		currentSprite.tick();
		
		//Adjust speed according to input
		getInput();
		//getSpeed();
		handleInput();
		
		//Make sure hitbox is aligned
		hitbox.x = x + 32;
		hitbox.y = y + 32;
		
		//Move according to our speed
		if(currentState != State.st_vaulting) moveX(xSpeed, null);
		moveY(ySpeed, null);
		
		//Adjust Camera
		handler.getGameCamera().centerOnEntity(this);
		
		//Check if we've moved into a dangerous position
		checkDeath();
		
	}
	
	//@Override
	public void render(Graphics g) {
		
		if(currentState == State.st_vaulting) {
			if(dir == Direction.RIGHT) g.drawImage(Assets.staff, x + PLAYERWIDTH/2 - handler.getGameCamera().getxOffset(), y + PLAYERHEIGHT/16 - handler.getGameCamera().getyOffset(), 112, 24, null);
			else g.drawImage(Assets.staff, x - PLAYERWIDTH/2 - handler.getGameCamera().getxOffset(), y + PLAYERHEIGHT/16 - handler.getGameCamera().getyOffset(), 112, 24, null);
		}
		
		if(dir == Direction.RIGHT) g.drawImage(currentSprite.getCurrentFrame(), x - 16 - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset(), PLAYERWIDTH, PLAYERHEIGHT, null);
		else g.drawImage(currentSprite.getCurrentFrame(), x + 16 - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset(), PLAYERWIDTH, PLAYERHEIGHT, null);
	}
	
	public void renderDebug(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(hitbox.x - handler.getGameCamera().getxOffset(), hitbox.y - handler.getGameCamera().getyOffset(), hitbox.width, hitbox.height);
	}
	
	//INPUT
	
	private void getInput() {
		up = handler.getKeyManager().up;
		down = handler.getKeyManager().down;
		left = handler.getKeyManager().left;
		right = handler.getKeyManager().right;
		space = handler.getKeyManager().space;
		leftArrow = handler.getKeyManager().leftArrow;
		rightArrow = handler.getKeyManager().rightArrow;
	}
	
	//MOVEMENT
	
	private void handleInput() {
		switch(currentState) {
		
			case st_standing:
				if(dir == Direction.RIGHT) currentSprite = playerIdleR;
				else currentSprite = playerIdleL;
				
				if(!collisionCheck(0, 1)) {
					ySpeed += 5 * GRAVITY;
					coyoteTimer = 0;
					currentState = State.st_falling;
					break;
				}
				
				if(right && !left) {
					dir = Direction.RIGHT;
					xSpeed += RUNACCEL;
					currentState = State.st_running;
				}
				else if(left && !right) {
					dir = Direction.LEFT;
					xSpeed -= RUNACCEL;
					currentState = State.st_running;
				}
				
				if(up) {
					ySpeed = -JUMPSPEED;
					currentState = State.st_jumping;
				}
				break;
				
			case st_running:
				if((currentSprite != playerTurningR && currentSprite != playerTurningL) || playerTurningR.getIndex() == 5 || playerTurningL.getIndex() == 5) {
					playerTurningR.setCurrentFrame(0);
					playerTurningL.setCurrentFrame(0);
					
					if(dir == Direction.RIGHT) currentSprite = playerRunningR;
					else currentSprite = playerRunningL;
				}
				
				if(!collisionCheck(0, 1)) {
					ySpeed += 5 * GRAVITY;
					coyoteTimer = 0;
					currentState = State.st_falling;
					break;
				}
				
				if((right && left) || (!right && !left)) {
					xSpeed *= RUNDECEL;
				}
				else if(right) {
					if(xSpeed < 0 && playerTurningL.getIndex() == 0) {
						playerRunningR.setCurrentFrame(0);
						currentSprite = playerTurningL;
					}
					dir = Direction.RIGHT;
					xSpeed += RUNACCEL;
				}
				else if(left) {
					if(xSpeed > 0 && playerTurningR.getIndex() == 0) {
						playerRunningL.setCurrentFrame(0);
						currentSprite = playerTurningR;
					}
					dir = Direction.LEFT;
					xSpeed -= RUNACCEL;
				}
				
				if(xSpeed > 0 && xSpeed < 0.25) xSpeed = 0;
				if(xSpeed < 0 && xSpeed > -0.25) xSpeed = 0;
				if(xSpeed == 0 && !right && !left) currentState = State.st_standing;
				
				if(xSpeed > RUNSPEED) xSpeed = RUNSPEED;
				if(xSpeed < -RUNSPEED) xSpeed = -RUNSPEED;
				
				if(up) {
					ySpeed = -JUMPSPEED;
					currentState = State.st_jumping;
				}
				break;
				
			case st_jumping:
				if(dir == Direction.RIGHT) currentSprite = playerFallingR;
				else currentSprite = playerFallingL;
				
				if((right && left) || (!right && !left)) {
					xSpeed *= AIRDECEL;
				}
				else if(right) {
					dir = Direction.RIGHT;
					xSpeed += AIRACCEL;
				}
				else if(left) {
					dir = Direction.LEFT;
					xSpeed -= AIRACCEL;
				}
				
				if(xSpeed > AIRSPEED) xSpeed = AIRSPEED;
				if(xSpeed < -AIRSPEED) xSpeed = -AIRSPEED;
				
				if(collisionCheck(0, -1)) {
					if(!collisionCheck(-hitbox.width / 2, -1))
						moveX(-hitbox.width / 2, null);
					else if(!collisionCheck(hitbox.width / 2, -1))
						moveX(hitbox.width / 2, null);
					else if(!collisionCheck((int) xSpeed, -1))
						moveX(xSpeed, null);
					else ySpeed = 1;
				}
				
				ySpeed += GRAVITY;
				
				if(ySpeed > 0) currentState = State.st_falling;
				
				if(ySpeed < 0 && collisionCheck(0, -1)) ySpeed = 1;
				
				if(leftArrow) {
					if(vaultCheck(VAULTRANGE, Direction.LEFT)) {
						dir = Direction.LEFT;
						ySpeed = 0;
						xSpeed = 0;
						vaultTime = 0;
						currentState = State.st_vaulting;
					}
				}
				else if(rightArrow) {
					if(vaultCheck(VAULTRANGE, Direction.RIGHT)) {
						dir = Direction.RIGHT;
						ySpeed = 0;
						xSpeed = 0;
						vaultTime = 0;
						currentState = State.st_vaulting;
					}
				}
				
				break;
				
			case st_falling:
				if(coyoteTimer < coyoteTime) {
					coyoteTimer++;
					if(up) {
						coyoteTimer = coyoteTime;
						ySpeed = -JUMPSPEED;
						currentState = State.st_jumping;
						break;
					}
				}
				
				if(dir == Direction.RIGHT) currentSprite = playerFallingR;
				else currentSprite = playerFallingL;
				
				if((right && left) || (!right && !left)) {
					xSpeed *= AIRDECEL;
				}
				else if(right) {
					dir = Direction.RIGHT;
					xSpeed += AIRACCEL;
				}
				else if(left) {
					dir = Direction.LEFT;
					xSpeed -= AIRACCEL;
				}
				
				if(xSpeed > AIRSPEED) xSpeed = AIRSPEED;
				if(xSpeed < -AIRSPEED) xSpeed = -AIRSPEED;
				
				if(collisionCheck(0, 1)) {
					ySpeed = 0;
					if(xSpeed == 0) currentState = State.st_standing;
					else currentState = State.st_running;
					break;
				}
				else {
					ySpeed += GRAVITY;
				}
				
				if(leftArrow) {
					if(vaultCheck(VAULTRANGE, Direction.LEFT)) {
						dir = Direction.LEFT;
						ySpeed = 0;
						xSpeed = 0;
						vaultTime = 0;
						currentState = State.st_vaulting;
					}
				}
				else if(rightArrow) {
					if(vaultCheck(VAULTRANGE, Direction.RIGHT)) {
						dir = Direction.RIGHT;
						ySpeed = 0;
						xSpeed = 0;
						vaultTime = 0;
						currentState = State.st_vaulting;
					}
				}
				
				break;
				
			case st_vaulting:
				if(dir == Direction.RIGHT) currentSprite = playerVaultingR;
				else currentSprite = playerVaultingL;
				
				vaultTime++;
				if(vaultTime > vaultTimeMax) {
					ySpeed -= VAULTSPEED;
					vaultSolid = null;
					currentState = State.st_flipping;
				}
				else ySpeed = 0;
				
				break;
				
			case st_flipping:
				if(dir == Direction.RIGHT) currentSprite = playerFlippingR;
				else currentSprite = playerFlippingL;
				
				if((right && left) || (!right && !left)) {
					xSpeed *= AIRDECEL;
				}
				else if(right) {
					dir = Direction.RIGHT;
					xSpeed += AIRACCEL;
				}
				else if(left) {
					dir = Direction.LEFT;
					xSpeed -= AIRACCEL;
				}
				
				if(xSpeed > AIRSPEED) xSpeed = AIRSPEED;
				if(xSpeed < -AIRSPEED) xSpeed = -AIRSPEED;
				
				if(ySpeed < 0 && collisionCheck(0, -1)) {
					if(!collisionCheck(-hitbox.width / 2, -1))
						moveX(-hitbox.width / 2, null);
					else if(!collisionCheck(hitbox.width / 2, -1))
						moveX(hitbox.width / 2, null);
					else ySpeed = 1;
				}
				
				if(collisionCheck(0, 1)) {
					ySpeed = 0;
					if(xSpeed == 0) currentState = State.st_standing;
					else currentState = State.st_running;
					break;
				}
				else {
					ySpeed += gravity;
				}
				
				if(ySpeed < 0 && collisionCheck(0, -1)) ySpeed = 1;
				
				break;
				
		}
	}
	
	public boolean vaultCheck(int range, Direction dir) {
		//Check for Solids
		for(Solid solid : handler.getWorld().getEntityManager().getSolids()) {
			if(solid.isVaultable()) {
				if(dir == Direction.RIGHT && solid.hitbox.contains(hitbox.x + hitbox.width + range, y + PLAYERHEIGHT/16)) {
					vaultSolid = solid;
					return true;
				}
				if(dir == Direction.LEFT && solid.hitbox.contains(hitbox.x - range, y + PLAYERHEIGHT/16)) {
					vaultSolid = solid;
					return true;
				}
			}
		}
		
		//Check for Tiles
		if(dir == Direction.RIGHT)
			return handler.getWorld().getTile((hitbox.x + hitbox.width + range) / Tile.TILEWIDTH, (y + PLAYERHEIGHT/16) / Tile.TILEHEIGHT).isVaultable();
		else
			return handler.getWorld().getTile((hitbox.x - range) / Tile.TILEWIDTH, (y + PLAYERHEIGHT/16) / Tile.TILEHEIGHT).isVaultable();
	}
	
	@Override
	public boolean isRiding(Solid solid) {
		if(solid.equals(vaultSolid)) return true;
		Rectangle tempHitbox = new Rectangle(hitbox.x, hitbox.y + 1, hitbox.width, hitbox.height);
		return tempHitbox.intersects(solid.hitbox);
	}
	
	@Override
	public void squish() {
		die();
	}
	
	private void checkDeath() {
		if(collideWithDanger(0, 0)) {
			die();
		}
	}
	
	public void die() {
		x = handler.getWorld().getSpawnX();
		y = handler.getWorld().getSpawnY();
	}
	
}
