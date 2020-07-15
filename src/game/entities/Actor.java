package game.entities;

import java.awt.Graphics;

import game.Handler;

public abstract class Actor extends Entity implements Squish {

	public Actor(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
	}

	public abstract void tick();

	public abstract void render(Graphics g);
	
	
	//MOVE HORIZONTALLY
	
	private float xRemainder = 0;
	
	public void moveX(float amount, Squish onCollide) {
		xRemainder += amount;
		int move = Math.round(xRemainder);
		
		if(move != 0) {
			xRemainder -= move;
			int sign = Integer.signum(move);
			
			while(move != 0) {
				if(!collisionCheck(sign, 0)) {
					x += sign;
					hitbox.x += sign;
					move -= sign;
				} else {
					if(onCollide != null)
						onCollide.squish();
					break;
				}
			}
		}
	}
	
	//MOVE VERTICALLY
	
	private float yRemainder = 0;
	
	public void moveY(float amount, Squish onCollide) {
		yRemainder += amount;
		int move = Math.round(yRemainder);
		
		if(move != 0) {
			yRemainder -= move;
			int sign = Integer.signum(move);
			
			while(move != 0) {
				if(!collisionCheck(0, sign)) {
					y += sign;
					hitbox.y += sign;
					move -= sign;
				} else {
					if(onCollide != null)
						onCollide.squish();
					break;
				}
			}
		}
	}
	
	//INTERACTION WITH SOLIDS
	
	public abstract boolean isRiding(Solid solid);
	
	@Override
	public abstract void squish();
	
}
