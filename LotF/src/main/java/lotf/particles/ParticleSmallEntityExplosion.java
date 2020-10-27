package main.java.lotf.particles;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.java.lotf.entities.util.EntityInfo;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.GetResource.ResourceType;
import main.java.lotf.util.ImageInfo;
import main.java.ulibs.utils.Console;
import main.java.ulibs.utils.Console.WarningType;
import main.java.ulibs.utils.math.MathH;

public class ParticleSmallEntityExplosion extends ParticleSmallExplosion {
	public ParticleSmallEntityExplosion() {
		super("small_entity_explosion");
		setIndex(-1);
	}
	
	@Override
	public void tick() {
		if (index == -1) {
			Console.print(WarningType.Warning, "Invalid particle texture index '" + index + "'");
			room.killParticle(this);
		} else {
			super.tick();
		}
	}
	
	@Override
	protected ImageInfo setupTexture() {
		List<BufferedImage> imgs = new ArrayList<BufferedImage>();
		for (int i = 0; i < EntityInfo.getAll().size(); i++) {
			EntityInfo e = EntityInfo.getAll().get(i);
			
			if (e.getTextureCount() > 1) {
				if (e.usesDirections()) {
					imgs.add(GetResource.getTexture(ResourceType.entity, e.getName() + "/" + e.getName() + "_north_0"));
				} else {
					imgs.add(GetResource.getTexture(ResourceType.entity, e.getName() + "/" + e.getName() + "_0"));
				}
			} else {
				if (e.usesDirections()) {
					imgs.add(GetResource.getTexture(ResourceType.entity, e.getName() + "_north"));
				} else {
					imgs.add(GetResource.getTexture(ResourceType.entity, e.getName()));
				}
			}
		}
		
		return new ImageInfo(imgs.toArray(new BufferedImage[0]));
	}
	
	@Override
	public int getIndex() {
		return MathH.clamp(index, 0, Integer.MAX_VALUE);
	}
	
	@Override
	public ParticleSmallEntityExplosion copy() {
		return new ParticleSmallEntityExplosion();
	}
}
