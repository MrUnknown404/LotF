package main.java.lotf.items.util;

import java.util.Map;

public abstract class ItemBase {
	
	protected final ItemInfo info;
	
	public ItemBase(ItemInfo info) {
		this.info = info;
	}
	
	public String getName() {
		return info.getName();
	}
	
	public String getDescription() {
		return info.getDescription();
	}
	
	public Map<String, Object> getData() {
		return info.getData();
	}
	
	public String getKey() {
		return info.langKey.getKey();
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
