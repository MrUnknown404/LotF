package main.java.lotf.world;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.util.IResetable;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.Vec2i;

public class World implements ITickable, IResetable {

	private List<Room> rooms = new ArrayList<>();
	private EnumWorldType worldType;
	
	World(Vec2i size, EnumWorldType worldType) {
		this.worldType = worldType;
		
		for (int yi = 0; yi < size.getY(); yi++) {
			for (int xi = 0; xi < size.getX(); xi++) {
				rooms.add(new Room(new Vec2i(xi, yi), new Vec2i(5, 5)));
			}
		}
	}
	
	@Override
	public void tick() {
		for (Room r : rooms) {
			r.tick();
		}
	}
	
	public void onEnter() {
		Main.getMain().getWorldHandler().setPlayerWorldType(worldType);
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
	
	public EnumWorldType getWorldType() {
		return worldType;
	}
}
