package main.java.lotf.world;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import main.java.lotf.entity.util.Entity;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.GameObject;
import main.java.lotf.util.IResetable;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public class Room extends GameObject implements ITickable, IResetable {

	private List<Tile> tiles_layer0 = new ArrayList<Tile>();
	private List<Tile> tiles_layer1 = new ArrayList<Tile>();
	private List<Entity> entities = new ArrayList<Entity>();
	
	public Room(Vec2i roomPos, Vec2i size) {
		super(new Vec2f(roomPos), size);
		
		/*
		if (size.getX() < 16 || size.getY() < 8) {
			Console.print(Console.WarningType.FatalError, "Rooms cannot be under 16x8!");
			return;
		}*/
		
		for (int yi = 0; yi < size.getY(); yi++) {
			for (int xi = 0; xi < size.getX(); xi++) {
				tiles_layer0.add(new Tile(new Vec2i(xi, yi), TileInfo.getRandomGrass()));
				tiles_layer1.add(new Tile(new Vec2i(xi, yi), TileInfo.AIR));
			}
		}
		
		setPos(new Vec2f(MathHelper.floor(getPosX() * size.getX() * Tile.TILE_SIZE), MathHelper.floor(getPosY() * size.getY() * Tile.TILE_SIZE)));
		
		onCreate();
	}
	
	@Override
	public void tick() {
		for (Entity e : entities) {
			e.tick();
		}
	}
	
	public void onCreate() {
		for (Tile t : tiles_layer0) {
			t.updateTile();
		}
		
		for (Tile t : tiles_layer1) {
			t.updateTile();
		}
	}
	
	public void onEnter() {
		
	}
	
	public void onLeave() {
		softReset();
	}
	
	@Override
	public void softReset() {
		for (Entity e : entities) {
			e.softReset();
		}
	}
	
	@Override
	public void hardReset() {
		for (Entity e : entities) {
			e.hardReset();
		}
	}
	
	public List<TileInfo> getAllTileInfos() {
		List<TileInfo> kinds = new ArrayList<TileInfo>();
		
		for (Tile t : tiles_layer0) {
			if (t.getTileInfo() != TileInfo.AIR) {
				if (kinds.isEmpty()) {
					kinds.add(t.getTileInfo());
				} else if (!kinds.contains(t.getTileInfo())) {
					kinds.add(t.getTileInfo());
				}
			}
		}
		
		for (Tile t : tiles_layer1) {
			if (t.getTileInfo() != TileInfo.AIR) {
				if (kinds.isEmpty()) {
					kinds.add(t.getTileInfo());
				} else if (!kinds.contains(t.getTileInfo())) {
					kinds.add(t.getTileInfo());
				}
			}
		}
		
		return kinds;
	}
	
	public List<Tile> getTilesLayer0() {
		return tiles_layer0;
	}
	
	public List<Tile> getTilesLayer1() {
		return tiles_layer1;
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(MathHelper.floor(getPosX()), MathHelper.floor(getPosY()), size.getX() * Tile.TILE_SIZE, size.getY() * Tile.TILE_SIZE);
	}
}
