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
		friendship        (0),
		money_1           (1),
		money_2           (2),
		kill_1            (3),
		kill_2            (4),
		walkIntoRoom_1    (5),
		walkIntoRoom_2    (6),
		walkIntoRoom_3    (7),
		walkIntoRoom_4    (8),
		swordDamageMulti_1(9),
		swordDamageMulti_2(10),
		swordDamageMulti_3(11),
		moreMoney_1       (12),
		moreMoney_2       (13),
		moreMagic_1       (14),
		moreMagic_2       (15),
		healthRegen_1     (16),
		healthRegen_2     (17),
		magicRegen_1      (18),
		magicRegen_2      (19),
		moreHammerDamage_1(20),
		moreHammerDamage_2(21),
		moreBowDamage_1   (22),
		moreBowDamage_2   (23),
		moreBombDamage_1  (24),
		moreBombDamage_2  (25),
		moreSpellDamage_1 (26),
		moreSpellDamage_2 (27),
		moreSpellDamage_3 (28);
		
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
