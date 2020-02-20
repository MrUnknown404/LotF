package main.java.lotf.tile;

import java.util.Random;

import main.java.lotf.util.GameObject;
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
		
		setPos(new Vec2f(roomPos.getX() + pos.getX() * TILE_SIZE, roomPos.getY() + pos.getY() * TILE_SIZE));
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
}
