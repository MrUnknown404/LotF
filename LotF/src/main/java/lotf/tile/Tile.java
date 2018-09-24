package main.java.lotf.tile;

import main.java.lotf.util.TickableGameObject;
import main.java.lotf.util.math.TilePos;
import main.java.lotf.world.Room;

public class Tile extends TickableGameObject {

	public static final Tile AIR = new Tile(new TilePos(), TileType.air, null, -1);
	
	public static final int TILE_SIZE = 32;
	
	protected Room room;
	protected TileType tileType;
	protected TilePos relativeTilePos = new TilePos(), tilePos = new TilePos();
	
	protected String stringID;
	protected int id = -1, meta = -1;
	
	public Tile(TilePos relativeTilePos, TileType tileType, Room room, int meta) {
		super(relativeTilePos.getX(), relativeTilePos.getY(), TILE_SIZE, TILE_SIZE);
		this.tileType = tileType;
		this.relativeTilePos = relativeTilePos;
		this.room = room;
		this.meta = meta;
		
		this.id = tileType.fId;
		this.stringID = "TIL_" + tileType.toString();
		
		tileUpdate();
	}
	
	@Override
	public void tick() {
		
	}
	
	public void tileUpdate() {
		if (room == null) {
			return;
		}
		
		tilePos = new TilePos(relativeTilePos.getX() + (room.getRoomPos().getX() * Room.ROOM_SIZE.getX()), relativeTilePos.getY() + (room.getRoomPos().getY() * Room.ROOM_SIZE.getY()));
		setPosition(tilePos.getX() * TILE_SIZE, tilePos.getY() * TILE_SIZE);
	}
	
	public TilePos getTilePos() {
		return tilePos;
	}
	
	public TilePos getRelativeTilePos() {
		return relativeTilePos;
	}
	
	public String getStringID() {
		return stringID;
	}
	
	public int getID() {
		return id;
	}
	
	public int getMeta() {
		return meta;
	}
	
	public TileType getTileType() {
		return tileType;
	}
	
	/* Must be the same as the texture name! */
	public enum TileType {
		air           (0, 1),
		blueWall      (1, 4),
		blueWallCorner(2, 4),
		blueWallHalf  (3, 4),
		sand          (4, 2);
		
		public final int fId, count;
		
		private TileType(int id, int count) {
			fId = id;
			this.count = count;
		}
		
		public static TileType getFromNumber(int id) {
			for (TileType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
