package main.java.lotf.client.renderer;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import main.java.lotf.Main;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.init.Particles;
import main.java.lotf.particles.Particle;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.ImageInfo;
import main.java.lotf.world.Room;
import main.java.ulibs.utils.Console;

public class RendererParticle implements IRenderer {
	private Map<String, ImageInfo> particleTextures = new HashMap<String, ImageInfo>();
	
	@Override
	public void setup() {
		for (Particle<?> p : Particles.getAll()) {
			ImageInfo img = p.setupAndGetTexture();
			
			if (img.getCurrentImage() != GetResource.nil) {
				Console.print(Console.WarningType.TextureDebug, "'" + p.getName() + "' was registered!");
			} else {
				Console.print(Console.WarningType.TextureDebug, "'" + p.getName() + "' was not registered!");
			}
			
			particleTextures.put(p.getName(), img);
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (particleTextures.isEmpty()) {
			Console.print(Console.WarningType.FatalError, "Particle textures were not set!");
			return;
		}
		
		if (Main.getMain().getWorldHandler() != null) {
			EntityPlayer pl = Main.getMain().getWorldHandler().getPlayer();
			if (pl != null) {
				Room r = pl.getRoom();
				
				for (Particle<?> p : r.getParticles()) {
					g.drawImage(particleTextures.get(p.getName()).imgs[p.getIndex()], (int) p.getPosX(), (int) p.getPosY(), p.getWidth(), p.getHeight(), null);
				}
			}
		}
	}
	
	@Override
	public boolean isHud() {
		return false;
	}
}
