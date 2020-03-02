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
		
		setPosX(getPosX() + x);
		setPosX(MathH.clamp(getPosX(), room.getPosX(), room.getPosX() + (room.getWidth() * Tile.TILE_SIZE) - getWidth()));
	}
	
	@Override
	public void addPosY(float y) {
		facing = y > 0 ? EnumDirection.south : EnumDirection.north;
		y = prepareY(y);
		
		setPosY(getPosY() + y);
		setPosY(MathH.clamp(getPosY(), room.getPosY(), room.getPosY() + (room.getHeight() * Tile.TILE_SIZE) - getHeight()));
	}
	
	protected float prepareX(float x) {
		for (Tile t : room.getTileLayers().get(1).get()) {
			if (t == null || t.getTileInfo().getCollisionType() == EnumCollisionType.none) {
				continue;
			}
			
			Rectangle b = getBounds();
			if (t.getBounds().intersects(b.x + (x > 0 ? MathH.ceil(x) : MathH.floor(x)), b.y, b.width, b.height)) {
				if (x > 0) {
					return -(getPosX() - t.getPosX() + getWidth());
				}
				
				return -(getPosX() - t.getPosX() - Tile.TILE_SIZE);
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
			if (t.getBounds().intersects(b.x, b.y + (y > 0 ? MathH.ceil(y) : MathH.floor(y)), b.width, b.height)) {
				if (y > 0) {
					return -(getPosY() - t.getPosY() + getHeight());
				}
				
				return -(getPosY() - t.getPosY() - Tile.TILE_SIZE);
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
