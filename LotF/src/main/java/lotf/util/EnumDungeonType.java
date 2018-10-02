package main.java.lotf.util;

public enum EnumDungeonType {
	one   (0, 1),
	two   (1, 1),
	three (2, 1),
	four  (3, 1),
	five  (4, 1),
	six   (5, 1),
	seven (6, 1),
	eight (7, 1),
	nine  (8, 1),
	ten   (9, 1),
	eleven(10, 1),
	twelve(11, 1),
	nil   (12, 1);
	
	public final int fId, size;
	
	private EnumDungeonType(int id, int size) {
		this.fId = id;
		this.size = size;
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
