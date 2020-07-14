package game.gfx;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class SpriteSheet {

private BufferedImage sheet;
	
	public SpriteSheet(BufferedImage sheet) {
		this.sheet = sheet;
	}
	
	public BufferedImage crop(int x, int y, int width, int height) {
		return sheet.getSubimage(x, y, width, height);
	}
	
	public BufferedImage[] cropSheet(int rows, int cols, int width, int height) {
		BufferedImage[] tileSet = new BufferedImage[rows * cols];
		int idx = 0;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				tileSet[idx] = crop(j * width, i * height, width, height);
				idx++;
			}
		}
		return tileSet;
	}
	
	public static BufferedImage flipHorizontally(BufferedImage image) {
		
		BufferedImage flipped = image;
		
	    AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
	    tx.translate(-flipped.getWidth(null), 0);
	    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	    flipped = op.filter(flipped, null);
	    
	    return flipped;
	}
	
}
