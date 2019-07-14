package main.java.lotf.items.util;

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
	
	public Object[] getData() {
		return info.getData();
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
