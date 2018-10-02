package main.java.lotfbuilder.util;

import main.java.lotf.items.util.Item;
import main.java.lotf.tile.Tile;

public class ItemTile extends Item {

	private Tile.TileType type;
	
	public ItemTile(Tile.TileType type, int meta) {
		super(type.toString(), meta, type.count, -99);
		this.type = type;
	}
	
	@Override
	protected void onUse() {
		
	}
	
	public Tile.TileType getTileType() {
		return type;
	}
}
