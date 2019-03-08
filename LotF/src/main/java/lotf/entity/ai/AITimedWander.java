package main.java.lotf.entity.ai;

import java.util.Random;

import main.java.lotf.entity.EntityMonster;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.EnumDirection;
import main.java.lotf.util.math.MathHelper;

public class AITimedWander extends AIMonsterBase {
	
	private EnumDirection moveDir = EnumDirection.nil;
	private final int max = 300, min = 120;
	private int t;
	
	@Override
	public void tick() {
		if (moveDir != EnumDirection.nil) {
			if (t == 0) {
				moveDir = EnumDirection.nil;
			} else {
				if (e.getInfront(moveDir) == null) {
					if (e instanceof EntityMonster) {
						if (moveDir == EnumDirection.north) {
							if (e.getPositionY() > 0) {
								e.addPosition(0, -e.getMonsterType().moveSpeed);
							}
						} else if (moveDir == EnumDirection.east) {
							if (e.getPositionX() < e.getRoom().getVecRoomSize().getX() * Tile.TILE_SIZE - e.getWidth()) {
								e.addPosition(e.getMonsterType().moveSpeed, 0);
							}
						} else if (moveDir == EnumDirection.south) {
							if (e.getPositionY() < e.getRoom().getVecRoomSize().getY() * Tile.TILE_SIZE - e.getHeight()) {
								e.addPosition(0, e.getMonsterType().moveSpeed);
							}
						} else if (moveDir == EnumDirection.west) {
							if (e.getPositionX() > 0) {
								e.addPosition(-e.getMonsterType().moveSpeed, 0);
							}
						}
					}
				}
				
				t = MathHelper.clamp(t - 3, 0, Integer.MAX_VALUE);
			}
		} else {
			if (t > min) {
				if (t == max) {
					move();
				} else if (new Random().nextInt(min) == 0) {
					move();
				}
			}
			t++;
		}
	}
	
	@Override
	protected void move() {
		int v;
		while (true) {
			v = new Random().nextInt(EnumDirection.values().length - 1);
			
			if (e.getPositionY() == 0 && v == 0) {
			} else if (e.getPositionX() == e.getRoom().getVecRoomSize().getX() * Tile.TILE_SIZE - e.getWidth() && v == 1) {
			} else if (e.getPositionY() == e.getRoom().getVecRoomSize().getY() * Tile.TILE_SIZE - e.getHeight() && v == 2) {
			} else if (e.getPositionX() == 0 && v == 3) {
			} else if (e.getFacing().fId != v + 1) {
				break;
			}
		}
		
		moveDir = EnumDirection.getFromNumber(v + 1);
		e.setMeta((moveDir.fId - 1) * 2);
		e.setFacing(moveDir);
	}
}
