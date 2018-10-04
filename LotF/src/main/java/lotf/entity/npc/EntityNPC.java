package main.java.lotf.entity.npc;

import main.java.lotf.entity.Entity;

public abstract class EntityNPC extends Entity {

	protected boolean isAlive;
	protected NPCType type;
	
	public EntityNPC(int x, int y, int width, int height, EntityType type, NPCType type2) {
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
	
	public NPCType getNPCType() {
		return type;
	}
	
	public enum NPCType {
		test1(0, 4);
		
		public final int fId, count;
		
		private NPCType(int id, int count) {
			this.fId = id;
			this.count = count;
		}
		
		public static NPCType getFromNumber(int id) {
			for (NPCType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
