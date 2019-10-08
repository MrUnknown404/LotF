package main.java.lotf.init;

import main.java.lotf.items.ItemBow;
import main.java.lotf.items.ItemCape;
import main.java.lotf.items.rings.Ring;
import main.java.lotf.items.rings.RingArcher1;
import main.java.lotf.items.rings.RingBasic;
import main.java.lotf.items.swords.ItemStarterSword;
import main.java.lotf.items.util.ItemBase;

public class InitItems {

	public static final ItemBase BOW = new ItemBow();
	public static final ItemBase CAPE = new ItemCape();
	
	public static final ItemStarterSword SWORD = new ItemStarterSword();
	
	public static final Ring BASIC = new RingBasic();
	public static final Ring ARCHER1 = new RingArcher1();
	
	/**
	 * This just forces the variables to be setup for later use
	 * <p>It very much isn't needed, but I like it
	 */
	public static void registerAll() { /* haha this doesn't do anything */ }
}
