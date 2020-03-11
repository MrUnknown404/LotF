package main.java.lotf.world;

import main.java.lotf.util.Grid;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.MathH;
import main.java.lotf.util.math.Vec2i;

public class World {
	
	public static final int WORLD_SIZE = 17;
	
	private Grid<Room> rooms = new Grid<Room>(WORLD_SIZE, WORLD_SIZE);
	private EnumWorldType worldType;
	
	World(EnumWorldType worldType) {
		this.worldType = worldType;
		
		for (int yi = 0; yi < WORLD_SIZE; yi++) {
			for (int xi = 0; xi < WORLD_SIZE; xi++) {
				if (yi > worldType.getStartActiveBounds().getY() && yi < worldType.getEndActiveBounds().getY() && xi > worldType.getStartActiveBounds().getX() &&
						xi < worldType.getEndActiveBounds().getX()) {
					rooms.add(Room.createEmptyGrass(worldType, new Vec2i(xi, yi), xi + yi * WORLD_SIZE == 144 ? true : false), xi, yi);
				} else {
					rooms.add(null, xi, yi);
				}
			}
		}
	}
	
	public Room getFirstActiveRoom() {
		for (Room r : rooms.get()) {
			if (r != null) {
				return r;
			}
		}
		
		return null;
	}
	
	public Room getLastActiveRoom() {
		Room room = null;
		for (Room r : rooms.get()) {
			if (r != null) {
				room = r;
			}
		}
		
		return room;
	}
	
	public Grid<Room> getRooms() {
		return rooms;
	}
	
	public Room getRoom(int id) {
		int x = id % WORLD_SIZE, y = MathH.floor(id / WORLD_SIZE);
		
		return rooms.get(x, y);
	}
	
	public Room getRoom(int x, int y) {
		return rooms.get(x, y);
	}
	
	public EnumWorldType getWorldType() {
		return worldType;
	}
}
