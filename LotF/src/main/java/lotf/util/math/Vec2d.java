package main.java.lotf.util.math;

public class Vec2d {
	
	public static final Vec2d ZERO = new Vec2d();
	protected double x, y;
	
	public Vec2d(double x, double y) {
		if (x == -0.0D) {
			x = 0.0D;
		}
		
		if (y == -0.0D) {
			y = 0.0D;
		}
		
		this.x = x;
		this.y = y;
	}
	
	public Vec2d() {
		this(0, 0);
	}
	
	public Vec2d(Vec2i vec) {
		this((double) vec.getX(), (double) vec.getY());
	}

	public void add(Vec2d vec) {
		this.x += vec.x;
		this.y += vec.y;
	}
	
	public void add(double x, double y) {
		this.x += x;
		this.y += y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vec2d) {
			if (((Vec2d) obj).x == x && ((Vec2d) obj).y == y) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public String toStringInt() {
		return "(" + (int) x + ", " + (int) y + ")";
	}
}
