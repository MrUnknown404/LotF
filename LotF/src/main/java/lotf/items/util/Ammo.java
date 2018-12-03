package main.java.lotf.items.util;

public class Ammo {

	private int count, maxCount, level;
	private AmmoType type;
	
	public Ammo(AmmoType type, int level) {
		this.type = type;
		
		if (level == 0) {
			maxCount = 10;
		} else if (level == 1) {
			maxCount = 30;
		} else if (level == 2) {
			maxCount = 50;
		} else if (level == 3) {
			maxCount = 99;
		}
		
		this.count = maxCount;
	}
	
	public void decreaseAmmo() {
		count--;
	}
	
	public void maxOutAmmo() {
		count = maxCount;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getMaxCount() {
		return maxCount;
	}
	
	public int getCount() {
		return count;
	}
	
	public AmmoType getAmmoType() {
		return type;
	}
	
	public enum AmmoType {
		arrow (0),
		bomb  (1),
		rcBomb(2);
		
		private final int fId;
		
		private AmmoType(int id) {
			fId = id;
		}
		
		public static AmmoType getFromNumber(int id) {
			for (AmmoType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
