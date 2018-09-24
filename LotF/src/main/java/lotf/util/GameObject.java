package main.java.lotf.util;

import java.awt.Rectangle;

import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.Vec2i;

public abstract class GameObject {
	
	private Vec2i pos = new Vec2i();
	private int width, height;
	
	public GameObject(int x, int y, int width, int height) {
		pos = new Vec2i(x, y);
		this.width = width;
		this.height = height;
	}
	
	/** Adds to the objects position */
	public void addPosition(int x, int y) {
		pos = pos.add(x, y);
	}
	
	/** Adds to the objects position */
	public void addPosition(Vec2i vec) {
		pos = pos.add(vec);
	}
	
	/** Adds to the objects X position */
	public void addPositionX(int x) {
		pos = pos.add(x, 0);
	}
	
	/** Adds to the objects Y position */
	public void addPositionY(int y) {
		pos = pos.add(0, y);
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
	
	/** Gets the entities entire bounds */
	public Rectangle getBoundsAll() {
		return new Rectangle(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()), width, height);
	}
	
	/** Gets the entities top bounds */
	public Rectangle getBoundsTop() {
		return new Rectangle(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()), width, height / 4);
	}
	
	/** Gets the entities bottom bounds */
	public Rectangle getBoundsBottom() {
		return new Rectangle(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()) + (height - (height / 4)), width, height / 4);
	}
	
	/** Gets the entities left bounds */
	public Rectangle getBoundsLeft() {
		return new Rectangle(MathHelper.floor(getPositionX()), MathHelper.floor(getPositionY()), width / 4, height);
	}
	
	/** Gets the entities right bounds */
	public Rectangle getBoundsRight() {
		return new Rectangle(MathHelper.floor(getPositionX() + (width - (width / 4))), MathHelper.floor(getPositionY()), width / 4, height);
	}
}
