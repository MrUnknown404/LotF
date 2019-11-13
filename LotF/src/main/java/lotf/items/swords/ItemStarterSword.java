package main.java.lotf.items.swords;

import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.items.util.ItemInfo;

public class ItemStarterSword extends ItemSwordBase {

	public ItemStarterSword() {
		super(ItemInfo.sword_starterSword, 10, true, 10, 2);
	}
	
	@Override
	public void onUse(EntityPlayer user) {
		
	}
}
