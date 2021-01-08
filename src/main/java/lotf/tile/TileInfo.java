package main.java.lotf.tile;

import main.java.lotf.init.Tiles;
import main.java.lotf.util.GameObjectInfo;
import main.java.lotf.util.enums.EnumCollisionType;

public class TileInfo extends GameObjectInfo {
	
	protected final boolean shouldRenderBehind, hasRandomFlip;
	protected final EnumCollisionType colType;
	
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
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof GameObjectInfo)) {
			return false;
		}
		
		TileInfo other = (TileInfo) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
