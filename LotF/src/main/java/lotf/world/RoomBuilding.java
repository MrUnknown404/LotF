package main.java.lotf.world;

import main.java.lotf.util.math.RoomPos;

public class RoomBuilding extends Room {

	public RoomBuilding(RoomPos roomPos, RoomSize size, int roomID) {
		super(roomPos, World.WorldType.building, size, roomID);
	}
}
