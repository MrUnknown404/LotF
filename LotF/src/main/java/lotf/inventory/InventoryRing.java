package main.java.lotf.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.java.lotf.init.Items;
import main.java.lotf.items.rings.Ring;
import main.java.lotf.items.util.Item;
import main.java.ulibs.utils.Console;
import main.java.ulibs.utils.math.Vec2i;

public class InventoryRing extends Inventory<Ring> {
	
	private Map<Ring, Boolean> rings = new HashMap<Ring, Boolean>();
	
	public InventoryRing() {
		super(new Vec2i(6, 1));
		
		for (Item item : Items.getAll()) {
			if (item instanceof Ring) {
				rings.put((Ring) item, false);
			}
		}
	}
	
	public boolean addGlobalRing(Ring ring) {
		if (rings.get(ring)) {
			Console.print(Console.WarningType.Warning, "Player already has that Ring!");
			return false;
		}
		
		rings.put(ring, true);
		return true;
	}
	public int getAllRingSize() {
		return rings.size();
	}
	
	public List<Ring> getAllRings() {
		List<Ring> rings = new ArrayList<Ring>();
		
		for (Entry<Ring, Boolean> e : this.rings.entrySet()) {
			rings.add(e.getKey());
		}
		
		return rings;
	}
}
