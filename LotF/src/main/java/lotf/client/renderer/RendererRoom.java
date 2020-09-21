package main.java.lotf.client.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.lotf.Main;
import main.java.lotf.client.renderer.util.ImageInfo;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.entities.util.Entity;
import main.java.lotf.entities.util.EntityInfo;
import main.java.lotf.init.Tiles;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.world.Room;
import main.java.ulibs.utils.Console;
import main.java.ulibs.utils.Grid;
import main.java.ulibs.utils.math.MathH;

public class RendererRoom implements IRenderer, ITickable {
	private Map<EntityInfo, Map<EnumDirection, ImageInfo>> entityTextures = new HashMap<EntityInfo, Map<EnumDirection, ImageInfo>>();
	private Map<TileInfo, ImageInfo> tileTextures = new HashMap<TileInfo, ImageInfo>();
	
	@Override
	public void setup() {
		for (TileInfo t :Tiles.getAll()) {
			registerTile(t, t.getTextureCount());
		}
		for (EntityInfo e : EntityInfo.getAll()) {
			registerEntity(e, e.getTextureCount());
		}
	}
	
	@Override
	public void tick() {
		if (Main.getMain().getWorldHandler() != null) {
			EntityPlayer p = Main.getMain().getWorldHandler().getPlayer();
			
			if (p != null && p.getRoom() != null) {
				for (TileInfo t : p.getRoom().getAllTileInfos()) {
					if (t.getAnimationTime() != 0) {
						ImageInfo info = tileTextures.get(t);
						
						if (info.currentFrameCounter == t.getAnimationTime()) {
							if (info.currentImage == info.imgs.length - 1) {
								info.currentImage = 0;
							} else {
								info.currentImage++;
							}
							
							info.currentFrameCounter = 0;
						} else {
							info.currentFrameCounter++;
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
		EntityPlayer p = Main.getMain().getWorldHandler().getPlayer();
		
		if (tileTextures.isEmpty()) {
			Console.print(Console.WarningType.FatalError, "Tile textures were not set!");
			return;
		}
		
		if (entityTextures.isEmpty()) {
			Console.print(Console.WarningType.FatalError, "Entity textures were not set!");
			return;
		}
		
		if (Main.getMain().getWorldHandler() != null) {
			if (p != null) {
				List<Room> roomsToRender = new ArrayList<Room>();
				
				if (p.getRoom() != null) {
					roomsToRender.add(p.getRoom());
				}
				
				if (p.getRoomToBe() != null) {
					roomsToRender.add(p.getRoomToBe());
				}
				
				for (Room r : roomsToRender) {
					for (Grid<Tile> grid : r.getVisibleTiles()) {
						for (Tile t : grid.get()) {
							if (t == null) {
								continue;
							}
							
							int wX = Tile.TILE_SIZE, x = (int) t.getPosX();
							
							if (t.isFlipped()) {
								wX = -wX;
								x += Tile.TILE_SIZE;
							}
							
							g.drawImage(tileTextures.get(t.getTileInfo()).getCurrentImage(), x, (int) t.getPosY(), wX, Tile.TILE_SIZE, null);
						}
					}
					
					for (Entity e : r.getEntities()) {
						g.setColor(Color.red);
						g.drawImage(entityTextures.get(e.getInfo()).get(e.getFacing()).getCurrentImage(), MathH.ceil(e.getPosX()), MathH.ceil(e.getPosY()),
								e.getWidth(), e.getHeight(), null);
					}
				}
				
				g.drawImage(getPlayerImageInfo().getCurrentImage(), MathH.ceil(p.getPosX()), MathH.ceil(p.getPosY()), p.getWidth(), p.getHeight(), null);
			}
		}
	}
	
	private void registerTile(TileInfo tInfo, int count) {
		if (count > 1) {
			BufferedImage[] imgs = new BufferedImage[count];
			for (int i = 0; i < count; i++) {
				BufferedImage img = GetResource.getTexture(GetResource.ResourceType.tile, tInfo.getName() + "/" + tInfo.getName() + "_" + i);
				
				imgs[i] = img;
				if (img != GetResource.nil) {
					Console.print(Console.WarningType.TextureDebug, "'" + tInfo.getName() + "_" + i + "' was registered!");
				} else {
					Console.print(Console.WarningType.Warning, "'" + tInfo.getName() + "_" + i + "' was not registered!");
				}
			}
			
			tileTextures.put(tInfo, new ImageInfo().setImages(imgs));
		} else {
			BufferedImage img = GetResource.getTexture(GetResource.ResourceType.tile, tInfo.getName());
			
			tileTextures.put(tInfo, new ImageInfo(img));
			if (img != GetResource.nil) {
				Console.print(Console.WarningType.TextureDebug, "'" + tInfo.getName() + "' was registered!");
			} else {
				Console.print(Console.WarningType.TextureDebug, "'" + tInfo.getName() + "' was not registered!");
			}
		}
	}
	
	private void registerEntity(EntityInfo tInfo, int count) {
		Map<EnumDirection, ImageInfo> m = new HashMap<EnumDirection, ImageInfo>();
		
		for (EnumDirection dir : EnumDirection.values()) {
			BufferedImage[] imgs = new BufferedImage[count];
			
			if (count > 1) {
				for (int i = 0; i < count; i++) {
					BufferedImage img = GetResource.getTexture(GetResource.ResourceType.entity, tInfo.getName() + "/" + tInfo.getName() + "_" + dir + "_" + i);
					
					imgs[i] = img;
					if (img != GetResource.nil) {
						Console.print(Console.WarningType.TextureDebug, "'" + tInfo.getName() + "_" + dir + "_" + i + "' was registered!");
					} else {
						Console.print(Console.WarningType.Warning, "'" + tInfo.getName() + "_" + dir + "_" + i + "' was not registered!");
					}
				}
				
				m.put(dir, new ImageInfo().setImages(imgs));
			} else {
				BufferedImage img = GetResource.getTexture(GetResource.ResourceType.entity, tInfo.getName() + "_" + dir);
				
				m.put(dir, new ImageInfo(img));
				if (img != GetResource.nil) {
					Console.print(Console.WarningType.TextureDebug, "'" + tInfo.getName() + "_" + dir + "' was registered!");
				} else {
					Console.print(Console.WarningType.TextureDebug, "'" + tInfo.getName() + "_" + dir + "' was not registered!");
				}
			}
		}
		
		entityTextures.put(tInfo, m);
	}
	
	public ImageInfo getPlayerImageInfo() {
		return entityTextures.get(EntityInfo.PLAYER).get(Main.getMain().getWorldHandler().getPlayer().getFacing());
	}
	
	@Override
	public boolean isHud() {
		return false;
	}
}
