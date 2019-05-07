package main.java.lotf.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.java.lotf.util.enums.EnumCollisionType;

public class TileInfo {
	
	private static List<TileInfo> all = new ArrayList<TileInfo>();
	
	public static final TileInfo AIR = new TileInfo("air", true, false, EnumCollisionType.none);
	public static final TileInfo EMPTY_GRASS = new TileInfo("empty_grass", false, false, EnumCollisionType.none);
	public static final TileInfo GRASS = new TileInfo("grass", 2, 120, false, true, EnumCollisionType.none);
	public static final TileInfo FLOWER_GRASS = new TileInfo("flower_grass", 2, 120, false, true, EnumCollisionType.none);
	
	private final String name;
	private final int textureCount, animationTime;
	private final boolean shouldRenderBehind, hasRandomFlip;
	private final EnumCollisionType colType;
	
	private TileInfo(String name, int textureCount, int animationTime, boolean shouldRenderBehind, boolean hasRandomFlip, EnumCollisionType colType) {
		this.name = name;
		this.textureCount = textureCount;
		this.animationTime = animationTime;
		this.shouldRenderBehind = shouldRenderBehind;
		this.hasRandomFlip = hasRandomFlip;
		this.colType = colType;
		
		all.add(this);
	}
	
	private TileInfo(String name, boolean shouldRenderBehind, boolean hasRandomFlip, EnumCollisionType colType) {
		this.name = name;
		this.textureCount = 1;
		this.animationTime = 0;
		this.shouldRenderBehind = shouldRenderBehind;
		this.hasRandomFlip = hasRandomFlip;
		this.colType = colType;
		
		all.add(this);
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
	
	public String getName() {
		return name;
	}
	
	public int getTextureCount() {
		return textureCount;
	}
	
	public int getAnimationTime() {
		return animationTime;
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
	
	public static List<TileInfo> getAllTypes() {
		return all;
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
