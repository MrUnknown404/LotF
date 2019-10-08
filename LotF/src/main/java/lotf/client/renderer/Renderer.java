package main.java.lotf.client.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.java.lotf.Main;
import main.java.lotf.client.renderer.util.ImageInfo;
import main.java.lotf.client.renderer.util.ThingImageInfo;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.entities.util.Entity;
import main.java.lotf.entities.util.EntityInfo;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.Console;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.world.Room;

public class Renderer implements ITickable {
	
	private Map<EnumDirection, ImageInfo> players = new HashMap<EnumDirection, ImageInfo>();
	private List<ImageInfo> textures = new ArrayList<ImageInfo>();
	private List<ThingImageInfo<TileInfo>> tileTextures = new ArrayList<ThingImageInfo<TileInfo>>();
	
	public void getTextures() {
		Console.print(Console.WarningType.Info, "Starting texture registering...");
		
		BufferedImage img = GetResource.getTexture("nil");
		
		if (img != null) {
			textures.add(new ImageInfo(img));
			Console.print(Console.WarningType.TextureDebug, "nil was registered!");
		} else {
			Console.print(Console.WarningType.TextureDebug, "nil was not registered!");
		}
		
		for (int i = 0; i < TileInfo.getAllTilesSize(); i++) {
			if (i != 0) {
				if (TileInfo.getTileInfo(i).getTextureCount() > 1) {
					registerTile(TileInfo.getTileInfo(i), TileInfo.getTileInfo(i).getTextureCount());
				} else {
					registerTile(TileInfo.getTileInfo(i));
				}
			}
		}
		
		//TODO temp, instead go thru all entities
		for (EnumDirection dir : EnumDirection.values()) {
			BufferedImage[] imgs = new BufferedImage[EntityInfo.PLAYER.getTextureCount()];
			
			for (int i = 0; i < EntityInfo.PLAYER.getTextureCount(); i++) {
				img = GetResource.getTexture(GetResource.ResourceType.entity, "player/player_" + dir + "_" + i);
				
				if (img != null) {
					imgs[i] = img;
					Console.print(Console.WarningType.TextureDebug, "player/player_" + dir + "_" + i + " was registered!");
				} else {
					Console.print(Console.WarningType.TextureDebug, "player/player_" + dir + "_" + i + " was not registered!");
				}
			}
			
			players.put(dir, new ImageInfo(imgs));
		}
		Console.print(Console.WarningType.Info, "Finished texture registering!");
	}
	
	@Override
	public void tick() {
		if (Main.getMain().getWorldHandler() != null) {
			EntityPlayer p = Main.getMain().getWorldHandler().getPlayer();
			
			if (p != null && p.getRoom() != null) {
				for (TileInfo t : p.getRoom().getAllTileInfos()) {
					if (t.getAnimationTime() != 0) {
						if (getTileImageInfo(t).currentFrameCounter == t.getAnimationTime()) {
							if (getTileImageInfo(t).currentImage == getTileImageInfo(t).imgs.length - 1) {
								getTileImageInfo(t).currentImage = 0;
							} else {
								getTileImageInfo(t).currentImage++;
							}
							
							getTileImageInfo(t).currentFrameCounter = 0;
						} else {
							getTileImageInfo(t).currentFrameCounter++;
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
	
	public void render(Graphics2D g) {
		EntityPlayer p = Main.getMain().getWorldHandler().getPlayer();
		
		if (tileTextures.isEmpty()) {
			Console.print(Console.WarningType.FatalError, "Tile textures were not set!");
			return;
		}
		
		if (players.isEmpty()) {
			Console.print(Console.WarningType.FatalError, "Player textures were not set!");
			return;
		}
		
		if (Main.getMain().getWorldHandler() != null) {
			if (p != null) {
				List<Room> roomsToRender = new ArrayList<>();
				
				if (p.getRoom() != null) {
					roomsToRender.add(p.getRoom());
				}
				
				if (p.getRoomToBe() != null) {
					roomsToRender.add(p.getRoomToBe());
				}
				
				for (Room r : roomsToRender) {
					List<Tile> tiles = new ArrayList<>();
					
					for (int i = 0; i < r.getTilesLayer0().size(); i++) {
						Tile t1 = r.getTilesLayer0().get(i);
						Tile t2 = r.getTilesLayer1().get(i);
						
						if (t1.getTileInfo() != TileInfo.AIR || t2.getTileInfo() != TileInfo.AIR) {
							if (t2.getTileInfo() != TileInfo.AIR) {
								if (t2.getTileInfo().shouldRenderBehind()) {
									tiles.add(t1);
									tiles.add(t2);
								} else {
									tiles.add(t2);
								}
							} else if (t1.getTileInfo() != TileInfo.AIR) {
								tiles.add(t1);
							}
						}
					}
					
					for (Tile t : tiles) {
						int wX = Tile.TILE_SIZE;
						int x = (int) t.getPosX();
						
						if (t.getFlipState() == 1) {
							wX = -wX;
							x += Tile.TILE_SIZE;
						}
						
						g.drawImage(getTileImageInfo(t.getTileInfo()).getCurrentImage(), x, (int) t.getPosY(), wX, Tile.TILE_SIZE, null);
					}
					
					for (Entity e : r.getEntities()) {
						//TODO rewrite_with_textures;
						g.setColor(Color.red);
						g.fillRect((int) e.getPosX(), (int) e.getPosY(), e.getWidth(), e.getHeight());
					}
				}
				
				g.drawImage(getPlayerImageInfo().getCurrentImage(), (int) p.getPosX(), (int) p.getPosY(), p.getWidth(), p.getHeight(), null);
			}
		}
	}
	
	private void registerTile(TileInfo tInfo, int count) {
		ThingImageInfo<TileInfo> info = new ThingImageInfo<TileInfo>(tInfo);
		BufferedImage[] imgs = new BufferedImage[count];
		
		for (int i = 0; i < count; i++) {
			BufferedImage img = GetResource.getTexture(GetResource.ResourceType.tile, tInfo.getName() + "/" + tInfo.getName() + "_" + i);
			
			if (img != null) {
				imgs[i] = img;
				Console.print(Console.WarningType.TextureDebug, tInfo.getName() + "_" + i + " was registered!");
			} else {
				Console.print(Console.WarningType.Warning, tInfo.getName() + "_" + i + " was not registered!");
			}
		}
		
		info.setImages(imgs);
		tileTextures.add(info);
	}
	
	private void registerTile(TileInfo tInfo) {
		BufferedImage img = GetResource.getTexture(GetResource.ResourceType.tile, tInfo.getName());
		
		if (img != null) {
			tileTextures.add(new ThingImageInfo<TileInfo>(tInfo, img));
			Console.print(Console.WarningType.TextureDebug, tInfo.getName() + " was registered!");
		} else {
			Console.print(Console.WarningType.TextureDebug, tInfo.getName() + " was not registered!");
		}
	}
	
	public ThingImageInfo<TileInfo> getTileImageInfo(TileInfo tInfo) {
		for (ThingImageInfo<TileInfo> info : tileTextures) {
			if (info.getInfo().equals(tInfo)) {
				return info;
			}
		}
		
		return null;
	}
	
	public ImageInfo getPlayerImageInfo() {
		Iterator<Entry<EnumDirection, ImageInfo>> it = players.entrySet().iterator();
		while (it.hasNext()) {
			Entry<EnumDirection, ImageInfo> pair = it.next();
			
			if (pair.getKey() == Main.getMain().getWorldHandler().getPlayer().getFacing()) {
				return pair.getValue();
			}
		}
		
		return null;
	}
}
