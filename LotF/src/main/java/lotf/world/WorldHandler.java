package main.java.lotf.world;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.ulibs.utils.Console;

public class WorldHandler implements ITickable {
	private List<World> worlds = new ArrayList<World>();
	
	private EntityPlayer player;
	
	public WorldHandler() {
		Console.print(Console.WarningType.Info, "World creation started...");
		
		for (EnumWorldType type : EnumWorldType.values()) {
			worlds.add(new World(type));
		}
		
		Console.print(Console.WarningType.Info, "World creation finished!");
	}
	
	public void startWorld() {
		player = new EntityPlayer(EnumWorldType.debugworld);
	}
	
	@Override
	public void tick() {
		if (player != null) {
			player.tick();
			getPlayerRoom().tick();
		}
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}
	
	public EnumWorldType getPlayerWorldType() {
		return player.getWorldType();
	}
	
	public Room getPlayerRoom() {
		return player.getRoom();
	}
	
	public World getPlayerWorld() {
		return getWorld(player.getWorldType());
	}
	
	public World getWorld(EnumWorldType worldType) {
		for (World w : worlds) {
			if (w.getWorldType() == worldType) {
				return w;
			}
		}
		
		return null;
	}
}
