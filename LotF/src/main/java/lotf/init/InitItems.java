package main.java.lotf.init;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.items.Item;
import main.java.lotf.items.ItemBow;
import main.java.lotf.items.ItemCape;
import main.java.lotf.items.rings.Ring;
import main.java.lotf.items.rings.RingArcher1;
import main.java.lotf.items.rings.RingBasic;
import main.java.lotf.items.swords.ItemSword;
import main.java.lotf.util.Console;

public class InitItems {

	private static List<Item> items = new ArrayList<Item>();
	private static List<Ring> rings = new ArrayList<Ring>();
	
	public static final Item BOW = addItem(new ItemBow());
	public static final Item CAPE = addItem(new ItemCape());
	
	public static final ItemSword SWORD = (ItemSword) addItem(new ItemSword("sword", 1));
	
	public static final Ring BASIC = addRing(new RingBasic());
	public static final Ring ARCHER1 = addRing(new RingArcher1());
	
	public static void registerAll() { /* haha this doesn't do anything */ }
	
	private static Item addItem(Item item) {
		items.add(item);
		Console.print(Console.WarningType.RegisterDebug, item.getName() + " was registered!");
		return item;
	}
	
	private static Ring addRing(Ring ring) {
		rings.add(ring);
		Console.print(Console.WarningType.RegisterDebug, ring.getName() + " was registered!");
		return ring;
	}
	
	public static int getItemsSize() {
		return items.size();
	}
	
	public static Item getItem(int i) {
		return items.get(i);
	}
	
	public static int getRingsSize() {
		return rings.size();
	}
	
	public static Ring getRing(int i) {
		return rings.get(i);
	}
}
