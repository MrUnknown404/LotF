package main.java.lotfbuilder.client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import main.java.lotf.init.Tiles;
import main.java.lotf.tile.Tile;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.Console;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.GetResource.ResourceType;
import main.java.lotf.util.Grid;
import main.java.lotf.world.Room;
import main.java.lotfbuilder.MainBuilder;

public class Renderer {
	
	private Map<TileInfo, BufferedImage> tiles = new HashMap<TileInfo, BufferedImage>();
	
	public void setupTextures() {
		Console.print(Console.WarningType.Info, "Starting texture registering for " + getClass().getSimpleName() + "...");
		
		for (TileInfo info : Tiles.getAll()) {
			if (info.getTextureCount() == 1) {
				tiles.put(info, GetResource.getTexture(false, ResourceType.tile, info.getName()));
			} else {
				tiles.put(info, GetResource.getTexture(false, ResourceType.tile, info.getName() + "/" + info.getName() + "_0"));
			}
		}
		
		Console.print(Console.WarningType.Info, "Finished texture registering for " + getClass().getSimpleName() + "!");
	}
	
	public void render(Graphics2D g) {
		Room r = MainBuilder.main.builder.getRoom();
		g.setColor(Color.MAGENTA);
		g.drawRect((int) r.getPosX(), (int) r.getPosY(), r.getWidth() * Tile.TILE_SIZE, r.getHeight() * Tile.TILE_SIZE);
		
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
				
				g.drawImage(tiles.get(t.getTileInfo()), x, (int) t.getPosY(), wX, Tile.TILE_SIZE, null);
			}
		}
	}
}
