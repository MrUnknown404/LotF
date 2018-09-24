package main.java.lotf.items;

import main.java.lotf.items.util.Ammo;
import main.java.lotf.items.util.Item;

public class ItemRCBombBag extends Item {

	private int level;
	
	public ItemRCBombBag(int level) {
		super("rcBombBag", new Ammo(Ammo.AmmoType.rcBomb, level), level, 4, 0);
		this.level = level;
	}
	
	@Override
	protected void onUse() {
		
	}
	
	public int getLevel() {
		return level;
	}
}
