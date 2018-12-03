package main.java.lotf.items;

import main.java.lotf.items.util.Item;

public class ItemHammer extends Item {

	protected int damage = 2;
	
	public ItemHammer() {
		super("hammer", InventoryType.normal);
	}
	
	@Override
	protected void onUse() {
		
	}
}
