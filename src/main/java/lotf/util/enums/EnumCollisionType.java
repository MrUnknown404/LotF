package main.java.lotf.util.enums;

public enum EnumCollisionType {
	none,
	whole,
	topLeft,
	topRight,
	bottomLeft,
	bottomRight,
	top,
	bottom,
	left,
	right;
	
	public int getXmod() {
		if (this == right || this == bottomRight || this == topRight) {
			return -8;
		}
		
		return 0;
	}
	
	public int getYmod() {
		if (this == bottom || this == bottomLeft || this == bottomRight) {
			return -8;
		}
		
		return 0;
	}
}
