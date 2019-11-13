package main.java.lotf.entities.projectiles;

import main.java.lotf.entities.util.Entity;
import main.java.lotf.entities.util.EntityInfo;
import main.java.lotf.entities.util.EntityLiving;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public abstract class EntityProjectile extends Entity {

	protected EntityLiving shooter;
	
	public EntityProjectile(EntityInfo info, Vec2f pos, Vec2i size, EntityLiving shooter) {
		super(info, shooter.getRoom(), pos, size);
		this.shooter = shooter;
	}
	
	@Override
	public void softReset() {
		kill();
	}
	
	@Override
	public void hardReset() {
		kill();
	}
}
