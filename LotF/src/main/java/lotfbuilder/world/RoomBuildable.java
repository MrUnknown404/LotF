package main.java.lotfbuilder.world;

import java.util.List;

import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.Grid;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public class RoomBuildable extends Room {
	
	public RoomBuildable() {
		super(EnumWorldType.debugworld, Vec2i.ZERO, false);
	}
	
	public RoomBuildable(EnumWorldType worldType, Vec2i roomPos, boolean hasLangKey) {
		super(worldType, roomPos, hasLangKey);
	}
	
	public void placeTileAt(TileInfo tile, int layer, Vec2i tilePos) {
		if (tilePos.getX() >= getWidth() || tilePos.getY() >= getHeight() || tilePos.getX() < 0 || tilePos.getY() < 0) {
			return;
		}
		
		Tile t = null;
		getTileLayers().get(layer).add(tile == null ? null : (t = new Tile(tilePos, tile)), tilePos);
		cached_visibleTiles.clear();
		
		if (t != null) {
			t.updateTile(roomPos);
		}
	}
	
	public void setNewTiles(List<Grid<Tile>> tiles) {
		tiles.clear();
		tiles.addAll(tiles);
		onCreate();
	}
}
