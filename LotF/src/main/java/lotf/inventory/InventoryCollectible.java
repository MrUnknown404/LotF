package main.java.lotf.inventory;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.init.Collectibles;
import main.java.lotf.items.util.Collectible;
import main.java.lotf.util.math.Vec2i;

public class InventoryCollectible {

	private List<Collectible> collectibles = new ArrayList<Collectible>();
	private final Vec2i size = new Vec2i(3, 5);
	
	public InventoryCollectible() {
		for (Collectible info : Collectibles.ALL) {
			collectibles.add(info);
		}
	}
	
	public void addCollectable(Collectible col, int set) {
		for (int i = 0; i < Collectibles.ALL.size(); i++) {
			Collectible col2 = Collectibles.ALL.get(i);
			
			if (col == col2) {
				collectibles.set(i, collectibles.get(i).setAmount(set).setHas(true));
			}
		}
	}
	
	public int getSizeX() {
		return size.getX();
	}
	
	public int getSizeY() {
		return size.getY();
	}
	
	public List<Collectible> getCollectibles() {
		return collectibles;
	}
}
