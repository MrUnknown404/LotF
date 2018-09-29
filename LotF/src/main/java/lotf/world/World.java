package main.java.lotf.world;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.util.EnumDungeonType;
import main.java.lotf.util.math.RoomPos;

public class World {
	
	private List<Room> rooms = new ArrayList<Room>();
	
	protected WorldType type;
	protected EnumDungeonType dungeonType = EnumDungeonType.nil;
	protected boolean isDungeon;
	
	public World(WorldType type, int xt, int yt) {
		this.type = type;
		
		generate(xt, yt);
	}
	
	public World(EnumDungeonType dungeonType, int xt, int yt) {
		this.type = WorldType.dungeon;
		this.dungeonType = dungeonType;
		this.isDungeon = true;
		
		generate(xt, yt);
	}
	
	public void generate(int xt, int yt) {
		int ti = 0;
		int yMulti = 0;
		
		for (int i = 0; i < xt * yt; i++) {
			
			if (ti == xt) {
				ti = 0;
				yMulti++;
			}
			
			rooms.add(new RoomOverworld(new RoomPos(ti, yMulti), Room.RoomSize.small, i));
			
			ti++;
		}
	}
	
	public List<Room> getRooms() {
		return rooms;
	}
	
	public WorldType getWorldType() {
		return type;
	}
	
	public enum WorldType {
		overworld (0),
		underworld(1),
		building  (2),
		dungeon   (3);
		
		private final int fId;
		
		private WorldType(int id) {
			fId = id;
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
