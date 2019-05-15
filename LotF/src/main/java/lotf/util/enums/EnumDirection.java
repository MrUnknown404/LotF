package main.java.lotf.util.enums;

import main.java.lotf.util.Console;

public enum EnumDirection {
	north,
	east,
	south,
	west;
	
	public EnumDirection getOpposite() {
		if (this == north) {
			return south;
		} else if (this == south) {
			return north;
		} else if (this == east) {
			return west;
		} else if (this == west) {
			return east;
		} else {
			Console.print(Console.WarningType.Error, "Unknown Opposite for : " + name());
			return null;
		}
	}
	
	public boolean isHorizontal() {
		return (this == EnumDirection.east || this == EnumDirection.west) ? true : false;
	}
	
	public boolean isVertical() {
		return (this == EnumDirection.north || this == EnumDirection.south) ? true : false;
	}
}
