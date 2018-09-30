package main.java.lotf.tile;

import main.java.lotf.util.math.TilePos;
import main.java.lotf.world.Room;

public class TileGrassFlowerLeft extends Tile {

	public TileGrassFlowerLeft(TilePos relativeTilePos, Room room) {
		super(relativeTilePos, TileType.grassFlowerLeft, room, 0, 2, true, true, 90);
	}
}
