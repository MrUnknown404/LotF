package main.java.lotf.particles;

import java.util.Random;

import main.java.ulibs.utils.math.Vec2f;
import main.java.ulibs.utils.math.Vec2i;

public class ParticleSmallExplosion extends Particle<ParticleSmallExplosion> {
	
	private final Vec2f velocity = new Vec2f();
	
	protected ParticleSmallExplosion(String name) {
		super(name, new Vec2i(2), 20);
	}
	
	public ParticleSmallExplosion() {
		super("small_explosion", new Vec2i(2), 20);
	}
	
	@Override
	protected void onCreate() {
		Random r = new Random();
		velocity.set(new Vec2f(r.nextInt(8) - 4, r.nextInt(8) - 4));
	}
	
	@Override
	public void tick() {
		super.tick();
		addPosX(velocity.getX() * 0.5f);
		addPosY(velocity.getY() * 0.5f);
		
		velocity.setX(velocity.getX() * 0.8f);
		velocity.setY(velocity.getY() * 0.8f);
	}
	
	@Override
	public ParticleSmallExplosion copy() {
		return new ParticleSmallExplosion();
	}
}
