package main.java.lotf.util;

import java.awt.Rectangle;

import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.Vec2f;

public abstract class GameObject {
	
	protected Vec2f pos;
	protected int width, height;
	
	public GameObject(Vec2f pos, int width, int height) {
		this.pos = pos;
		this.width = width;
		this.height = height;
	}
	
	public void setPos(Vec2f pos) {
		pos.set(pos);
	}
	
	public void setPosX(float x) {
		pos.setX(x);
	}
	
	public void setPosY(float y) {
		pos.setY(y);
	}
	
	public void addPosX(float x) {
		pos.addX(x);
	}
	
	public void addPosY(float y) {
		pos.addY(y);
	}
	
	public Vec2f getPos() {
		return pos;
	}
	
	public float getPosX() {
		return pos.getX();
	}
	
	public float getPosY() {
		return pos.getY();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(MathHelper.floor(getPosX()), MathHelper.floor(getPosY()), width, height);
	}
}
