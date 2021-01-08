package main.java.lotf.init;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.particles.Particle;
import main.java.lotf.particles.ParticleSmallEntityExplosion;
import main.java.lotf.particles.ParticleSmallExplosion;
import main.java.lotf.particles.ParticleSmallTileExplosion;
import main.java.ulibs.utils.Console;
import main.java.ulibs.utils.Console.WarningType;

public class Particles {
	private static final List<Particle<?>> ALL = new ArrayList<Particle<?>>();
	
	public static final ParticleSmallExplosion SMALL_EXPLOSION = add(new ParticleSmallExplosion());
	public static final ParticleSmallTileExplosion SMALL_TILE_EXPLOSION = add(new ParticleSmallTileExplosion());
	public static final ParticleSmallEntityExplosion SMALL_ENTITY_EXPLOSION = add(new ParticleSmallEntityExplosion());
	
	public static List<Particle<?>> getAll() {
		return ALL;
	}

	public static <T extends Particle<?>> T add(T p) {
		if (ALL.isEmpty()) {
			Console.print(WarningType.Info, "Started registering " + p.getName() + "!");
		}
		
		ALL.add(p);
		Console.print(Console.WarningType.RegisterDebug, "'" + p.getClass().getSimpleName() + "' was registered!");
		return p;
	}
	
	/** Forces an early load */
	public static void registerAll() { }
}
