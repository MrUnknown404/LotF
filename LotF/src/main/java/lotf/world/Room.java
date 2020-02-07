package main.java.lotf.world;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.entities.util.Entity;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.Console;
import main.java.lotf.util.Console.WarningType;
import main.java.lotf.util.GameObject;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.Grid;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.LangKey;
import main.java.lotf.util.LangKey.LangKeyType;
import main.java.lotf.util.LangKey.LangType;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.MathH;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public class Room extends GameObject implements ITickable {
	
	public static final Vec2i ROOM_SIZE = new Vec2i(16, 8);
	
	private final String description;
	private final EnumWorldType worldType;
	
	@SuppressWarnings("unchecked")
	private List<Grid<Tile>> tiles = Arrays.asList(new Grid[3]);
	private List<Entity> entities = new ArrayList<Entity>();
	
	private final Vec2i roomPos;
	
	public Room(EnumWorldType worldType, Vec2i roomPos, boolean hasLangKey) {
		super(new Vec2f(roomPos), ROOM_SIZE);
		this.worldType = worldType;
		this.roomPos = roomPos;
		
		for (int i = 0; i < 3; i++) {
			tiles.set(i, new Grid<Tile>(size.getX(), size.getY()));
		}
		
		description = hasLangKey ? GetResource.getStringFromLangKey(
				new LangKey(LangType.gui, "room" + (roomPos.getX() + roomPos.getY() * World.WORLD_SIZE), LangKeyType.desc), LangKeyType.desc) : null;
		
		for (int yi = 0; yi < this.size.getY(); yi++) {
			for (int xi = 0; xi < this.size.getX(); xi++) {
				tiles.get(0).add(new Tile(new Vec2i(xi, yi), TileInfo.getRandomGrass()), xi, yi);
				for (int i = 1; i < 3; i++) {
					tiles.get(i).add(null, xi, yi);
				}
			}
		}
		
		setPos(MathH.floor(getPosX() * this.size.getX() * Tile.TILE_SIZE), MathH.floor(getPosY() * this.size.getY() * Tile.TILE_SIZE));
		
		onCreate();
	}
	
	@Override
	public void tick() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
		}
		
		for (Grid<Tile> g : tiles) {
			for (Tile t : g.get()) {
				if (t instanceof ITickable) {
					((ITickable) t).tick();
				}
			}
		}
	}
	
	public void onCreate() {
		for (Grid<Tile> g : tiles) {
			for (Tile t : g.get()) {
				if (t != null) {
					t.updateTile(new Vec2i(pos));
				}
			}
		}
	}
	
	public void onEnter(EntityPlayer p) {
		p.exploreRoom();
	}
	
	public void onLeave() {
		for (Entity e : entities) {
			e.reset();
		}
	}
	
	public List<TileInfo> getAllTileInfos() {
		List<TileInfo> kinds = new ArrayList<TileInfo>();
		
		for (Grid<Tile> g : tiles) {
			for (Tile t : g.get()) {
				if (t != null) {
					if (kinds.isEmpty()) {
						kinds.add(t.getTileInfo());
					} else if (!kinds.contains(t.getTileInfo())) {
						kinds.add(t.getTileInfo());
					}
				}
			}
		}
		
		return kinds;
	}
	
	public void spawnEntity(Entity entity) {
		entities.add(entity);
	}
	
	public void killEntity(Entity entity) {
		if (entities.contains(entity)) {
			entities.remove(entity);
		} else {
			Console.print(WarningType.Warning, "Tried to kill entity that doesn't exist!");
		}
	}
	
	public World getWorld() {
		return Main.getMain().getWorldHandler().getWorld(worldType);
	}
	
	public int getRoomID() {
		return roomPos.getX() + roomPos.getY() * World.WORLD_SIZE;
	}
	
	public String getDescription() {
		return description;
	}
	
	public List<Grid<Tile>> getTileLayers() {
		return tiles;
	}
	
	public List<Entity> getEntities() {
		return entities;
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
