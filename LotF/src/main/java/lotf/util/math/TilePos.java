package main.java.lotf.util.math;

public class TilePos {
	
	public static final TilePos ZERO = new TilePos();
	private int x, y;
	
	public TilePos(Vec2i vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public TilePos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public TilePos(double x, double y) {
		this.x = (int) x;
		this.y = (int) y;
	}
	
	public TilePos() {
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
		if (obj instanceof TilePos) {
			if (((TilePos) obj).x == x && ((TilePos) obj).y == y) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
