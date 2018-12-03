package main.java.lotf.items;

import main.java.lotf.items.util.Item;

public class ItemCape extends Item {

	private int level;
	
	public ItemCape(int level) {
		super("cape", level, 1, InventoryType.normal);
		this.level = level;
	}
	
	@Override
	protected void onUse() {
		
	}
	
	public int getLevel() {
		return level;
	}
}
