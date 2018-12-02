package main.java.lotfbuilder.util;

import main.java.lotf.entity.EntityMonster;
import main.java.lotf.entity.EntityNPC;
import main.java.lotf.inventory.Inventory;
import main.java.lotf.tile.Tile;
import main.java.lotfbuilder.MainBuilder;

public final class BuilderInventory extends Inventory {
	
	public static boolean didBlocks, didMonsters;
	
	public BuilderInventory(int pageNumber) {
		super(11, 5);
		
		int start = MainBuilder.getRoomBuilder().lastType;
		int end = Tile.TileType.values().length;
		int startMeta = MainBuilder.getRoomBuilder().lastMeta;
		
		if (!didBlocks) {
			firstLoop:
			for (int i = start; i < end; i++) {
				Tile.TileType t = Tile.TileType.getFromNumber(i);
				if (t.isAnimated == false) {
					for (int m = startMeta; m < t.count; m++) {
						if (!isFull()) {
							addItem(new ItemTile(t, m));
						} else {
							MainBuilder.getRoomBuilder().lastType = i;
							MainBuilder.getRoomBuilder().lastMeta = m;
							break firstLoop;
						}
					}
				} else {
					if (!isFull()) {
						addItem(new ItemTile(t, 0));
					} else {
						MainBuilder.getRoomBuilder().lastType = i;
						MainBuilder.getRoomBuilder().lastMeta = 0;
					}
				}
				
				if (i == end - 1) {
					didBlocks = true;
				}
			}
		}
		
		if (didBlocks) {
			if (!didMonsters) {
				start = 0;
				end = EntityMonster.MonsterType.values().length;
				
				firstLoop:
				for (int i = start; i < end; i++) {
					
					EntityMonster.MonsterType t = EntityMonster.MonsterType.getFromNumber(i);
					if (!isFull()) {
						addItem(new ItemEntity(t, 0));
					} else {
						MainBuilder.getRoomBuilder().lastType = i;
						break firstLoop;
					}
					
					if (i == end - 1) {
						didMonsters = true;
					}
				}
			}
			
			if (didMonsters) {
				start = 0;
				end = EntityNPC.NPCType.values().length;
				
				firstLoop:
				for (int i = start; i < end; i++) {
					
					EntityNPC.NPCType t = EntityNPC.NPCType.getFromNumber(i);
					if (!isFull()) {
						addItem(new ItemEntity(t, 0));
					} else {
						MainBuilder.getRoomBuilder().lastType = i;
						break firstLoop;
					}
				}
			}
		}
	}
}
