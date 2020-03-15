package main.java.lotfbuilder.world;

import java.util.List;

import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.Grid;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public class RoomBuildable extends Room {
	
	public RoomBuildable(EnumWorldType worldType, Vec2i roomPos, boolean hasLangKey) {
		super(worldType, roomPos, hasLangKey);
	}
	
	public void placeTileAt(TileInfo tile, int layer, Vec2i tilePos) {
		Tile t = null;
		getTileLayers().get(layer).add(tile == null ? null : (t = new Tile(tilePos, tile)), tilePos);
		
		if (t != null) {
			t.updateTile(roomPos);
		}
	}
	
	public void setNewTiles(List<Grid<Tile>> tiles) {
		this.tiles = tiles;
		onCreate();
	}
}
