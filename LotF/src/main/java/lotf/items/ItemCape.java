package main.java.lotf.items;

import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.items.util.ItemInfo;
import main.java.lotf.items.util.ItemUseable;

public class ItemCape extends ItemUseable {

	public ItemCape() {
		super(ItemInfo.item_cape, 0, false, 0);
	}
	
	@Override
	public void onUse(EntityPlayer user) {
		
	}
}
