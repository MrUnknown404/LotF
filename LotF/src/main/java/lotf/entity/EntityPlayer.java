package main.java.lotf.entity;

import main.java.lotf.entity.util.Entity;
import main.java.lotf.entity.util.IDamageable;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public class EntityPlayer extends Entity implements IDamageable {

	private static final float MOVE_SPEED = 1.2f;
	
	private Room r;
	private int moveX, moveY;
	
	public EntityPlayer(Vec2f pos, Room r) {
		super(pos, new Vec2i(16, 16));
		this.r = r;
		setupHealth(3);
	}
	
	@Override
	public void tick() {
		if (moveX != 0) {
			addPosX(moveX * MOVE_SPEED);
		}
		
		if (moveY != 0) {
			addPosY(moveY * MOVE_SPEED);
		}
	}
	
	@Override
	public void softReset() {
		
	}
	
	@Override
	public void hardReset() {
		
	}
	
	public void addHeartContainer() {
		if (canAddHeartContainer()) {
			HEARTS.add(4);
		}
	}
	
	public boolean canAddHeartContainer() {
		if (HEARTS.size() < 24) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setMoveX(int moveX) {
		this.moveX = moveX;
	}
	
	public void setMoveY(int moveY) {
		this.moveY = moveY;
	}
	
	public Room getRoom() {
		return r;
	}
}
