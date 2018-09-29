package main.java.lotf.world;

import main.java.lotf.util.math.RoomPos;

public class RoomOverworld extends Room {

	public RoomOverworld(RoomPos roomPos, RoomSize size, int roomID) {
		super(roomPos, World.WorldType.overworld, size, roomID);
	}
}
