package main.java.lotf.tile;

import java.util.Random;

import main.java.lotf.util.GameObject;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public class Tile extends GameObject {

	public static final int TILE_SIZE = 16;
	
	private final TileInfo tileInfo;
	private final Vec2i tilePos;
	
	private int flipState = -1;
	
	public Tile(Vec2i tilePos, TileInfo tileInfo) {
		super(new Vec2f(tilePos), new Vec2i(16, 16));
		this.tileInfo = tileInfo;
		this.tilePos = tilePos;
		
		updateTile();
	}
	
	public void updateTile() {
		if (new Random().nextBoolean()) {
			flipState = 1;
		} else {
			flipState = 0;
		}
		
		setPos(new Vec2f(tilePos.getX() * TILE_SIZE, tilePos.getY() * TILE_SIZE));
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
	
	public int getFlipState() {
		return flipState;
	}
	
	public TileInfo getTileInfo() {
		return tileInfo;
	}
	
	@Override
	public String toString() {
		return "(" + tileInfo + ", " + tilePos + ")";
	}
}
