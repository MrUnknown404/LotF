package main.java.lotf.entities.projectiles;

import main.java.lotf.entities.util.Entity;
import main.java.lotf.entities.util.EntityInfo;
import main.java.lotf.entities.util.EntityLiving;
import main.java.lotf.init.Particles;
import main.java.lotf.init.Tiles;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.GameObject;
import main.java.lotf.util.enums.EnumDirection;
import main.java.ulibs.utils.math.Vec2f;
import main.java.ulibs.utils.math.Vec2i;

public class ProjectileArrow extends EntityProjectile {
	
	private final EnumDirection dir;
	
	public ProjectileArrow(Vec2f pos, EntityLiving shooter, EnumDirection dir, float speed, int damage) {
		super(EntityInfo.ARROW, pos, new Vec2i(), shooter, speed, damage);
		this.dir = dir;
	}
	
	@Override
	protected void hit(GameObject obj, float hitX, float hitY) {
		if (obj instanceof Tile) {
			TileInfo t = ((Tile) obj).getTileInfo();
			int index = 0;
			for (int i = 0; i < Tiles.getAll().size(); i++) {
				if (Tiles.getAll().get(i).equals(t)) {
					index = i;
					break;
				}
			}
			
			room.spawnParticle(Particles.SMALL_TILE_EXPLOSION.setIndex(index), new Vec2f(hitX, hitY), 5);
		} else if (obj instanceof Entity) {
			EntityInfo e = ((Entity) obj).getInfo();
			int index = 0;
			for (int i = 0; i < EntityInfo.getAll().size(); i++) {
				if (EntityInfo.getAll().get(i).equals(e)) {
					index = i;
					break;
				}
			}
			
			room.spawnParticle(Particles.SMALL_ENTITY_EXPLOSION.setIndex(index), new Vec2f(hitX, hitY), 5);
		}
	}
	
	@Override
	public void reset() {
		room.killEntity(this);
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
