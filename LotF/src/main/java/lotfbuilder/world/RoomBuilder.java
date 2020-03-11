package main.java.lotfbuilder.world;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.init.Tiles;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.Grid;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.MathH;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public class RoomBuilder {
	
	private RoomBuildable room;
	private int tileLayer, selectedSlot;
	private boolean isInvOpen;
	
	private List<TileInfo> hotbar = new ArrayList<TileInfo>();
	private Grid<TileInfo> inv;
	private TileInfo mouseTile;
	
	public RoomBuilder() {
		room = new RoomBuildable(EnumWorldType.debugworld, Vec2i.ZERO, false);
		inv = new Grid<TileInfo>(11, MathH.ceil(Tiles.getAll().size() / 11f));
		
		for (TileInfo info : Tiles.getAll()) {
			inv.addFirstEmpty(info);
		}
		for (int i = 0; i < 11; i++) {
			hotbar.add(null);
		}
	}
	
	public void placeMouseTileAt(Vec2i mouseWorldPos) {
		Vec2i tilePos = new Vec2i(MathH.floor(mouseWorldPos.getX() / Tile.TILE_SIZE), MathH.floor(mouseWorldPos.getY() / Tile.TILE_SIZE));
		
		room.placeTileAt(hotbar.get(selectedSlot), tileLayer, tilePos);
	}
	
	public void increaseSlot() {
		if (selectedSlot == 10) {
			selectedSlot = 0;
		} else {
			selectedSlot++;
		}
	}
	
	public void decreaseSlot() {
		if (selectedSlot == 0) {
			selectedSlot = 10;
		} else {
			selectedSlot--;
		}
	}
	
	public void increaseLayer() {
		if (tileLayer == 2) {
			tileLayer = 0;
		} else {
			tileLayer++;
		}
	}
	
	public void decreaseLayer() {
		if (tileLayer == 0) {
			tileLayer = 2;
		} else {
			tileLayer--;
		}
	}
	
	public void setHotbarSlotToMouse(int hotbarID) {
		hotbar.set(hotbarID, mouseTile);
		mouseTile = null;
	}
	
	public void clearHotbarSlot(int hotbarID) {
		hotbar.set(hotbarID, null);
	}
	
	public void toggleInv() {
		isInvOpen = !isInvOpen;
	}
	
	public void setMouseTile(TileInfo mouseTile) {
		this.mouseTile = mouseTile;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public int getTileLayer() {
		return tileLayer;
	}
	
	public boolean isInvOpen() {
		return isInvOpen;
	}
	
	public int getSelectedSlot() {
		return selectedSlot;
	}
	
	public TileInfo getMouseTile() {
		return mouseTile;
	}
	
	public List<TileInfo> getHotbar() {
		return hotbar;
	}
}
