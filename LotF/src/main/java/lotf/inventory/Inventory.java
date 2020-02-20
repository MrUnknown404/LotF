package main.java.lotf.inventory;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.items.util.Item;
import main.java.lotf.util.Console;
import main.java.lotf.util.math.Vec2i;

public class Inventory<I extends Item> {

	protected List<I> items = new ArrayList<I>();
	protected Vec2i size;
	
	public Inventory(Vec2i size) {
		this.size = size;
		
		for (int i = 0; i < size.getBothMulti(); i++) {
			items.add(null);
		}
	}
	
	public boolean addItem(I item) {
		if (items.contains(item)) {
			Console.print(Console.WarningType.Warning, "Player already has that Item!");
			return false;
		}
		
		if (items.indexOf(null) != -1) {
			items.set(items.indexOf(null), item);
			return true;
		}
		
		return false;
	}
	
	public void setItem(int where, I item) {
		items.set(where, item);
	}
	
	public Vec2i getSize() {
		return size;
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
	
	public I getItem(int i) {
		return items.get(i);
	}
}
