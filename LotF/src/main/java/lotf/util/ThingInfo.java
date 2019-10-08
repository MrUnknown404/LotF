package main.java.lotf.util;

public abstract class ThingInfo {
	protected final String name;
	protected final int textureCount, animationTime;
	
	public ThingInfo(String name, int textureCount, int animationTime) {
		this.name = name;
		this.textureCount = textureCount;
		this.animationTime = animationTime;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTextureCount() {
		return textureCount;
	}
	
	public int getAnimationTime() {
		return animationTime;
	}
}
