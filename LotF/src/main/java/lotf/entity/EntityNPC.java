package main.java.lotf.entity;

import main.java.lotf.entity.ai.AIChest;
import main.java.lotf.entity.ai.AINPCBase;

public class EntityNPC extends Entity {

	protected boolean isAlive;
	protected NPCType npcType;
	
	public EntityNPC(int x, int y, int width, int height, NPCType npcType) {
		super(x, y, width, height, EntityType.npc);
		this.npcType = npcType;
		this.name = npcType.toString();
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (isAlive) {
			tickAlive();
		}
	}
	
	public void tickAlive() {
		
	}
	
	public boolean getIsAlive() {
		return isAlive;
	}
	
	public NPCType getNPCType() {
		return npcType;
	}
	
	public enum NPCType {
		chest(0, 2, new AIChest(), 0);
		
		public final int fId, count, moveSpeed;
		public AINPCBase ai;
		
		private NPCType(int id, int count, AINPCBase ai, int moveSpeed) {
			this.fId = id;
			this.count = count;
			this.ai = ai;
			this.moveSpeed = moveSpeed;
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
