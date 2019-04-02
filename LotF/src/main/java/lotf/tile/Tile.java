package main.java.lotf.tile;

import main.java.lotf.util.GameObject;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public final class Tile extends GameObject {

	private static final Vec2i TILE_SIZE = new Vec2i(16, 16);
	
	private final TileInfo tileInfo;
	private final Vec2i tilePos;
	
	public Tile(Vec2i tilePos, TileInfo tileInfo) {
		super(new Vec2f(tilePos), TILE_SIZE);
		this.tileInfo = tileInfo;
		this.tilePos = tilePos;
		
		updateTile();
	}
	
	public void updateTile() {
		setPos(new Vec2f(tilePos.getX() * 16, tilePos.getY() * 16));
	}
	
	public Vec2i getTilePos() {
		return tilePos;
	}
	
	public float getTilePosX() {
		return tilePos.getX();
	}
	
	public float getTilePosY() {
		return tilePos.getY();
	}
	
	public TileInfo getTileInfo() {
		return tileInfo;
	}
	
	@Override
	public String toString() {
		return "(" + tileInfo.getName() + ", " + tilePos + ")";
	}
}
