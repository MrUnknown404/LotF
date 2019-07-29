package main.java.lotf.entities.util;

import java.util.ArrayList;
import java.util.List;

public class EntityInfo {
	
	private static List<EntityInfo> all = new ArrayList<EntityInfo>();
	
	public static final EntityInfo PLAYER = new EntityInfo("player", true, 2, 6);
	public static final EntityInfo ARROW = new EntityInfo("arrow", true, 1, 0);
	
	private final String name;
	private final boolean usesDirections;
	private final int textureCount, animationTime;
	
	private EntityInfo(String name, boolean usesDirections, int textureCount, int animationTime) {
		this.name = name;
		this.usesDirections = usesDirections;
		this.textureCount = textureCount;
		this.animationTime = animationTime;
		
		all.add(this);
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isUsesDirections() {
		return usesDirections;
	}
	
	public int getTextureCount() {
		return textureCount;
	}
	
	public int getAnimationTime() {
		return animationTime;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EntityInfo) {
			EntityInfo info = (EntityInfo) obj;
			
			return info.name == name && info.textureCount == textureCount && info.animationTime == animationTime && info.usesDirections == usesDirections ? true : false;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "(name:" + name + ", usesDirections:" + usesDirections + ", textureCount:" + textureCount + ", animationTime:" + animationTime + ")";
	}
}
