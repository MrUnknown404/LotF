package main.java.lotf.tile;

import main.java.lotf.util.math.TilePos;
import main.java.lotf.world.Room;

public class TileSand extends Tile {

	public TileSand(TilePos relativeTilePos, Room room, int meta) {
		super(relativeTilePos, TileType.sand, room, meta, 2);
	}
}
