package main.java.lotf.entities.util;

import main.java.lotf.entities.ai.AITypeAttack;
import main.java.lotf.entities.ai.AITypeMovement;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public abstract class EntityLiving extends Entity implements IDamageable {
	
	protected AITypeMovement movementAI;
	protected AITypeAttack attackAI;
	
	protected EnumDirection dir = EnumDirection.nil;
	
	public EntityLiving(Vec2f pos, Vec2i size, int totalHearts) {
		super(pos, size);
		setupHealth(totalHearts);
		setupAIs();
	}
	
	protected abstract void setupAIs();
	
	public void attemptMovement() {
		if (movementAI != null) {
			movementAI.attemptAction();
		}
	}
	
	public void attemptAttack() {
		if (attackAI != null) {
			attackAI.attemptAction();
		}
	}
	
	public EnumDirection getDirection() {
		return dir;
	}
}
