package main.java.lotf.world;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.util.IResetable;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.LangKey;
import main.java.lotf.util.LangKey.LangKeyType;
import main.java.lotf.util.LangKey.LangType;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.Vec2i;

public class World implements ITickable, IResetable {

	public static final Vec2i WORLD_SIZE = new Vec2i(17, 17);
	
	private List<Room> rooms = new ArrayList<>();
	private EnumWorldType worldType;
	
	World(Vec2i size, EnumWorldType worldType) {
		this.worldType = worldType;
		
		for (int yi = 0; yi < size.getY(); yi++) {
			for (int xi = 0; xi < size.getX(); xi++) {
				if (yi > worldType.getStartActiveBounds().getY() && yi < worldType.getEndActiveBounds().getY() &&
						xi > worldType.getStartActiveBounds().getX() && xi < worldType.getEndActiveBounds().getX()) {
					if (xi + yi * size.getX() == 144) {
						rooms.add(new Room(xi + yi * size.getX(), new Vec2i(xi, yi), new Vec2i(16, 8), new LangKey(LangType.gui, "room" + (xi + yi * size.getX()),
								LangKeyType.desc)));
					} else {
						rooms.add(new Room(xi + yi * size.getX(), new Vec2i(xi, yi), new Vec2i(16, 8), null));
					}
				} else {
					rooms.add(null);
				}
			}
		}
	}
	
	@Override
	public void tick() {
		for (Room r : rooms) {
			if (r != null) {
				r.tick();
			}
		}
	}
	
	public Room getFirstActiveRoom() {
		for (Room r : rooms) {
			if (r != null) {
				return r;
			}
		}
		
		return null;
	}
	
	public Room getLastActiveRoom() {
		Room room = null;
		for (Room r : rooms) {
			if (r != null) {
				room = r;
			}
		}
		
		return room;
	}
	
	public void onEnter() {
		
	}
	
	public void onLeave() {
		hardReset();
	}
	
	@Override
	public void softReset() {
		for (Room r : rooms) {
			r.softReset();
		}
	}
	
	@Override
	public void hardReset() {
		for (Room r : rooms) {
			r.hardReset();
		}
	}
	
	public List<Room> getRooms() {
		return rooms;
	}
	
	public Room getRoom(int id) {
		return rooms.get(id);
	}
	
	public EnumWorldType getWorldType() {
		return worldType;
	}
}
