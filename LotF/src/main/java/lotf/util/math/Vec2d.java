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

	public Vec2d add(Vec2d vec) {
		return new Vec2d(x + vec.x, y + vec.y);
	}
	
	public Vec2d add(double x, double y) {
		return new Vec2d(this.x + x, this.y + y);
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
