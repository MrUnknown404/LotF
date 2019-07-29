package main.java.lotf.entities.util;

import main.java.lotf.entities.ai.AITypeAttack;
import main.java.lotf.entities.ai.AITypeMovement;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public abstract class EntityMonster extends EntityLiving {
	
	protected AITypeMovement movementAI;
	protected AITypeAttack attackAI;
	
	public EntityMonster(EntityInfo info, Vec2f pos, Vec2i size, int totalHearts) {
		super(info, pos, size, totalHearts);
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
}
