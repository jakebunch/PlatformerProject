package game.worlds;

import java.awt.Graphics;

import game.Handler;
import game.Utils;
import game.entities.Box;
import game.entities.EntityManager;
import game.entities.Player;
import game.tiles.Tile;

public class World {

	private Handler handler;
	//private BufferedImage background;
	private int width, height;			//Width and Height in terms of tiles
	private int spawnX, spawnY;
	private int[][] tiles;
	private int[][] tileOrientation;
	
	private EntityManager entityManager;
	
	public World(Handler handler, String path) {
		this.handler = handler;
		entityManager = new EntityManager(handler, new Player(handler, 0, 0));
		//entityManager.addEntity(new Box(handler, 50, 350, 128, 64));
		
		loadWorld(path);
		
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
	}
	
	public void tick() {
		entityManager.tick();
	}
	
	public void render(Graphics g) {
		int xStart = Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
		int xEnd = Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);
		int yStart = Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
		int yEnd = Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);
		
		//g.drawImage(background, 0, 0, 1024, 1024, null);
		entityManager.render(g);
		
		for(int y = yStart; y < yEnd; y++) {
			for(int x = xStart; x < xEnd; x++) {
				getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()),
					(int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()), tileOrientation[x][y]);
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tile.dirt;
		
		Tile t = Tile.tiles[tiles[x][y]];
		if(t == null)
			return Tile.dirt;
		return t;
	}
	
	private void loadWorld(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		
		//background = ImageLoader.loadImage(tokens[0]);
		width = Utils.parseInt(tokens[1]);
		height = Utils.parseInt(tokens[2]);
		spawnX = Utils.parseInt(tokens[3]);
		spawnY = Utils.parseInt(tokens[4]);
		
		tiles = new int[width][height];
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 5]);
			}
		}
		
		tileOrientation = new int[width][height];
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(getTile(x, y - 1).isSolid()) {
					if(getTile(x, y + 1).isSolid()) {
						if(getTile(x - 1, y).isSolid()) {
							if(getTile(x + 1, y).isSolid()) {
								if(getTile(x + 1, y + 1).isSolid()) {
									if(getTile(x + 1, y - 1).isSolid()) {
										if(getTile(x - 1, y + 1).isSolid()) {
											if(getTile(x - 1, y - 1).isSolid()) tileOrientation[x][y] = Utils.randomInt(22, 27);
											else tileOrientation[x][y] = 10;
										} else tileOrientation[x][y] = 6;
									} else tileOrientation[x][y] = 9;
								} else tileOrientation[x][y] = 5;
							} else tileOrientation[x][y] = 7 + 4 * Utils.randomInt(0, 1);
						} else {
							if(getTile(x + 1, y).isSolid()) tileOrientation[x][y] = 4 + 4 * Utils.randomInt(0, 1);
							else tileOrientation[x][y] = 21;
						}
					} else {
						if(getTile(x - 1, y).isSolid()) {
							if(getTile(x + 1, y).isSolid()) tileOrientation[x][y] = Utils.randomInt(13, 14);
							else tileOrientation[x][y] = 15;
						} else {
							if(getTile(x + 1, y).isSolid()) tileOrientation[x][y] = 12;
							else tileOrientation[x][y] = 19;
						}
					}
				} else {
					if(getTile(x, y + 1).isSolid()) {
						if(getTile(x - 1, y).isSolid()) {
							if(getTile(x + 1, y).isSolid()) tileOrientation[x][y] = Utils.randomInt(1, 2);
							else tileOrientation[x][y] = 3;
						} else {
							if(getTile(x + 1, y).isSolid()) tileOrientation[x][y] = 0;
							else tileOrientation[x][y] = 17;
						}
					} else {
						if(getTile(x - 1, y).isSolid()) {
							if(getTile(x + 1, y).isSolid()) tileOrientation[x][y] = 20;
							else tileOrientation[x][y] = 18;
						} else {
							if(getTile(x + 1, y).isSolid()) tileOrientation[x][y] = 16;
							else tileOrientation[x][y] = 27;
						}
					}
				}
			}
		}
	}

	
	//GETTERS
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getSpawnX() {
		return spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}
