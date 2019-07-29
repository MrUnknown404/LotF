package main.java.lotf.entities.util;

import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public abstract class EntityLiving extends Entity implements IDamageable {
	
	protected EnumDirection facing = EnumDirection.north;
	
	public EntityLiving(EntityInfo info, Vec2f pos, Vec2i size, int totalHearts) {
		super(info, pos, size);
		setupHealth(totalHearts);
	}
	
	public EnumDirection getFacing() {
		return facing;
	}
}
