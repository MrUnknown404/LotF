package main.java.lotf.items;

import main.java.lotf.items.util.ItemDungeon;
import main.java.lotf.util.EnumDungeonType;

public class ItemCompass extends ItemDungeon {

	public ItemCompass(EnumDungeonType type) {
		super("compass", type);
	}
	
	@Override
	protected void onUse() {
		
	}
}
