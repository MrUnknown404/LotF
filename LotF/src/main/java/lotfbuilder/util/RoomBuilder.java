package main.java.lotfbuilder.util;

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
import main.java.lotf.entity.Entity;
import main.java.lotf.entity.EntityMonster;
import main.java.lotf.entity.EntityNPC;
import main.java.lotf.inventory.Inventory;
import main.java.lotf.items.ItemEmpty;
import main.java.lotf.items.util.Item;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.Console;
import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.RoomPos;
import main.java.lotf.util.math.TilePos;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;
import main.java.lotfbuilder.MainBuilder;

public final class RoomBuilder {

	private Room room;
	
	public boolean isOpen;
	
	public int selectedSlot, selectedPage, creationState, lastType = 1, lastMeta;
	
	public List<BuilderInventory> pages = new ArrayList<BuilderInventory>();
	public Inventory selInv = new Inventory(11, 1);
	
	public Item hand;
	
	public void setup(Room.RoomSize size) {
		Console.print(Console.WarningType.Info, "Creating room!");
		setPages();
		
		room = new Room(size);
		room.setRoomPos(new RoomPos(0, 0));
		
		for (int i = 0; i < room.getTileLayer0().size(); i++) {
			room.getTileLayer0().get(i).tileUpdate();
		}
		
		MainBuilder.setDoesRoomExist(true);
		Console.print(Console.WarningType.Info, "Finished creating room!");
	}
	
	public void tick() {
		room.tick();
		
		if (!isOpen && hand != null) {
			hand = null;
		}
	}
	
	public void saveRoom() {
		Console.print(Console.WarningType.Debug, "Creating file chooser!");
		MainBuilder.gamestate = Main.Gamestate.hardPause;
		
		Gson g = new Gson().newBuilder().create();
		FileWriter fw;
		JFileChooser fc = new JFileChooser("D:/");
		File f = null;
		
		fc.setFileFilter(new RoomFileFilter());
		fc.setAcceptAllFileFilterUsed(true);
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		if (room.getWorldType() == null) {
			fc.setSelectedFile(new File("null.lotfroom"));
		} else {
			fc.setSelectedFile(new File("null.lotfroom"));
		}
		
		Console.print(Console.WarningType.Debug, "Opening file chooser!");
		int returnVal = fc.showSaveDialog(new JFrame());
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Console.print(Console.WarningType.Info, "Saving room...");
			f = fc.getSelectedFile();
			
			try {
				fw = new FileWriter(f);
				
				g.toJson(room, fw);
				
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Console.print(Console.WarningType.Info, "Finished saving room!");
		} else {
			Console.print(Console.WarningType.FatalError, "Saving room failed!");
		}
		
		MainBuilder.gamestate = Main.Gamestate.run;
	}
	
	public void loadRoom() {
		Console.print(Console.WarningType.Debug, "Creating file chooser!");
		MainBuilder.gamestate = Main.Gamestate.hardPause;
		
		Gson g = new Gson().newBuilder().setPrettyPrinting().create();
		FileReader fr;
		JFileChooser fc = new JFileChooser("D:/");
		File f = null;
		
		fc.setFileFilter(new RoomFileFilter());
		fc.setAcceptAllFileFilterUsed(true);
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		Console.print(Console.WarningType.Debug, "Opening file chooser!");
		int returnVal = fc.showOpenDialog(new JFrame());
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Console.print(Console.WarningType.Info, "Loading room...");
			f = fc.getSelectedFile();
			
			try {
				fr = new FileReader(f);
				
				room = g.fromJson(fr, Room.class);
				
				fr.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Console.print(Console.WarningType.Info, "Finished loading room!");
		} else {
			Console.print(Console.WarningType.FatalError, "Loading room failed!");
			MainBuilder.gamestate = Main.Gamestate.run;
			return;
		}
		
		Console.print(Console.WarningType.Info, "Setting up the room...");
		setPages();
		
		room.setRoomPos(new RoomPos());
		
		for (int i = 0; i < room.getTileLayer0().size(); i++) {
			room.getTileLayer0().get(i).setRoom(room);
			room.getTileLayer1().get(i).setRoom(room);
			room.getTileLayer0().get(i).tileUpdate();
			room.getTileLayer1().get(i).tileUpdate();
		}
		Console.print(Console.WarningType.Info, "Finished setting up the room!");
		
		MainBuilder.setDoesRoomExist(true);
		MainBuilder.gamestate = Main.Gamestate.run;
	}
	
