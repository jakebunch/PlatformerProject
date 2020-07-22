package game.tiles;

import java.awt.image.BufferedImage;

public class Brick extends TileSet {

	public Brick(BufferedImage[] texture, int id) {
		super(null, id);
		this.texture = texture;
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
}
