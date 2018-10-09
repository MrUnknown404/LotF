package main.java.lotf.items;

import main.java.lotf.items.util.Item;

public class ItemRing extends Item {

	private RingType type;
	
	public ItemRing(RingType type) {
		super("ring", type.fId, RingType.values().length, 4);
		this.type = type;
	}
	
	@Override
	protected void onUse() {
		//ring effect
	}
	
	public RingType getRingType() {
		return type;
	}
	
	/** add more later! */
	public enum RingType {
		unknown           (0),
		friendship        (1),
		money_1           (2),
		money_2           (3),
		kill_1            (4),
		kill_2            (5),
		ExploreRooms_1    (6),
		ExploreRooms_2    (7),
		ExploreRooms_3    (8),
		ExploreRooms_4    (9),
		swordDamageMulti_1(10),
		swordDamageMulti_2(11),
		swordDamageMulti_3(12),
		moreMoney_1       (13),
		moreMoney_2       (14),
		moreMagic_1       (15),
		moreMagic_2       (16),
		healthRegen_1     (17),
		healthRegen_2     (18),
		magicRegen_1      (19),
		magicRegen_2      (20),
		moreHammerDamage_1(21),
		moreHammerDamage_2(22),
		moreBowDamage_1   (23),
		moreBowDamage_2   (24),
		moreBombDamage_1  (25),
		moreBombDamage_2  (26),
		moreSpellDamage_1 (27),
		moreSpellDamage_2 (28),
		moreSpellDamage_3 (29);
		
		private final int fId;
		
		private RingType(int id) {
			fId = id;
		}
		
		public static RingType getFromNumber(int id) {
			for (RingType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
