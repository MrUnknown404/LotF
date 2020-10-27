package main.java.lotf.world;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.entities.EntityEnemyTest;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.entities.util.Entity;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.ulibs.utils.Console;
import main.java.ulibs.utils.math.Vec2f;

public class WorldHandler implements ITickable {
	private List<World> worlds = new ArrayList<World>();
	
	private EntityPlayer player;
	
	public WorldHandler() {
		Console.print(Console.WarningType.Info, "World creation started...");
		
		for (EnumWorldType type : EnumWorldType.values()) {
			worlds.add(new World(type));
		}
		
		Console.print(Console.WarningType.Info, "World creation finished!");
		
		player = new EntityPlayer(EnumWorldType.debugworld, new Vec2f(10, 10), worlds.get(1).getRooms().get(8, 8));
		spawnEntity(new EntityEnemyTest(getPlayerRoom(), new Vec2f(200, 30)));
	}
	
	@Override
	public void tick() {
		if (player != null) {
			player.tick();
			getPlayerRoom().tick();
		}
	}
	
	public void spawnEntity(Entity entity) {
		getPlayerRoom().spawnEntity(entity);
	}
	
	public void killEntity(Entity entity) {
		getPlayerRoom().killEntity(entity);
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
