package main.java.lotf.entities.projectile;

import main.java.lotf.entities.util.EntityInfo;
import main.java.lotf.entities.util.EntityLiving;
import main.java.lotf.entities.util.Projectile;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public class ProjectileArrow extends Projectile {

	private final EnumDirection dir;
	
	public ProjectileArrow(Vec2f pos, EntityLiving shooter, EnumDirection dir) {
		super(EntityInfo.ARROW, pos, Vec2i.ZERO, shooter);
		this.dir = dir;
	}
	
	@Override
	public void tick() {
		
	}
	
	@Override
	public void softReset() {
		
	}
	
	@Override
	public void hardReset() {
		
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
