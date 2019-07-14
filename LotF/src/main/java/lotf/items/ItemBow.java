package main.java.lotf.items;

import main.java.lotf.items.util.IItemUseable;
import main.java.lotf.items.util.ItemBase;
import main.java.lotf.items.util.ItemInfo;

public class ItemBow extends ItemBase implements IItemUseable {

	public ItemBow() {
		super(ItemInfo.item_bow);
	}
	
	@Override
	public void onUse() {
		
	}
}
