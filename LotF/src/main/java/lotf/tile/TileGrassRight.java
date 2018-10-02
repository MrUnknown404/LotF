package main.java.lotf.tile;

import main.java.lotf.util.math.TilePos;
import main.java.lotf.world.Room;

public class TileGrassRight extends Tile {

	public TileGrassRight(TilePos relativeTilePos, Room room) {
		super(relativeTilePos, TileType.grassRight, room, 0, 2);
	}
}
