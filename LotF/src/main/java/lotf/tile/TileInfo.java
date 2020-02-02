package main.java.lotf.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.java.lotf.util.ThingInfo;
import main.java.lotf.util.enums.EnumCollisionType;

public class TileInfo extends ThingInfo {
	
	private static final List<TileInfo> ALL = new ArrayList<TileInfo>();
	
	public static final TileInfo AIR = new TileInfo("air", true, false, EnumCollisionType.none);
	public static final TileInfo EMPTY_GRASS = new TileInfo("empty_grass", false, false, EnumCollisionType.none);
	public static final TileInfo GRASS = new TileInfo("grass", 2, 120, false, true, EnumCollisionType.none);
	public static final TileInfo FLOWER_GRASS = new TileInfo("flower_grass", 2, 120, false, true, EnumCollisionType.none);
	
	private final boolean shouldRenderBehind, hasRandomFlip;
	private final EnumCollisionType colType;
	
	private TileInfo(String name, int textureCount, int animationTime, boolean shouldRenderBehind, boolean hasRandomFlip, EnumCollisionType colType) {
		super(name, textureCount, animationTime);
		this.shouldRenderBehind = shouldRenderBehind;
		this.hasRandomFlip = hasRandomFlip;
		this.colType = colType;
		
		ALL.add(this);
	}
	
	private TileInfo(String name, boolean shouldRenderBehind, boolean hasRandomFlip, EnumCollisionType colType) {
		this(name, 1, 0, shouldRenderBehind, hasRandomFlip, colType);
	}
	
	public static TileInfo getRandomGrass() {
		int r = new Random().nextInt(3);
		
		if (r == 0) {
			return EMPTY_GRASS;
		} else if (r == 1) {
			return GRASS;
		} else {
			return FLOWER_GRASS;
		}
	}
	
	public boolean shouldRenderBehind() {
		return shouldRenderBehind;
	}
	
	public boolean hasRandomFlip() {
		return hasRandomFlip;
	}
	
	public EnumCollisionType getCollisionType() {
		return colType;
	}
	
	public static List<TileInfo> getAll() {
		return ALL;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TileInfo) {
			TileInfo info = (TileInfo) obj;
			
			if (info.name == name && info.textureCount == textureCount && info.animationTime == animationTime && info.shouldRenderBehind == shouldRenderBehind &&
					info.colType == colType) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "(name:" + name + ", textureCount:" + textureCount + ", animationTime:" + animationTime + ", shouldRenderBehind:" + shouldRenderBehind
				+ ", hasRandomFlip:" + hasRandomFlip + ", colType:" + colType + ")";
	}
}
