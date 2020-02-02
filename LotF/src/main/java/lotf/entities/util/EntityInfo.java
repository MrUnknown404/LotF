package main.java.lotf.entities.util;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.util.ThingInfo;

public class EntityInfo extends ThingInfo {
	
	private static final List<EntityInfo> ALL = new ArrayList<EntityInfo>();
	
	public static final EntityInfo PLAYER = new EntityInfo("player", true, 2, 6);
	public static final EntityInfo ARROW = new EntityInfo("arrow", true, 1, 0);
	public static final EntityInfo ENEMY_TEST = new EntityInfo("enemy_test", true, 1, 0);
	
	private final boolean usesDirections;
	
	private EntityInfo(String name, boolean usesDirections, int textureCount, int animationTime) {
		super(name, textureCount, animationTime);
		this.usesDirections = usesDirections;
		
		ALL.add(this);
	}
	
	public boolean isUsesDirections() {
		return usesDirections;
	}
	
	public static List<EntityInfo> getAll() {
		return ALL;
	}

	@Override
	public String toString() {
		return "(name:" + name + ", usesDirections:" + usesDirections + ", textureCount:" + textureCount + ", animationTime:" + animationTime + ")";
	}
}
