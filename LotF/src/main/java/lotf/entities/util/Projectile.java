package main.java.lotf.entities.util;

import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public abstract class Projectile extends Entity {

	protected EntityLiving shooter;
	
	public Projectile(EntityInfo info, Vec2f pos, Vec2i size, EntityLiving shooter) {
		super(info, pos, size);
		this.shooter = shooter;
	}
}
