package main.java.lotf.world;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.entities.util.Entity;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.GameObject;
import main.java.lotf.util.IResetable;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.math.MathH;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public class Room extends GameObject implements ITickable, IResetable {

	private final int roomID;
	private boolean isActive;
	
	private List<Tile> tiles_layer0 = new ArrayList<Tile>();
	private List<Tile> tiles_layer1 = new ArrayList<Tile>();
	private List<Entity> entities = new ArrayList<Entity>();
	
	public Room(int roomID, Vec2i roomPos, Vec2i size, boolean isActive) {
		super(new Vec2f(roomPos), size);
		this.roomID = roomID;
		this.isActive = isActive;
		
		/*
		if (size.getX() < 16 || size.getY() < 8) {
			Console.print(Console.WarningType.FatalError, "Rooms cannot be under 16x8!");
			return;
		}*/
		
		for (int yi = 0; yi < size.getY(); yi++) {
			for (int xi = 0; xi < size.getX(); xi++) {
				tiles_layer0.add(new Tile(new Vec2i(xi, yi), TileInfo.getRandomGrass(), roomPos));
				tiles_layer1.add(new Tile(new Vec2i(xi, yi), TileInfo.AIR, roomPos));
			}
		}
		
		setPos(MathH.floor(getPosX() * size.getX() * Tile.TILE_SIZE), MathH.floor(getPosY() * size.getY() * Tile.TILE_SIZE));
		
		onCreate();
	}
	
	@Override
	public void tick() {
		for (Entity e : entities) {
			e.tick();
		}
		
		for (Tile t : tiles_layer0) {
			if (t instanceof ITickable) {
				((ITickable) t).tick();
			}
		}
		
		for (Tile t : tiles_layer1) {
			if (t instanceof ITickable) {
				((ITickable) t).tick();
			}
		}
	}
	
	public void onCreate() {
		for (Tile t : tiles_layer0) {
			t.updateTile(new Vec2i(pos));
		}
		
		for (Tile t : tiles_layer1) {
			t.updateTile(new Vec2i(pos));
		}
	}
	
	public void onEnter(EntityPlayer p) {
		p.exploreRoom(roomID);
	}
	
	public void onLeave(EntityPlayer p) {
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
	
	public int getRoomID() {
		return roomID;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public List<Tile> getTilesLayer0() {
		return tiles_layer0;
	}
	
	public List<Tile> getTilesLayer1() {
		return tiles_layer1;
	}
	
	public Rectangle getRoomBounds(EnumDirection dir) {
		final int boundsSize = 8;
		
		switch (dir) {
			case north:
				return new Rectangle(MathH.floor(getPosX()), MathH.floor(getPosY()) - boundsSize, size.getX() * Tile.TILE_SIZE, boundsSize);
			case east:
				return new Rectangle(MathH.floor(getPosX() + (size.getX() * Tile.TILE_SIZE)), MathH.floor(getPosY()), boundsSize, size.getY() * Tile.TILE_SIZE);
			case south:
				return new Rectangle(MathH.floor(getPosX()), MathH.floor(getPosY() + (size.getY() * Tile.TILE_SIZE)), size.getX() * Tile.TILE_SIZE, boundsSize);
			case west:
				return new Rectangle(MathH.floor(getPosX()) - boundsSize, MathH.floor(getPosY()), boundsSize, size.getY() * Tile.TILE_SIZE);
			default:
				return getBounds();
		}
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(MathH.floor(getPosX()), MathH.floor(getPosY()), size.getX() * Tile.TILE_SIZE, size.getY() * Tile.TILE_SIZE);
	}
}
