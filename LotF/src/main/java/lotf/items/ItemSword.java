package main.java.lotf.items;

import main.java.lotf.items.util.Item;

public class ItemSword extends Item {

	private int damage, level, speed;
	
	public ItemSword(int level) {
		super("sword", level, 5, InventoryType.sword);
		this.level = level;
		
		if (level == 0) {
			damage = 2;
			speed = 2;
		} else if (level == 1) {
			damage = 1;
			speed = 1;
		} else if (level == 2) {
			damage = 4;
			speed = 2;
		} else if (level == 3) {
			damage = 8;
			speed = 3;
		} else if (level == 4) {
			damage = 6;
			speed = 2;
		}
	}
	
	@Override
	protected void onUse() {
		
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getSpeed() {
		return speed;
	}
}
