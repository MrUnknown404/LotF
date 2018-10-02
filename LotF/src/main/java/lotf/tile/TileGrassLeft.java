package main.java.lotf.tile;

import main.java.lotf.util.math.TilePos;
import main.java.lotf.world.Room;

public class TileGrassLeft extends Tile {

	public TileGrassLeft(TilePos relativeTilePos, Room room) {
		super(relativeTilePos, TileType.grassLeft, room, 0, 2);
	}
}
