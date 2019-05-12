package main.java.lotf.items.swords;

import main.java.lotf.items.Item;

public class ItemSword extends Item {

	protected int damage;
	
	public ItemSword(String name, int damage) {
		super(name);
		this.damage = damage;
	}
	
	@Override
	public void onUse() {
		
	}
}
