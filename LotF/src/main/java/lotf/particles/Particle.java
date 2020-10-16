package main.java.lotf.particles;

import main.java.lotf.util.GameObject;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.GetResource.ResourceType;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.ImageInfo;
import main.java.lotf.world.Room;
import main.java.ulibs.utils.ICopyable;
import main.java.ulibs.utils.math.Vec2f;
import main.java.ulibs.utils.math.Vec2i;

public abstract class Particle<T extends Particle<?>> extends GameObject implements ITickable, ICopyable<T> {
	
	protected final String name;
	protected Room room;
	protected ImageInfo texture;
	
	protected final int maxLife;
	protected int life, index;
	
	protected Particle(String name, Vec2i size, int maxLife) {
		super(new Vec2f(), size);
		this.name = name;
		this.maxLife = maxLife;
		this.life = maxLife;
	}
	
	protected abstract void onCreate();
	
	@Override
	public void tick() {
		if (life > 0) {
			life--;
		} else {
			room.killParticle(this);
		}
	}
	
	public static Particle<?> create(Particle<?> type, Vec2f pos, Room room) {
		Particle<?> newType = type.copy();
		newType.setPos(pos);
		newType.room = room;
		newType.onCreate();
		newType.setIndex(type.getIndex());
		return newType;
	}
	
	public String getName() {
		return name;
	}
	
	public final ImageInfo setupAndGetTexture() {
		if (texture == null) {
			texture = setupTexture();
		}
		
		return texture;
	}
	
	protected ImageInfo setupTexture() {
		return new ImageInfo(GetResource.getTexture(ResourceType.particle, getName()));
	}
	
	public Particle<?> setIndex(int index) {
		this.index = index;
		return this;
	}
	
	public int getIndex() {
		return index;
	}
}
