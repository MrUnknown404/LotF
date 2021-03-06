package main.java.lotf.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.entities.util.Entity;
import main.java.lotf.init.Tiles;
import main.java.lotf.particles.Particle;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.GameObject;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.HitBox;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.LangKey;
import main.java.lotf.util.LangKey.LangKeyType;
import main.java.lotf.util.LangKey.LangType;
import main.java.lotf.util.enums.EnumCollisionType;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.ulibs.utils.Console;
import main.java.ulibs.utils.Console.WarningType;
import main.java.ulibs.utils.Grid;
import main.java.ulibs.utils.math.MathH;
import main.java.ulibs.utils.math.Vec2f;
import main.java.ulibs.utils.math.Vec2i;

public class Room extends GameObject implements ITickable {
	
	public static final Vec2i ROOM_SIZE = new Vec2i(16, 8);
	
	protected transient final String description;
	protected final EnumWorldType worldType;
	protected final Vec2i roomPos;
	
	@SuppressWarnings("unchecked")
	protected final List<Grid<Tile>> tiles = Arrays.asList(new Grid[3]);
	protected final List<Entity> entities = new ArrayList<Entity>();
	protected transient List<Particle<?>> particles = new ArrayList<Particle<?>>();
	
	protected transient List<TileInfo> cached_allInfos = new ArrayList<TileInfo>();
	protected transient List<Grid<Tile>> cached_visibleTiles = new ArrayList<Grid<Tile>>();
	protected transient List<Tile> cached_tilesWithCollision = new ArrayList<Tile>();
	
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
					r.tiles.get(i).add(null, xi, yi);
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
		
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).tick();
		}
		
		for (Grid<Tile> g : tiles) {
			for (Tile t : g.get()) {
				if (t != null && t.getTileInfo() instanceof ITickable) {
					((ITickable) t.getTileInfo()).tick();
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
		
		if (cached_allInfos == null) {
			cached_allInfos = new ArrayList<TileInfo>();
		}
		if (cached_visibleTiles == null) {
			cached_visibleTiles = new ArrayList<Grid<Tile>>();
		}
		if (cached_tilesWithCollision == null) {
			cached_tilesWithCollision = new ArrayList<Tile>();
		}
		
		particles = new ArrayList<Particle<?>>();
	}
	
	public void onEnter(EntityPlayer p) {
		p.exploreRoom();
	}
	
	public void onLeave() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).reset();
		}
		for (int i = 0; i < particles.size(); i++) {
			killParticle(particles.get(i));
		}
	}
	
	public void spawnParticle(Particle<?> type, Vec2f pos, int amount) {
		for (int i = 0; i < amount; i++) {
			particles.add(Particle.create(type, pos, this));
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
			Console.print(WarningType.Warning, "Tried to kill an entity that doesn't exist!");
		}
	}
	
	public void killParticle(Particle<?> particle) {
		if (particles.contains(particle)) {
			particles.remove(particle);
		} else {
			Console.print(WarningType.Warning, "Tried to kill a particle that doesn't exist!");
		}
	}
	
	public World getWorld() {
		return Main.getMain().getWorldHandler().getWorld(worldType);
	}
	
	public int getRoomID() {
		return roomPos.getX() + roomPos.getY() * World.WORLD_SIZE;
	}
	
	public Vec2i getRoomPos() {
		return roomPos;
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
	
	public List<Particle<?>> getParticles() {
		return particles;
	}
	
	@Override
	public int getWidth() {
		return ROOM_SIZE.getX();
	}
	
	@Override
	public int getHeight() {
		return ROOM_SIZE.getY();
	}
	
	public HitBox getRoomBounds(EnumDirection dir) {
		switch (dir) {
			case north:
				return new HitBox(MathH.floor(getPosX()), MathH.floor(getPosY()) - 8, getWidth() * Tile.TILE_SIZE, 8);
			case east:
				return new HitBox(MathH.floor(getPosX() + (getWidth() * Tile.TILE_SIZE)), MathH.floor(getPosY()), 8, getHeight() * Tile.TILE_SIZE);
			case south:
				return new HitBox(MathH.floor(getPosX()), MathH.floor(getPosY() + (getHeight() * Tile.TILE_SIZE)), getWidth() * Tile.TILE_SIZE, 8);
			case west:
				return new HitBox(MathH.floor(getPosX()) - 8, MathH.floor(getPosY()), 8, getHeight() * Tile.TILE_SIZE);
			default:
				return getBounds();
		}
	}
	
	@Override
	public HitBox getBounds() {
		return new HitBox(MathH.floor(getPosX()), MathH.floor(getPosY()), getWidth() * Tile.TILE_SIZE, getHeight() * Tile.TILE_SIZE);
	}
}
