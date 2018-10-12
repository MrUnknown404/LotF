package main.java.lotf.tile;

import java.awt.Rectangle;

import main.java.lotf.util.EnumCollisionType;
import main.java.lotf.util.TickableGameObject;
import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.TilePos;
import main.java.lotf.world.Room;

public class Tile extends TickableGameObject {
	
	public static final int TILE_SIZE = 32;
	
	/** Must be set after load! */
	protected transient Room room;
	protected TileType tileType;
	protected TilePos relativeTilePos = new TilePos();
	protected transient EnumCollisionType collisionType;
	
	protected int meta;
	
	public Tile(TilePos relativeTilePos, TileType tileType, Room room, int meta) {
		super(relativeTilePos.getX(), relativeTilePos.getY(), TILE_SIZE, TILE_SIZE);
		this.tileType = tileType;
		this.relativeTilePos = relativeTilePos;
		this.room = room;
		this.meta = meta;
		
		tileUpdate();
	}
	
	private transient int ti;
	
	@Override
	public void tick() {
		
	}
	
	public void tickAnimation() {
		if (tileType == TileType.grassLeft || tileType == TileType.grassRight || tileType == TileType.grassFlowerLeft || tileType == TileType.grassFlowerRight ) {
			if (ti == tileType.animationTime) {
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
		
		width = Tile.TILE_SIZE;
		height = Tile.TILE_SIZE;
		
		if (tileType.isAnimated) {
			meta = 0;
		}
		
		if (tileType.colType == CollisionType.none) {
			collisionType = EnumCollisionType.none;
		} else if (tileType.colType == CollisionType.whole) {
			collisionType = EnumCollisionType.whole;
		} else if (tileType.colType == CollisionType.halfTop) {
			collisionType = EnumCollisionType.top;
		} else if (tileType.colType == CollisionType.halfBottom) { 
			collisionType = EnumCollisionType.bottom;
		} else if (tileType.colType == CollisionType.halfLeft) {
			collisionType = EnumCollisionType.left;
		} else if (tileType.colType == CollisionType.halfRight) {
			collisionType = EnumCollisionType.right;
		} else if (tileType.colType == CollisionType.halfHorz) {
			if (meta == 0 || meta == 2) {
				collisionType = EnumCollisionType.left;
			} else if (meta == 1 || meta == 3) {
				collisionType = EnumCollisionType.right;
			}
		} else if (tileType.colType == CollisionType.halfVert) {
			if (meta == 0 || meta == 2) {
				collisionType = EnumCollisionType.top;
			} else if (meta == 1 || meta == 3) {
				collisionType = EnumCollisionType.bottom;
			}
		} else if (tileType.colType == CollisionType.corner) {
			if (meta == 0) {
				collisionType = EnumCollisionType.topLeft;
			} else if (meta == 1) {
				collisionType = EnumCollisionType.topRight;
			} else if (meta == 2) {
				collisionType = EnumCollisionType.bottomRight;
			} else if (meta == 3) {
				collisionType = EnumCollisionType.bottomLeft;
			}
		} else if (tileType.colType == CollisionType.door) {
			collisionType = EnumCollisionType.door;
		}
		
		TilePos tilePos = new TilePos(relativeTilePos.getX() + (room.getRoomPos().getX() * room.getVecRoomSize().getX()), relativeTilePos.getY() + (room.getRoomPos().getY() * room.getVecRoomSize().getY()));
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
		return tileType.isAnimated;
	}
	
	public String getName() {
		return tileType.toString();
	}
	
	public int getMeta() {
		return meta;
	}
	
	public int getMaxMeta() {
		return tileType.count;
	}
	
	public TilePos getRelativeTilePos() {
		return relativeTilePos;
	}
	
	public EnumCollisionType getCollisionType() {
		return collisionType;
	}
	
	public TileType getTileType() {
		return tileType;
	}
	
	@Override
	public Rectangle getBounds() {
		if (collisionType == EnumCollisionType.whole) {
			return new Rectangle(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()), width, height);
		} else if (collisionType == EnumCollisionType.top) {
			return new Rectangle(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()), width, height / 2);
		} else if (collisionType == EnumCollisionType.bottom) {
			return new Rectangle(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY() + height / 2), width, height / 2);
		} else if (collisionType == EnumCollisionType.left) {
			return new Rectangle(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()), width / 2, height);
		} else if (collisionType == EnumCollisionType.right) {
			return new Rectangle(MathHelper.floor(getPositionX() + width / 2), MathHelper.floor(getPositionY()), width / 2, height);
		} else if (collisionType == EnumCollisionType.topLeft) {
			return new Rectangle(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()), width / 2, height / 2);
		} else if (collisionType == EnumCollisionType.topRight) {
			return new Rectangle(MathHelper.floor(getPositionX() + width / 2), MathHelper.floor(getPositionY()), width / 2, height / 2);
		} else if (collisionType == EnumCollisionType.bottomLeft) {
			return new Rectangle(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY() + height / 2), width / 2, height / 2);
		} else if (collisionType == EnumCollisionType.bottomRight) {
			return new Rectangle(MathHelper.floor(getPositionX() + width / 2), MathHelper.floor(getPositionY() + height / 2), width / 2, height / 2);
		} else if (collisionType == EnumCollisionType.door) {
			return new Rectangle(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY() - Tile.TILE_SIZE), width, height);
		}
		
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tile) {
			if (((Tile) obj).getTileType() == tileType) {
				return true;
			}
		}
		return false;
	}
	
	/** Must be the same as the texture name! */
	public enum TileType {
		air               (0,  1, false, 0, CollisionType.none,       false),
		door              (1,  2, false, 0, CollisionType.door,       false),
		blueWall          (2,  4, false, 0, CollisionType.whole,      true),
		blueWallCorner    (3,  8, false, 0, CollisionType.whole,      true),
		blueWallHalf      (4,  4, false, 0, CollisionType.halfHorz,   false),
		blueFloor         (5,  1, false, 0, CollisionType.none,       false),
		sand              (6,  2, false, 0, CollisionType.none,       false),
		grass             (7,  1, true, 90, CollisionType.none,       false),
		grassLeft         (8,  2, true, 90, CollisionType.none,       false),
		grassRight        (9,  2, true, 90, CollisionType.none,       false),
		grassFlowerLeft   (10, 2, true, 90, CollisionType.none,       false),
		grassFlowerRight  (11, 2, true, 90, CollisionType.none,       false),
		woodWall          (12, 4, false, 0, CollisionType.whole,      true),
		woodWallCorner    (13, 4, false, 0, CollisionType.whole,      true),
		woodWallHalf      (14, 4, false, 0, CollisionType.halfHorz,   false),
		woodFloor         (15, 1, false, 0, CollisionType.none,       false),
		stoneWall         (16, 2, false, 0, CollisionType.whole,      true),
		stoneWallCorner   (17, 4, false, 0, CollisionType.whole,      false),
		stoneWallSide     (18, 4, false, 0, CollisionType.whole,      false),
		stoneWallTop      (19, 1, false, 0, CollisionType.whole,      true),
		stoneWallTopCorner(20, 2, false, 0, CollisionType.whole,      false),
		stoneWallJump     (21, 1, false, 0, CollisionType.halfBottom, false),
		stairs            (22, 1, false, 0, CollisionType.none,       false);
		
		public final int fId, count, animationTime;
		public final boolean isAnimated, shouldRenderBehind;
		public CollisionType colType;
		
		private TileType(int id, int count, boolean isAnimated, int animationTime, CollisionType colType, boolean shouldRenderBehind) {
			this.fId = id;
			this.count = count;
			this.isAnimated = isAnimated;
			this.animationTime = animationTime;
			this.colType = colType;
			this.shouldRenderBehind = shouldRenderBehind;
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
		none      (0),
		whole     (1),
		halfTop   (2),
		halfBottom(3),
		halfLeft  (4),
		halfRight (5),
		halfHorz  (6),
		halfVert  (7),
		corner    (8),
		door      (9);
		
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
