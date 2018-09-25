package main.java.lotf.entity;

import main.java.lotf.tile.Tile;
import main.java.lotf.util.TickableGameObject;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public abstract class Entity extends TickableGameObject {
	
	protected Room room;
	protected boolean isAlive;
	protected Vec2i relativePos = new Vec2i();
	
	protected String stringID, name;
	protected int meta = -1;
	
	public int count;
	
	public Entity(int x, int y, int width, int height, Room room, boolean isAlive, String stringID, int count) {
		super(x, y, width, height);
		this.room = room;
		this.isAlive = isAlive;
		this.stringID = stringID;
		this.count = count;
		this.name = stringID.substring(4, stringID.length());
	}
	
	public Entity(int x, int y, int width, int height, boolean isAlive, String stringID, int count) {
		super(x, y, width, height);
		this.isAlive = isAlive;
		this.stringID = stringID;
		this.count = count;
		this.name = stringID.substring(4, stringID.length());
	}
	
	@Override
	public void tick() {
		if (isAlive) {
			tickAlive();
			
			if (getPositionX() != ((room.getRoomPos().getX() * Room.ROOM_SIZE.getX()) * Tile.TILE_SIZE) + getRelativePos().getX() || getPositionY() != ((room.getRoomPos().getY() * Room.ROOM_SIZE.getY()) * Tile.TILE_SIZE) + getRelativePos().getY()) {
				updatePosition();
			}
		}
	}
	
	public abstract void tickAlive();
	
	public void updatePosition() {
		setPosition(((room.getRoomPos().getX() * Room.ROOM_SIZE.getX()) * Tile.TILE_SIZE) + getRelativePos().getX(), ((room.getRoomPos().getY() * Room.ROOM_SIZE.getY()) * Tile.TILE_SIZE) + getRelativePos().getY());
	}
	
	public void setRelativePos(int x, int y) {
		relativePos = new Vec2i(x, y);
	}
	
	public void setRelativePos(Vec2i vec) {
		relativePos = vec;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	
	public void addRelativePos(Vec2i vec) {
		relativePos = relativePos.add(vec);
	}
	
	public void addRelativePos(int x, int y) {
		relativePos = relativePos.add(x, y);
	}
	
	public Room getRoom() {
		return room;
	}
	
	public Vec2i getRelativePos() {
		return relativePos;
	}
	
	public String getName() {
		return name;
	}
	
	public String getStringID() {
		return stringID;
	}
	
	public int getMeta() {
		return meta;
	}
	
	public boolean getIsAlive() {
		return isAlive;
	}
	
	@Override
	public String toString() {
		return "(" + getStringID() + ")";
	}
}