	public void resetRoom() {
		MainBuilder.setDoesRoomExist(false);
		
		selectedSlot = 0;
		selectedPage = 0;
		creationState = 0;
		lastType = 1;
		lastMeta = 0;
		
		isOpen = false;
		
		room = null;
		hand = null;
		
		pages = new ArrayList<BuilderInventory>();
		selInv.clearItems();
		
		MainBuilder.getCamera().pos = new Vec2i();
	}
	
	private void setPages() {
		pages.clear();
		selectedPage = 0;
		BuilderInventory.didBlocks = false;
		BuilderInventory.didMonsters = false;
		
		int ti = 0;
		
		for (int i = 0; i < Tile.TileType.values().length; i++) {
			ti += Tile.TileType.getFromNumber(i).count;
		}
		
		for (int i = 0; i < EntityMonster.MonsterType.values().length; i++) {
			ti += EntityMonster.MonsterType.getFromNumber(i).count;
		}
		
		for (int i = 0; i < EntityNPC.NPCType.values().length; i++) {
			ti += EntityNPC.NPCType.getFromNumber(i).count;
		}
		
		for (int i = 0; i < 1 + MathHelper.floor(ti / 55); i++) {
			pages.add(new BuilderInventory(i));
		}
	}
	
	public void setTile(Vec2i vec) {
		if (getSelectedItem() instanceof ItemTile) {
			if (((ItemTile) getSelectedItem()).getTileType() != Tile.TileType.air && ((ItemTile) getSelectedItem()).getTileType() != Tile.TileType.door) {
				if (((ItemTile) getSelectedItem()).getTileType().colType != Tile.CollisionType.none) {
					room.getTileLayer1().set(room.TilePosToID(room.getRoomSize(), vec.getX(), vec.getY()), new Tile(new TilePos(vec), ((ItemTile) getSelectedItem()).getTileType(), room, getSelectedItem().getMeta()));
				} else {
					room.getTileLayer0().set(room.TilePosToID(room.getRoomSize(), vec.getX(), vec.getY()), new Tile(new TilePos(vec), ((ItemTile) getSelectedItem()).getTileType(), room, getSelectedItem().getMeta()));
				}
			} else if (((ItemTile) getSelectedItem()).getTileType() == Tile.TileType.door) {
				room.getTileLayer1().set(room.TilePosToID(room.getRoomSize(), vec.getX(), vec.getY()), new Tile(new TilePos(vec), ((ItemTile) getSelectedItem()).getTileType(), room, getSelectedItem().getMeta()));
			}
		} else {
			if (((ItemEntity) getSelectedItem()).getEntityType() == Entity.EntityType.monster) {
				
			} else {
				
			}
		}
		
		room.resetAnimations();
	}
	
	public void deleteTile(Vec2i vec) {
		if (room.getTileLayer1().get(room.TilePosToID(room.getRoomSize(), vec.getX(), vec.getY())).getTileType() == Tile.TileType.air) {
			room.getTileLayer0().set(room.TilePosToID(room.getRoomSize(), vec.getX(), vec.getY()), new Tile(new TilePos(vec), Tile.TileType.air, room, getSelectedItem().getMeta()));
		} else {
			room.getTileLayer1().set(room.TilePosToID(room.getRoomSize(), vec.getX(), vec.getY()), new Tile(new TilePos(vec), Tile.TileType.air, room, getSelectedItem().getMeta()));
		}
	}
	
	public void addSelectedSlot() {
		if (selectedSlot == getSelectedPage().getSlotsX() - 1) {
			selectedSlot = 0;
		} else {
			selectedSlot++;
		}
	}
	
	public void decreaseSelectedSlot() {
		if (selectedSlot == 0) {
			selectedSlot = getSelectedPage().getSlotsX() - 1;
		} else {
			selectedSlot--;
		}
	}
	
	public void addSelectedPage() {
		if (selectedPage == pages.size() - 1) {
			selectedPage = 0;
		} else {
			selectedPage++;
		}
	}
	
	public void decreaseSelectedPage() {
		if (selectedPage == 0) {
			selectedPage = pages.size() - 1;
		} else {
			selectedPage--;
		}
	}
	
	public BuilderInventory getSelectedPage() {
		return pages.get(selectedPage);
	}
	
	public Item getSelectedItem() {
		if (selInv.getItems().get(selectedSlot) instanceof ItemEmpty) {
			return new ItemTile(Tile.TileType.air, 0);
		}
		return selInv.getItems().get(selectedSlot);
	}
	
	public Room getRoom() {
		return room;
	}
}