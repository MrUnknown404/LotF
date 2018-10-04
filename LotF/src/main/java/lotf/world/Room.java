package main.java.lotf.world;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileAir;
import main.java.lotf.util.EnumDungeonType;
import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.RoomPos;
import main.java.lotf.util.math.TilePos;
import main.java.lotf.util.math.Vec2i;

public final class Room {
	
	protected List<Tile> tileLayer_0 = new ArrayList<Tile>();
	protected List<Tile> tileLayer_1 = new ArrayList<Tile>();
	//protected List<Entity> entities = new ArrayList<Entity>();
	
	protected transient RoomPos roomPos = new RoomPos();
	protected transient World.WorldType type;
	protected Vec2i roomSize, mapPos, exitPos = new Vec2i();
	protected EnumDungeonType dungeonType;
	protected RoomSize size;
	protected transient int roomID;
	
	protected int exitID = -1, worldID;
	
	protected transient boolean  wasAllEnemiesDefeated = false;
	
	public Room(RoomSize size) {
		this.roomSize = new Vec2i(size.x, size.y);
		this.roomPos = IDToRoomPos(roomID);
		this.size = size;
		
		for (int i = 0; i < roomSize.getBothMulti(); i++) {
			tileLayer_0.add(new TileAir(IDToTilePos(i), this));
			tileLayer_1.add(new TileAir(IDToTilePos(i), this));
		}
	}
	
	public void tick() {
		for (int i = 0; i < tileLayer_0.size(); i++) {
			tileLayer_0.get(i).tick();
			tileLayer_1.get(i).tick();
		}
		
		/*
		boolean tb = false;
		if (!entities.isEmpty()) {
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).tick();
				
				if (entities.get(i) instanceof EntityMonster) {
					if (((EntityMonster) entities.get(i)).getIsAlive()) {
						tb = true;
					}
				}
			}
		}
		
		if (!entities.isEmpty() && !tb && !wasAllEnemiesDefeated) {
			onAllEnemyDefeated();
		}*/
	}
	
	public void onRoomEnter() {
		if (type != World.WorldType.inside) {
			Main.getWorldHandler().getPlayerWorld().updateMap(roomID);
		}
	}
	
	public void onRoomExit() {
		
	}
	
	public void onAllEnemyDefeated() {
		wasAllEnemiesDefeated = true;
	}
	
	public void onSoftReset() {
		
	}
	
	public void onHardReset() {
		wasAllEnemiesDefeated = false;
	}
	
	public void resetAnimations() {
		for (int i = 0; i < getTileLayer0().size(); i++) {
			if (getTileLayer0().get(i).getIsAnimated()) {
				getTileLayer0().get(i).resetAnimation();
			}
			if (getTileLayer1().get(i).getIsAnimated()) {
				getTileLayer1().get(i).resetAnimation();
			}
		}
	}
	
	public TilePos IDToTilePos(int id) {
		int ytt = MathHelper.floor((double) id / roomSize.getX());
		int xtt = id - (ytt * roomSize.getX());
		
		return new TilePos(xtt, ytt);
	}
	
	public RoomPos IDToRoomPos(int id) {
		int ytt = MathHelper.floor((double) id / roomSize.getX());
		int xtt = id - (ytt * roomSize.getX());
		
		return new RoomPos(xtt, ytt);
	}
	
	public int TilePosToID(RoomSize size, int x, int y) {
		return (y * size.x) + x;
	}
	
	public void setRoomPos(RoomPos roomPos) {
		this.roomPos = roomPos;
	}
	
	public int getExitID() {
		return exitID;
	}
	
	public int getWorldID() {
		return worldID;
	}
	
	public int getRoomID() {
		return roomID;
	}
	
	public RoomPos getRoomPos() {
		return roomPos;
	}
	
	public Vec2i getExitPos() {
		return exitPos;
	}
	
	public Vec2i getVecRoomSize() {
		return roomSize;
	}
	
	public RoomSize getRoomSize() {
		return size;
	}
	
	public World.WorldType getWorldType() {
		return type;
	}
	
	public EnumDungeonType getDungeonType() {
		return dungeonType;
	}
	
	public Vec2i getMapPos() {
		return mapPos;
	}
	
	public List<Tile> getTileLayer0() {
		return tileLayer_0;
	}
	
	public List<Tile> getTileLayer1() {
		return tileLayer_1;
	}
	
	//public List<Entity> getEntities() {
	//	return entities;
	//}
	
	public Rectangle getBounds() {
		return new Rectangle((getRoomPos().getX() * roomSize.getX()) * Tile.TILE_SIZE, 
				(getRoomPos().getY() * roomSize.getY()) * Tile.TILE_SIZE, 
				roomSize.getX() * Tile.TILE_SIZE, 
				roomSize.getY() * Tile.TILE_SIZE);
	}
	
	public Rectangle getActiveBounds() {
		return new Rectangle((getRoomPos().getX() * (roomSize.getX() - 2)) * Tile.TILE_SIZE,
				(getRoomPos().getY() * (roomSize.getY() - 2)) * Tile.TILE_SIZE,
				(roomSize.getX() + 4) * Tile.TILE_SIZE, (roomSize.getY() + 4) * Tile.TILE_SIZE);
	}
	
	public Rectangle getBoundsNorth() {
		return new Rectangle(
				(getRoomPos().getX() * roomSize.getX()) * Tile.TILE_SIZE + (Tile.TILE_SIZE / 2),
				(getRoomPos().getY() * roomSize.getY()) * Tile.TILE_SIZE,
				roomSize.getX() * Tile.TILE_SIZE - Tile.TILE_SIZE, 1);
	}
	
	public Rectangle getBoundsEast() {
		return new Rectangle(
				((getRoomPos().getX() * roomSize.getX()) * Tile.TILE_SIZE) + (roomSize.getX() * Tile.TILE_SIZE) - 1,
				(getRoomPos().getY() * roomSize.getY()) * Tile.TILE_SIZE + (Tile.TILE_SIZE / 2),
				1, roomSize.getY() * Tile.TILE_SIZE - Tile.TILE_SIZE);
	}
	
	public Rectangle getBoundsSouth() {
		return new Rectangle(
				(getRoomPos().getX() * roomSize.getX()) * Tile.TILE_SIZE + (Tile.TILE_SIZE / 2),
				((getRoomPos().getY() * roomSize.getY()) * Tile.TILE_SIZE) + (roomSize.getY() * Tile.TILE_SIZE) - 1,
				roomSize.getX() * Tile.TILE_SIZE - Tile.TILE_SIZE, 1);
	}
	
	public Rectangle getBoundsWest() {
		return new Rectangle(
				(getRoomPos().getX() * roomSize.getX()) * Tile.TILE_SIZE,
				(getRoomPos().getY() * roomSize.getY()) * Tile.TILE_SIZE + (Tile.TILE_SIZE / 2),
				1, roomSize.getY() * Tile.TILE_SIZE - Tile.TILE_SIZE);
	}
	
	public enum RoomSize {
		small  (0, 16, 9),
		medium (1, 24, 13),
		big    (2, 32, 17),
		veryBig(3, 48, 25);
		
		public final int fId, x, y;
		
		private RoomSize(int id, int x, int y) {
			this.fId = id;
			this.x = x;
			this.y = y;
		}
		
		public static RoomSize getFromNumber(int id) {
			for (RoomSize type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
	
	@Override
	public String toString() {
		return roomPos.toString();
	}
}
