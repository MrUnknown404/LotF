package main.java.lotf.entities;

import main.java.lotf.entities.ai.AIType;
import main.java.lotf.entities.ai.movement.AiMovementRandom;
import main.java.lotf.entities.ai.spot.AiSpotIgnore;
import main.java.lotf.entities.util.Entity;
import main.java.lotf.entities.util.EntityInfo;
import main.java.lotf.entities.util.EntityMonster;
import main.java.lotf.world.Room;
import main.java.ulibs.utils.math.Vec2f;
import main.java.ulibs.utils.math.Vec2i;

public class EntityEnemyTest extends EntityMonster {

	public EntityEnemyTest(Room room, Vec2f pos) {
		super(EntityInfo.ENEMY_TEST, room, pos, new Vec2i(14, 14), 1);
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
		return new AiSpotIgnore<EntityEnemyTest>(this);
	}
}
