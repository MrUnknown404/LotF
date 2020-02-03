package main.java.lotf.entities.projectiles;

import main.java.lotf.entities.util.Entity;
import main.java.lotf.entities.util.EntityInfo;
import main.java.lotf.entities.util.EntityLiving;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public abstract class EntityProjectile extends Entity {

	protected EntityLiving shooter;
	private int life = 5 * 60;
	
	public EntityProjectile(EntityInfo info, Vec2f pos, Vec2i size, EntityLiving shooter) {
		super(info, shooter.getRoom(), pos, size);
		this.shooter = shooter;
	}
	
	@Override
	public void tick() {
		if (life <= 0) {
			kill();
		} else {
			life--;
		}
	}
	
	@Override
	public void softReset() {
		kill();
	}
	
	@Override
	public void hardReset() {
		kill();
	}
	
	@Override
	public void addPosX(float x) {
		if (x > 0) {
			facing = EnumDirection.east;
		} else {
			facing = EnumDirection.west;
		}
		
		pos.addX(x);
	}
	
	@Override
	public void addPosY(float y) {
		if (y > 0) {
			facing = EnumDirection.south;
		} else {
			facing = EnumDirection.north;
		}
		
		pos.addY(y);
	}
}
