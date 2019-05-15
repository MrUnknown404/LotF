package main.java.lotf.world;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.util.Console;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.Vec2f;

public class WorldHandler implements ITickable {
	private List<World> worlds = new ArrayList<World>();
	
	private EntityPlayer player;
	
	public WorldHandler() {
		Console.print(Console.WarningType.Info, "World creation started...");
		
		for (EnumWorldType type : EnumWorldType.values()) {
			worlds.add(new World(World.WORLD_SIZE, type));
		}
		
		Console.print(Console.WarningType.Info, "World creation finished!");
		
		player = new EntityPlayer(worlds.get(0).getWorldType(), new Vec2f(10, 10), worlds.get(0).getRooms().get(0));
	}
	
	@Override
	public void tick() {
		if (player != null) {
			player.tick();
			getPlayerWorld().tick();
		}
	}
	
	public World getPlayerWorld() {
		for (World w : worlds) {
			if (w.getWorldType() == player.getWorldType()) {
				return w;
			}
		}
		
		return null;
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}
	
	public EnumWorldType getPlayerWorldType() {
		return  player.getWorldType();
	}
	
	public Room getPlayerRoom() {
		return player.getRoom();
	}
}
