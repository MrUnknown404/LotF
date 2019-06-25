package main.java.lotf.items;

public abstract class Item {
	
	protected final String name, description;
	
	public Item(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public abstract void onUse();
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item) {
			Item item = (Item) obj;
			if (item.name == name) {
				return true;
			}
		}
		
		return false;
	}
}
