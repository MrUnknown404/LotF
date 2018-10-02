package main.java.lotfbuilder.room;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.google.gson.Gson;

import main.java.lotf.Main;
import main.java.lotf.inventory.Inventory;
import main.java.lotf.items.ItemEmpty;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.Console;
import main.java.lotf.util.EnumDungeonType;
import main.java.lotf.util.math.TilePos;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;
import main.java.lotf.world.World;
import main.java.lotfbuilder.MainBuilder;
import main.java.lotfbuilder.util.BlockInventory;
import main.java.lotfbuilder.util.ItemTile;
import main.java.lotfbuilder.util.RoomFileFilter;

public final class RoomBuilder {

	private Room room;
	
	public boolean isOpen;
	public int selectedSlot, selectedInv, creationState, lastTileType = 1, lastMeta;
	
	public List<BlockInventory> pages = new ArrayList<BlockInventory>();
	public Inventory selInv = new Inventory(11, 1);
	
	public ItemTile hand;
	
	private World.WorldType type;
	private EnumDungeonType type2;
	private Room.RoomSize size;
	
	public void setup(World.WorldType type, EnumDungeonType type2, Room.RoomSize size, int id) {
		for (int i = 0; i < 2; i++) {
			pages.add(new BlockInventory(i));
		}
		
		room = new Room(type, type2, size, id);
		
		this.type = type;
		this.type2 = type2;
		this.size = size;
		
		MainBuilder.setDoesRoomExist(true);
	}
	
	public void tick() {
		room.tick();
		
		if (!isOpen && hand != null) {
			hand = null;
		}
	}
	
	public void saveRoom() {
		Console.print(Console.WarningType.Info, "Saving room...");
		MainBuilder.gamestate = Main.Gamestate.hardPause;
		
		Gson g = new Gson().newBuilder().setPrettyPrinting().create();
		FileWriter fw;
		JFileChooser fc = new JFileChooser("D:/");
		File f = null;
		
		fc.setFileFilter(new RoomFileFilter());
		fc.setAcceptAllFileFilterUsed(true);
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		if (type2 == null) {
			fc.setSelectedFile(new File(type + ".lotfroom"));
		} else {
			fc.setSelectedFile(new File(type + "_" + type2 + ".lotfroom"));
		}
		
		int returnVal = fc.showSaveDialog(new JFrame());
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			f = fc.getSelectedFile();
			
			try {
				fw = new FileWriter(f);
				
				g.toJson(room, fw);
				
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		MainBuilder.gamestate = Main.Gamestate.run;
		Console.print(Console.WarningType.Info, "Finished saving room!");
	}
	
	public void loadRoom() {
		Console.print(Console.WarningType.Info, "Saving room...");
		MainBuilder.gamestate = Main.Gamestate.hardPause;
		
		Gson g = new Gson().newBuilder().setPrettyPrinting().create();
		FileReader fr;
		JFileChooser fc = new JFileChooser("D:/");
		File f = null;
		
		fc.setFileFilter(new RoomFileFilter());
		fc.setAcceptAllFileFilterUsed(true);
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		int returnVal = fc.showOpenDialog(new JFrame());
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			f = fc.getSelectedFile();
			
			try {
				fr = new FileReader(f);
				
				room = g.fromJson(fr, Room.class);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < room.getTileLayer0().size(); i++) {
			room.getTileLayer0().get(i).setRoom(room);
			room.getTileLayer1().get(i).setRoom(room);
		}
		
		room.setRoomPos(room.IDToRoomPos(room.getRoomID()));
		
		MainBuilder.setDoesRoomExist(true);
		
		MainBuilder.gamestate = Main.Gamestate.run;
		Console.print(Console.WarningType.Info, "Finished saving room!");
	}
	
	public void resetRoom() {
		MainBuilder.setDoesRoomExist(false);
		
		creationState = 0;
		
		isOpen = false;
		
		room = null;
		type = null;
		type2 = null;
		size = null;
		hand = null;
		
		MainBuilder.getCamera().pos = new Vec2i();
	}
	
	public void setTile(Vec2i vec) {
		if (getSelectedItem().getTileType() == Tile.TileType.air) {
			deleteTile(vec);
		} else if (getSelectedItem().getTileType().hasCollision) {
			room.getTileLayer1().set(room.TilePosToID(size, vec.getX(), vec.getY()), new Tile(new TilePos(vec), getSelectedItem().getTileType(), room, getSelectedItem().getMeta(), getSelectedItem().getMaxMeta()));
		} else {
			room.getTileLayer0().set(room.TilePosToID(size, vec.getX(), vec.getY()), new Tile(new TilePos(vec), getSelectedItem().getTileType(), room, getSelectedItem().getMeta(), getSelectedItem().getMaxMeta()));
		}
		
		room.resetAnimations();
	}
	
	public void deleteTile(Vec2i vec) {
		if (room.getTileLayer1().get(room.TilePosToID(size, vec.getX(), vec.getY())).getTileType() == Tile.TileType.air) {
			room.getTileLayer0().set(room.TilePosToID(size, vec.getX(), vec.getY()), new Tile(new TilePos(vec), Tile.TileType.air, room, getSelectedItem().getMeta(), getSelectedItem().getMaxMeta()));
		} else {
			room.getTileLayer1().set(room.TilePosToID(size, vec.getX(), vec.getY()), new Tile(new TilePos(vec), Tile.TileType.air, room, getSelectedItem().getMeta(), getSelectedItem().getMaxMeta()));
		}
	}
	
	public void addSelectedSlot() {
		if (selectedSlot == getSelectedInv().getSlotsX() - 1) {
			selectedSlot = 0;
		} else {
			selectedSlot++;
		}
	}
	
	public void decreaseSelectedSlot() {
		if (selectedSlot == 0) {
			selectedSlot = getSelectedInv().getSlotsX() - 1;
		} else {
			selectedSlot--;
		}
	}
	
	public void addSelectedInv() {
		if (selectedInv == pages.size() - 1) {
			selectedInv = 0;
		} else {
			selectedInv++;
		}
	}
	
	public void decreaseSelectedInv() {
		if (selectedInv == 0) {
			selectedInv = pages.size() - 1;
		} else {
			selectedInv--;
		}
	}
	
	public BlockInventory getSelectedInv() {
		return pages.get(selectedInv);
	}
	
	public ItemTile getSelectedItem() {
		if (selInv.getItems().get(selectedSlot) instanceof ItemEmpty) {
			return new ItemTile(Tile.TileType.air, 0);
		}
		return (ItemTile) selInv.getItems().get(selectedSlot);
	}
	
	public Room getRoom() {
		return room;
	}
}
