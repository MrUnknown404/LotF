package main.java.lotf.init;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.items.util.Collectible;

public class InitCollectibles {
	public static final List<Collectible> COLLECTIBLES = new ArrayList<Collectible>();
	
	public static final Collectible RED_FLY = new Collectible("redFly");
	public static final Collectible GREEN_FLY = new Collectible("greenFly");
	public static final Collectible BLUE_FLY = new Collectible("blueFly");
	public static final Collectible SPIKEY_BEETLE = new Collectible("spikyBeetle");
	public static final Collectible LAVA_BEETLE = new Collectible("lavaBeetle");
	public static final Collectible BUTTERFLY = new Collectible("butterfly");
	
	public static Collectible find(String name) {
		for (Collectible col : COLLECTIBLES) {
			if (col.getKey().equalsIgnoreCase(name)) {
				return col;
			}
		}
		
		return null;
	}
	
	/**
	 * This just forces the variables to be setup early. It isn't needed, but I like it
	 */
	public static void registerAll() { /* haha this doesn't do anything */ }
}
