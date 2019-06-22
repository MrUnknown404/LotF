package main.java.lotf.inventory;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.items.Item;
import main.java.lotf.util.Console;
import main.java.lotf.util.math.Vec2i;

public class Inventory {

	private List<Item> items = new ArrayList<Item>();
	private Vec2i size;
	
	public Inventory(Vec2i size) {
		this.size = size;
		
		for (int i = 0; i < size.getBothMulti(); i++) {
			items.add(Item.EMPTY);
		}
	}
	
	public boolean addItem(Item item) {
		if (items.contains(item)) {
			Console.print(Console.WarningType.Warning, "Player already has that Item!");
			return false;
		}
		
		if (items.indexOf(Item.EMPTY) != -1) {
			items.set(items.indexOf(Item.EMPTY), item);
			return true;
		}
		
		return false;
	}
	
	public int getSizeX() {
		return size.getX();
	}
	
	public int getSizeY() {
		return size.getY();
	}
	
	public int getItemSize() {
		return items.size();
	}
	
	public Item getItem(int i) {
		return items.get(i);
	}
}
