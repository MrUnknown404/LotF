package main.java.lotfbuilder.world;

import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public class RoomBuildable extends Room {
	
	public RoomBuildable(EnumWorldType worldType, Vec2i roomPos, boolean hasLangKey) {
		super(worldType, roomPos, hasLangKey);
	}
	
	public void placeTileAt(TileInfo tile, int layer, Vec2i tilePos) {
		getTileLayers().get(layer).add(new Tile(new Vec2i(tilePos.getX() * Tile.TILE_SIZE, tilePos.getY() * Tile.TILE_SIZE), tile), tilePos);
	}
}
