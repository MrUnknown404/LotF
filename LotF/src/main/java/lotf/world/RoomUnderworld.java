package main.java.lotf.world;

import main.java.lotf.util.math.RoomPos;

public class RoomUnderworld extends Room {

	public RoomUnderworld(RoomPos roomPos, RoomSize size, int roomID) {
		super(roomPos, World.WorldType.underworld, size, roomID);
	}
}
