package game.entities.solids;

import java.awt.Graphics;

import game.Handler;
import game.entities.Solid;
import game.gfx.Assets;
import game.tiles.Tile;

public class Spike extends Solid {
	
	public static final int SPIKEWIDTH = Tile.TILEWIDTH, SPIKEHEIGHT = Tile.TILEHEIGHT;

	public Spike(Handler handler, int x, int y) {
		super(handler, x, y, SPIKEWIDTH, SPIKEHEIGHT);
		
		collidable = false;
	}

	@Override
	public void tick() {
		if(handler.getWorld().getTile(x / Tile.TILEWIDTH, (y + SPIKEHEIGHT) / Tile.TILEHEIGHT).isSolid()) {
			hitbox.x = x + SPIKEWIDTH / 8;
			hitbox.y = y + SPIKEHEIGHT / 2 + SPIKEHEIGHT / 4;
			hitbox.width = SPIKEWIDTH - SPIKEWIDTH / 4;
			hitbox.height = SPIKEHEIGHT / 4;
		}
		
		else if(handler.getWorld().getTile((x - SPIKEWIDTH) / Tile.TILEWIDTH, y / Tile.TILEHEIGHT).isSolid()) {
			hitbox.x = x;
			hitbox.y = y + SPIKEHEIGHT / 8;
			hitbox.width = SPIKEWIDTH / 4;
			hitbox.height = SPIKEHEIGHT - SPIKEHEIGHT / 4;
		}
		
		else if(handler.getWorld().getTile((x + SPIKEWIDTH) / Tile.TILEWIDTH, y / Tile.TILEHEIGHT).isSolid()) {
			hitbox.x = x + SPIKEWIDTH / 2 + SPIKEWIDTH / 4;
			hitbox.y = y + SPIKEHEIGHT / 8;
			hitbox.width = SPIKEWIDTH / 4;
			hitbox.height = SPIKEHEIGHT - SPIKEHEIGHT / 4;
		}
		
		else if(handler.getWorld().getTile(x / Tile.TILEWIDTH, (y - SPIKEHEIGHT) / Tile.TILEHEIGHT).isSolid()) {
			hitbox.x = x + SPIKEWIDTH / 8;
			hitbox.y = y;
			hitbox.width = SPIKEWIDTH - SPIKEWIDTH / 4;
			hitbox.height = SPIKEHEIGHT / 4;
		}
	}

	@Override
	public void render(Graphics g) {
		
		if(handler.getWorld().getTile(x / Tile.TILEWIDTH, (y + SPIKEHEIGHT) / Tile.TILEHEIGHT).isSolid())
			g.drawImage(Assets.spikes[0], x - handler.getGameCamera().getxOffset(), y + (SPIKEWIDTH / 8) - handler.getGameCamera().getyOffset(), width, height, null);
		
		else if(handler.getWorld().getTile((x - SPIKEWIDTH) / Tile.TILEWIDTH, y / Tile.TILEHEIGHT).isSolid())
			g.drawImage(Assets.spikes[1], x - (SPIKEWIDTH / 8) - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset(), width, height, null);
		
		else if(handler.getWorld().getTile((x + SPIKEWIDTH) / Tile.TILEWIDTH, y / Tile.TILEHEIGHT).isSolid())
			g.drawImage(Assets.spikes[2], x + (SPIKEWIDTH / 8) - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset(), width, height, null);
		
		else if(handler.getWorld().getTile(x / Tile.TILEWIDTH, (y - SPIKEHEIGHT) / Tile.TILEHEIGHT).isSolid())
			g.drawImage(Assets.spikes[3], x - handler.getGameCamera().getxOffset(), y - (SPIKEWIDTH / 8) - handler.getGameCamera().getyOffset(), width, height, null);
	}
	
	@Override
	public boolean isDangerous() {
		return true;
	}

}
