package main.java.lotf.items;

import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.items.util.ItemUseable;
import main.java.lotf.util.LangKey.LangType;

public class ItemCape extends ItemUseable {

	public ItemCape() {
		super(LangType.item, "cape", 0, false, 0);
	}
	
	@Override
	public void onUse(EntityPlayer user) {
		
	}
}
