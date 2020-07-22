package game.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import game.Handler;
import game.tiles.Tile;

public abstract class Entity {

	protected Handler handler;
	protected int x, y;
	protected int width, height;
	protected boolean active = true;
	public Rectangle hitbox;
	
	public Entity(Handler handler, int x, int y, int width, int height) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		hitbox = new Rectangle(x, y, width, height);
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public boolean collisionCheck(int xOffset, int yOffset) {
		return collideWithTile(xOffset, yOffset) || collideWithSolid(xOffset, yOffset);
	}
	
	public boolean collideWithTile(int x, int y) {
		return handler.getWorld().getTile((hitbox.x + x) / Tile.TILEWIDTH, (hitbox.y + y) / Tile.TILEHEIGHT).isSolid() ||
				handler.getWorld().getTile((hitbox.x + x) / Tile.TILEWIDTH, (hitbox.y + hitbox.height + y) / Tile.TILEHEIGHT).isSolid() ||
				handler.getWorld().getTile((hitbox.x + hitbox.width + x) / Tile.TILEWIDTH, (hitbox.y + y) / Tile.TILEHEIGHT).isSolid() ||
				handler.getWorld().getTile((hitbox.x + hitbox.width + x) / Tile.TILEWIDTH, (hitbox.y + hitbox.height + y) / Tile.TILEHEIGHT).isSolid() ||
				handler.getWorld().getTile((hitbox.x + (hitbox.width / 2) + x) / Tile.TILEWIDTH, (hitbox.y + y) / Tile.TILEHEIGHT).isSolid() ||
				handler.getWorld().getTile((hitbox.x + (hitbox.width / 2) + x) / Tile.TILEWIDTH, (hitbox.y + hitbox.height + y) / Tile.TILEHEIGHT).isSolid() ||
				handler.getWorld().getTile((hitbox.x + x) / Tile.TILEWIDTH, (hitbox.y + (hitbox.height / 2) + y) / Tile.TILEHEIGHT).isSolid() ||
				handler.getWorld().getTile((hitbox.x + hitbox.width + x) / Tile.TILEWIDTH, (hitbox.y + (hitbox.height / 2) + y) / Tile.TILEHEIGHT).isSolid();
	}

	public boolean collideWithSolid(int x, int y) {
		boolean solidCollision = false;
		Rectangle temphitbox = new Rectangle(hitbox.x + x, hitbox.y + y, hitbox.width, hitbox.height);
		for(Solid solid : handler.getWorld().getEntityManager().getSolids()) {
			if(solid.collidable)
				solidCollision |= temphitbox.intersects(solid.hitbox);
		}
		return solidCollision;
	}
	
	public boolean collideWithDanger(int x, int y) {
		boolean solidCollision = false;
		Rectangle temphitbox = new Rectangle(hitbox.x + x, hitbox.y + y, hitbox.width, hitbox.height);
		for(Solid solid : handler.getWorld().getEntityManager().getSolids()) {
			if(solid.isDangerous())
				solidCollision |= temphitbox.intersects(solid.hitbox);
		}
		return solidCollision;
	}
	
	//GETTERS AND SETTERS
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
}
