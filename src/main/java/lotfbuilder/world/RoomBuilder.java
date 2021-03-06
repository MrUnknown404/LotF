package main.java.lotfbuilder.world;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import main.java.lotf.init.Tiles;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.world.Room;
import main.java.lotfbuilder.MainBuilder;
import main.java.ulibs.ucrypt.UCrypt;
import main.java.ulibs.utils.Console;
import main.java.ulibs.utils.Console.WarningType;
import main.java.ulibs.utils.Grid;
import main.java.ulibs.utils.math.MathH;
import main.java.ulibs.utils.math.Vec2i;

public class RoomBuilder {
	
	private RoomBuildable room;
	private TileInfo mouseTile;
	private int tileLayer, selectedSlot;
	private boolean isInvOpen;
	public Vec2i roomPos = new Vec2i();
	
	private final List<TileInfo> hotbar = new ArrayList<TileInfo>();
	private final Grid<TileInfo> inv;
	
	public RoomBuilder() {
		room = new RoomBuildable(EnumWorldType.debugworld, new Vec2i(), false);
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
		dialog.setFile("room.lotfr");
		dialog.setVisible(true);
		if (dialog.getFile() == null) {
			Console.print(WarningType.Error, "Could not find file!");
			return;
		} else if (!dialog.getFile().endsWith(".lotfr")) {
			Console.print(WarningType.Error, "Wrong file type!");
			return;
		}
		
		Console.print(WarningType.Info, "Started saving room!");
		File file = new File(dialog.getDirectory() + dialog.getFile());
		
		room.setNewTiles(room.getVisibleTiles());
		room.setRoomPos(roomPos);
		
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(UCrypt.encode(dialog.getFile(), MainBuilder.main.getGson().toJson(room)).getBytes(StandardCharsets.UTF_8));
			fos.close();
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
		
		Console.print(WarningType.Info, "Finished saving room!");
	}
	
	public void loadRoom() {
		Console.print(WarningType.Info, "Opening file dialog!");
		
		FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
		dialog.setMode(FileDialog.LOAD);
		dialog.setFile("room.lotfr");
		dialog.setVisible(true);
		if (dialog.getFile() == null) {
			Console.print(WarningType.Error, "Could not find file!");
			return;
		} else if (!dialog.getFile().endsWith(".lotfr")) {
			Console.print(WarningType.Error, "Wrong file type!");
			return;
		}
		
		Console.print(WarningType.Info, "Started loading room!");
		File file = new File(dialog.getDirectory() + dialog.getFile());
		
		Gson g = MainBuilder.main.getGson();
		
		try {
			FileInputStream fis = new FileInputStream(file);
			room = g.fromJson(UCrypt.decode(dialog.getFile(), new String(fis.readAllBytes())), RoomBuildable.class);
			roomPos = new Vec2i(room.getRoomPos());
			
			room.setRoomPos(new Vec2i());
			room.onCreate();
			
			fis.close();
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
	
	public void increaseX() {
		if (roomPos.getX() == 16) {
			roomPos.setX(0);
		} else {
			roomPos.addX(1);
		}
	}
	
	public void increaseY() {
		if (roomPos.getY() == 16) {
			roomPos.setY(0);
		} else {
			roomPos.addY(1);
		}
	}
	
	public void decreaseX() {
		if (roomPos.getX() == 0) {
			roomPos.setX(16);
		} else {
			roomPos.addX(-1);
		}
	}
	
	public void decreaseY() {
		if (roomPos.getY() == 0) {
			roomPos.setY(16);
		} else {
			roomPos.addY(-1);
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
