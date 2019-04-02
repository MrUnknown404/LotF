package main.java.lotf.client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import main.java.lotf.Main;
import main.java.lotf.entity.EntityPlayer;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.Console;
import main.java.lotf.util.GetResource;

public class Renderer {

	//public List<> tileTextures = new ArrayList<>();
	public Map<String, BufferedImage> tileTextures = new HashMap<String, BufferedImage>();
	
	public void getTileTextures() {
		Console.print(Console.WarningType.Info, "Starting texture registering...");
		
		for (int i = 0; i < TileInfo.getAllTypes().size(); i++) {
			if (i == 0) {
				BufferedImage img = GetResource.getTexture("nil");
				
				if (img != null) {
					tileTextures.put(TileInfo.getAllTypes().get(i).getName(), img);
					Console.print(Console.WarningType.TextureDebug, "nil (air) was registered!");
				} else {
					Console.print(Console.WarningType.Warning, "nil (air) was not registered!");
				}
			} else {
				if (TileInfo.getAllTypes().get(i).getTextureCount() > 1) {
					for (int j = 0; j < TileInfo.getAllTypes().get(i).getTextureCount(); j++) {
						BufferedImage img = GetResource.getTexture(GetResource.ResourceType.tile, TileInfo.getAllTypes().get(i).getName() + "/" + TileInfo.getAllTypes().get(i).getName() + "_" + j);
						
						if (img != null) {
							tileTextures.put(TileInfo.getAllTypes().get(i).getName(), img);
							Console.print(Console.WarningType.TextureDebug, TileInfo.getAllTypes().get(i).getName() + "_" + j + " was registered!");
						} else {
							Console.print(Console.WarningType.Warning, TileInfo.getAllTypes().get(i).getName() + "_" + j + " was not registered!");
						}
					}
				} else {
					BufferedImage img = GetResource.getTexture(GetResource.ResourceType.tile, TileInfo.getAllTypes().get(i).getName());
					
					if (img != null) {
						tileTextures.put(TileInfo.getAllTypes().get(i).getName(), img);
						Console.print(Console.WarningType.TextureDebug, TileInfo.getAllTypes().get(i).getName() + " was registered!");
					} else {
						Console.print(Console.WarningType.TextureDebug, TileInfo.getAllTypes().get(i).getName() + " was not registered!");
					}
				}
			}
		}
		
		Console.print(Console.WarningType.Info, "Finished texture registering!");
	}
	
	public void render(Graphics2D g) {
		EntityPlayer p = Main.getWorld().getPlayer();
		
		if (tileTextures.isEmpty()) {
			//Console.print(Console.WarningType.FatalError, "Tile textures were not set!");
			return;
		}
		
		if (Main.getWorld() != null) {
			if (p != null) {
				if (p.getRoom() != null) {
					for (Tile t : p.getRoom().getTiles()) {
						g.setColor(Color.BLUE);
						g.fillRect((int) t.getPos().getX(), (int) t.getPosY(), t.getWidth(), t.getHeight());
					}
				}
				
				g.setColor(Color.BLUE);
				g.fillRect((int) p.getPos().getX(), (int) p.getPosY(), p.getWidth(), p.getHeight());
			}
		}
	}
}
