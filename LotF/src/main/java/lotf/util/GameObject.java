package main.java.lotf.util;

import java.awt.Rectangle;

import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public abstract class GameObject {
	
	protected Vec2f pos;
	protected Vec2i size;
	
	public GameObject(Vec2f pos, Vec2i size) {
		this.pos = pos;
		this.size = size;
	}
	
	public void setPos(Vec2f pos) {
		this.pos.set(pos);
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
	
	public Vec2i getSize() {
		return size;
	}
	
	public int getWidth() {
		return size.getX();
	}
	
	public int getHeight() {
		return size.getY();
	}
	
	public Rectangle getBounds() {
		return new Rectangle(MathHelper.floor(getPosX()), MathHelper.floor(getPosY()), size.getX(), size.getY());
	}
}
