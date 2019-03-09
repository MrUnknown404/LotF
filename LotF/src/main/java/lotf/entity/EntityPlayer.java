package main.java.lotf.entity;

import main.java.lotf.util.math.Vec2f;

public class EntityPlayer extends Entity {

	public EntityPlayer(Vec2f pos) {
		super(pos, 16, 16, 3);
	}
	
	@Override
	public void tick() {
		super.tick();
		
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
}
