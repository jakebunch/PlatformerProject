package game.entities.solids;

import java.awt.Graphics;

import game.Handler;
import game.entities.Solid;
import game.gfx.Assets;
import game.tiles.Tile;

public class Platform extends Solid {

	public Platform(Handler handler, int x, int y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);
	}

	@Override
	public void tick() {
		hitbox.x = x;
		hitbox.y = y;
		
		if(handler.getWorld().getEntityManager().getPlayer().hitbox.y + handler.getWorld().getEntityManager().getPlayer().hitbox.height > hitbox.y) collidable = false;
		else collidable = true;
	}

	@Override
	public void render(Graphics g) {
		if(handler.getWorld().getTile((x - Tile.TILEWIDTH) / Tile.TILEWIDTH, y / Tile.TILEHEIGHT).isSolid())
			g.drawImage(Assets.platform[0], x - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset(), width, height, null);
		
		else if(handler.getWorld().getTile((x + Tile.TILEWIDTH) / Tile.TILEWIDTH, y / Tile.TILEHEIGHT).isSolid())
			g.drawImage(Assets.platform[2], x - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset(), width, height, null);
		
		else g.drawImage(Assets.platform[1], x - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset(), width, height, null);
	}

}
