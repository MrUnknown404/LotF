package main.java.lotf.world;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.entity.EntityPlayer;
import main.java.lotf.util.IResetable;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public class World implements ITickable, IResetable {

	private List<Room> rooms = new ArrayList<>();
	
	private EntityPlayer player;
	
	public World(Vec2i size) {
		for (int yi = 0; yi < size.getY(); yi++) {
			for (int xi = 0; xi < size.getX(); xi++) {
				rooms.add(new Room(new Vec2i(xi, yi), new Vec2i(5, 5)));
			}
		}
		
		//temp
		player = new EntityPlayer(new Vec2f(10, 10), rooms.get(0));
	}
	
	@Override
	public void tick() {
		for (Room r : rooms) {
			r.tick();
		}
		
		if (player != null) {
			player.tick();
		}
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
	
	public EntityPlayer getPlayer() {
		return player;
	}
	
	public List<Room> getRooms() {
		return rooms;
	}
}
