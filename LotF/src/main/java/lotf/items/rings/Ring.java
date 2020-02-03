package main.java.lotf.items.rings;

import main.java.lotf.items.util.Item;
import main.java.lotf.util.LangKey.LangType;

public abstract class Ring extends Item {
	
	protected final RingType ringType;
	
	public Ring(String key, RingType ringType) {
		super(LangType.ring, key);
		this.ringType = ringType;
	}
	
	public abstract void onUse(RingType type);
	
	public RingType getRingType() {
		return ringType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Ring) {
			Ring ring = (Ring) obj;
			if (ring.getName() == getName() && ring.ringType == ringType) {
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
