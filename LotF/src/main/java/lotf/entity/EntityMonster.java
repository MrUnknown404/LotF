package main.java.lotf.entity;

import main.java.lotf.entity.ai.AIMonsterBase;
import main.java.lotf.entity.ai.AITimedWander;

public class EntityMonster extends Entity {

	protected boolean isAlive = true;
	protected MonsterType monsterType;
	
	public EntityMonster(int x, int y, int width, int height, MonsterType monsterType) {
		super(x, y, width, height, EntityType.monster);
		this.monsterType = monsterType;
		this.name = monsterType.toString();
		
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
		testWander(0, 2, new AITimedWander(), 1);
		
		public final int fId, count, moveSpeed;
		public AIMonsterBase ai;
		
		private MonsterType(int id, int count, AIMonsterBase ai, int moveSpeed) {
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
