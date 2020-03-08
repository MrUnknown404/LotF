package main.java.lotf.init;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.items.ItemBow;
import main.java.lotf.items.ItemCape;
import main.java.lotf.items.potions.Potion;
import main.java.lotf.items.potions.PotionRed;
import main.java.lotf.items.rings.Ring;
import main.java.lotf.items.rings.RingArcher1;
import main.java.lotf.items.rings.RingBasic;
import main.java.lotf.items.swords.Sword;
import main.java.lotf.items.swords.SwordStarter;
import main.java.lotf.items.util.Item;
import main.java.lotf.util.Console;
import main.java.lotf.util.Console.WarningType;

public class Items {
	private static final List<Item> ALL = new ArrayList<Item>();
	
	public static final Item BOW = new ItemBow();
	public static final Item CAPE = new ItemCape();
	
	public static final Sword SWORD = new SwordStarter();
	
	public static final Ring BASIC = new RingBasic();
	public static final Ring ARCHER1 = new RingArcher1();
	
	public static final Potion EMPTY_POTION = new Potion("empty") { @Override protected void onDrink() { } };
	public static final Potion RED_POTION = new PotionRed();
	
	public static List<Item> getAll() {
		return ALL;
	}

	public static void add(Item i) {
		if (ALL.isEmpty()) {
			Console.print(WarningType.Info, "Started registering " + Items.class.getSimpleName() + "!");
		}
		
		ALL.add(i);
		Console.print(Console.WarningType.RegisterDebug, "'" + i.getKey() + "' was registered!");
	}
	
	/** Forces an early load */
	public static void registerAll() { }
}
