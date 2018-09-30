package main.java.lotf.tile;

import main.java.lotf.util.TickableGameObject;
import main.java.lotf.util.math.TilePos;
import main.java.lotf.world.Room;

public abstract class Tile extends TickableGameObject {

	public static final Tile AIR = new TileAir(new TilePos());
	
	public static final int TILE_SIZE = 32;
	
	protected Room room;
	protected TileType tileType;
	protected TilePos relativeTilePos = new TilePos(), tilePos = new TilePos();
	
	protected String stringID;
	protected int meta, maxMeta, animationTime;
	protected boolean isWhole, isAnimated;
	
	public Tile(TilePos relativeTilePos, TileType tileType, Room room, int meta, int maxMeta, boolean isWhole, boolean isAnimated, int animationTime) {
		super(relativeTilePos.getX(), relativeTilePos.getY(), TILE_SIZE, TILE_SIZE);
		this.tileType = tileType;
		this.relativeTilePos = relativeTilePos;
		this.room = room;
		this.meta = meta;
		
		this.maxMeta = maxMeta;
		this.isWhole = isWhole;
		this.isAnimated = isAnimated;
		this.animationTime = animationTime;
		
		this.stringID = "TIL_" + tileType.toString();
		
		tileUpdate();
	}
	
	private int ti;
	
	@Override
	public void tick() {
		if (tileType == TileType.grassLeft || tileType == TileType.grassRight || tileType == TileType.grassFlowerLeft || tileType == TileType.grassFlowerRight ) {
			if (ti == animationTime) {
				if (meta == 0) {
					meta = 1;
				} else {
					meta = 0;
				}
				ti = 0;
			} else {
				ti++;
			}
		}
	}
	
	public void tileUpdate() {
		if (room == null) {
			return;
		}
		
		tilePos = new TilePos(relativeTilePos.getX() + (room.getRoomPos().getX() * room.getRoomSize().getX()), relativeTilePos.getY() + (room.getRoomPos().getY() * room.getRoomSize().getY()));
		setPosition(tilePos.getX() * TILE_SIZE, tilePos.getY() * TILE_SIZE);
	}
	
	public boolean getIsWhole() {
		return isWhole;
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
	
	public int getMeta() {
		return meta;
	}
	
	public int getMaxMeta() {
		return maxMeta;
	}
	
	public TileType getTileType() {
		return tileType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tile) {
			if (((Tile) obj).getTilePos().equals(tilePos) && ((Tile) obj).getStringID().equals(stringID) && ((Tile) obj).getTileType() == tileType) {
				return true;
			}
		}
		return false;
	}
	
	/** Must be the same as the texture name! */
	public enum TileType {
		air             (0, 1),
		blueWall        (1, 4),
		blueWallCorner  (2, 4),
		blueWallHalf    (3, 4),
		sand            (4, 2),
		grass           (5, 1),
		grassLeft       (6, 2),
		grassRight      (7, 2),
		grassFlowerLeft (8, 2),
		grassFlowerRight(9, 2);
		
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
	
	public enum CollisionType {
		none       (0),
		whole      (1),
		topLeft    (2),
		topRight   (3),
		bottomLeft (4),
		bottomRight(5),
		top        (6),
		bottom     (7),
		left       (8),
		right      (9);
		
		public final int fId;
		
		private CollisionType(int id) {
			fId = id;
		}
		
		public static CollisionType getFromNumber(int id) {
			for (CollisionType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
