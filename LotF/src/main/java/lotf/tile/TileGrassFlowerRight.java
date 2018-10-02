package main.java.lotf.tile;

import main.java.lotf.util.math.TilePos;
import main.java.lotf.world.Room;

public class TileGrassFlowerRight extends Tile {

	public TileGrassFlowerRight(TilePos relativeTilePos, Room room) {
		super(relativeTilePos, TileType.grassFlowerRight, room, 0, 2);
	}
}
