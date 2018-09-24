package main.java.lotf.items;

import main.java.lotf.items.util.Ammo;
import main.java.lotf.items.util.Item;

public class ItemBombBag extends Item {

	private int level;
	
	public ItemBombBag(int level) {
		super("bombBag", new Ammo(Ammo.AmmoType.bomb, level), level, 4, 0);
		this.level = level;
	}
	
	@Override
	protected void onUse() {
		
	}
	
	public int getLevel() {
		return level;
	}
}
