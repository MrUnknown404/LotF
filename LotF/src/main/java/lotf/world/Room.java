package main.java.lotf.world;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.entity.Entity;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.math.Vec2i;

public final class Room implements ITickable {

	private List<Tile> tiles = new ArrayList<Tile>();
	private List<Entity> entities = new ArrayList<Entity>();
	
	private Vec2i pos = Vec2i.ZERO, size = Vec2i.ZERO;
	
	public Room(Vec2i pos, Vec2i size) {
		this.pos = pos;
		this.size = size;
		
		onCreate();
	}
	
	@Override
	public void tick() {
		for (Entity e : entities) {
			e.tick();
		}
	}
	
	public void onCreate() {
		for (Tile t : tiles) {
			t.updateTile();
		}
	}
	
	public void onEnter() {
		
	}
	
	public void onLeave() {
		softReset();
	}
	
	public void softReset() {
		
	}
	
	public void hardReset() {
		
	}
	
	public List<Tile> getTiles() {
		return tiles;
	}
}
