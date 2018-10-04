package main.java.lotf.util;

import java.awt.Rectangle;

import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.Vec2i;

public abstract class GameObject {
	
	private transient Vec2i pos = new Vec2i();
	protected transient int width, height;
	
	public GameObject(int x, int y, int width, int height) {
		pos = new Vec2i(x, y);
		this.width = width;
		this.height = height;
	}
	
	/** Adds to the objects position */
	public void addPosition(int x, int y) {
		pos.add(x, y);
	}
	
	/** Adds to the objects position */
	public void addPosition(Vec2i vec) {
		pos.add(vec);
	}
	
	/** Adds to the objects X position */
	public void addPositionX(int x) {
		pos.add(x, 0);
	}
	
	/** Adds to the objects Y position */
	public void addPositionY(int y) {
		pos.add(0, y);
	}
	
	/** Sets the objects position */
	public void setPosition(int x, int y) {
		pos = new Vec2i(x, y);
	}
	
	/** Sets the objects position */
	public void setPosition(Vec2i vec) {
		pos = vec;
	}
	
	/** Sets the objects X position */
	public void setPositionX(int x) {
		pos.setX(x);
	}
	
	/** Sets the objects Y position */
	public void setPositionY(int y) {
		pos.setY(y);;
	}
	
	/** Gets the objects position */
	public Vec2i getPosition() {
		return pos;
	}
	
	/** Gets the objects X position */
	public int getPositionX() {
		return pos.getX();
	}
	
	/** Gets the objects Y position */
	public int getPositionY() {
		return pos.getY();
	}
	
	/** Returns the object's width */
	public int getWidth() {
		return width;
	}
	
	/** Returns the object's height */
	public int getHeight() {
		return height;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()), width, height);
	}
}
