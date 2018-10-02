package main.java.lotf.tile;

import main.java.lotf.util.math.TilePos;
import main.java.lotf.world.Room;

public class TileBlueWallHalf extends Tile {

	public TileBlueWallHalf(TilePos relativeTilePos, Room room, int meta) {
		super(relativeTilePos, TileType.blueWallHalf, room, meta, 4);
	}
}
