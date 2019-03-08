package main.java.lotf.inventory;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.init.InitItems;
import main.java.lotf.items.ItemSword;
import main.java.lotf.items.util.Item;
import main.java.lotf.items.util.Slot;
import main.java.lotf.util.Console;

public class Inventory {
	
	protected List<Item> items;
	protected transient List<Slot> slotsList = new ArrayList<Slot>();
	protected transient int slots, slotsX, slotsY;
	
	public Inventory(int slotsX, int slotsY) {
		setupSlots(slotsX, slotsY);
		items = new ArrayList<Item>(slots);
		
		for (int i = 0; i < slots; i++) {
			items.add(InitItems.EMPTY);
		}
		
		
	}
	
	public void setupSlots(int slotsX, int slotsY) {
		this.slotsX = slotsX;
		this.slotsY = slotsY;
		slots = slotsX * slotsY;
		
		slotsList.clear();
		for (int sy = 0; sy < slotsY; sy++) {
			for (int sx = 0; sx < slotsX; sx++) {
				slotsList.add(new Slot(10 + sx * 22, 26 + sy * 22, sx + (sy * slotsX)));
			}
		}
	}
	
	public boolean isFull() {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).equals(InitItems.EMPTY)) {
				return false;
			}
		}
		
		return true;
	}
	
	public Item findItem(String str, int meta) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getName().equals(str) && items.get(i).getMeta() == meta) {
				return items.get(i);
			}
		}
		
		return null;
	}
	
	public void addItem(Item item) {
		Item it = findItem(item.getName(), item.getMeta());
		
		if (it != null) {
			Console.print(Console.WarningType.Warning, item.getName() + ":" + item.getMeta() + " already exists in this inventory");
			return;
		}
		
		if (item instanceof ItemSword) {
			if (items.get(item.getMeta()).equals(InitItems.EMPTY)) {
				items.set(item.getMeta(), item);
			}
		} else {
			for (int i = 0; i < slots; i++) {
				if (items.get(i).equals(InitItems.EMPTY)) {
					items.set(i, item);
					break;
				}
			}
		}
	}
	
	public void clearItems() {
		for (int i = 0; i < slots; i++) {
			items.set(i, InitItems.EMPTY);
		}
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public List<Slot> getSlotsList() {
		return slotsList;
	}
	
	public int getSlots() {
		return slots;
	}
	
	public int getSlotsX() {
		return slotsX;
	}
	
	public int getSlotsY() {
		return slotsY;
	}
}
