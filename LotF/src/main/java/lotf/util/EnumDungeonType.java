package main.java.lotf.util;

public enum EnumDungeonType {
	one   (0),
	two   (1),
	three (2),
	four  (3),
	five  (4),
	six   (5),
	seven (6),
	eight (7),
	nine  (8),
	ten   (9),
	eleven(10),
	twelve(11);
	
	public final int fId;
	
	private EnumDungeonType(int id) {
		fId = id;
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
