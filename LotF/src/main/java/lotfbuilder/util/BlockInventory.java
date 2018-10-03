package main.java.lotfbuilder.util;

import main.java.lotf.inventory.Inventory;
import main.java.lotf.tile.Tile;
import main.java.lotfbuilder.MainBuilder;

public final class BlockInventory extends Inventory {
	
	public BlockInventory(int pageNumber) {
		super(11, 5);
		
		int start = MainBuilder.getRoomBuilder().lastTileType;
		int end = Tile.TileType.values().length;
		int startMeta = MainBuilder.getRoomBuilder().lastMeta;
		
		firstLoop:
		for (int i = start; i < end; i++) {
			Tile.TileType t = Tile.TileType.getFromNumber(i);
			if (t.isAnimated == false) {
				for (int m = startMeta; m < t.count; m++) {
					if (!isFull()) {
						addItem(new ItemTile(t, m));
					} else {
						MainBuilder.getRoomBuilder().lastTileType = i;
						MainBuilder.getRoomBuilder().lastMeta = m;
						break firstLoop;
					}
				}
			} else {
				if (!isFull()) {
					addItem(new ItemTile(t, 0));
				} else {
					MainBuilder.getRoomBuilder().lastTileType = i;
					MainBuilder.getRoomBuilder().lastMeta = 0;
				}
			}
		}
	}
}
