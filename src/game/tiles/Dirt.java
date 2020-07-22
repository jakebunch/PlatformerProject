package game.tiles;

import java.awt.image.BufferedImage;

public class Dirt extends TileSet {

	public Dirt(BufferedImage[] texture, int id) {
		super(null, id);
		this.texture = texture;
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
	@Override
	public boolean isVaultable() {
		return true;
	}

}
