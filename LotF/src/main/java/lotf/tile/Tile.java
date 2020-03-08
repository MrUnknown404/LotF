package main.java.lotf.tile;

import java.awt.Rectangle;
import java.util.Random;

import main.java.lotf.util.GameObject;
import main.java.lotf.util.enums.EnumCollisionType;
import main.java.lotf.util.math.MathH;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public class Tile extends GameObject {
	
	public static final int TILE_SIZE = 16;
	
	private final TileInfo tileInfo;
	private boolean isFlipped;
	
	public Tile(Vec2i tilePos, TileInfo tileInfo) {
		super(new Vec2f(tilePos), new Vec2i(TILE_SIZE, TILE_SIZE));
		this.tileInfo = tileInfo;
	}
	
	public void updateTile(Vec2i roomPos) {
		if (tileInfo.hasRandomFlip()) {
			isFlipped = new Random().nextBoolean();
		}
		
		setPos(new Vec2f(roomPos.getX() + getPosX() * TILE_SIZE, roomPos.getY() + getPosY() * TILE_SIZE));
	}
	
	public boolean isFlipped() {
		return isFlipped;
	}
	
	public TileInfo getTileInfo() {
		return tileInfo;
	}
	
	@Override
	public String toString() {
		return tileInfo.toString();
	}
	
	@Override
	public Rectangle getBounds() {
		if (tileInfo.getCollisionType() == EnumCollisionType.bottom || tileInfo.getCollisionType() == EnumCollisionType.bottomLeft) {
			return new Rectangle(MathH.floor(getPosX()), MathH.floor(getPosY()) + getHeight(), getWidth(), getHeight());
		} else if (tileInfo.getCollisionType() == EnumCollisionType.bottomRight) {
			return new Rectangle(MathH.floor(getPosX()) + getWidth(), MathH.floor(getPosY()) + getHeight(), getWidth(), getHeight());
		} else if (tileInfo.getCollisionType() == EnumCollisionType.right || tileInfo.getCollisionType() == EnumCollisionType.topRight) {
			return new Rectangle(MathH.floor(getPosX()) + getWidth(), MathH.floor(getPosY()), getWidth(), getHeight());
		}
		
		return super.getBounds();
	}
	
	@Override
	public int getWidth() {
		if (tileInfo.getCollisionType() == EnumCollisionType.none) {
			return 0;
		} else if (tileInfo.getCollisionType() == EnumCollisionType.whole || tileInfo.getCollisionType() == EnumCollisionType.top ||
				tileInfo.getCollisionType() == EnumCollisionType.bottom) {
			return TILE_SIZE;
		} else {
			return TILE_SIZE / 2;
		}
	}
	
	@Override
	public int getHeight() {
		if (tileInfo.getCollisionType() == EnumCollisionType.none) {
			return 0;
		} else if (tileInfo.getCollisionType() == EnumCollisionType.whole || tileInfo.getCollisionType() == EnumCollisionType.left ||
				tileInfo.getCollisionType() == EnumCollisionType.right) {
			return TILE_SIZE;
		} else {
			return TILE_SIZE / 2;
		}
	}
}
