package main.java.lotf.entities.projectiles;

import main.java.lotf.entities.util.EntityInfo;
import main.java.lotf.entities.util.EntityLiving;
import main.java.lotf.util.enums.EnumDirection;
import main.java.ulibs.utils.math.Vec2f;
import main.java.ulibs.utils.math.Vec2i;

public class ProjectileArrow extends EntityProjectile {
	
	private final EnumDirection dir;
	
	public ProjectileArrow(Vec2f pos, EntityLiving shooter, EnumDirection dir, float speed, int damage) {
		super(EntityInfo.ARROW, pos, Vec2i.ZERO, shooter, speed, damage);
		this.dir = dir;
	}
	
	@Override
	public void tick() {
		switch (dir) {
			case east:
				addPosX(getSpeed());
				break;
			case north:
				addPosY(-getSpeed());
				break;
			case south:
				addPosY(getSpeed());
				break;
			case west:
				addPosX(-getSpeed());
				break;
		}
	}
	
	@Override
	public int getWidth() {
		return dir == EnumDirection.north || dir == EnumDirection.south ? 2 : 8;
	}
	
	@Override
	public int getHeight() {
		return dir == EnumDirection.east || dir == EnumDirection.west ? 2 : 8;
	}
}
