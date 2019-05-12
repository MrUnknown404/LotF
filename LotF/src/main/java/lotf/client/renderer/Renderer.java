package main.java.lotf.client.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.client.renderer.util.ImageInfo;
import main.java.lotf.client.renderer.util.TileImageInfo;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.Console;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.world.Room;

public class Renderer implements ITickable {
	
	private List<ImageInfo> textures = new ArrayList<ImageInfo>();
	private List<TileImageInfo> tileTextures = new ArrayList<TileImageInfo>();
	
	private BufferedImage player;
	
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
		
		//TODO temp
		player = GetResource.getTexture(GetResource.ResourceType.entity, "player");
		
		if (player != null) {
			Console.print(Console.WarningType.TextureDebug, "player was registered!");
		} else {
			Console.print(Console.WarningType.TextureDebug, "player was not registered!");
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
			}
		}
	}
	
	public void render(Graphics2D g) {
		EntityPlayer p = Main.getMain().getWorldHandler().getPlayer();
		
		if (tileTextures.isEmpty()) {
			Console.print(Console.WarningType.FatalError, "Tile textures were not set!");
			return;
		}
		
		if (Main.getMain().getWorldHandler() != null) {
			if (p != null) {
				for (Room r : Main.getMain().getWorldHandler().getPlayerWorld().getRooms()) { //TODO temp
					g.setColor(Color.BLUE);
					g.drawRect((int) r.getBounds().x, (int) r.getBounds().y, r.getBounds().width, r.getBounds().height);
				}
				
				List<Room> roomsToRender = new ArrayList<>();
				
				if (p.getRoom() != null) {
					roomsToRender.add(p.getRoom());
				}
				
				if (p.getRoomToBe() != null) {
					roomsToRender.add(p.getRoomToBe());
				}
				
				//if (p.getRoom() != null) {
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
					
					g.setColor(Color.ORANGE);
					Rectangle rec = p.getRoom().getRoomBounds(EnumDirection.east);
					g.drawRect(rec.x, rec.y, rec.width, rec.height);
					rec = p.getRoom().getRoomBounds(EnumDirection.west);
					g.drawRect(rec.x, rec.y, rec.width, rec.height);
					rec = p.getRoom().getRoomBounds(EnumDirection.north);
					g.drawRect(rec.x, rec.y, rec.width, rec.height);
					rec = p.getRoom().getRoomBounds(EnumDirection.south);
					g.drawRect(rec.x, rec.y, rec.width, rec.height);
				}
				
				g.drawImage(player, (int) p.getPos().getX(), (int) p.getPosY(), p.getWidth(), p.getHeight(), null);
			}
		}
	}
	
	private void registerTile(TileInfo tInfo, int count) {
		TileImageInfo info = new TileImageInfo(tInfo);
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
			tileTextures.add(new TileImageInfo(tInfo, img));
			Console.print(Console.WarningType.TextureDebug, tInfo.getName() + " was registered!");
		} else {
			Console.print(Console.WarningType.TextureDebug, tInfo.getName() + " was not registered!");
		}
	}
	
	public TileImageInfo getTileImageInfo(TileInfo tInfo) {
		for (TileImageInfo info : tileTextures) {
			if (info.getTileInfo().equals(tInfo)) {
				return info;
			}
		}
		
		return null;
	}
}
