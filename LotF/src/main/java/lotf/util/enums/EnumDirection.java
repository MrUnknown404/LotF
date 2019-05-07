package main.java.lotf.util.enums;

public enum EnumDirection {
	nil  (0, 0),
	north(1, 3),
	east (2, 4),
	south(3, 1),
	west (4, 2);
	
	public final int id, oppositeID;
	
	private EnumDirection(int id, int oppositeID) {
		this.id = id;
		this.oppositeID = oppositeID;
	}
	
	public EnumDirection getOpposite() {
		return getFromNumber(oppositeID);
	}
	
	public static EnumDirection getFromNumber(int id) {
		for (EnumDirection type : values()) {
			if (type.id == id) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid Type id: " + id);
	}
}
