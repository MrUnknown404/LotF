package main.java.lotf.init;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.items.ItemBombBag;
import main.java.lotf.items.ItemBoomerang;
import main.java.lotf.items.ItemBottle;
import main.java.lotf.items.ItemBow;
import main.java.lotf.items.ItemCape;
import main.java.lotf.items.ItemClock;
import main.java.lotf.items.ItemCompass;
import main.java.lotf.items.ItemDungeonItem;
import main.java.lotf.items.ItemEmpty;
import main.java.lotf.items.ItemFlippers;
import main.java.lotf.items.ItemGraplingHook;
import main.java.lotf.items.ItemHammer;
import main.java.lotf.items.ItemHermesBoots;
import main.java.lotf.items.ItemLavaFlippers;
import main.java.lotf.items.ItemMap;
import main.java.lotf.items.ItemRCBombBag;
import main.java.lotf.items.ItemRing;
import main.java.lotf.items.ItemSpellBook;
import main.java.lotf.items.ItemSword;
import main.java.lotf.items.util.Item;
import main.java.lotf.util.Console;
import main.java.lotf.util.EnumDungeonType;

public final class InitItems {

	public static final Item EMPTY = new ItemEmpty();
	
	private static List<Item> regItem = new ArrayList<Item>();
	
	public static void registerAll() {
		registerItem(EMPTY);
		
		for (int i = 0; i < EnumDungeonType.values().length - 1; i++) {
			registerItem(new ItemMap(EnumDungeonType.getFromNumber(i)));
		}
		
		for (int i = 0; i < EnumDungeonType.values().length - 1; i++) {
			registerItem(new ItemCompass(EnumDungeonType.getFromNumber(i)));
		}
		
		for (int i = 0; i < EnumDungeonType.values().length - 1; i++) {
			registerItem(new ItemDungeonItem(EnumDungeonType.getFromNumber(i)));
		}
		
		for (int i = 0; i < ItemBottle.BottleType.values().length; i++) {
			registerItem(new ItemBottle(ItemBottle.BottleType.getFromNumber(i)));
		}
		
		for (int i = 0; i < 4; i++) {
			registerItem(new ItemBow(i));
		}
		for (int i = 0; i < 4; i++) {
			registerItem(new ItemBombBag(i));
		}
		for (int i = 0; i < 4; i++) {
			registerItem(new ItemRCBombBag(i));
		}
		
		for (int i = 0; i < 5; i++) {
			registerItem(new ItemSword(i));
		}
		
		registerItem(new ItemHammer());
		registerItem(new ItemSpellBook());
		registerItem(new ItemBoomerang());
		registerItem(new ItemFlippers());
		registerItem(new ItemLavaFlippers());
		registerItem(new ItemGraplingHook());
		registerItem(new ItemHermesBoots());
		registerItem(new ItemClock());
		
		registerItem(new ItemCape(0));
		registerItem(new ItemCape(1));
		
		for (int i = 0; i < ItemRing.RingType.values().length; i++) {
			registerItem(new ItemRing(ItemRing.RingType.getFromNumber(i)));
		}
	}
	
	public static void registerItem(Item item) {
		if (!regItem.isEmpty()) {
			for (int i = 0; i < regItem.size(); i++) {
				if (regItem.get(i).getStringID().equals(item.getStringID()) && regItem.get(i).getMeta() == item.getMeta()) {
					Console.print(Console.WarningType.Error, "Item already registered with this ID : " + item.getStringID() + ":" + item.getMeta());
					return;
				}
			}
		}
		
		regItem.add(item);
		
		Console.print(Console.WarningType.Register, "Registered Item with IDs : \"" + item.getStringID() + "\":" + item.getMeta() + "!");
	}
	
	public static Item get(String name, int meta) {
		for (int i = 0; i < regItem.size(); i++) {
			if ((regItem.get(i).getName().equals(name) || regItem.get(i).getStringID().equals(name)) && regItem.get(i).getMeta() == meta) {
				return regItem.get(i);
			}
		}
		
		Console.print(Console.WarningType.Error, "Cannot find " + name + ":" + meta + "!");
		return regItem.get(0);
	}
	
	public static List<Item> getItems() {
		return regItem;
	}
}
