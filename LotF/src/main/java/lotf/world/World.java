package main.java.lotf.world;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.util.Console;
import main.java.lotf.util.EnumDungeonType;
import main.java.lotf.util.math.RoomPos;
import main.java.lotf.util.math.Vec2i;

public class World {
	
	private List<Room> rooms = new ArrayList<Room>();
	
	protected Vec2i size = new Vec2i();;
	protected WorldType type;
	protected EnumDungeonType dungeonType = EnumDungeonType.nil;
	protected boolean isDungeon;
	protected Map map;
	
	public World(WorldType type) {
		this.type = type;
		this.size = new Vec2i(type.size, type.size);
		this.map = new Map(type);
		
		generate(type.size, type.size);
	}
	
	public World(EnumDungeonType dungeonType) {
		this.type = WorldType.dungeon;
		this.dungeonType = dungeonType;
		this.isDungeon = true;
		this.size = new Vec2i(dungeonType.size, dungeonType.size);
		this.map = new Map(type);
		
		generate(dungeonType.size, dungeonType.size);
	}
	
	private void generate(int xt, int yt) {
		for (int i = 0; i < xt * yt; i++) {
			rooms.add(new Room(type, dungeonType, Room.RoomSize.small, i));
		}
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
