package main.java.lotf.world;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.entities.util.Entity;
import main.java.lotf.init.Tiles;
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
import main.java.lotf.util.enums.EnumCollisionType;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.MathH;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public class Room extends GameObject implements ITickable {
	
	public static final Vec2i ROOM_SIZE = new Vec2i(16, 8);
	
	protected transient final String description;
	protected final EnumWorldType worldType;
	protected final Vec2i roomPos;
	
	@SuppressWarnings("unchecked")
	protected List<Grid<Tile>> tiles = Arrays.asList(new Grid[3]);
	private List<Entity> entities = new ArrayList<Entity>();
	
	private List<TileInfo> cached_allInfos = new ArrayList<TileInfo>();
	private List<Grid<Tile>> cached_visibleTiles = new ArrayList<Grid<Tile>>();
	private List<Tile> cached_tilesWithCollision = new ArrayList<Tile>();
	
	public Room(EnumWorldType worldType, Vec2i roomPos, boolean hasLangKey) {
		super(new Vec2f(roomPos), ROOM_SIZE);
		this.worldType = worldType;
		this.roomPos = roomPos;
		
		for (int i = 0; i < 3; i++) {
			tiles.set(i, new Grid<Tile>(getWidth(), getHeight()));
		}
		
		description = hasLangKey ? GetResource.getStringFromLangKey(
				new LangKey(LangType.gui, "room" + (roomPos.getX() + roomPos.getY() * World.WORLD_SIZE), LangKeyType.desc), LangKeyType.desc) : null;
		
		onCreate();
	}
	
	public static Room createEmptyGrass(EnumWorldType worldType, Vec2i roomPos, boolean hasLangKey) {
		Room r = new Room(worldType, roomPos, hasLangKey);
		
		for (int yi = 0; yi < r.getHeight(); yi++) {
			for (int xi = 0; xi < r.getWidth(); xi++) {
				r.tiles.get(0).add(new Tile(new Vec2i(xi, yi), Tiles.getRandomGrass()), xi, yi);
				
				for (int i = 1; i < 3; i++) {
					r.tiles.get(i).add((i == 1 && yi == 2 && xi == 2) ? new Tile(new Vec2i(xi, yi), Tiles.WALL) :
							(i == 1 && yi == 2 && xi == 6) ? new Tile(new Vec2i(xi, yi), Tiles.WALL2) : null, xi, yi);
				}
			}
		}
		
		r.onCreate();
		return r;
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
		setPos(roomPos.getX() * getWidth() * Tile.TILE_SIZE, roomPos.getY() * getHeight() * Tile.TILE_SIZE);
		
		for (Grid<Tile> g : tiles) {
			for (Tile t : g.get()) {
				if (t != null) {
					t.updateTile(new Vec2i(MathH.floor(getPosX()), MathH.floor(getPosY())));
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
		if (cached_allInfos.isEmpty()) {
			for (Grid<Tile> g : tiles) {
				for (Tile t : g.get()) {
					if (t != null) {
						if (cached_allInfos.isEmpty()) {
							cached_allInfos.add(t.getTileInfo());
						} else if (!cached_allInfos.contains(t.getTileInfo())) {
							cached_allInfos.add(t.getTileInfo());
						}
					}
				}
			}
		}
		
		return cached_allInfos;
	}
	
	public List<Grid<Tile>> getVisibleTiles() {
		if (cached_visibleTiles.isEmpty()) {
			for (int i = 0; i < 3; i++) {
				cached_visibleTiles.add(new Grid<Tile>(getWidth(), getHeight()));
			}
			
			for (int i = getTileLayers().size() - 1; i >= 0; i--) {
				Grid<Tile> grid = getTileLayers().get(i);
				Grid<Tile> nextGrid = i != getTileLayers().size() - 1 ? getTileLayers().get(i + 1) : null;
				
				for (Tile t : grid.get()) {
					if (t != null) {
						if (t.getTileInfo().shouldRenderBehind()) {
							cached_visibleTiles.get(i).add(t, t.getTilePos());
						} else if (nextGrid != null && (nextGrid.get(t.getTilePos().getX(), t.getTilePos().getY()) == null ||
								nextGrid.get(t.getTilePos().getX(), t.getTilePos().getY()).getTileInfo().shouldRenderBehind())) {
							cached_visibleTiles.get(i).add(t, t.getTilePos());
						}
					}
				}
			}
		}
		
		return cached_visibleTiles;
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
	
	public List<Tile> getTilesWithCollision() {
		if (cached_tilesWithCollision.isEmpty()) {
			for (Grid<Tile> g : tiles) {
				for (Tile t : g.get()) {
					if (t != null && t.getTileInfo().getCollisionType() != EnumCollisionType.none) {
						cached_tilesWithCollision.add(t);
					}
				}
			}
		}
		
		return cached_tilesWithCollision;
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public Rectangle getRoomBounds(EnumDirection dir) {
		switch (dir) {
			case north:
				return new Rectangle(MathH.floor(getPosX()), MathH.floor(getPosY()) - 8, getWidth() * Tile.TILE_SIZE, 8);
			case east:
				return new Rectangle(MathH.floor(getPosX() + (getWidth() * Tile.TILE_SIZE)), MathH.floor(getPosY()), 8, getHeight() * Tile.TILE_SIZE);
			case south:
				return new Rectangle(MathH.floor(getPosX()), MathH.floor(getPosY() + (getHeight() * Tile.TILE_SIZE)), getWidth() * Tile.TILE_SIZE, 8);
			case west:
				return new Rectangle(MathH.floor(getPosX()) - 8, MathH.floor(getPosY()), 8, getHeight() * Tile.TILE_SIZE);
			default:
				return getBounds();
		}
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(MathH.floor(getPosX()), MathH.floor(getPosY()), getWidth() * Tile.TILE_SIZE, getHeight() * Tile.TILE_SIZE);
	}
}
