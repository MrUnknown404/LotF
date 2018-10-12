package main.java.lotf.util;

public enum EnumDungeonType {
	one   (0, 6,  new int[]{3, 4, 9, 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 26}),
	two   (1, 1,  new int[]{0}),
	three (2, 1,  new int[]{0}),
	four  (3, 1,  new int[]{0}),
	five  (4, 1,  new int[]{0}),
	six   (5, 1,  new int[]{0}),
	seven (6, 1,  new int[]{0}),
	eight (7, 1,  new int[]{0}),
	nine  (8, 1,  new int[]{0}),
	ten   (9, 1,  new int[]{0}),
	eleven(10, 1, new int[]{0}),
	twelve(11, 1, new int[]{0}),
	nil   (12, 1, new int[]{0});
	
	public final int fId, size;
	public final int[] has;
	
	private EnumDungeonType(int id, int size, int[] has) {
		this.fId = id;
		this.size = size;
		this.has = has;
	}
	
	public static EnumDungeonType getFromNumber(int id) {
		for (EnumDungeonType type : values()) {
			if (type.fId == id) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid Type id: " + id);
	}
}
