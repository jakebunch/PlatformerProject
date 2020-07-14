package game.entities;

import java.awt.Color;
import java.awt.Graphics;

import game.Handler;
import game.tiles.Tile;

public class Box extends Solid {

	public Box(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
	}

	private float speed = 2;
	
	@Override
	public void tick() {
		//Make sure hitbox is aligned
		hitbox.x = x;
		hitbox.y = y;
		
		if(hitbox.x + hitbox.width > handler.getWorld().getWidth() * Tile.TILEWIDTH)
			speed *= -1;
		
		if(hitbox.x < 0)
			speed *= -1;
		
		move(speed, 0);
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	}

}
