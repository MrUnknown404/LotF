package main.java.lotf.items.rings;

import java.util.ArrayList;
import java.util.List;

public abstract class Ring {

	private static List<Ring> rings = new ArrayList<Ring>();
	
	public static final Ring EMPTY = null;
	public static final Ring BASIC = new RingBasic();
	public static final Ring ARCHER1 = new RingArcher1();
	
	private final String name;
	private final RingType ringType;
	
	Ring(String name, RingType ringType) {
		this.name = name;
		this.ringType = ringType;
		
		rings.add(this);
	}
	
	public void onUse(RingType type) {
		
	}
	
	public static List<Ring> getRings() {
		return rings;
	}
	
	public String getName() {
		return name;
	}
	
	public RingType getRingType() {
		return ringType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Ring) {
			Ring ring = (Ring) obj;
			if (ring.name == name && ring.ringType == ringType) {
				return true;
			}
		}
		
		return false;
	}
	
	public enum RingType {
		passive,
		monsterKill;
	}
}
