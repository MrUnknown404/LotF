package main.java.lotf.entities.util;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.util.ThingInfo;

public class EntityInfo extends ThingInfo {
	
	private static List<EntityInfo> all = new ArrayList<EntityInfo>();
	
	public static final EntityInfo PLAYER = new EntityInfo("player", true, 2, 6);
	public static final EntityInfo ARROW = new EntityInfo("arrow", true, 1, 0);
	
	private final boolean usesDirections;
	
	private EntityInfo(String name, boolean usesDirections, int textureCount, int animationTime) {
		super(name, textureCount, animationTime);
		this.usesDirections = usesDirections;
		
		all.add(this);
	}
	
	public boolean isUsesDirections() {
		return usesDirections;
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
