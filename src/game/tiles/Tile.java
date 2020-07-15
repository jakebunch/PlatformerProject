package game.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {

	//STATIC STUFF
	
	//Main Tile Array
	public static final int TILEWIDTH = 64, TILEHEIGHT = 64;
	public static Tile[] tiles = new Tile[256];
	
	public static Tile noTile = new NoTile(0);
	public static Tile dirt = new Dirt(1);
	public static Tile bricks = new Brick(2);
	
	
	//CLASS
	
	protected BufferedImage texture;
	protected final int id;
	
	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g, int x, int y, int dir) {
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	public boolean isSolid() {
		return false;
	}
	
	public boolean isDangerous() {
		return false;
	}
	
	public boolean isPlatform() {
		return false;
	}
	
	public boolean isVaultable() {
		return false;
	}
	
	public int getId() {
		return id;
	}
	
}
