package game.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Handler;
import game.gfx.Animation;
import game.gfx.Assets;

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
	public static final float GRAVITY = 1.2f;
	
	//MOVEMENT VALUES
	
	private float xSpeed = 0;
	private float ySpeed = 0;
	
	private boolean grounded = false;
	
	private boolean canJump = false;
	private boolean jumping = false;
	private long jumpTimer = 0;
	private float gravity = 0.9f;
	
	//INPUT
	
	@SuppressWarnings("unused")
	private boolean up, down, left, right;
	
	//ANIMATIONS
	
	private Animation playerIdleR = new Animation(80, Assets.playerIdleR);
	private Animation playerIdleL = new Animation(80, Assets.playerIdleL);
	private Animation playerRunningR = new Animation(50, Assets.playerRunningR);
	private Animation playerRunningL = new Animation(50, Assets.playerRunningL);
	private Animation playerJumpingR = new Animation(80, Assets.playerJumpingR);
	private Animation playerJumpingL = new Animation(80, Assets.playerJumpingL);
	private Animation playerFallingR = new Animation(50, Assets.playerFallingR);
	private Animation playerFallingL = new Animation(50, Assets.playerFallingL);
	
	
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
		g.drawImage(getCurrentSprite().getCurrentFrame(), x, y, PLAYERWIDTH, PLAYERHEIGHT, null);
	}
	
	//INPUT
	
	private void getInput() {
		up = handler.getKeyManager().up;
		down = handler.getKeyManager().down;
		left = handler.getKeyManager().left;
		right = handler.getKeyManager().right;
	}
	
	//MOVEMENT
	
	private void getSpeed() {
		
		//HORIZONTAL SPEED
		
		if(grounded) {
			
			if((right && left) || (!right && !left)) {
				xSpeed *= RUNDECEL;
			} else if(right && !left) {
				xSpeed += RUNACCEL;
			} else {
				xSpeed -= RUNACCEL;
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
		if(collideWithTile(0, -1) || collideWithSolid(0, -1)) ySpeed = 1;
		
		if(collideWithTile(0, 1) || collideWithSolid(0, 1)) {
			//Just landed
			if(grounded = false) xSpeed = 0;
			
			//Set grounded
			grounded = true;
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
		
		if(jumping) {
			if(up && System.currentTimeMillis() - jumpTimer < 200) gravity = 0;
			else gravity = GRAVITY;
			
			//Reached the peak of our jump
			if(ySpeed >= 0) jumping = false;
			
		} else gravity = GRAVITY;
		
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
	
	private boolean direction; //true = right, false = left
	
	private Animation getCurrentSprite() {
		if(xSpeed > 0) direction = true;
		if(xSpeed < 0) direction = false;
		
		if(grounded) {	
			if(xSpeed > 0) {
				return playerRunningR;
			} else if(xSpeed < 0) {
				return playerRunningL;
			} else {
				
				playerRunningR.setCurrentFrame(0);
				playerRunningL.setCurrentFrame(0);
				
				if(direction) return playerIdleR;
				else return playerIdleL;
			}
		} else {
			
			playerIdleR.setCurrentFrame(0);
			playerIdleL.setCurrentFrame(0);
			
			if(ySpeed > 0) {
				
				playerJumpingR.setCurrentFrame(0);
				playerJumpingL.setCurrentFrame(0);
				
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
