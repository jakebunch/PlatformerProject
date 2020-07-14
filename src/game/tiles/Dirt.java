package game.tiles;

import java.awt.Graphics;

import game.gfx.Assets;

public class Dirt extends Tile {

	public Dirt(int id) {
		super(null, id);
	}
	
	@Override
	public void render(Graphics g, int x, int y, int dir) {
		g.drawImage(Assets.dirtTiles[dir], x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

}
