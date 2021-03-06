package main.java.lotf.entities.util;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.util.GameObjectInfo;

public class EntityInfo extends GameObjectInfo {
	
	private static final List<EntityInfo> ALL = new ArrayList<EntityInfo>();
	
	public static final EntityInfo PLAYER = new EntityInfo("player", true, 2, 6);
	public static final EntityInfo ARROW = new EntityInfo("arrow", true);
	public static final EntityInfo ENEMY_TEST = new EntityInfo("enemy_test", true);
	
	private final boolean usesDirections;
	
	private EntityInfo(String name, boolean usesDirections, int textureCount, int animationTime) {
		super(name, textureCount, animationTime);
		this.usesDirections = usesDirections;
		
		ALL.add(this);
	}
	
	private EntityInfo(String name, boolean usesDirections) {
		this(name, usesDirections, 1, 0);
	}
	
	public boolean usesDirections() {
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
