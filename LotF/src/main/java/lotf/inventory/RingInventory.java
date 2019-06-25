package main.java.lotf.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.java.lotf.init.InitItems;
import main.java.lotf.items.rings.Ring;
import main.java.lotf.util.Console;
import main.java.lotf.util.math.Vec2i;

public class RingInventory {
	
	private Map<Ring, Boolean> rings = new HashMap<Ring, Boolean>();
	private List<Ring> selectedRings = new ArrayList<>(5);
	
	private final Vec2i size = new Vec2i(6, 1);
	
	public RingInventory() {
		for (int i = 0; i < InitItems.getRingsSize(); i++) {
			rings.put(InitItems.getRing(i), false);
		}
		
		for (int i = 0; i < size.getBothMulti(); i++) {
			selectedRings.add(null);
		}
	}
	
	public boolean addSelectedRing(Ring ring) {
		if (selectedRings.contains(ring)) {
			Console.print(Console.WarningType.Warning, "Player already has that Ring!");
			return false;
		}
		
		if (selectedRings.indexOf(null) != -1) {
			selectedRings.set(selectedRings.indexOf(null), ring);
			return true;
		}
		
		return false;
	}
	
	public boolean addRing(Ring ring) {
		if (rings.get(ring)) {
			Console.print(Console.WarningType.Warning, "Player already has that Ring!");
			return false;
		} else {
			rings.put(ring, true);
			return true;
		}
	}
	
	public int getSizeX() {
		return size.getX();
	}
	
	public int getSizeY() {
		return size.getY();
	}
	
	public int getAllRingSize() {
		return rings.size();
	}
	
	public int getSelectedRingSize() {
		return selectedRings.size();
	}
	
	public Ring getSelectedRing(int i) {
		return selectedRings.get(i);
	}
	
	public List<Ring> getAllRing() {
		List<Ring> rings = new ArrayList<Ring>();
		
		for (Entry<Ring, Boolean> e : this.rings.entrySet()) {
			rings.add(e.getKey());
		}
		
		return rings;
	}
}
