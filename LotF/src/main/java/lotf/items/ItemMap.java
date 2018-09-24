package main.java.lotf.items;

import main.java.lotf.items.util.ItemDungeon;
import main.java.lotf.util.EnumDungeonType;

public class ItemMap extends ItemDungeon {
	
	public ItemMap(EnumDungeonType type) {
		super("map", type);
	}
	
	@Override
	protected void onUse() {
		
	}
}
