package main.java.lotf.items;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.items.swords.ItemSword;

public abstract class Item {

	private static List<Item> items = new ArrayList<Item>();
	
	public static final Item EMPTY = null;
	public static final Item BOW = new ItemBow();
	public static final Item CAPE = new ItemCape();
	
	public static final ItemSword SWORD = new ItemSword("sword", 1);
	
	private final String name, description;
	
	public Item(String name, String description) {
		this.name = name;
		this.description = description;
		
		items.add(this);
	}
	
	public abstract void onUse();
	
	public String getName() {
		return name;
	}
	
	public static int getItemsSize() {
		return items.size();
	}
	
	public static Item getItem(int i) {
		return items.get(i);
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
