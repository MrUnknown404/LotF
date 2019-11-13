package main.java.lotf.items.util;

import main.java.lotf.util.Console;

public abstract class ItemBase {
	
	protected final ItemInfo info;
	
	public ItemBase(ItemInfo info) {
		this.info = info;
		Console.print(Console.WarningType.RegisterDebug, getKey() + " was registered!");
	}
	
	public String getName() {
		return info.getName();
	}
	
	public String getDescription() {
		return info.getDescription();
	}
	
	public String getKey() {
		return info.getLangKey().getKey();
	}
	
	public ItemInfo getInfo() {
		return info;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemBase) {
			ItemBase item = (ItemBase) obj;
			if (item.getName() == getName()) {
				return true;
			}
		}
		
		return false;
	}
}
