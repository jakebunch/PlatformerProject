package game.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class TileSet extends Tile {

	BufferedImage[] texture;
	
	public TileSet(BufferedImage[] texture, int id) {
		super(null, id);
		this.texture = texture;
	}

	@Override
	public void render(Graphics g, int x, int y, int dir) {
		g.drawImage(texture[dir], x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
}
