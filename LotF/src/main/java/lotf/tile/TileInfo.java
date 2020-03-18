package main.java.lotf.tile;

import main.java.lotf.init.Tiles;
import main.java.lotf.util.ThingInfo;
import main.java.lotf.util.enums.EnumCollisionType;

public class TileInfo extends ThingInfo {
	
	private final boolean shouldRenderBehind, hasRandomFlip;
	private final EnumCollisionType colType;
	
	public TileInfo(String name, int textureCount, int animationTime, boolean shouldRenderBehind, boolean hasRandomFlip, EnumCollisionType colType) {
		super(name, textureCount, animationTime);
		this.shouldRenderBehind = shouldRenderBehind;
		this.hasRandomFlip = hasRandomFlip;
		this.colType = colType;
		
		Tiles.add(this);
	}
	
	public TileInfo(String name, boolean shouldRenderBehind, boolean hasRandomFlip, EnumCollisionType colType) {
		this(name, 1, 0, shouldRenderBehind, hasRandomFlip, colType);
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
	
	@Override
	public String toString() {
		return "(name:" + name + ", textureCount:" + textureCount + ", animationTime:" + animationTime + ", shouldRenderBehind:" + shouldRenderBehind
				+ ", hasRandomFlip:" + hasRandomFlip + ", colType:" + colType + ")";
	}
}
