package main.java.lotf.tile;

import main.java.lotf.util.math.TilePos;
import main.java.lotf.world.Room;

public class TileBlueWall extends Tile {

	public TileBlueWall(TilePos relativeTilePos, Room room, int meta) {
		super(relativeTilePos, TileType.blueWall, room, meta, 4, true, false, 0);
	}
}
