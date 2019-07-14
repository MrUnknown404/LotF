package main.java.lotf.inventory;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.items.util.CollectableInfo;
import main.java.lotf.items.util.ItemInfo;
import main.java.lotf.util.math.Vec2i;

public class CollectableInventory {

	private final Vec2i size = new Vec2i(3, 5);
	
	private List<CollectableInfo> collectables = new ArrayList<CollectableInfo>();
	
	public CollectableInventory() {
		for (ItemInfo info : ItemInfo.values()) {
			if (info.toString().startsWith("collectable_")) {
				collectables.add(new CollectableInfo(info, false, 0));
			}
		}
	}
	
	public void addCollectable(ItemInfo info, int set) {
		for (int i = 0; i < ItemInfo.values().length; i++) {
			ItemInfo newInfo = ItemInfo.values()[0];
			
			if (info == newInfo) {
				CollectableInfo col = collectables.get(i);
				collectables.set(i, col.setAmount(set).setHas(true));
			}
		}
	}
	
	
	public int getSizeX() {
		return size.getX();
	}
	
	public int getSizeY() {
		return size.getY();
	}
	
	public List<CollectableInfo> getCollectables() {
		return collectables;
	}
}
