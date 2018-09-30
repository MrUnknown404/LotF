package main.java.lotf.world;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.java.lotf.entity.Entity;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileBlueWall;
import main.java.lotf.tile.TileBlueWallCorner;
import main.java.lotf.tile.TileBlueWallHalf;
import main.java.lotf.tile.TileGrass;
import main.java.lotf.tile.TileGrassFlowerLeft;
import main.java.lotf.tile.TileGrassFlowerRight;
import main.java.lotf.tile.TileGrassLeft;
import main.java.lotf.tile.TileGrassRight;
import main.java.lotf.util.Console;
import main.java.lotf.util.EnumDungeonType;
import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.RoomPos;
import main.java.lotf.util.math.TilePos;
import main.java.lotf.util.math.Vec2i;

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
	
	public Room(RoomPos roomPos, World.WorldType type, EnumDungeonType dungeonType, RoomSize size, int roomID) {
		this.roomPos = roomPos;
		this.type = type;
		this.dungeonType = dungeonType;
		this.roomID = roomID;
		
		if (size == RoomSize.small) {
			this.roomSize = new Vec2i(16, 9);
		} else if (size == RoomSize.medium) {
			this.roomSize = new Vec2i(24, 13);
		} else if (size == RoomSize.big) {
			this.roomSize = new Vec2i(32, 17);
		} else if (size == RoomSize.veryBig) {
			this.roomSize = new Vec2i(48, 25);
		}
		
		for (int i = 0; i < roomSize.getBothMuli(); i++) {
			tileLayer_0.add(Tile.AIR);
			tileLayer_1.add(Tile.AIR);
		}
		
		generate();
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
		
		if (!tb && !wasAllEnemiesDefeated) {
			onAllEnemyDefeated();
		}
	}
	
	public void onRoomEnter() {
		
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
	
	public TilePos IDToTilePos(int id) {
		int ytt = MathHelper.floor((double) id / roomSize.getX());
		int xtt = id - (ytt * roomSize.getX());
		
		return new TilePos(xtt, ytt);
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
		small  (0),
		medium (1),
		big    (2),
		veryBig(3);
		
		private final int fId;
		
		private RoomSize(int id) {
			fId = id;
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
	
	public void generate() {
		if (type == World.WorldType.overworld) {
			generateRandomGrassFloor();
			
			if (roomID == 149) {
				tileLayer_1.set(0, new TileBlueWallCorner(IDToTilePos(0), this, 0));
				tileLayer_1.set(15, new TileBlueWallCorner(IDToTilePos(15), this, 1));
				tileLayer_1.set(128, new TileBlueWallCorner(IDToTilePos(128), this, 3));
				tileLayer_1.set(143, new TileBlueWallCorner(IDToTilePos(143), this, 2));
				
				for (int i = 1; i < 8; i++) {
					if (i != 4) {
						tileLayer_1.set(roomSize.getX() * i, new TileBlueWall(IDToTilePos(roomSize.getX() * i), this, 3));
						tileLayer_1.set(roomSize.getX() * i + 15, new TileBlueWall(IDToTilePos(roomSize.getX() * i + 15), this, 1));
					}
				}
				
				for (int i = 1; i < 15; i++) {
					if (i == 7) {
						tileLayer_1.set(i, new TileBlueWallHalf(IDToTilePos(i), this, 0));
						tileLayer_1.set(roomSize.getBothMuli() - i - 1, new TileBlueWallHalf(IDToTilePos(roomSize.getBothMuli() - i - 1), this, 3));
					} else if (i == 8) {
						tileLayer_1.set(i, new TileBlueWallHalf(IDToTilePos(i), this, 1));
						tileLayer_1.set(roomSize.getBothMuli() - i - 1, new TileBlueWallHalf(IDToTilePos(roomSize.getBothMuli() - i - 1), this, 2));
					} else {
						tileLayer_1.set(i, new TileBlueWall(IDToTilePos(i), this, 0));
						tileLayer_1.set(roomSize.getBothMuli() - i - 1, new TileBlueWall(IDToTilePos(roomSize.getBothMuli() - i - 1), this, 2));
					}
				}
			}
		} else if (type == World.WorldType.underworld) {
			
		} else if (type == World.WorldType.inside) {
			
		} else if (type == World.WorldType.dungeon) {
			if (dungeonType == EnumDungeonType.nil) {
				Console.print(Console.WarningType.FatalError, "Unknown dungeon generation type!");
			} else if (dungeonType == EnumDungeonType.one) {
				
			} else if (dungeonType == EnumDungeonType.two) {
				
			} else if (dungeonType == EnumDungeonType.three) {
				
			} else if (dungeonType == EnumDungeonType.four) {
				
			} else if (dungeonType == EnumDungeonType.five) {
				
			} else if (dungeonType == EnumDungeonType.six) {
				
			} else if (dungeonType == EnumDungeonType.seven) {
				
			} else if (dungeonType == EnumDungeonType.eight) {
				
			} else if (dungeonType == EnumDungeonType.nine) {
				
			} else if (dungeonType == EnumDungeonType.ten) {
				
			} else if (dungeonType == EnumDungeonType.eleven) {
				
			} else if (dungeonType == EnumDungeonType.twelve) {
				
			}
		}
	}
}
