package main.java.lotf.util;

public abstract class GameObjectInfo {
	protected final String name;
	protected final int textureCount, animationTime;
	
	public GameObjectInfo(String name, int textureCount, int animationTime) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof GameObjectInfo)) {
			return false;
		}
		
		GameObjectInfo other = (GameObjectInfo) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
