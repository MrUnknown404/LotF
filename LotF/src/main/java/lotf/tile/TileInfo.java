package main.java.lotf.tile;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.util.enums.EnumCollisionType;

public final class TileInfo {
	
	private static List<TileInfo> all = new ArrayList<TileInfo>();
	
	public static final TileInfo AIR = new TileInfo("air", 0, EnumCollisionType.none);
	
	private final String name;
	private final int textureCount, animationTime;
	private final boolean shouldRenderBehind;
	private final EnumCollisionType colType;
	
	public TileInfo(String name, int textureCount, int animationTime, boolean shouldRenderBehind, EnumCollisionType colType) {
		this.name = name;
		this.textureCount = textureCount;
		this.animationTime = animationTime;
		this.shouldRenderBehind = shouldRenderBehind;
		this.colType = colType;
		
		all.add(this);
	}
	
	public TileInfo(String name, int textureCount, boolean shouldRenderBehind, EnumCollisionType colType) {
		this.name = name;
		this.textureCount = textureCount;
		this.animationTime = 0;
		this.shouldRenderBehind = shouldRenderBehind;
		this.colType = colType;
		
		all.add(this);
	}
	
	public TileInfo(String name, int textureCount, int animationTime, EnumCollisionType colType) {
		this.name = name;
		this.textureCount = textureCount;
		this.animationTime = animationTime;
		this.shouldRenderBehind = false;
		this.colType = colType;
		
		all.add(this);
	}
	
	public TileInfo(String name, int textureCount, EnumCollisionType colType) {
		this.name = name;
		this.textureCount = textureCount;
		this.animationTime = 0;
		this.shouldRenderBehind = false;
		this.colType = colType;
		
		all.add(this);
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
	
	public boolean getShouldRenderBehind() {
		return shouldRenderBehind;
	}
	
	public EnumCollisionType getCollisionType() {
		return colType;
	}
	
	public static List<TileInfo> getAllTypes() {
		return all;
	}
}
