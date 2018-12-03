package main.java.lotf.items;

import main.java.lotf.items.util.Item;

public final class ItemEmpty extends Item {

	public ItemEmpty() {
		super("empty", InventoryType.hidden);
	}
	
	@Override
	protected void onUse() {
		
	}
}
