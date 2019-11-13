package main.java.lotf.entities.util;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public abstract class EntityLiving extends Entity implements IDamageable {
	
	protected List<Integer> hearts = new ArrayList<Integer>();
	
	public EntityLiving(EntityInfo info, Room room, Vec2f pos, Vec2i size, int totalHearts) {
		super(info, room, pos, size);
		setupHealth(totalHearts);
	}
	
	@Override
	public List<Integer> getHearts() {
		return hearts;
	}
	
	public EnumDirection getFacing() {
		return facing;
	}
}
