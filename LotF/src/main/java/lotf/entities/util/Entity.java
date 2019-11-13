package main.java.lotf.entities.util;

import main.java.lotf.tile.Tile;
import main.java.lotf.util.GameObject;
import main.java.lotf.util.IResetable;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.math.MathH;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public abstract class Entity extends GameObject implements ITickable, IResetable {
	
	protected EntityInfo info;
	protected EnumDirection facing = EnumDirection.north;
	protected Room room;
	
	public Entity(EntityInfo info, Room room, Vec2f pos, Vec2i size) {
		super(new Vec2f(room.getPosX() + pos.getX(), room.getPosY() + pos.getY()), size);
		this.info = info;
		this.room = room;
	}
	
	public EntityInfo getInfo() {
		return info;
	}
	
	public void kill() {
		room.killEntity(this);
	}
	
	public Room getRoom() {
		return room;
	}
	
	@Override
	public void addPosX(float x) {
		if (x > 0) {
			facing = EnumDirection.east;
		} else {
			facing = EnumDirection.west;
		}
		
		pos.addX(x);
		pos.setX(MathH.clamp(pos.getX(), getRoom().getPosX(), getRoom().getPosX() + (getRoom().getWidth() * Tile.TILE_SIZE) - size.getX()));
	}
	
	@Override
	public void addPosY(float y) {
		if (y > 0) {
			facing = EnumDirection.south;
		} else {
			facing = EnumDirection.north;
		}
		
		pos.addY(y);
		pos.setY(MathH.clamp(pos.getY(), getRoom().getPosY(), getRoom().getPosY() + (getRoom().getHeight() * Tile.TILE_SIZE) - size.getY()));
	}
}
