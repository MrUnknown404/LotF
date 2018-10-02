package main.java.lotf.world;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.java.lotf.Main;
import main.java.lotf.entity.Entity;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileAir;
import main.java.lotf.tile.TileGrass;
import main.java.lotf.tile.TileGrassFlowerLeft;
import main.java.lotf.tile.TileGrassFlowerRight;
import main.java.lotf.tile.TileGrassLeft;
import main.java.lotf.tile.TileGrassRight;
import main.java.lotf.util.EnumDungeonType;
import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.RoomPos;
import main.java.lotf.util.math.TilePos;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.World.WorldType;

public class Room {
	
	protected List<Tile> tileLayer_0 = new ArrayList<Tile>();
	protected List<Tile> tileLayer_1 = new ArrayList<Tile>();
	protected List<Entity> entities = new ArrayList<Entity>();
	
	protected RoomPos roomPos = new RoomPos();
	protected Vec2i roomSize, mapPos;
	protected World.WorldType type;
	protected EnumDungeonType dungeonType;
	protected RoomSize size;
	protected int roomID;
	
	protected boolean wasAllEnemiesDefeated = false;
	
	public Room(World.WorldType type, EnumDungeonType dungeonType, RoomSize size, int roomID) {
		this.type = type;
		this.dungeonType = dungeonType;
		this.roomID = roomID;
		this.roomSize = new Vec2i(size.x, size.y);
		this.roomPos = IDToRoomPos(roomID);
		
		for (int i = 0; i < roomSize.getBothMulti(); i++) {
			tileLayer_0.add(new TileAir(IDToTilePos(i), this));
			tileLayer_1.add(new TileAir(IDToTilePos(i), this));
		}
	}
	
	public void tick() {
		boolean tb = false;
		
		for (int i = 0; i < tileLayer_0.size(); i++) {
			tileLayer_0.get(i).tick();
			tileLayer_1.get(i).tick();
		}
		
		if (!entities.isEmpty()) {
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).tick();
				
				if (entities.get(i).getIsAlive()) {
					tb = true;
				}
			}
		}
		
		if (!entities.isEmpty() && !tb && !wasAllEnemiesDefeated) {
			onAllEnemyDefeated();
		}
	}
	
	public void onRoomEnter() {
		Main.getWorldHandler().getPlayerWorld().updateMap(roomID);
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
		int xtt, ytt;
		
		if (type != WorldType.dungeon) {
			ytt = MathHelper.floor((double) id / type.size);
			xtt = id - (ytt * roomSize.getX());
		} else {
			ytt = MathHelper.floor((double) id / dungeonType.size);
			xtt = id - (ytt * roomSize.getX());
		}
		
		return new RoomPos(xtt, ytt);
	}
	
	public int TilePosToID(RoomSize size, int x, int y) {
		return (y * size.x) + x;
	}
	
	public void setRoomPos(RoomPos roomPos) {
		this.roomPos = roomPos;
	}
	
	public int getRoomID() {
		return roomID;
	}
	
	public RoomPos getRoomPos() {
		return roomPos;
	}
	
	public Vec2i getRoomSize() {
		return roomSize;
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
	
	public List<Entity> getEntities() {
		return entities;
	}
	
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
		veryBig(3, 40, 21);
		
		private final int fId, x, y;
		
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
	
	public void generateRandomGrassFloor() {
		for (int i = 0; i < tileLayer_0.size(); i++) {
			if (new Random().nextInt(32) == 0) {
				if (new Random().nextBoolean()) {
					tileLayer_0.set(i, new TileGrassFlowerLeft(IDToTilePos(i), this));
				} else {
					tileLayer_0.set(i, new TileGrassFlowerRight(IDToTilePos(i), this));
				}
			} else {
				if (new Random().nextBoolean()) {
					if (new Random().nextBoolean()) {
						tileLayer_0.set(i, new TileGrassLeft(IDToTilePos(i), this));
					} else {
						tileLayer_0.set(i, new TileGrassRight(IDToTilePos(i), this));
					}
				} else {
					tileLayer_0.set(i, new TileGrass(IDToTilePos(i), this));
				}
			}
		}
	}
}
