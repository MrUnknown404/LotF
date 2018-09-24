package main.java.lotf.items;

import main.java.lotf.items.util.ItemDungeon;
import main.java.lotf.util.EnumDungeonType;

public class ItemDungeonItem extends ItemDungeon {

	public ItemDungeonItem(EnumDungeonType type) {
		super("dungeonItem", type);
	}
	
	@Override
	protected void onUse() {
		
	}
}
