package main.java.lotf.entities;

import main.java.lotf.entities.ai.AIType;
import main.java.lotf.entities.ai.movement.AiMovementRandom;
import main.java.lotf.entities.util.Entity;
import main.java.lotf.entities.util.EntityInfo;
import main.java.lotf.entities.util.EntityMonster;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public class EntityEnemyTest extends EntityMonster {

	public EntityEnemyTest(EntityInfo info, Room room, Vec2f pos, Vec2i size, int totalHearts) {
		super(info, room, pos, size, totalHearts);
	}
	
	@Override
	public void tick() {
		super.tick();
	}
	
	@Override
	public void softReset() {
		
	}
	
	@Override
	public void hardReset() {
		
	}
	
	@Override
	protected AIType<EntityEnemyTest> setMovementAI() {
		return new AiMovementRandom<EntityEnemyTest>(this, 1);
	}
	
	@Override
	protected AIType<EntityEnemyTest> setAttackAI() {
		return null;
	}
	
	@Override
	protected AIType<? extends Entity> setSpotAI() {
		return null;
	}
}
