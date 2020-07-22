package game.entities;

import java.awt.Graphics;
import java.util.ArrayList;

import game.Handler;

public abstract class Solid extends Entity {

	protected boolean collidable = true;
	
	public Solid(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
	}

	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	//MOVEMENT
	
	private float xRemainder = 0;
	private float yRemainder = 0;
	
	public void move(float xAmount, float yAmount) {
		xRemainder += xAmount;
		yRemainder += yAmount;
		
		int moveX = Math.round(xRemainder);
		int moveY = Math.round(yRemainder);
		
		if(moveX != 0 || moveY != 0) {
			
			ArrayList<Actor> riding = getRidingActors();
			
			//Make this Solid non-collidable for Actors, so that Actors moved by it don't get stuck on it
			collidable = false;
			
			if(moveX != 0) {
				xRemainder -= moveX;
				x += moveX;
				
				if(moveX > 0) {
					//Moving to the right
					for(Actor actor : handler.getWorld().getEntityManager().getActors()) {
						if(overlapCheck(actor)) {
							//Push actor right
							actor.moveX((hitbox.x + hitbox.width) - actor.hitbox.x, actor);
						} else if(riding.contains(actor)) {
							//Carry actor right
							actor.moveX(moveX, null);
						}
					}
					
				} else {
					//Moving to the left
					for(Actor actor : handler.getWorld().getEntityManager().getActors()) {
						if(overlapCheck(actor)) {
							//Push actor left
							actor.moveX(hitbox.x - (actor.hitbox.x + actor.hitbox.width), actor);
						} else if(riding.contains(actor)) {
							//Carry actor left
							actor.moveX(moveX, null);
						}
					}
				}
			}
			
			if(moveY != 0) {
				yRemainder -= moveY;
				y += moveY;
				
				if(moveY > 0) {
					//Moving down
					for(Actor actor : handler.getWorld().getEntityManager().getActors()) {
						if(overlapCheck(actor)) {
							//Push actor down
							actor.moveY((hitbox.y + hitbox.height) - actor.hitbox.y, actor);
						} else if(riding.contains(actor)) {
							//Carry actor down
							actor.moveY(moveY, null);
						}
					}
					
				} else {
					//Moving up
					for(Actor actor : handler.getWorld().getEntityManager().getActors()) {
						if(overlapCheck(actor)) {
							//Push actor up
							actor.moveY(hitbox.y - (actor.hitbox.y + actor.hitbox.height), actor);
						} else if(riding.contains(actor)) {
							//Carry actor up
							actor.moveY(moveY, null);
						}
					}
				}
			}	
			
			//Re-enable collisions for this Solid
			collidable = true;
		}
	}
	
	public boolean isVaultable() {
		return false;
	}
	
	public boolean isDangerous() {
		return false;
	}
	
	private ArrayList<Actor> getRidingActors() {
		ArrayList<Actor> riding = new ArrayList<>();
		for(Actor actor : handler.getWorld().getEntityManager().getActors()) {
			if(actor.isRiding(this))
				riding.add(actor);
		}
		return riding;
	}
	
	private boolean overlapCheck(Actor actor) {
		return actor.hitbox.intersects(hitbox);
	}
	
}
