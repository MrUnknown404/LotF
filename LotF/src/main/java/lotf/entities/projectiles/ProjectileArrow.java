package main.java.lotf.entities.projectiles;

import main.java.lotf.entities.util.EntityInfo;
import main.java.lotf.entities.util.EntityLiving;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public class ProjectileArrow extends EntityProjectile {

	private final float speed;
	private final EnumDirection dir;
	
	public ProjectileArrow(Vec2f pos, EntityLiving shooter, EnumDirection dir, float speed) {
		super(EntityInfo.ARROW, pos, Vec2i.ZERO, shooter);
		this.dir = dir;
		this.speed = speed;
	}
	
	@Override
	public void tick() {
		super.tick();
		switch (dir) {
			case east:
				addPosX(speed);
				break;
			case north:
				addPosY(-speed);
				break;
			case south:
				addPosY(speed);
				break;
			case west:
				addPosX(-speed);
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
