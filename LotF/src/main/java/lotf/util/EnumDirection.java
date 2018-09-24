package main.java.lotf.util;

public enum EnumDirection {
	nil  (0),
	north(1),
	east (2),
	south(3),
	west (4);
	
	private final int fId;
	
	private EnumDirection(int id) {
		fId = id;
	}
	
	public static EnumDirection getFromNumber(int id) {
		for (EnumDirection type : values()) {
			if (type.fId == id) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid Type id: " + id);
	}
}
