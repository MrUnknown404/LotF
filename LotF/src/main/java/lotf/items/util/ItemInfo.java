package main.java.lotf.items.util;

import main.java.lotf.items.rings.Ring.RingType;

public enum ItemInfo {
	item_bow("Bow", "Shoots arrows (write later)"),
	item_cape("Cape", "Lets you jump (write later)"),
	
	sword_starterSword("Wooden Sword", "It's a sword why do i have to explain this? (write later)", 2),
	
	ring_basic("Basic Ring", "write later", RingType.passive),
	ring_archer1("Archer 1 Ring", "write later", RingType.passive),
	
	collectable_blueFly("blueFly", "write later"),
	collectable_redFly("redFly", "write later"),
	collectable_greenFly("greenFly", "write later"),
	collectable_spikyBeetle("spikyBeetle", "write later"),
	collectable_lavaBeetle("lavaBeetle", "write later"),
	collectable_butterfly("butterfly", "write later");
	
	protected final String name, description;
	protected final Object[] data;
	
	private ItemInfo(String name, String description, Object... data) {
		this.name = name;
		this.description = description;
		this.data = data;
	}
	
	public static ItemInfo find(String name) {
		for (ItemInfo info : values()) {
			if (info.toString().equalsIgnoreCase(name)) {
				return info;
			}
		}
		
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Object[] getData() {
		return data;
	}
}
