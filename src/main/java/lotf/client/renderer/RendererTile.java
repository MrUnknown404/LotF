package main.java.lotf.client.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.lotf.Main;
import main.java.lotf.Main.Gamestate;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.init.Tiles;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.IStateTickable;
import main.java.lotf.util.ImageInfo;
import main.java.lotf.world.Room;
import main.java.ulibs.utils.Console;
import main.java.ulibs.utils.Grid;

public class RendererTile implements IRenderer, IStateTickable {
	private Map<TileInfo, ImageInfo> tileTextures = new HashMap<TileInfo, ImageInfo>();
	
	@Override
	public void setup() {
		for (TileInfo t : Tiles.getAll()) {
			registerTile(t, t.getTextureCount());
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
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if (tileTextures.isEmpty()) {
			Console.print(Console.WarningType.FatalError, "Tile textures were not set!");
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
				}
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
	
	@Override
	public boolean isHud() {
		return false;
	}
	
	@Override
	public Gamestate whenToTick() {
		return Gamestate.run;
	}
}
