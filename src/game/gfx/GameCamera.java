package game.gfx;

import game.Handler;
import game.entities.Entity;
import game.tiles.Tile;

public class GameCamera {

	private Handler handler;
	private int xOffset, yOffset;
	
	public GameCamera(Handler handler, int xOffset, int yOffset) {
		this.handler = handler;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void checkBlankSpace() {
		if(xOffset < 0) {
			xOffset = 0;
		} else if(xOffset > handler.getWorld().getWidth() * Tile.TILEWIDTH - handler.getWidth()) {
			xOffset = handler.getWorld().getWidth() * Tile.TILEWIDTH;
		}
		
		if(yOffset < 0) {
			yOffset = 0;
		} else if(yOffset > handler.getWorld().getHeight() * Tile.TILEHEIGHT - handler.getHeight()) {
			yOffset = handler.getWorld().getHeight() * Tile.TILEHEIGHT;
		}
	}
	
	public void centerOnEntity(Entity e) {
		xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2;
		yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight() / 2;
		checkBlankSpace();
	}
	
	public void move(int xAmount, int yAmount) {
		xOffset += xAmount;
		yOffset += yAmount;
		checkBlankSpace();
	}
	
	
	//GETTERS AND SETTERS
	
	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}
}
