package main.java.lotf.items;

import main.java.lotf.items.util.Item;

public class ItemBottle extends Item {

	private BottleType type = BottleType.empty;
	
	public ItemBottle(BottleType type) {
		super("bottle", type.fId, BottleType.values().length, 0);
		this.type = type;
	}
	
	@Override
	protected void onUse() {
		
	}
	
	public BottleType getBottleType() {
		return type;
	}
	
	public enum BottleType {
		empty (0),
		red   (1),
		blue  (2),
		purple(3),
		yellow(4),
		orange(5);
		
		private final int fId;
		
		private BottleType(int id) {
			fId = id;
		}
		
		public static BottleType getFromNumber(int id) {
			for (BottleType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
