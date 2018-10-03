package main.java.lotf.world;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.util.EnumDungeonType;

public class Map {

	private World.WorldType type = World.WorldType.dungeon;
	private EnumDungeonType type2;
	private List<Boolean> rooms = new ArrayList<>();
	
	public Map(World.WorldType type) {
		this.type = type;
		
		for (int i = 0; i < type.size * type.size; i++) {
			rooms.add(false);
		}
	}
	
	public Map(EnumDungeonType type2) {
		this.type2 = type2;
		
		for (int i = 0; i < type2.size * type2.size; i++) {
			rooms.add(false);
		}
	}
	
	public void addRoom(int roomID) {
		rooms.set(roomID, true);
	}
	
	public List<Boolean> getRooms() {
		return rooms;
	}
	
	public World.WorldType getWorldType() {
		return type;
	}
	
	public EnumDungeonType getDungeonType() {
		return type2;
	}
}
