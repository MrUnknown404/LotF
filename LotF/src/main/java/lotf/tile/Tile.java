package main.java.lotf.tile;

import main.java.lotf.util.TickableGameObject;
import main.java.lotf.util.math.TilePos;
import main.java.lotf.world.Room;

public class Tile extends TickableGameObject {
	
	public static final int TILE_SIZE = 32;
	
	/** Must be set after load! */
	protected transient Room room;
	protected TileType tileType;
	protected CollisionType collisionType;
	protected TilePos relativeTilePos = new TilePos(), tilePos = new TilePos();
	
	protected String stringID;
	protected int meta, maxMeta, animationTime;
	protected boolean isAnimated;
	
	public Tile(TilePos relativeTilePos, TileType tileType, Room room, int meta, int maxMeta) {
		super(relativeTilePos.getX(), relativeTilePos.getY(), TILE_SIZE, TILE_SIZE);
		this.tileType = tileType;
		this.relativeTilePos = relativeTilePos;
		this.room = room;
		this.meta = meta;
		
		this.maxMeta = maxMeta;
		this.isAnimated = tileType.isAnimated;
		this.animationTime = tileType.animationTime;
		
		if (tileType.hasCollision) {
			if (tileType.toString().substring(tileType.toString().length() - 4, tileType.toString().length()).equals("Half")) {
				if (meta == 0 || meta == 2) {
					this.collisionType = CollisionType.left;
				} else if (meta == 1 || meta == 3) {
					this.collisionType = CollisionType.right;
				}
			} else {
				this.collisionType = CollisionType.whole;
			}
		} else {
			this.collisionType = CollisionType.none;
		}
		
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
	
	public void resetAnimation() {
		ti = 0;
		meta = 0;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	
	public boolean getIsAnimated() {
		return isAnimated;
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
	
	public CollisionType getCollisionType() {
		return collisionType;
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
		air             (0, 1, false, 0, false),
		blueWall        (1, 4, false, 0, true),
		blueWallCorner  (2, 4, false, 0, true),
		blueWallHalf    (3, 4, false, 0, true),
		sand            (4, 2, false, 0, false),
		grass           (5, 1, true, 90, false),
		grassLeft       (6, 2, true, 90, false),
		grassRight      (7, 2, true, 90, false),
		grassFlowerLeft (8, 2, true, 90, false),
		grassFlowerRight(9, 2, true, 90, false);
		
		public final int fId, count, animationTime;
		public final boolean isAnimated, hasCollision;
		
		private TileType(int id, int count, boolean isAnimated, int animationTime, boolean hasCollision) {
			this.fId = id;
			this.count = count;
			this.isAnimated = isAnimated;
			this.animationTime = animationTime;
			this.hasCollision = hasCollision;
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
