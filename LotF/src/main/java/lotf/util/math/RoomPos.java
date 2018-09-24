package main.java.lotf.util.math;

public class RoomPos {
	
	public static final RoomPos ZERO = new RoomPos();
	private int x, y;
	
	public RoomPos(Vec2i vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public RoomPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public RoomPos(double x, double y) {
		this.x = (int) x;
		this.y = (int) y;
	}
	
	public RoomPos() {
		this(0, 0);
	}
	
	public void addX(int x) {
		this.x += x;
	}
	
	public void addY(int y) {
		this.y += y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RoomPos) {
			if (((RoomPos) obj).x == x && ((RoomPos) obj).y == y) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
