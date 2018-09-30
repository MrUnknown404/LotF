package main.java.lotf.tile;

import main.java.lotf.util.math.TilePos;

public class TileAir extends Tile {

	public TileAir(TilePos relativeTilePos) {
		super(relativeTilePos, TileType.air, null, 0, 0, false, false, 0);
	}
}
