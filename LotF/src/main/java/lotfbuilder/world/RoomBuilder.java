package main.java.lotfbuilder.world;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import main.java.lotf.init.Tiles;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.Console;
import main.java.lotf.util.Console.WarningType;
import main.java.lotf.util.Grid;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.MathH;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;
import main.java.lotfbuilder.MainBuilder;

public class RoomBuilder {
	
	private RoomBuildable room;
	private TileInfo mouseTile;
	private int tileLayer, selectedSlot;
	private boolean isInvOpen;
	
	private final List<TileInfo> hotbar = new ArrayList<TileInfo>();
	private final Grid<TileInfo> inv;
	
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
	
	public void saveRoom() {
		Console.print(WarningType.Info, "Opening file dialog!");
		
		FileDialog dialog = new FileDialog((Frame) null, "Select File to Save");
		dialog.setMode(FileDialog.SAVE);
		dialog.setFile("room.lotfroom");
		dialog.setVisible(true);
		if (dialog.getFile() == null) {
			Console.print(WarningType.Error, "Could not find file!");
			return;
		} else if (!dialog.getFile().endsWith(".lotfroom")) {
			Console.print(WarningType.Error, "Wrong file type!");
			return;
		}
		
		Console.print(WarningType.Info, "Started saving room!");
		File file = new File(dialog.getDirectory() + dialog.getFile());
		
		room.setNewTiles(room.getVisibleTiles());
		
		Gson g = MainBuilder.main.getGson();
		FileWriter fw = null;
		
		try {
			g.toJson(room, fw = new FileWriter(file));
			fw.close();
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
		
		Console.print(WarningType.Info, "Finished saving room!");
	}
	
	public void loadRoom() {
		Console.print(WarningType.Info, "Opening file dialog!");
		
		FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
		dialog.setMode(FileDialog.LOAD);
		dialog.setFile("room.lotfroom");
		dialog.setVisible(true);
		if (dialog.getFile() == null) {
			Console.print(WarningType.Error, "Could not find file!");
			return;
		} else if (!dialog.getFile().endsWith(".lotfroom")) {
			Console.print(WarningType.Error, "Wrong file type!");
			return;
		}
		
		Console.print(WarningType.Info, "Started loading room!");
		File file = new File(dialog.getDirectory() + dialog.getFile());
		
		Gson g = MainBuilder.main.getGson();
		FileReader fr = null;
		
		try {
			room = g.fromJson(fr = new FileReader(file), RoomBuildable.class);
			room.onCreate();
			fr.close();
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
		
		Console.print(WarningType.Info, "Finished loading room!");
	}
	
	public void placeTileAt(TileInfo t, Vec2i mouseWorldPos) {
		Vec2i tilePos = new Vec2i(MathH.floor(mouseWorldPos.getX() / Tile.TILE_SIZE), MathH.floor(mouseWorldPos.getY() / Tile.TILE_SIZE));
		room.placeTileAt(t, tileLayer, tilePos);
	}
	
	/** Places hotbar tile */
	public void placeMouseTileAt(Vec2i mouseWorldPos) {
		placeTileAt(hotbar.get(selectedSlot), mouseWorldPos);
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
