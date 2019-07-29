package main.java.lotf.init;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.items.ItemBow;
import main.java.lotf.items.ItemCape;
import main.java.lotf.items.rings.Ring;
import main.java.lotf.items.rings.RingArcher1;
import main.java.lotf.items.rings.RingBasic;
import main.java.lotf.items.swords.ItemStarterSword;
import main.java.lotf.items.util.ItemBase;
import main.java.lotf.util.Console;

public class InitItems {

	private static List<ItemBase> items = new ArrayList<ItemBase>();
	private static List<Ring> rings = new ArrayList<Ring>();
	
	public static final ItemBase BOW = addItem(new ItemBow());
	public static final ItemBase CAPE = addItem(new ItemCape());
	
	public static final ItemStarterSword SWORD = (ItemStarterSword) addItem(new ItemStarterSword());
	
	public static final Ring BASIC = addRing(new RingBasic());
	public static final Ring ARCHER1 = addRing(new RingArcher1());
	
	public static void registerAll() { /* haha this doesn't do anything */ }
	
	private static ItemBase addItem(ItemBase item) {
		items.add(item);
		Console.print(Console.WarningType.RegisterDebug, item.getKey() + " was registered!");
		return item;
	}
	
	private static Ring addRing(Ring ring) {
		rings.add(ring);
		Console.print(Console.WarningType.RegisterDebug, ring.getKey() + " was registered!");
		return ring;
	}
	
	public static int getItemsSize() {
		return items.size();
	}
	
	public static ItemBase getItem(int i) {
		return items.get(i);
	}
	
	public static int getRingsSize() {
		return rings.size();
	}
	
	public static Ring getRing(int i) {
		return rings.get(i);
	}
}
