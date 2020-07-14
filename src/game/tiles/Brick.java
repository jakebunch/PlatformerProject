package game.tiles;

import java.awt.Graphics;

import game.gfx.Assets;

public class Brick extends Tile {

	public Brick(int id) {
		super(null, id);
	}
	
	@Override
	public void render(Graphics g, int x, int y, int dir) {
		g.drawImage(Assets.brickTiles[dir], x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
}
