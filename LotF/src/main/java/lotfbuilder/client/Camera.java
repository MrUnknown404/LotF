package main.java.lotfbuilder.client;

import main.java.lotf.util.GameObject;
import main.java.lotf.util.ITickable;
import main.java.ulibs.utils.math.Vec2f;
import main.java.ulibs.utils.math.Vec2i;

public class Camera extends GameObject implements ITickable {
	
	private float moveX, moveY;
	
	public Camera() {
		super(new Vec2f(), new Vec2i());
	}
	
	@Override
	public void tick() {
		addPosX(moveX * 5f);
		addPosY(moveY * 5f);
	}
	
	public void setMoveX(float moveX) {
		this.moveX = moveX;
	}
	
	public void setMoveY(float moveY) {
		this.moveY = moveY;
	}
}
