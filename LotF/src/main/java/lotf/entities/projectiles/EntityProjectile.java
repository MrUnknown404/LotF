package main.java.lotf.entities.projectiles;

import main.java.lotf.entities.util.Entity;
import main.java.lotf.entities.util.EntityInfo;
import main.java.lotf.entities.util.EntityLiving;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.GameObject;
import main.java.lotf.util.enums.EnumDirection;
import main.java.ulibs.utils.math.Vec2f;
import main.java.ulibs.utils.math.Vec2i;

public abstract class EntityProjectile extends Entity {
	
	protected final EntityLiving shooter;
	private final int damage;
	private final float speed;
	
	public EntityProjectile(EntityInfo info, Vec2f pos, Vec2i size, EntityLiving shooter, float speed, int damage) {
		super(info, shooter.getRoom(), pos, size);
		this.shooter = shooter;
		this.speed = speed;
		this.damage = damage;
	}
	
	@Override
	public void addPosX(float x) {
		if (checkForDeath()) {
			return;
		} else if (checkForTileCollision() || checkForEntityCollision()) {
			kill();
			return;
		}
		
		setFacing(x > 0 ? EnumDirection.east : EnumDirection.west);
		setPosX(getPosX() + x);
	}
	
	@Override
	public void addPosY(float y) {
		if (checkForDeath()) {
			return;
		} else if (checkForTileCollision() || checkForEntityCollision()) {
			kill();
			return;
		}
		
		setFacing(y > 0 ? EnumDirection.south : EnumDirection.north);
		setPosY(getPosY() + y);
	}
	
	private void findHitLocationAndHit(GameObject obj) {
		hit(obj, getPosX() < obj.getPosX() ? obj.getPosX() : getPosX(), getPosY() < obj.getPosY() ? obj.getPosY() : getPosY());
	}
	
	/** Does not kill! */
	protected boolean checkForTileCollision() {
		for (Tile t : room.getTilesWithCollision()) {
			if (t.getBounds().intersects(getBounds())) {
				findHitLocationAndHit(t);
				return true;
			}
		}
		
		return false;
	}

	/** Does not kill! but does hit entity */
	protected boolean checkForEntityCollision() {
		for (Entity e : room.getEntities()) {
			if (e != this && e.getBounds().intersects(getBounds())) {
				if (e instanceof EntityLiving) {
					findHitLocationAndHit(e);
					((EntityLiving) e).hit(damage);
				}
				return true;
			}
		}
		
		return false;
	}
	
	/** Also kills! */
	protected boolean checkForDeath() {
		if (!getBounds().intersects(room.getBounds())) {
			kill();
			return true;
		}
		
		return false;
	}
	
	protected abstract void hit(GameObject obj, float hitX, float hitY);
	
	public float getSpeed() {
		return speed;
	}
	
	public int getDamage() {
		return damage;
	}
}
