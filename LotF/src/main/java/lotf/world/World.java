package main.java.lotf.world;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.util.IResetable;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.Vec2i;

public class World implements ITickable, IResetable {

	private List<Room> rooms = new ArrayList<>();
	private EnumWorldType worldType;
	
	World(Vec2i size, EnumWorldType worldType) {
		this.worldType = worldType;
		
		for (int yi = 0; yi < size.getY(); yi++) {
			for (int xi = 0; xi < size.getX(); xi++) {
				rooms.add(new Room(xi + yi * size.getX(), new Vec2i(xi, yi), new Vec2i(5, 5)));
			}
		}
	}
	
	@Override
	public void tick() {
		for (Room r : rooms) {
			r.tick();
		}
		
		for (EnumDirection type : EnumDirection.values()) {
			if (type != EnumDirection.nil) {
				EntityPlayer player = Main.getMain().getWorldHandler().getPlayer();
				if (player != null) {
					if (player.getRoom().getRoomBounds(type).intersects(player.getBounds())) {
						for (Room r : rooms) {
							if (r != player.getRoom() && player.getRoom().getRoomBounds(type).intersects(r.getBounds())) {
								player.moveRoom(r, type);
								return;
							}
						}
					}
				}
			}
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
