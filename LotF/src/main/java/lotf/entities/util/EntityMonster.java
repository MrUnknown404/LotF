package main.java.lotf.entities.util;

import main.java.lotf.entities.ai.AIType;
import main.java.lotf.world.Room;
import main.java.ulibs.utils.math.Vec2f;
import main.java.ulibs.utils.math.Vec2i;

public abstract class EntityMonster extends EntityLiving {
	
	protected AIType<? extends Entity> movementAI, attackAI, spotAI;
	
	protected EntityMonster(EntityInfo info, Room room, Vec2f pos, Vec2i size, int totalHearts) {
		super(info, room, pos, size, totalHearts);
		spotAI = setSpotAI();
		movementAI = setMovementAI();
		attackAI = setAttackAI();
	}
	
	@Override
	public void tick() {
		if (attemptSpot()) {
			spotAI.action();
			
			if (attemptAttack()) {
				attackAI.action();
			}
		}
		
		if (attemptMovement()) {
			movementAI.action();
		}
	}
	
	@Override
	public void reset() {
		super.reset();
		if (movementAI != null) {
			movementAI.reset();
		}
		if (spotAI != null) {
			spotAI.reset();
		}
		if (attackAI != null) {
			attackAI.reset();
		}
	}
	
	public final boolean attemptMovement() {
		return movementAI != null ? movementAI.attemptAction() : false;
	}
	
	public final boolean attemptSpot() {
		return spotAI != null ? spotAI.attemptAction() : false;
	}
	
	public final boolean attemptAttack() {
		return attackAI != null ? attackAI.attemptAction() : false;
	}
	
	protected abstract AIType<? extends Entity> setMovementAI();
	protected abstract AIType<? extends Entity> setSpotAI();
	protected abstract AIType<? extends Entity> setAttackAI();
	
	@Override
	public boolean isWalking() {
		return movementAI != null ? movementAI.isActionRunning() : false;
	}
}
