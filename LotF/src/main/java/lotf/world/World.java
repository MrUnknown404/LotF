package main.java.lotf.world;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import main.java.lotf.Main;
import main.java.lotf.util.Console;
import main.java.lotf.util.EnumDungeonType;
import main.java.lotf.util.math.RoomPos;
import main.java.lotf.util.math.Vec2i;

public final class World {
	
	private List<Room> rooms = new ArrayList<Room>();
	
	protected Vec2i size = new Vec2i();;
	protected WorldType type;
	protected EnumDungeonType dungeonType = EnumDungeonType.nil;
	protected boolean isDungeon;
	protected Map map;
	
	public World(WorldType type) {
		this.type = type;
		this.size = new Vec2i(type.size, type.size);
		if (type != WorldType.inside) {
			this.map = new Map(type);
		}
		
		loadRooms(type.size, type.size);
	}
	
	public World(EnumDungeonType dungeonType) {
		this.type = WorldType.dungeon;
		this.dungeonType = dungeonType;
		this.isDungeon = true;
		this.size = new Vec2i(dungeonType.size, dungeonType.size);
		this.map = new Map(dungeonType);
		
		loadRooms(dungeonType.size, dungeonType.size);
	}
	
	private static final String BASE_LOCATION = "/main/resources/lotf/assets/rooms/";
	
	public void loadRooms(int xt, int yt) {
		Main.gamestate = Main.Gamestate.hardPause;
		
		Gson g = new Gson().newBuilder().setPrettyPrinting().create();
		FileReader fr;
		
		for (int i = 0; i < xt * yt; i++) {
			try {
				File f = null;
				String p = (World.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath() + BASE_LOCATION;
				
				
				if (type != WorldType.dungeon) {
					if (new File(p + type.toString() + "/" + type.toString() + "_" + i + ".lotfroom").exists()) {
						f = new File(p + type.toString() + "/" + type.toString() + "_" + i + ".lotfroom");
					} else {
						Console.print(Console.WarningType.FatalError, "Could not find room at " + p + type.toString() + "/" + type.toString() + "_" + i + ".lotfroom");
						f = new File(p + "missingRoom.lotfroom");
					}
				} else {
					if (new File(p + dungeonType.toString() + "/" + dungeonType.toString() + "_" + i + ".lotfroom").exists()) {
						f = new File(p + dungeonType.toString() + "/" + dungeonType.toString() + "_" + i + ".lotfroom");
					} else {
						Console.print(Console.WarningType.FatalError, "Could not find room at " + p + dungeonType.toString() + "/" + dungeonType.toString() + "_" + i + ".lotfroom");
						f = new File(p + "missingRoom.lotfroom");
					}
				}
				
				fr = new FileReader(f);
				
				rooms.add(g.fromJson(fr, Room.class));
				
				rooms.get(i).type = type;
				rooms.get(i).dungeonType = dungeonType;
				rooms.get(i).roomID = i;
				rooms.get(i).setRoomPos(rooms.get(i).IDToRoomPos(i));
				
				for (int j = 0; j < rooms.get(i).getTileLayer0().size(); j++) {
					rooms.get(i).getTileLayer0().get(j).setRoom(rooms.get(i));
					rooms.get(i).getTileLayer1().get(j).setRoom(rooms.get(i));
					rooms.get(i).getTileLayer0().get(j).tileUpdate();
					rooms.get(i).getTileLayer1().get(j).tileUpdate();
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
	
	public Room getStartingRoom(World.WorldType type, EnumDungeonType dungeon) {
		
		/* 
		 * CHANGE TO PROPER COORDS LATER
		 */
		
		if (type == World.WorldType.overworld) {
			return getRoomAt(5, 9);
		} else if (type == World.WorldType.underworld) {
			return null;
		} else if (type == World.WorldType.inside) {
			return null;
		} else if (type == World.WorldType.dungeon) {
			if (dungeon == EnumDungeonType.nil) {
				Console.print(Console.WarningType.FatalError, "Unknown dungeon generation type!");
			} else if (dungeon == EnumDungeonType.one) {
				return getRoomAt(0, 0);
			} else if (dungeon == EnumDungeonType.two) {
				return getRoomAt(0, 0);
			} else if (dungeon == EnumDungeonType.three) {
				return getRoomAt(0, 0);
			} else if (dungeon == EnumDungeonType.four) {
				return getRoomAt(0, 0);
			} else if (dungeon == EnumDungeonType.five) {
				return getRoomAt(0, 0);
			} else if (dungeon == EnumDungeonType.six) {
				return getRoomAt(0, 0);
			} else if (dungeon == EnumDungeonType.seven) {
				return getRoomAt(0, 0);
			} else if (dungeon == EnumDungeonType.eight) {
				return getRoomAt(0, 0);
			} else if (dungeon == EnumDungeonType.nine) {
				return getRoomAt(0, 0);
			} else if (dungeon == EnumDungeonType.ten) {
				return getRoomAt(0, 0);
			} else if (dungeon == EnumDungeonType.eleven) {
				return getRoomAt(0, 0);
			} else if (dungeon == EnumDungeonType.twelve) {
				return getRoomAt(0, 0);
			}
		}
		
		return null;
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
