package main.java.lotf.entity;

import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public class EntityPlayer extends EntityLiving {

	private Room r;
	
	public EntityPlayer(Vec2f pos, Room r) {
		super(pos, new Vec2i(16, 16), 3);
		this.r = r;
	}
	
	@Override
	public void tick() {
		
	}
	
	@Override
	public void softReset() {
		
	}
	
	@Override
	public void hardReset() {
		
	}
	
	public void addHeartContainer() {
		if (canAddHeartContainer()) {
			hearts.add(4);
		}
	}
	
	public boolean canAddHeartContainer() {
		if (hearts.size() < 24) {
			return true;
		} else {
			return false;
		}
	}
	
	public Room getRoom() {
		return r;
	}
}
