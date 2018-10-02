package main.java.lotf.world;

import java.util.ArrayList;
import java.util.List;

public class Map {

	private World.WorldType type;
	private List<Boolean> rooms = new ArrayList<>();
	
	public Map(World.WorldType type) {
		this.type = type;
		
		for (int i = 0; i < type.size * type.size; i++) {
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
}
