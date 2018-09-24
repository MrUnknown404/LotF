package main.java.lotf.world;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.entity.EntityPlayer;
import main.java.lotf.init.InitEntities;
import main.java.lotf.util.math.RoomPos;

public final class RoomHandler {

	private List<Room> rooms = new ArrayList<Room>();
	
	private EntityPlayer player;
	
	public RoomHandler() {
		int ti = 0;
		int yMulti = 0;
		
		for (int i = 0; i < 64; i++) {
			
			if (ti == 8) {
				ti = 0;
				yMulti++;
			}
			
			rooms.add(new Room(new RoomPos(ti, yMulti)));
			
			ti++;
		}
		
		player = (EntityPlayer) InitEntities.get("ENT_player");
		player.setRoom(rooms.get(0));
	}
	
	public void tick() {
		if (player != null) {
			player.tick();
			player.getRoom().tick();;
		}
	}
	
	public void updateActives() {
		
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}
	
	public Room getPlayerRoom() {
		return player.getRoom();
	}
	
	public List<Room> getRooms() {
		return rooms;
	}
}
