package main.java.lotf.items.util;

import main.java.lotf.util.EnumDungeonType;

public abstract class ItemDungeon extends Item {

	private EnumDungeonType type = EnumDungeonType.one;
	
	public ItemDungeon(String name, EnumDungeonType type) {
		super(name, type.fId, EnumDungeonType.values().length, InventoryType.hidden);
		this.type = type;
	}
	
	public EnumDungeonType getDungeonType() {
		return type;
	}
}
