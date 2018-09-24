package main.java.lotf.items.util;

import java.awt.Rectangle;

public class Slot {
	private int x, y, slotID;
	
	public Slot(int x, int y, int slotID) {
		this.x = x;
		this.y = y;
		this.slotID = slotID;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getSlotID() {
		return slotID;
	}
	
	public String toString() {
		return "(" + x + ", " + y + " | " + slotID + ")";
	}
	
	public Rectangle getBoundsAll() {
		return new Rectangle(x, y, 40, 40);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Slot) {
			if (((Slot) obj).x == x && ((Slot) obj).y == y && ((Slot) obj).slotID == slotID) {
				return true;
			}
		}
		return false;
	}
}
