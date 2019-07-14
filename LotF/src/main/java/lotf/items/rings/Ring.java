package main.java.lotf.items.rings;

import main.java.lotf.items.util.ItemBase;
import main.java.lotf.items.util.ItemInfo;

public abstract class Ring extends ItemBase {
	
	protected final RingType ringType;
	
	public Ring(ItemInfo info) {
		super(info);
		this.ringType = (RingType) info.getData()[0];
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
