package main.java.lotf.items;

import main.java.lotf.items.util.IItemUseable;
import main.java.lotf.items.util.ItemBase;
import main.java.lotf.items.util.ItemInfo;

public class ItemCape extends ItemBase implements IItemUseable {

	public ItemCape() {
		super(ItemInfo.item_cape);
	}
	
	@Override
	public void onUse() {
		
	}
}
