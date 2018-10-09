package main.java.lotf.world;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.entity.EntityPlayer;
import main.java.lotf.util.EnumDungeonType;

public final class WorldHandler {

	private List<World> worlds = new ArrayList<World>();
	
	private EntityPlayer player;
	
	public WorldHandler() {
		worlds.add(new World(World.WorldType.overworld));
		worlds.add(new World(World.WorldType.underworld));
		worlds.add(new World(World.WorldType.inside));
		worlds.add(new World(EnumDungeonType.one));
		worlds.add(new World(EnumDungeonType.two));
		worlds.add(new World(EnumDungeonType.three));
		worlds.add(new World(EnumDungeonType.four));
		worlds.add(new World(EnumDungeonType.five));
		worlds.add(new World(EnumDungeonType.six));
		worlds.add(new World(EnumDungeonType.seven));
		worlds.add(new World(EnumDungeonType.eight));
		worlds.add(new World(EnumDungeonType.nine));
		worlds.add(new World(EnumDungeonType.ten));
		worlds.add(new World(EnumDungeonType.eleven));
		worlds.add(new World(EnumDungeonType.twelve));
		
		player = new EntityPlayer();
		player.setWorld(worlds.get(2));
		player.setRoom(player.getWorld().getRoomAt(0, 0));
	}
	
	public void tick() {
		if (player != null) {
			player.tick();
			player.getRoom().tick();;
		}
	}
	
	public void tickAnimation() {
		if (player != null) {
			player.getRoom().tickAnimation();;
		}
	}
	
	public List<World> getWorlds() {
		return worlds;
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}
	
	public World getPlayerWorld() {
		return player.getWorld();
	}
	
	public Room getPlayerRoom() {
		return player.getRoom();
	}
	
	public Room getPlayerRoomToBe() {
		return player.getRoomToBe();
	}
}
