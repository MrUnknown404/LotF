package main.java.lotf.entity;

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
		test1(0, 2);
		
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
