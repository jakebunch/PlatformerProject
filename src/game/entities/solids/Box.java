package game.entities.solids;

import java.awt.Color;
import java.awt.Graphics;

import game.Handler;
import game.entities.Solid;
import game.tiles.Tile;

public class Box extends Solid {

	public static final int BOXWIDTH = 128;
	public static final int BOXHEIGHT = 64;
	
	public Box(Handler handler, int x, int y) {
		super(handler, x, y, BOXWIDTH, BOXHEIGHT);
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
		g.fillRect(hitbox.x - handler.getGameCamera().getxOffset(), hitbox.y - handler.getGameCamera().getyOffset(), hitbox.width, hitbox.height);
	}
	
	@Override
	public boolean isVaultable() {
		return true;
	}

}
