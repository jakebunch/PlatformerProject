package game.entities;

import java.awt.Graphics;

import game.Handler;
import game.Utils;
import game.gfx.Assets;

public class Dust extends Entity {

	private int count = 0;
	
	public Dust(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);
	}
	
	@Override
	public void tick() {
		//if(count >= 3) active = false;
		count++;
	}
	
	@Override
	public void render(Graphics g) {
		g.fillRect(x, y, width, height);
		g.drawImage(Assets.dustParticles[4 * count + Utils.randomInt(0, 3)], x, y, width, height, null);
	}
	
}
