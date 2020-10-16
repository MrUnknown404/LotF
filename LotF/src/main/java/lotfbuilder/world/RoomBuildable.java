package main.java.lotfbuilder.world;

import java.util.List;

import javax.annotation.Nullable;

import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.world.Room;
import main.java.ulibs.utils.Grid;
import main.java.ulibs.utils.math.Vec2i;

public class RoomBuildable extends Room {
	
	public RoomBuildable() {
		super(EnumWorldType.debugworld, new Vec2i(), false);
	}
	
	public RoomBuildable(EnumWorldType worldType, Vec2i roomPos, boolean hasLangKey) {
		super(worldType, roomPos, hasLangKey);
	}
	
	public void placeTileAt(@Nullable TileInfo tile, int layer, Vec2i tilePos) {
		if (tilePos.getX() >= getWidth() || tilePos.getY() >= getHeight() || tilePos.getX() < 0 || tilePos.getY() < 0) {
			return;
		} else if (tile != null && getTileLayers().get(layer).get(tilePos.getX(), tilePos.getY()) != null &&
				getTileLayers().get(layer).get(tilePos.getX(), tilePos.getY()).getTileInfo() == tile) {
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
	
	public void setRoomPos(Vec2i roomPos) {
		this.roomPos.set(roomPos);
	}
}
