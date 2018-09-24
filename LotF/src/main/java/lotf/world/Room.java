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

public class Room {
	
	public static final Vec2i ROOM_SIZE = new Vec2i(16, 9);
	
	protected List<Tile> tiles = new ArrayList<Tile>(ROOM_SIZE.getBothMuli());
	protected List<Entity> entities = new ArrayList<Entity>(ROOM_SIZE.getBothMuli());
	
	protected RoomPos roomPos = new RoomPos();
	
	public Room(RoomPos roomPos) {
		this.roomPos = roomPos;
		for (int i = 0; i < ROOM_SIZE.getBothMuli(); i++) {
			tiles.add(Tile.AIR);
		}
		
		generateDefaultRoom();
	}
	
	public void tick() {
		for (int i = 0; i < tiles.size(); i++) {
			tiles.get(i).tick();
		}
		
		if (!entities.isEmpty()) {
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).tick();
			}
		}
	}
	
	public void generateDefaultRoom() {
		tiles.set(0, new Tile(IDToTilePos(0), Tile.TileType.blueWallCorner, this, 0));
		tiles.set(15, new Tile(IDToTilePos(15), Tile.TileType.blueWallCorner, this, 1));
		tiles.set(128, new Tile(IDToTilePos(128), Tile.TileType.blueWallCorner, this, 3));
		tiles.set(143, new Tile(IDToTilePos(143), Tile.TileType.blueWallCorner, this, 2));
		
		for (int i = 1; i < 8; i++) {
			if (i != 4) {
				tiles.set(ROOM_SIZE.getX() * i, new Tile(IDToTilePos(ROOM_SIZE.getX() * i), Tile.TileType.blueWall, this, 3));
				tiles.set(ROOM_SIZE.getX() * i + 15, new Tile(IDToTilePos(ROOM_SIZE.getX() * i + 15), Tile.TileType.blueWall, this, 1));
			}
		}
		
		for (int i = 1; i < 15; i++) {
			if (i == 7) {
				tiles.set(i, new Tile(IDToTilePos(i), Tile.TileType.blueWallHalf, this, 0));
				tiles.set(ROOM_SIZE.getBothMuli() - i - 1, new Tile(IDToTilePos(ROOM_SIZE.getBothMuli() - i - 1), Tile.TileType.blueWallHalf, this, 3));
			} else if (i == 8) {
				tiles.set(i, new Tile(IDToTilePos(i), Tile.TileType.blueWallHalf, this, 1));
				tiles.set(ROOM_SIZE.getBothMuli() - i - 1, new Tile(IDToTilePos(ROOM_SIZE.getBothMuli() - i - 1), Tile.TileType.blueWallHalf, this, 2));
			} else {
				tiles.set(i, new Tile(IDToTilePos(i), Tile.TileType.blueWall, this, 0));
				tiles.set(ROOM_SIZE.getBothMuli() - i - 1, new Tile(IDToTilePos(ROOM_SIZE.getBothMuli() - i - 1), Tile.TileType.blueWall, this, 2));
			}
		}
	}
	
	public TilePos IDToTilePos(int id) {
		int ytt = MathHelper.floor((double) id / ROOM_SIZE.getX());
		int xtt = id - (ytt * ROOM_SIZE.getX());
		
		return new TilePos(xtt, ytt);
	}
	
	public void renderTilesAsText() {
		StringBuilder b = new StringBuilder();
		int ti = 0;
		for (int i = 0; i < tiles.size(); i++) {
			ti++;
			
			if (ti == ROOM_SIZE.getX()) {
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
		for (int i = 1; i < ROOM_SIZE.getX(); i++) {
			b.append("--");
		}
		b.append("-");
		
		Console.print(b.toString());
	}
	
	@Override
	public String toString() {
		return roomPos.toString();
	}
	
	public RoomPos getRoomPos() {
		return roomPos;
	}
	
	public List<Tile> getTiles() {
		return tiles;
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((getRoomPos().getX() * Room.ROOM_SIZE.getX()) * Tile.TILE_SIZE, 
				(getRoomPos().getY() * Room.ROOM_SIZE.getY()) * Tile.TILE_SIZE, 
				Room.ROOM_SIZE.getX() * Tile.TILE_SIZE, 
				Room.ROOM_SIZE.getY() * Tile.TILE_SIZE);
	}
	
	public Rectangle getActiveBounds() {
		return new Rectangle((getRoomPos().getX() * (Room.ROOM_SIZE.getX() - 2)) * Tile.TILE_SIZE,
				(getRoomPos().getY() * (Room.ROOM_SIZE.getY() - 2)) * Tile.TILE_SIZE,
				(Room.ROOM_SIZE.getX() + 4) * Tile.TILE_SIZE, (Room.ROOM_SIZE.getY() + 4) * Tile.TILE_SIZE);
	}
	
	public Rectangle getBoundsNorth() {
		return new Rectangle(
				(getRoomPos().getX() * Room.ROOM_SIZE.getX()) * Tile.TILE_SIZE + (Tile.TILE_SIZE / 2),
				(getRoomPos().getY() * Room.ROOM_SIZE.getY()) * Tile.TILE_SIZE,
				Room.ROOM_SIZE.getX() * Tile.TILE_SIZE - Tile.TILE_SIZE, 1);
	}
	
	public Rectangle getBoundsEast() {
		return new Rectangle(
				((getRoomPos().getX() * Room.ROOM_SIZE.getX()) * Tile.TILE_SIZE) + (Room.ROOM_SIZE.getX() * Tile.TILE_SIZE) - 1,
				(getRoomPos().getY() * Room.ROOM_SIZE.getY()) * Tile.TILE_SIZE + (Tile.TILE_SIZE / 2),
				1, Room.ROOM_SIZE.getY() * Tile.TILE_SIZE - Tile.TILE_SIZE);
	}
	
	public Rectangle getBoundsSouth() {
		return new Rectangle(
				(getRoomPos().getX() * Room.ROOM_SIZE.getX()) * Tile.TILE_SIZE + (Tile.TILE_SIZE / 2),
				((getRoomPos().getY() * Room.ROOM_SIZE.getY()) * Tile.TILE_SIZE) + (Room.ROOM_SIZE.getY() * Tile.TILE_SIZE) - 1,
				Room.ROOM_SIZE.getX() * Tile.TILE_SIZE - Tile.TILE_SIZE, 1);
	}
	
	public Rectangle getBoundsWest() {
		return new Rectangle(
				(getRoomPos().getX() * Room.ROOM_SIZE.getX()) * Tile.TILE_SIZE,
				(getRoomPos().getY() * Room.ROOM_SIZE.getY()) * Tile.TILE_SIZE + (Tile.TILE_SIZE / 2),
				1, Room.ROOM_SIZE.getY() * Tile.TILE_SIZE - Tile.TILE_SIZE);
	}
}
