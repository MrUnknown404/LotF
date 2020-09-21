package main.java.lotf.tile;

import java.awt.Rectangle;
import java.util.Random;

import com.google.gson.annotations.SerializedName;

import main.java.lotf.Main;
import main.java.lotf.init.Tiles;
import main.java.lotf.util.GameObject;
import main.java.lotf.util.annotation.UseGetter;
import main.java.lotf.util.enums.EnumCollisionType;
import main.java.ulibs.utils.math.MathH;
import main.java.ulibs.utils.math.Vec2f;
import main.java.ulibs.utils.math.Vec2i;

public class Tile extends GameObject {
	
	public static final int TILE_SIZE = 16;
	
	@Deprecated @UseGetter("getTileInfo")
	private transient TileInfo tileInfo;
	@SerializedName("tile")
	private final String tileInfoName;
	private final Vec2i tilePos;
	private transient boolean isFlipped;
	
	public Tile(Vec2i tilePos, TileInfo tileInfo) {
		super(new Vec2f(tilePos), new Vec2i(TILE_SIZE));
		this.tilePos = tilePos;
		this.tileInfo = tileInfo;
		this.tileInfoName = tileInfo.getName();
	}
	
	public void updateTile(Vec2i roomPos) {
		if (getTileInfo().hasRandomFlip()) {
			isFlipped = new Random().nextBoolean();
		}
		
		setPos(new Vec2f(roomPos.getX() + tilePos.getX() * TILE_SIZE, roomPos.getY() + tilePos.getY() * TILE_SIZE));
	}
	
	public boolean isFlipped() {
		return isFlipped;
	}
	
	public TileInfo getTileInfo() {
		if (tileInfo == null) {
			if (Main.isBuilder || !tileInfoName.equalsIgnoreCase(Tiles.RANDOM_GRASS.getName())) {
				for (TileInfo t : Tiles.getAll()) {
					if (t.getName().equals(tileInfoName)) {
						tileInfo = t;
						break;
					}
				}
			} else {
				return (tileInfo = Tiles.getRandomGrass());
			}
		}
		
		return tileInfo;
	}
	
	public Vec2i getTilePos() {
		return tilePos;
	}
	
	@Override
	public String toString() {
		return getTileInfo().toString();
	}
	
	@Override
	public Rectangle getBounds() {
		if (getTileInfo().getCollisionType() == EnumCollisionType.bottom || getTileInfo().getCollisionType() == EnumCollisionType.bottomLeft) {
			return new Rectangle(MathH.floor(getPosX()), MathH.floor(getPosY()) + getHeight(), getWidth(), getHeight());
		} else if (getTileInfo().getCollisionType() == EnumCollisionType.bottomRight) {
			return new Rectangle(MathH.floor(getPosX()) + getWidth(), MathH.floor(getPosY()) + getHeight(), getWidth(), getHeight());
		} else if (getTileInfo().getCollisionType() == EnumCollisionType.right || getTileInfo().getCollisionType() == EnumCollisionType.topRight) {
			return new Rectangle(MathH.floor(getPosX()) + getWidth(), MathH.floor(getPosY()), getWidth(), getHeight());
		}
		
		return super.getBounds();
	}
	
	@Override
	public int getWidth() {
		if (getTileInfo().getCollisionType() == EnumCollisionType.none) {
			return 0;
		} else if (getTileInfo().getCollisionType() == EnumCollisionType.whole || getTileInfo().getCollisionType() == EnumCollisionType.top ||
				getTileInfo().getCollisionType() == EnumCollisionType.bottom) {
			return TILE_SIZE;
		} else {
			return TILE_SIZE / 2;
		}
	}
	
	@Override
	public int getHeight() {
		if (getTileInfo().getCollisionType() == EnumCollisionType.none) {
			return 0;
		} else if (getTileInfo().getCollisionType() == EnumCollisionType.whole || getTileInfo().getCollisionType() == EnumCollisionType.left ||
				getTileInfo().getCollisionType() == EnumCollisionType.right) {
			return TILE_SIZE;
		} else {
			return TILE_SIZE / 2;
		}
	}
}
