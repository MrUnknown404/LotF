package main.java.lotf.util.math;

public class Vec2i {
	
	public static final Vec2i NULL_VECTOR = new Vec2i();
	protected int x, y;
	
	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2i(double x, double y) {
		this(MathHelper.floor(x), MathHelper.floor(y));
	}
	
	public Vec2i() {
		this(0, 0);
	}
	
	public void add(Vec2i vec) {
		this.x += vec.x;
		this.y += vec.y;
	}
	
	public void add(int x, int y) {
		this.x += x;
		this.y += y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;	
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getBothMuli() {
		return x * y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vec2i) {
			if (((Vec2i) obj).x == x && ((Vec2i) obj).y == y) {
				return true;
			}
		}
		return false;
	}
}

