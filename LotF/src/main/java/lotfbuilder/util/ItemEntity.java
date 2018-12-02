package main.java.lotfbuilder.util;

import main.java.lotf.entity.Entity;
import main.java.lotf.entity.EntityMonster;
import main.java.lotf.entity.EntityNPC;
import main.java.lotf.items.util.Item;

public class ItemEntity extends Item {

	private Entity.EntityType type1;
	private EntityMonster.MonsterType type2;
	private EntityNPC.NPCType type3;
	
	public ItemEntity(EntityMonster.MonsterType type2, int meta) {
		super(type2.toString(), meta, type2.count, -99);
		this.type1 = Entity.EntityType.monster;
		this.type2 = type2;
	}
	
	public ItemEntity(EntityNPC.NPCType type3, int meta) {
		super(type3.toString(), meta, type3.count, -99);
		this.type1 = Entity.EntityType.npc;
		this.type3 = type3;
	}
	
	@Override
	protected void onUse() {
		
	}
	
	public Entity.EntityType getEntityType() {
		return type1;
	}
	
	public EntityMonster.MonsterType getMonsterType() {
		return type2;
	}
	
	public EntityNPC.NPCType getNPCType() {
		return type3;
	}
}
