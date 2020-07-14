package game.states;

import java.awt.Graphics;

import game.worlds.World;
import game.Handler;

public class GameState extends State {
	
	private World testworld;
	
	public GameState(Handler handler) {
		super(handler);
		
		testworld = new World(handler, "res/worlds/world1.txt");
		handler.setWorld(testworld);
	}
	
	@Override
	public void tick() {
		testworld.tick();
	}
	
	@Override
	public void render(Graphics g) {
		testworld.render(g);
	}

}
