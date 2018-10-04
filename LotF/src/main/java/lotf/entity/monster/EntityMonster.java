package main.java.lotf.entity.monster;

import main.java.lotf.entity.Entity;

public abstract class EntityMonster extends Entity {

	protected boolean isAlive;
	protected MonsterType type;
	
	public EntityMonster(int x, int y, int width, int height, EntityType type, MonsterType type2) {
		super(x, y, width, height, type);
		this.type = type2;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (isAlive) {
			tickAlive();
		}
	}
	
	public abstract void tickAlive();
	
	public boolean getIsAlive() {
		return isAlive;
	}
	
	public MonsterType getMonsterType() {
		return type;
	}
	
	public enum MonsterType {
		test1(0, 4);
		
		public final int fId, count;
		
		private MonsterType(int id, int count) {
			this.fId = id;
			this.count = count;
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
