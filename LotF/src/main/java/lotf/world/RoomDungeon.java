package main.java.lotf.world;

import main.java.lotf.util.math.RoomPos;

public class RoomDungeon extends Room {

	public RoomDungeon(RoomPos roomPos, RoomSize size, int roomID) {
		super(roomPos, World.WorldType.dungeon, size, roomID);
	}
}
