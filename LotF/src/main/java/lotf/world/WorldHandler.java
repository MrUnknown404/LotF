package main.java.lotf.world;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.entity.EntityPlayer;
import main.java.lotf.init.InitEntities;
import main.java.lotf.util.EnumDungeonType;

public final class WorldHandler {

	private List<World> worlds = new ArrayList<World>();
	
	private EntityPlayer player;
	
	public WorldHandler() {
		worlds.add(new World(World.WorldType.overworld, 16, 16));
		worlds.add(new World(World.WorldType.underworld, 16, 16));
		worlds.add(new World(World.WorldType.building, 16, 16)); //change later!
		worlds.add(new World(EnumDungeonType.one, 1, 1));
		worlds.add(new World(EnumDungeonType.two, 1, 1));
		worlds.add(new World(EnumDungeonType.three, 1, 1));
		worlds.add(new World(EnumDungeonType.four, 1, 1));
		worlds.add(new World(EnumDungeonType.five, 1, 1));
		worlds.add(new World(EnumDungeonType.six, 1, 1));
		worlds.add(new World(EnumDungeonType.seven, 1, 1));
		worlds.add(new World(EnumDungeonType.eight, 1, 1));
		worlds.add(new World(EnumDungeonType.nine, 1, 1));
		worlds.add(new World(EnumDungeonType.ten, 1, 1));
		worlds.add(new World(EnumDungeonType.eleven, 1, 1));
		worlds.add(new World(EnumDungeonType.twelve, 1, 1));
		
		player = (EntityPlayer) InitEntities.get("ENT_player");
		player.setWorld(worlds.get(0));
		player.setRoom(player.getWorld().getRooms().get(149)); //5, 9
		
	}
	
	public void tick() {
		if (player != null) {
			player.tick();
			player.getRoom().tick();;
		}
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
