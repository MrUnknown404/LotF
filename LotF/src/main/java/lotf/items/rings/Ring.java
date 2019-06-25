package main.java.lotf.items.rings;

import main.java.lotf.items.Item;

public abstract class Ring extends Item {
	
	protected final RingType ringType;
	
	public Ring(String name, String description, RingType ringType) {
		super(name, description);
		this.ringType = ringType;
	}
	
	public void onUse(RingType type) {
		
	}
	
	/** Not going to use */
	@Override public void onUse() {}
	
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
