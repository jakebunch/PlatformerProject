package game.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import game.Handler;

public class EntityManager {

	private Handler handler;
	private Player player;
	private ArrayList<Entity> entities;
	private ArrayList<Solid> solids;
	private ArrayList<Actor> actors;
	
	public EntityManager(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<Entity>();
		solids = new ArrayList<Solid>();
		actors = new ArrayList<Actor>();
		addEntity(player);
	}
	
	public void tick() {
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()) {
			Entity e = it.next();
			e.tick();
			if(!e.isActive()) {
				it.remove();
				if(e instanceof Solid) {
					solids.remove(e);
				}
				if(e instanceof Actor) {
					actors.remove(e);
				}
			}
		}
	}
	
	public void render(Graphics g) {
		for(Entity e : entities) {
			e.render(g);
		}
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
		if(e instanceof Solid) {
			solids.add((Solid) e);
		}
		if(e instanceof Actor) {
			actors.add((Actor) e);
		}
	}

	
	//GETTERS

	public Handler getHandler() {
		return handler;
	}
	
	public Player getPlayer() {
		return player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	public ArrayList<Solid> getSolids() {
		return solids;
	}
	
	public ArrayList<Actor> getActors() {
		return actors;
	}
}
