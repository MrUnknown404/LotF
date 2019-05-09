package main.java.lotf.world;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.entity.EntityPlayer;
import main.java.lotf.util.Console;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public class WorldHandler implements ITickable {
	private List<World> worlds = new ArrayList<World>();
	
	private EntityPlayer player;
	private EnumWorldType playerWorld;
	private Room playerRoom;
	
	public WorldHandler() {
		Console.print(Console.WarningType.Info, "World creation started...");
		
		for (EnumWorldType type : EnumWorldType.values()) {
			worlds.add(new World(new Vec2i(2, 2), type)); //TODO temporary
		}
		
		Console.print(Console.WarningType.Info, "World creation finished!");
		
		player = new EntityPlayer(new Vec2f(10, 10), worlds.get(0).getRooms().get(0));
		playerWorld = worlds.get(0).getWorldType();
	}
	
	@Override
	public void tick() {
		for (World w : worlds) {
			w.tick();
		}
		
		if (player != null) {
			player.tick();
		}
	}
	
	public void setPlayerWorldType(EnumWorldType type) {
		playerWorld = type;
	}
	
	public void setPlayerRoom(Room room) {
		playerRoom = room;
	}
	
	public World getPlayerWorld() {
		for (World w : worlds) {
			if (w.getWorldType() == playerWorld) {
				return w;
			}
		}
		
		return null;
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}
	
	public EnumWorldType getPlayerWorldType() {
		return playerWorld;
	}
	
	public Room getPlayerRoom() {
		return playerRoom;
	}
}
