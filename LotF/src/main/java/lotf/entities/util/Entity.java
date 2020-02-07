package main.java.lotf.entities.util;

import main.java.lotf.tile.Tile;
import main.java.lotf.util.GameObject;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.math.MathH;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public abstract class Entity extends GameObject implements ITickable {
	
	protected final EntityInfo info;
	protected EnumDirection facing = EnumDirection.north;
	protected Room room;
	private Vec2f startPos;
	
	protected Entity(EntityInfo info, Room room, Vec2f pos, Vec2i size) {
		super(new Vec2f(room.getPosX() + pos.getX(), room.getPosY() + pos.getY()), size);
		this.info = info;
		this.room = room;
		startPos = pos;
	}
	
	public void reset() {
		setPos(new Vec2f(room.getPosX() + startPos.getX(), room.getPosY() + startPos.getY()));
	}
	
	public void kill() {
		room.killEntity(this);
	}
	
	@Override
	public void addPosX(float x) {
		facing = x > 0 ? EnumDirection.east : EnumDirection.west;
		
		pos.addX(x);
		pos.setX(MathH.clamp(pos.getX(), room.getPosX(), room.getPosX() + (room.getWidth() * Tile.TILE_SIZE) - size.getX()));
	}
	
	@Override
	public void addPosY(float y) {
		facing = y > 0 ? EnumDirection.south : EnumDirection.north;
		
		pos.addY(y);
		pos.setY(MathH.clamp(pos.getY(), room.getPosY(), room.getPosY() + (room.getHeight() * Tile.TILE_SIZE) - size.getY()));
	}
	
	public Room getRoom() {
		return room;
	}
	
	public EntityInfo getInfo() {
		return info;
	}
}
