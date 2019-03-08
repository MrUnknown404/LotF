package main.java.lotf.world;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import main.java.lotf.Main;
import main.java.lotf.entity.EntityMonster;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.Console;
import main.java.lotf.util.EnumDirection;
import main.java.lotf.util.EnumDungeonType;
import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.RoomPos;
import main.java.lotf.util.math.Vec2i;

public final class World {
	
	private List<Room> rooms = new ArrayList<Room>();
	
	protected Vec2i size = new Vec2i();;
	protected WorldType type;
	protected EnumDungeonType dungeonType = EnumDungeonType.nil;
	protected boolean isDungeon;
	protected Map map;
	
	private int jj = 0;
	
	public World(WorldType type) {
		this.type = type;
		this.size = new Vec2i(type.size, type.size);
		if (type != WorldType.inside) {
			this.map = new Map(type);
		}
		
		loadRooms(type.size);
		
		if (type.fId == 0) {
			rooms.get(149).addMonster(new EntityMonster(64, 64, 16, 16, EntityMonster.MonsterType.testWander));
		}
	}
	
	public World(EnumDungeonType dungeonType) {
		this.type = WorldType.dungeon;
		this.dungeonType = dungeonType;
		this.isDungeon = true;
		this.size = new Vec2i(dungeonType.size, dungeonType.size);
		this.map = new Map(dungeonType);
		
		loadRooms(dungeonType.size);
	}
	
	public void loadRooms(int size) {
		Main.gamestate = Main.Gamestate.hardPause;
		
		Gson g = new Gson().newBuilder().setPrettyPrinting().create();
		FileReader fr;
		
		for (int i = 0; i < size * size; i++) {
			try {
				File f = null;
				String p = (World.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath() + Main.getBaseLocationRooms();
				
				
				if (type != WorldType.dungeon) {
					if (new File(p + type.toString() + "/" + type.toString() + "_" + i + ".lotfroom").exists()) {
						f = new File(p + type.toString() + "/" + type.toString() + "_" + i + ".lotfroom");
					} else {
						Console.print(Console.WarningType.FatalError, "Could not find room at " + p + type.toString() + "/" + type.toString() + "_" + i + ".lotfroom");
						f = new File(p + "testRoom.lotfroom");
					}
				} else {
					if (dungeonType.has[MathHelper.clamp(jj, 0, dungeonType.has.length - 1)] == i) {
						if (new File(p + "dungeon/" + dungeonType.toString() + "/" + dungeonType.toString() + "_" + i + ".lotfroom").exists()) {
							f = new File(p + "dungeon/" + dungeonType.toString() + "/" + dungeonType.toString() + "_" + i + ".lotfroom");
							jj++;
						} else {
							Console.print(Console.WarningType.FatalError, "Could not find room at " + p + "dungeon/" + dungeonType.toString() + "/" + dungeonType.toString() + "_" + i + ".lotfroom");
							f = new File(p + "testRoom.lotfroom");
						}
					} else {
						f = new File(p + "emptyRoom.lotfroom");
					}
				}
				
				fr = new FileReader(f);
				
				rooms.add(g.fromJson(fr, Room.class));
				
				rooms.get(i).type = type;
				rooms.get(i).dungeonType = dungeonType;
				rooms.get(i).roomID = i;
				rooms.get(i).setRoomPos(rooms.get(i).IDToRoomPos(i));
				
				boolean tb = false;
				for (int j = 0; j < rooms.get(i).getTileLayer0().size(); j++) {
					rooms.get(i).getTileLayer0().get(j).setRoom(rooms.get(i));
					rooms.get(i).getTileLayer1().get(j).setRoom(rooms.get(i));
					rooms.get(i).getTileLayer0().get(j).tileUpdate();
					rooms.get(i).getTileLayer1().get(j).tileUpdate();
					
					if (rooms.get(i).getTileLayer1().get(j).getTileType() == Tile.TileType.door) {
						rooms.get(i).setEnterPos(new Vec2i(rooms.get(i).getTileLayer1().get(j).getRelativeTilePos().getX() * Tile.TILE_SIZE, rooms.get(i).getTileLayer1().get(j).getRelativeTilePos().getY() * Tile.TILE_SIZE));
						tb = true;
					}
				}
				
				if (!tb) {
					if (rooms.get(i).getEntranceDir() == EnumDirection.nil) {
						rooms.get(i).setEnterPos(new Vec2i(0, 0));
					} else if (rooms.get(i).getEntranceDir() == EnumDirection.north) {
						rooms.get(i).setEnterPos(new Vec2i((rooms.get(i).roomSize.getX() * Tile.TILE_SIZE) / 2, 0));
					} else if (rooms.get(i).getEntranceDir() == EnumDirection.south) {
						rooms.get(i).setEnterPos(new Vec2i((rooms.get(i).roomSize.getX() * Tile.TILE_SIZE) / 2 - 16, (rooms.get(i).roomSize.getY() - 1) * Tile.TILE_SIZE));
					} else if (rooms.get(i).getEntranceDir() == EnumDirection.west) {
						rooms.get(i).setEnterPos(new Vec2i(0, (rooms.get(i).roomSize.getY() * Tile.TILE_SIZE) / 2 - 16));
					} else if (rooms.get(i).getEntranceDir() == EnumDirection.east) {
						rooms.get(i).setEnterPos(new Vec2i((rooms.get(i).roomSize.getX() - 1) * Tile.TILE_SIZE, (rooms.get(i).roomSize.getY() * Tile.TILE_SIZE) / 2 - 16));
					}
				}
				
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		
		Main.gamestate = Main.Gamestate.run;
	}
	
	public void updateMap(int roomID) {
		map.addRoom(roomID);
	}
	
	public Room getRoomAt(int x, int y) {
		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).getRoomPos().equals(new RoomPos(x, y))) {
				return rooms.get(i);
			}
		}
		
		return null;
	}
	
	public Map getMap() {
		return map;
	}
	
	public List<Room> getRooms() {
		return rooms;
	}
	
	public EnumDungeonType getDungeonType() {
		return dungeonType;
	}
	
	public WorldType getWorldType() {
		return type;
	}
	
	public enum WorldType {
		overworld (0, 16),
		underworld(1, 16),
		inside    (2, 16),
		dungeon   (3, 0);
		
		public final int fId, size;
		
		private WorldType(int id, int size) {
			this.fId = id;
			this.size = size;
		}
		
		public static WorldType getFromNumber(int id) {
			for (WorldType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
	
	@Override
	public String toString() {
		return "(" + type + ", " + dungeonType + ")";
	}
}
