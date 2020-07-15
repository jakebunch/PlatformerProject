package game.entities;

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
	
	public static final float JUMPSPEED = 12;
	public static final float JUMPWINDOW = 200;
	public static final float GRAVITY = 1.2f;
	
	public static final float VAULTSPEED = 30;
	public static final float VAULTDRIFT = 6;
	public static final float VAULTTIME = 200;
	public static final int VAULTRANGE = 64;
	
	//MOVEMENT VALUES
	
	private float xSpeed = 0;
	private float ySpeed = 0;
	
	private boolean grounded = false;
	private boolean direction; //true = right, false = left
	
	private boolean canJump = false;
	private boolean jumping = false;
	private long jumpTimer = 0;
	private float gravity = 0.9f;
	
	private boolean canVault = true;
	private boolean vaulting = false;
	private long vaultTimer = 0;
	
	//INPUT
	
	@SuppressWarnings("unused")
	private boolean up, down, left, right, space;
	
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
	
	private Animation lastSprite = playerIdleR;
	
	
	//CLASS
	
	public Player(Handler handler, int x, int y) {
		super(handler, x, y, PLAYERWIDTH, PLAYERHEIGHT);
		
		hitbox.width = 63;
		hitbox.height = 95;
	}

	@Override
	public void tick() {
		
		//Animation
		getCurrentSprite().tick();
		
		//Adjust speed according to input
		getInput();
		getSpeed();
		
		//Make sure hitbox is aligned
		hitbox.x = x + 32;
		hitbox.y = y + 32;
		
		//Move according to our speed
		moveX(xSpeed, null);
		moveY(ySpeed, null);
		
	}
	
	@Override
	public void render(Graphics g) {
		lastSprite = getCurrentSprite();
		
		if(vaulting) {
			if(direction) g.drawImage(Assets.staff, x + PLAYERWIDTH/2, y + PLAYERHEIGHT/16, 112, 24, null);
			else g.drawImage(Assets.staff, x - PLAYERWIDTH/2, y + PLAYERHEIGHT/16, 112, 24, null);
		}
		
		if(direction) g.drawImage(lastSprite.getCurrentFrame(), x - 16, y, PLAYERWIDTH, PLAYERHEIGHT, null);
		else g.drawImage(lastSprite.getCurrentFrame(), x + 16, y, PLAYERWIDTH, PLAYERHEIGHT, null);
	}
	
	//INPUT
	
	private void getInput() {
		up = handler.getKeyManager().up;
		down = handler.getKeyManager().down;
		left = handler.getKeyManager().left;
		right = handler.getKeyManager().right;
		space = handler.getKeyManager().space;
	}
	
	//MOVEMENT
	
	private void getSpeed() {
		
		//HORIZONTAL SPEED
		
		if(grounded) {
			
			if((right && left) || (!right && !left)) {
				xSpeed *= RUNDECEL;
			} else if(right && !left) {
				if(!vaulting) xSpeed += RUNACCEL;
				else xSpeed += VAULTDRIFT;
			} else {
				if(!vaulting) xSpeed -= RUNACCEL;
				else xSpeed -= VAULTDRIFT;
			}
			
			if(xSpeed > RUNSPEED) xSpeed = RUNSPEED;
			if(xSpeed < -RUNSPEED) xSpeed = -RUNSPEED;
			
		} else {
			
			if((right && left) || (!right && !left)) {
				xSpeed *= AIRDECEL;
			} else if(right && !left) {
				xSpeed += AIRACCEL;
			} else {
				xSpeed -= AIRACCEL;
			}
			
			if(xSpeed > AIRSPEED) xSpeed = AIRSPEED;
			if(xSpeed < -AIRSPEED) xSpeed = -AIRSPEED;
		}
		
		if(xSpeed > 0 && xSpeed < 0.25) xSpeed = 0;
		if(xSpeed < 0 && xSpeed > -0.25) xSpeed = 0;
		
		
		//VERTICAL SPEED
		
		//Ceiling bonk
		if(ySpeed <= 0 && collisionCheck(0, -1)) {
			
			if(!collisionCheck(-hitbox.width / 2, -1))
				moveX(-hitbox.width / 2, null);
			else if(!collisionCheck(hitbox.width / 2, -1))
				moveX(hitbox.width / 2, null);
			else ySpeed = 1;
		}
		
		if(collisionCheck(0, 1)) {
			//Just landed
			if(grounded = false) xSpeed = 0;
			
			//Set grounded
			grounded = true;
			canVault = true;
			vaulting = false;
			ySpeed = 0;
			
		} else {
			grounded = false;
			ySpeed += gravity;
		}
		
		if(up && grounded && canJump) {
			grounded = false;
			canJump = false;
			jumping = true;
			ySpeed = -JUMPSPEED;
			jumpTimer = System.currentTimeMillis();
		}
		
		if(!up && !canJump) canJump = true;
		
		if(jumping && !vaulting) {
			if(up && System.currentTimeMillis() - jumpTimer < JUMPWINDOW) gravity = 0;
			else gravity = GRAVITY;
			
			//Reached the peak of our jump
			if(ySpeed >= 0) jumping = false;
			
		} else gravity = GRAVITY;
		
		if(!grounded && canVault && space) {
			if(direction) {
				if(vaultCheck(VAULTRANGE)) {
					
					//Vault Right
					canVault = false;
					vaulting = true;
					xSpeed = 0;
					vaultTimer = System.currentTimeMillis();
				}
			} else {
				if(vaultCheck(VAULTRANGE)) {
					
					//Vault Left
					canVault = false;
					vaulting = true;
					xSpeed = 0;
					vaultTimer = System.currentTimeMillis();
				}
			}
		}
		
		if(vaulting) {
			if(System.currentTimeMillis() - vaultTimer < VAULTTIME) ySpeed = 0;
			else {
				ySpeed -= VAULTSPEED;
				vaulting = false;
			}
		}
		
	}
	
	public boolean vaultCheck(int range) {
		if(direction) {
			//Check for Solids
			for(Solid solid : handler.getWorld().getEntityManager().getSolids()) {
				if(solid.isVaultable())
					return true;
			}
			
			//Check for Tiles
			return handler.getWorld().getTile((hitbox.x + hitbox.width + range) / Tile.TILEWIDTH, (y + PLAYERHEIGHT/16) / Tile.TILEHEIGHT).isVaultable();
			
		} else {
			//Check for Solids
			for(Solid solid : handler.getWorld().getEntityManager().getSolids()) {
				if(solid.isVaultable())
					return true;
			}
			
			//Check for Tiles
			return handler.getWorld().getTile((hitbox.x - range) / Tile.TILEWIDTH, (y + PLAYERHEIGHT/16) / Tile.TILEHEIGHT).isVaultable();
		}
	}
	
	@Override
	public boolean isRiding(Solid solid) {
		return collideWithSolid(0, 1);
	}
	
	@Override
	public void squish() {
		x = handler.getWorld().getSpawnX();
		y = handler.getWorld().getSpawnY();
	}
	
	//ANIMATION
	
	private Animation getCurrentSprite() {
		
		if(!vaulting) {
			if(xSpeed > 0) direction = true;
			if(xSpeed < 0) direction = false;
		}
		
		if(grounded) {	
			if(xSpeed > 0) {
				return playerRunningR;
			} else if(xSpeed < 0) {
				return playerRunningL;
			} else {
				if(direction) return playerIdleR;
				else return playerIdleL;
			}
		} else {
			
			if(space && canVault) {
				if(playerPreVaultR.getIndex() == 9 || playerPreVaultL.getIndex() == 9) {
					playerPreVaultR.setCurrentFrame(0);
					playerPreVaultL.setCurrentFrame(0);
					canVault = false;
					if(direction) return playerFallingR;
					else return playerFallingL;
				}
				
				if(direction) return playerPreVaultR;
				else return playerPreVaultL;
			} else {
				playerPreVaultR.setCurrentFrame(0);
				playerPreVaultL.setCurrentFrame(0);
			}
			
			if(vaulting) {
				if(direction) return playerVaultingR;
				else return playerVaultingL;
			}
			
			if(ySpeed > 0) {		
				if(direction) return playerFallingR;
				else return playerFallingL;
			} else {
				if(direction) {
					return playerJumpingR;
				} else {
					return playerJumpingL;
				}
			}
		}
	}
	
}
