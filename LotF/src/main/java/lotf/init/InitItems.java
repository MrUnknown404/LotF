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
import main.java.lotf.items.swords.SwordStarter;
import main.java.lotf.items.util.Item;

public class InitItems {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item BOW = new ItemBow();
	public static final Item CAPE = new ItemCape();
	
	public static final SwordStarter SWORD = new SwordStarter();
	
	public static final Ring BASIC = new RingBasic();
	public static final Ring ARCHER1 = new RingArcher1();
	
	public static final Potion RED_POTION = new PotionRed();
	
	/**
	 * This just forces the variables to be setup early. It isn't needed, but I like it
	 */
	public static void registerAll() { /* haha this doesn't do anything */ }
}
