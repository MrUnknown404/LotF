package main.java.lotf.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.Console;
import main.java.lotf.util.Console.WarningType;
import main.java.lotf.util.enums.EnumCollisionType;

public class Tiles {
	private static final List<TileInfo> ALL = new ArrayList<TileInfo>();
	
	public static final TileInfo EMPTY_GRASS = new TileInfo("empty_grass", false, false, EnumCollisionType.none);
	public static final TileInfo GRASS = new TileInfo("grass", 2, 120, false, true, EnumCollisionType.none);
	public static final TileInfo FLOWER_GRASS = new TileInfo("flower_grass", 2, 120, false, true, EnumCollisionType.none);
	public static final TileInfo WALL = new TileInfo("wall", false, false, EnumCollisionType.whole);
	public static final TileInfo WALL2 = new TileInfo("wall2", false, false, EnumCollisionType.bottom);
	
	public static TileInfo getRandomGrass() {
		int r = new Random().nextInt(3);
		
		if (r == 0) {
			return EMPTY_GRASS;
		} else if (r == 1) {
			return GRASS;
		} else {
			return FLOWER_GRASS;
		}
	}
	
	public static List<TileInfo> getAll() {
		return ALL;
	}

	public static void add(TileInfo i) {
		if (ALL.isEmpty()) {
			Console.print(WarningType.Info, "Started registering " + Tiles.class.getSimpleName() + "!");
		}
		
		ALL.add(i);
		Console.print(Console.WarningType.RegisterDebug, "'" + i.getName() + "' was registered!");
	}
	
	/** Forces an early load */
	public static void registerAll() { }
}
