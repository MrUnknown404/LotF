package main.java.lotf.client.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.lotf.Main;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.entities.util.Entity;
import main.java.lotf.entities.util.EntityInfo;
import main.java.lotf.entities.util.EntityLiving;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.ImageInfo;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.world.Room;
import main.java.ulibs.utils.Console;
import main.java.ulibs.utils.math.MathH;

public class RendererEntity implements IRenderer, ITickable {
	private Map<EntityInfo, Map<EnumDirection, ImageInfo>> directionalEntityTextures = new HashMap<EntityInfo, Map<EnumDirection, ImageInfo>>();
	private Map<EntityInfo, ImageInfo> nonDirectionalEntityTextures = new HashMap<EntityInfo, ImageInfo>();
	
	@Override
	public void setup() {
		for (EntityInfo e : EntityInfo.getAll()) {
			registerEntity(e, e.getTextureCount());
		}
	}
	
	@Override
	public void tick() {
		if (Main.getMain().getWorldHandler() != null) {
			EntityPlayer p = Main.getMain().getWorldHandler().getPlayer();
			
			if (p != null && p.getRoom() != null) {
				for (Entity e : p.getRoom().getEntities()) {
					if (e instanceof EntityLiving && ((EntityLiving) e).isWalking()) {
						EntityInfo pi = e.getInfo();
						if (pi.getAnimationTime() != 0) {
							if (pi.usesDirections()) {
								if (directionalEntityTextures.get(pi).get(e.getFacing()).currentFrameCounter == pi.getAnimationTime()) {
									if (directionalEntityTextures.get(pi)
											.get(e.getFacing()).currentImage == directionalEntityTextures.get(pi).get(e.getFacing()).imgs.length - 1) {
										directionalEntityTextures.get(pi).get(e.getFacing()).currentImage = 0;
									} else {
										directionalEntityTextures.get(pi).get(e.getFacing()).currentImage++;
									}
									
									directionalEntityTextures.get(pi).get(e.getFacing()).currentFrameCounter = 0;
								} else {
									directionalEntityTextures.get(pi).get(e.getFacing()).currentFrameCounter++;
								}
							} else {
								if (nonDirectionalEntityTextures.get(pi).currentFrameCounter == pi.getAnimationTime()) {
									if (nonDirectionalEntityTextures.get(pi).currentImage == nonDirectionalEntityTextures.get(pi).imgs.length - 1) {
										nonDirectionalEntityTextures.get(pi).currentImage = 0;
									} else {
										nonDirectionalEntityTextures.get(pi).currentImage++;
									}
									
									nonDirectionalEntityTextures.get(pi).currentFrameCounter = 0;
								} else {
									nonDirectionalEntityTextures.get(pi).currentFrameCounter++;
								}
							}
						}
					}
				}
				
				if (p.isWalking()) {
					EntityInfo pi = p.getInfo();
					if (pi.getAnimationTime() != 0) {
						if (getPlayerImageInfo().currentFrameCounter == pi.getAnimationTime()) {
							if (getPlayerImageInfo().currentImage == getPlayerImageInfo().imgs.length - 1) {
								getPlayerImageInfo().currentImage = 0;
							} else {
								getPlayerImageInfo().currentImage++;
							}
							
							getPlayerImageInfo().currentFrameCounter = 0;
						} else {
							getPlayerImageInfo().currentFrameCounter++;
						}
					}
				}
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (directionalEntityTextures.isEmpty() && nonDirectionalEntityTextures.isEmpty()) {
			Console.print(Console.WarningType.FatalError, "Entity textures were not set!");
			return;
		}
		
		if (Main.getMain().getWorldHandler() != null) {
			EntityPlayer p = Main.getMain().getWorldHandler().getPlayer();
			if (p != null) {
				List<Room> roomsToRender = new ArrayList<Room>();
				
				if (p.getRoom() != null) {
					roomsToRender.add(p.getRoom());
				}
				
				if (p.getRoomToBe() != null) {
					roomsToRender.add(p.getRoomToBe());
				}
				
				for (Room r : roomsToRender) {
					for (Entity e : r.getEntities()) {
						if (e.getInfo().usesDirections()) {
							g.drawImage(directionalEntityTextures.get(e.getInfo()).get(e.getFacing()).getCurrentImage(), MathH.ceil(e.getPosX()),
									MathH.ceil(e.getPosY()), e.getWidth(), e.getHeight(), null);
						} else {
							g.drawImage(nonDirectionalEntityTextures.get(e.getInfo()).getCurrentImage(), MathH.ceil(e.getPosX()), MathH.ceil(e.getPosY()),
									e.getWidth(), e.getHeight(), null);
						}
					}
				}
				
				g.drawImage(getPlayerImageInfo().getCurrentImage(), MathH.ceil(p.getPosX()), MathH.ceil(p.getPosY()), p.getWidth(), p.getHeight(), null);
			}
		}
	}
	
	private void registerEntity(EntityInfo info, int count) {
		if (info.usesDirections()) {
			Map<EnumDirection, ImageInfo> m = new HashMap<EnumDirection, ImageInfo>();
			
			for (EnumDirection dir : EnumDirection.values()) {
				BufferedImage[] imgs = new BufferedImage[count];
				
				if (count > 1) {
					for (int i = 0; i < count; i++) {
						BufferedImage img = GetResource.getTexture(GetResource.ResourceType.entity, info.getName() + "/" + info.getName() + "_" + dir + "_" + i);
						
						imgs[i] = img;
						if (img != GetResource.nil) {
							Console.print(Console.WarningType.TextureDebug, "'" + info.getName() + "_" + dir + "_" + i + "' was registered!");
						} else {
							Console.print(Console.WarningType.Warning, "'" + info.getName() + "_" + dir + "_" + i + "' was not registered!");
						}
					}
					
					m.put(dir, new ImageInfo().setImages(imgs));
				} else {
					BufferedImage img = GetResource.getTexture(GetResource.ResourceType.entity, info.getName() + "_" + dir);
					
					m.put(dir, new ImageInfo(img));
					if (img != GetResource.nil) {
						Console.print(Console.WarningType.TextureDebug, "'" + info.getName() + "_" + dir + "' was registered!");
					} else {
						Console.print(Console.WarningType.TextureDebug, "'" + info.getName() + "_" + dir + "' was not registered!");
					}
				}
			}
			
			directionalEntityTextures.put(info, m);
		} else {
			BufferedImage[] imgs = new BufferedImage[count];
			ImageInfo iinfo = null;
			
			if (count > 1) {
				for (int i = 0; i < count; i++) {
					BufferedImage img = GetResource.getTexture(GetResource.ResourceType.entity, info.getName() + "/" + info.getName() + "_" + i);
					
					imgs[i] = img;
					if (img != GetResource.nil) {
						Console.print(Console.WarningType.TextureDebug, "'" + info.getName() + "_" + i + "' was registered!");
					} else {
						Console.print(Console.WarningType.Warning, "'" + info.getName() + "_" + i + "' was not registered!");
					}
				}
				
				iinfo = new ImageInfo().setImages(imgs);
			} else {
				BufferedImage img = GetResource.getTexture(GetResource.ResourceType.entity, info.getName());
				
				iinfo = new ImageInfo(img);
				if (img != GetResource.nil) {
					Console.print(Console.WarningType.TextureDebug, "'" + info.getName() + "' was registered!");
				} else {
					Console.print(Console.WarningType.TextureDebug, "'" + info.getName() + "' was not registered!");
				}
			}
			
			nonDirectionalEntityTextures.put(info, iinfo);
		}
		
	}
	
	public ImageInfo getPlayerImageInfo() {
		return directionalEntityTextures.get(EntityInfo.PLAYER).get(Main.getMain().getWorldHandler().getPlayer().getFacing());
	}
	
	@Override
	public boolean isHud() {
		return false;
	}
}
