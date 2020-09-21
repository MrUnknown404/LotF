package main.java.lotf.init;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.items.util.Collectible;
import main.java.ulibs.utils.Console;
import main.java.ulibs.utils.Console.WarningType;

public class Collectibles {
	public static final List<Collectible> ALL = new ArrayList<Collectible>();
	
	public static final Collectible RED_FLY = new Collectible("redFly");
	public static final Collectible GREEN_FLY = new Collectible("greenFly");
	public static final Collectible BLUE_FLY = new Collectible("blueFly");
	public static final Collectible SPIKEY_BEETLE = new Collectible("spikyBeetle");
	public static final Collectible LAVA_BEETLE = new Collectible("lavaBeetle");
	public static final Collectible BUTTERFLY = new Collectible("butterfly");
	
	public static Collectible find(String name) {
		for (Collectible col : ALL) {
			if (col.getKey().equalsIgnoreCase(name)) {
				return col;
			}
		}
		
		return null;
	}
	
	public static void registerAll() {
		Console.print(WarningType.Info, "Started registering " + Collectibles.class.getSimpleName() + "!");
	}

	public static void add(Collectible i) {
		ALL.add(i);
		Console.print(Console.WarningType.RegisterDebug, "'" + i.getKey() + "' was registered!");
	}
}
