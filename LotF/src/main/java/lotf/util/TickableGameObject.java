package main.java.lotf.util;

public abstract class TickableGameObject extends GameObject {

	public TickableGameObject(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public abstract void tick();
}
