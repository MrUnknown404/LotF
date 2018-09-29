package main.java.lotf.world;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import main.java.lotf.entity.Entity;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.Console;
import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.RoomPos;
import main.java.lotf.util.math.TilePos;
import main.java.lotf.util.math.Vec2i;

public abstract class Room {
	
	protected List<Tile> tiles = new ArrayList<Tile>();
	protected List<Entity> entities = new ArrayList<Entity>();
	
	protected RoomPos roomPos = new RoomPos();
	protected Vec2i roomSize, mapPos;
	protected World.WorldType type;
	protected RoomSize size;
	protected int roomID;
	
	protected boolean wasAllEnemiesDefeated = false;
	
	public Room(RoomPos roomPos, World.WorldType type, RoomSize size, int roomID) {
		this.roomPos = roomPos;
		this.type = type;
		this.roomID = roomID;
		
		if (size == RoomSize.small) {
			this.roomSize = new Vec2i(16, 9);
		} else if (size == RoomSize.medium) {
			this.roomSize = new Vec2i(24, 13);
		} else if (size == RoomSize.big) {
			this.roomSize = new Vec2i(32, 17);
		} else if (size == RoomSize.veryBig) {
			
		}
		
		for (int i = 0; i < roomSize.getBothMuli(); i++) {
			tiles.add(Tile.AIR);
		}
		
		if (size == RoomSize.small) {
			generateTestRoom();
		}
	}
	
	public void tick() {
		boolean tb = false;
		
		for (int i = 0; i < tiles.size(); i++) {
			tiles.get(i).tick();
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
	
	/** delete this! */
	private void generateTestRoom() {
		tiles.set(0, new Tile(IDToTilePos(0), Tile.TileType.blueWallCorner, this, 0));
		tiles.set(15, new Tile(IDToTilePos(15), Tile.TileType.blueWallCorner, this, 1));
		tiles.set(128, new Tile(IDToTilePos(128), Tile.TileType.blueWallCorner, this, 3));
		tiles.set(143, new Tile(IDToTilePos(143), Tile.TileType.blueWallCorner, this, 2));
		
		for (int i = 1; i < 8; i++) {
			if (i != 4) {
				tiles.set(roomSize.getX() * i, new Tile(IDToTilePos(roomSize.getX() * i), Tile.TileType.blueWall, this, 3));
				tiles.set(roomSize.getX() * i + 15, new Tile(IDToTilePos(roomSize.getX() * i + 15), Tile.TileType.blueWall, this, 1));
			}
		}
		
		for (int i = 1; i < 15; i++) {
			if (i == 7) {
				tiles.set(i, new Tile(IDToTilePos(i), Tile.TileType.blueWallHalf, this, 0));
				tiles.set(roomSize.getBothMuli() - i - 1, new Tile(IDToTilePos(roomSize.getBothMuli() - i - 1), Tile.TileType.blueWallHalf, this, 3));
			} else if (i == 8) {
				tiles.set(i, new Tile(IDToTilePos(i), Tile.TileType.blueWallHalf, this, 1));
				tiles.set(roomSize.getBothMuli() - i - 1, new Tile(IDToTilePos(roomSize.getBothMuli() - i - 1), Tile.TileType.blueWallHalf, this, 2));
			} else {
				tiles.set(i, new Tile(IDToTilePos(i), Tile.TileType.blueWall, this, 0));
				tiles.set(roomSize.getBothMuli() - i - 1, new Tile(IDToTilePos(roomSize.getBothMuli() - i - 1), Tile.TileType.blueWall, this, 2));
			}
		}
	}
	
	public TilePos IDToTilePos(int id) {
		int ytt = MathHelper.floor((double) id / roomSize.getX());
		int xtt = id - (ytt * roomSize.getX());
		
		return new TilePos(xtt, ytt);
	}
	
	public void renderTilesAsText() {
		StringBuilder b = new StringBuilder();
		int ti = 0;
		for (int i = 0; i < tiles.size(); i++) {
			ti++;
			
			if (ti == roomSize.getX()) {
				ti = 0;
				if (tiles.get(i).getTileType() != Tile.TileType.air) {
					b.append("O");
				} else {
					b.append("X");
				}
				
				Console.print(b.toString());
				b = new StringBuilder();
			} else {
				if (tiles.get(i).getTileType() != Tile.TileType.air) {
					b.append("O,");
				} else {
					b.append("X,");
				}
			}
		}
		
		b = new StringBuilder();
		for (int i = 1; i < roomSize.getX(); i++) {
			b.append("--");
		}
		b.append("-");
		
		Console.print(b.toString());
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
	
	public List<Tile> getTiles() {
		return tiles;
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
}
