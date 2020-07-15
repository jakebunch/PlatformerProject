package game.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	//PLAYER
	public static BufferedImage[] playerIdleR;
	public static BufferedImage[] playerIdleL;
	public static BufferedImage[] playerRunningR;
	public static BufferedImage[] playerRunningL;
	public static BufferedImage[] playerJumpingR;
	public static BufferedImage[] playerJumpingL;
	public static BufferedImage[] playerFallingR;
	public static BufferedImage[] playerFallingL;
	public static BufferedImage[] playerPreVaultR;
	public static BufferedImage[] playerPreVaultL;
	public static BufferedImage[] playerVaultingR;
	public static BufferedImage[] playerVaultingL;
	
	//OBJECTS
	public static BufferedImage staff;
	
	//TILES
	public static BufferedImage[] dirtTiles;
	public static BufferedImage[] brickTiles;
	
	public static void init() {
		
		//PLAYER
		SpriteSheet sh_playerIdleR = new SpriteSheet(ImageLoader.loadImage("/textures/player.png"));
		SpriteSheet sh_playerIdleL = new SpriteSheet(SpriteSheet.flipHorizontally(ImageLoader.loadImage("/textures/player.png")));
		SpriteSheet sh_playerRunningR = new SpriteSheet(ImageLoader.loadImage("/textures/playerRunning.png"));
		SpriteSheet sh_playerRunningL = new SpriteSheet(SpriteSheet.flipHorizontally(ImageLoader.loadImage("/textures/playerRunning.png")));
		SpriteSheet sh_playerJumpingR = new SpriteSheet(ImageLoader.loadImage("/textures/playerJumping.png"));
		SpriteSheet sh_playerJumpingL = new SpriteSheet(SpriteSheet.flipHorizontally(ImageLoader.loadImage("/textures/playerJumping.png")));
		SpriteSheet sh_playerFallingR = new SpriteSheet(ImageLoader.loadImage("/textures/playerFalling.png"));
		SpriteSheet sh_playerFallingL = new SpriteSheet(SpriteSheet.flipHorizontally(ImageLoader.loadImage("/textures/playerFalling.png")));
		SpriteSheet sh_playerPreVaultR = new SpriteSheet(ImageLoader.loadImage("/textures/playerPreVault.png"));
		SpriteSheet sh_playerPreVaultL = new SpriteSheet(SpriteSheet.flipHorizontally(ImageLoader.loadImage("/textures/playerPreVault.png")));
		SpriteSheet sh_playerVaultingR = new SpriteSheet(ImageLoader.loadImage("/textures/playerVaulting.png"));
		SpriteSheet sh_playerVaultingL = new SpriteSheet(SpriteSheet.flipHorizontally(ImageLoader.loadImage("/textures/playerVaulting.png")));
		
		playerIdleR = sh_playerIdleR.cropSheet(34, 1, 16, 16);
		playerIdleL = sh_playerIdleL.cropSheet(34, 1, 16, 16);
		playerRunningR = sh_playerRunningR.cropSheet(12, 1, 16, 16);
		playerRunningL = sh_playerRunningL.cropSheet(12, 1, 16, 16);
		playerJumpingR = sh_playerJumpingR.cropSheet(6, 1, 16, 16);
		playerJumpingL = sh_playerJumpingL.cropSheet(6, 1, 16, 16);
		playerFallingR = sh_playerFallingR.cropSheet(1, 1, 16, 16);
		playerFallingL = sh_playerFallingL.cropSheet(1, 1, 16, 16);
		playerPreVaultR = sh_playerPreVaultR.cropSheet(10, 1, 16, 16);
		playerPreVaultL = sh_playerPreVaultL.cropSheet(10, 1, 16, 16);
		playerVaultingR = sh_playerVaultingR.cropSheet(4, 1, 16, 16);
		playerVaultingL = sh_playerVaultingL.cropSheet(4, 1, 16, 16);
		
		//OBJECTS
		staff = ImageLoader.loadImage("/textures/staff.png");
		
		//TILES
		SpriteSheet sh_dirt = new SpriteSheet(ImageLoader.loadImage("/textures/dirt.png"));
		SpriteSheet sh_bricks = new SpriteSheet(ImageLoader.loadImage("/textures/bricks.png"));
		
		dirtTiles = sh_dirt.cropSheet(7, 4, 8, 8);
		brickTiles = sh_bricks.cropSheet(7, 4, 8, 8);
		
	}
	
}
