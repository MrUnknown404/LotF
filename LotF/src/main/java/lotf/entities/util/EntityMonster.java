package main.java.lotf.entities.util;

import main.java.lotf.entities.ai.util.AIType;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public abstract class EntityMonster extends EntityLiving {
	
	protected AIType<? extends Entity> movementAI, attackAI, spotAI;
	
	public EntityMonster(EntityInfo info, Room room, Vec2f pos, Vec2i size, int totalHearts) {
		super(info, room, pos, size, totalHearts);
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
	
	public boolean attemptMovement() {
		if (movementAI != null) {
			return movementAI.attemptAction();
		} else {
			return false;
		}
	}
	
	public boolean attemptSpot() {
		if (spotAI != null) {
			return spotAI.attemptAction();
		} else {
			return false;
		}
	}
	
	public boolean attemptAttack() {
		if (attackAI != null) {
			return attackAI.attemptAction();
		} else {
			return false;
		}
	}
	
	protected abstract AIType<? extends Entity> setMovementAI();
	protected abstract AIType<? extends Entity> setSpotAI();
	protected abstract AIType<? extends Entity> setAttackAI();
}
