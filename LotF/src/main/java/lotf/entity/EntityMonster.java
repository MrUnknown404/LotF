package main.java.lotf.entity;

import main.java.lotf.entity.ai.AIBase;
import main.java.lotf.entity.ai.AITimedWander;

public class EntityMonster extends Entity {

	protected boolean isAlive = true;
	protected MonsterType monsterType;
	
	public EntityMonster(int x, int y, int width, int height, MonsterType monsterType) {
		super(x, y, width, height, EntityType.monster);
		this.monsterType = monsterType;
		
		monsterType.ai.setEntity(this);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (isAlive) {
			tickAlive();
			monsterType.ai.tick();
		}
	}
	
	public void tickAlive() {
		
	}
	
	public boolean getIsAlive() {
		return isAlive;
	}
	
	public MonsterType getMonsterType() {
		return monsterType;
	}
	
	public enum MonsterType {
		test1(0, 4, new AITimedWander(), 1);
		
		public final int fId, count, moveSpeed;
		public AIBase ai;
		
		private MonsterType(int id, int count, AIBase ai, int moveSpeed) {
			this.fId = id;
			this.count = count;
			this.ai = ai;
			this.moveSpeed = moveSpeed;
		}
		
		public static MonsterType getFromNumber(int id) {
			for (MonsterType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
