package main.java.lotf.entities.util;

import java.awt.Rectangle;

import main.java.lotf.tile.Tile;
import main.java.lotf.util.GameObject;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.annotation.UseGetter;
import main.java.lotf.util.enums.EnumCollisionType;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.math.MathH;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public abstract class Entity extends GameObject implements ITickable {
	
	protected final EntityInfo info;
	private final Vec2f startPos;
	
	@Deprecated @UseGetter({"getFacing"})
	private EnumDirection facing = EnumDirection.north;
	protected Room room;
	
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
		setFacing(x > 0 ? EnumDirection.east : EnumDirection.west);
		x = prepareX(x);
		
		setPosX(getPosX() + x);
		setPosX(MathH.clamp(getPosX(), room.getPosX(), room.getPosX() + (room.getWidth() * Tile.TILE_SIZE) - getWidth()));
	}
	
	@Override
	public void addPosY(float y) {
		setFacing(y > 0 ? EnumDirection.south : EnumDirection.north);
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
				int ii = t.getTileInfo().getCollisionType().getXmod();
				
				if (x > 0) {
					return -(getPosX() - t.getPosX() + getWidth()) - ii;
				}
				
				return -(getPosX() - t.getPosX() - t.getWidth() + ii);
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
				int ii = t.getTileInfo().getCollisionType().getYmod();
				
				if (y > 0) {
					return -(getPosY() - t.getPosY() + getHeight()) - ii;
				}
				
				return -(getPosY() - t.getPosY() - t.getHeight() + ii);
			}
		}
		
		return y;
	}
	
	protected void setFacing(EnumDirection facing) {
		this.facing = facing;
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
