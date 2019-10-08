package main.java.lotf.items.swords;

import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.items.util.ItemInfo;
import main.java.lotf.items.util.ItemUseable;

public class ItemStarterSword extends ItemUseable {

	public ItemStarterSword() {
		super(ItemInfo.sword_starterSword, 10, true);
	}
	
	@Override
	public void onUse(EntityPlayer user) {
		
	}
}
