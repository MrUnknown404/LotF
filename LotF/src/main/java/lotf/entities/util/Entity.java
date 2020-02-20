package main.java.lotf.entities.util;

import java.awt.Rectangle;

import main.java.lotf.tile.Tile;
import main.java.lotf.util.GameObject;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumCollisionType;
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
		x = prepareX(x);
		
		pos.addX(x);
		pos.setX(MathH.clamp(pos.getX(), room.getPosX(), room.getPosX() + (room.getWidth() * Tile.TILE_SIZE) - size.getX()));
	}
	
	@Override
	public void addPosY(float y) {
		facing = y > 0 ? EnumDirection.south : EnumDirection.north;
		y = prepareY(y);
		
		pos.addY(y);
		pos.setY(MathH.clamp(pos.getY(), room.getPosY(), room.getPosY() + (room.getHeight() * Tile.TILE_SIZE) - size.getY()));
	}
	
	protected float prepareX(float x) {
		for (Tile t : room.getTileLayers().get(1).get()) {
			if (t == null || t.getTileInfo().getCollisionType() == EnumCollisionType.none) {
				continue;
			}
			
			Rectangle b = getBounds();
			if (t.getBounds().intersects(b.x + (x > 0 ? MathH.floor(x) : MathH.ceil(x)), b.y, b.width, b.height)) {
				if (x > 0) {
					return pos.getX() + getWidth() - t.getPosX() - (x - MathH.floor(x));
				}
				return pos.getX() - t.getPosX() - Tile.TILE_SIZE - (x - MathH.floor(x));
			}
		}
		
		return x;
	}
	
	protected float prepareY(float y) {
		for (Tile t : room.getTileLayers().get(1).get()) {
			if (t == null || t.getTileInfo().getCollisionType() == EnumCollisionType.none) {
				continue;
			}
			
			Rectangle b = getBounds();
			if (t.getBounds().intersects(b.x, b.y + (y > 0 ? MathH.floor(y) : MathH.ceil(y)), b.width, b.height)) {
				if (y > 0) {
					return pos.getY() + getHeight() - t.getPosY();
				}
				return pos.getY() - t.getPosY() - Tile.TILE_SIZE;
			}
		}
		
		return y;
	}
	
	public EnumDirection getFacing() {
		return facing;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public EntityInfo getInfo() {
		return info;
	}
}
