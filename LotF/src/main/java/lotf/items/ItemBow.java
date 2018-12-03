package main.java.lotf.items;

import main.java.lotf.items.util.Ammo;
import main.java.lotf.items.util.Item;

public class ItemBow extends Item {

	private int damage = 3;
	
	public ItemBow(int level) {
		super("bow", new Ammo(Ammo.AmmoType.arrow, level), level, 4, InventoryType.normal);
	}
	
	@Override
	protected void onUse() {
		
	}
	
	public int getDamage() {
		return damage;
	}
}
