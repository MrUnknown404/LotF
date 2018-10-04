package main.java.lotf.util;

public enum EnumCollisionType {
	none       (0),
	whole      (1),
	topLeft    (2),
	topRight   (3),
	bottomLeft (4),
	bottomRight(5),
	top        (6),
	bottom     (7),
	left       (8),
	right      (9);
	
	public final int fId;
	
	private EnumCollisionType(int id) {
		fId = id;
	}
	
	public static EnumCollisionType getFromNumber(int id) {
		for (EnumCollisionType type : values()) {
			if (type.fId == id) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid Type id: " + id);
	}
}
