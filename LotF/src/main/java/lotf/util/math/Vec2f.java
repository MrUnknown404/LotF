package main.java.lotf.util.math;

public class Vec2f {
	
	public static final Vec2f ZERO = new Vec2f();
	protected float x, y;
	
	public Vec2f() {
		x = 0;
		y = 0;
	}
	
	public Vec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2f(float xy) {
		this.x = xy;
		this.y = xy;
	}
	
	public Vec2f(Vec2i vec) {
		this(vec.getX(), vec.getY());
	}
	
	public void add(Vec2f vec) {
		this.x += vec.x;
		this.y += vec.y;
	}
	
	public void add(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public void addX(float x) {
		this.x += x;
	}
	
	public void addY(float y) {
		this.y += y;
	}
	
	public void set(Vec2f vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getBothMulti() {
		return x * y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vec2f) {
			if (((Vec2f) obj).x == x && ((Vec2f) obj).y == y) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
