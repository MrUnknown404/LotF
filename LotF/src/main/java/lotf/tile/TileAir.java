package main.java.lotf.tile;

import main.java.lotf.util.math.TilePos;
import main.java.lotf.world.Room;

public class TileAir extends Tile {

	public TileAir(TilePos relativeTilePos, Room room) {
		super(relativeTilePos, TileType.air, room, 0);
	}
}
