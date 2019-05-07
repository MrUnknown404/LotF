package main.java.lotf.entity;

import main.java.lotf.util.IInteractable;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public class EntityNPC extends Entity implements IInteractable {

	public EntityNPC(Vec2f pos, Vec2i size) {
		super(pos, size);
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
	
	@Override
	public void onInteract() {
		
	}
}
