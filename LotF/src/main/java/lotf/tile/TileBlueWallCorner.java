package main.java.lotf.tile;

import main.java.lotf.util.math.TilePos;
import main.java.lotf.world.Room;

public class TileBlueWallCorner extends Tile {

	public TileBlueWallCorner(TilePos relativeTilePos, Room room, int meta) {
		super(relativeTilePos, TileType.blueWallCorner, room, meta, 4);
	}
}
