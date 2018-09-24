package main.java.lotf.client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.entity.Entity;
import main.java.lotf.entity.EntityPlayer;
import main.java.lotf.init.InitEntities;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.Console;
import main.java.lotf.util.EnumDirection;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.ImageList;
import main.java.lotf.world.Room;
import main.java.lotf.world.RoomHandler;

public final class Renderer {

	private RoomHandler handler;
	
	private BufferedImage[] playerTexture = new BufferedImage[4];
	
	private List<ImageList> tiles = new ArrayList<ImageList>(Tile.TileType.values().length);
	private List<ImageList> entities = new ArrayList<ImageList>(InitEntities.getEntities().size());
	
	public Renderer() {
		handler = Main.getRoomHandler();
	}
	
	public void loadTextures() {
		for (int i = 0; i < Main.getRoomHandler().getPlayer().count; i++) {
			playerTexture[i] = GetResource.getTexture(GetResource.ResourceType.entity, "player/player_" + i);
			Console.print(Console.WarningType.Texture, "Registered texture for EntityPlayer : " + "entity/player/player_" + i + ".png!");
		}
		
		for (int i = 0; i < Tile.TileType.values().length; i++) {
			tiles.add(new ImageList());
			
			if (i == 0) {
				tiles.get(i).images.add(GetResource.getTexture(GetResource.ResourceType.tile, "missing"));
				tiles.get(i).stringKey = Tile.TileType.getFromNumber(i).toString();
				tiles.get(i).meta = Tile.TileType.getFromNumber(i).count;
				
				Console.print(Console.WarningType.Texture, "Registered missing texture Tile : tile/missing.png!");
			} else {
				if (Tile.TileType.getFromNumber(i).count == 1) {
					tiles.get(i).images.add(GetResource.getTexture(GetResource.ResourceType.tile, Tile.TileType.getFromNumber(i).toString()));
					tiles.get(i).stringKey = Tile.TileType.getFromNumber(i).toString();
					tiles.get(i).meta = Tile.TileType.getFromNumber(i).count;
					
					Console.print(Console.WarningType.Texture, "Registered texture for Tile : " + "tile/" + Tile.TileType.getFromNumber(i) + ".png!");
				} else {
					for (int j = 0; j < Tile.TileType.getFromNumber(i).count; j++) {
						tiles.get(i).images.add(GetResource.getTexture(GetResource.ResourceType.tile, Tile.TileType.getFromNumber(i).toString() + "/" + Tile.TileType.getFromNumber(i).toString() + "_" + j));
						
						Console.print(Console.WarningType.Texture, "Registered texture for Tile : " + "tile/" + Tile.TileType.getFromNumber(i).toString() + "/" + Tile.TileType.getFromNumber(i).toString() + "_" + j + ".png!");
					}
					
					tiles.get(i).stringKey = Tile.TileType.getFromNumber(i).toString();
					tiles.get(i).meta = Tile.TileType.getFromNumber(i).count;
				}
			}
		}
		
		for (int i = 0; i < InitEntities.getEntities().size(); i++) {
			entities.add(new ImageList());
			
			if (i == 0) {
				entities.get(i).images.add(GetResource.getTexture(GetResource.ResourceType.entity, "missing"));
				entities.get(i).stringKey = InitEntities.getEntities().get(i).toString();
				entities.get(i).meta = InitEntities.getEntities().get(i).count;
				
				Console.print(Console.WarningType.Texture, "Registered missing texture Entity : entity/missing.png!");
			} else {
				if (Tile.TileType.getFromNumber(i).count == 1) {
					entities.get(i).images.add(GetResource.getTexture(GetResource.ResourceType.entity, InitEntities.getEntities().get(i).toString()));
					entities.get(i).stringKey = InitEntities.getEntities().get(i).toString();
					entities.get(i).meta = InitEntities.getEntities().get(i).count;
					
					Console.print(Console.WarningType.Texture, "Registered texture for " + entities.get(i).getClass().getCanonicalName() + " : " + "entity/" + InitEntities.getEntities().get(i).toString() + ".png!");
				} else {
					for (int j = 0; j < Tile.TileType.getFromNumber(i).count; j++) {
						entities.get(i).images.add(GetResource.getTexture(GetResource.ResourceType.entity, InitEntities.getEntities().get(i).toString() + "/" + InitEntities.getEntities().get(i).toString() + "_" + j));
						
						Console.print(Console.WarningType.Texture, "Registered texture for " + entities.get(i).getClass().getCanonicalName() + " : " + "entity/" + InitEntities.getEntities().get(i).toString() + "/" + InitEntities.getEntities().get(i).toString() + "_" + j + ".png!");
					}
					
					entities.get(i).stringKey = InitEntities.getEntities().get(i).toString();
					entities.get(i).meta = InitEntities.getEntities().get(i).count;
				}
			}
		}
	}
	
	public void render(Graphics g2) {
		Graphics2D g = (Graphics2D) g2;
		
		List<Room> rooms = handler.getRooms();
		
		for (int i = 0; i < rooms.size(); i++) {
			Room r = rooms.get(i);
			
			if (!r.getActiveBounds().intersects(0, 0, Main.WIDTH_DEF, Main.HEIGHT_DEF)) {
				continue;
			}
			
			for (int j = 0; j < r.getTiles().size(); j++) {
				Tile t = r.getTiles().get(j);
				
				if (t.getTileType() == Tile.TileType.air) {
					continue;
				}
				
				for (int k = 0; k < tiles.size(); k++) {
					if (t.getStringID().substring(4, t.getStringID().length()).equals(tiles.get(k).stringKey)) {
						if (t.getMeta() == -1) {
							g.drawImage(tiles.get(k).images.get(0), t.getPositionX(), t.getPositionY(), t.getWidth(), t.getHeight(), null);
						} else {
							if (t.getMeta() < tiles.get(k).images.size()) {
								g.drawImage(tiles.get(k).images.get(t.getMeta()), t.getPositionX(), t.getPositionY(), t.getWidth(), t.getHeight(), null);
							} else {
								g.drawImage(tiles.get(0).images.get(0), t.getPositionX(), t.getPositionY(), t.getWidth(), t.getHeight(), null);
								Console.print(Console.WarningType.Error, "Invalid meta " + t.getMeta());
							}
						}
					}
				}
			}
			
			for (int j = 0; j < r.getEntities().size(); j++) {
				Entity e = r.getEntities().get(j);
				
				if (e == InitEntities.get("ENT_player")) {
					Console.print(Console.WarningType.FatalError, "why is the player here?");
					continue;
				}
				
				for (int k = 0; k < entities.size(); k++) {
					if (e.getStringID().substring(4, e.getStringID().length()).equals(tiles.get(k).stringKey)) {
						if (e.getMeta() == -1) {
							g.drawImage(entities.get(k).images.get(0), e.getPositionX(), e.getPositionY(), e.getWidth(), e.getHeight(), null);
						} else {
							if (e.getMeta() < entities.get(k).images.size()) {
								g.drawImage(entities.get(k).images.get(e.getMeta()), e.getPositionX(), e.getPositionY(), e.getWidth(), e.getHeight(), null);
							} else {
								g.drawImage(entities.get(0).images.get(0), e.getPositionX(), e.getPositionY(), e.getWidth(), e.getHeight(), null);
								Console.print(Console.WarningType.Error, "Invalid meta " + e.getMeta());
							}
						}
					}
				}
			}
			
			EntityPlayer player = Main.getRoomHandler().getPlayer();
			if (player != null) {
				if (player.getFacing() == EnumDirection.north) {
					g.drawImage(playerTexture[0], player.getPositionX(), player.getPositionY(), player.getWidth(), player.getHeight(), null);
				} else if (player.getFacing() == EnumDirection.east) {
					g.drawImage(playerTexture[1], player.getPositionX(), player.getPositionY(), player.getWidth(), player.getHeight(), null);
				} else if (player.getFacing() == EnumDirection.south) {
					g.drawImage(playerTexture[2], player.getPositionX(), player.getPositionY(), player.getWidth(), player.getHeight(), null);
				} else if (player.getFacing() == EnumDirection.west) {
					g.drawImage(playerTexture[3], player.getPositionX(), player.getPositionY(), player.getWidth(), player.getHeight(), null);
				} else {
					Console.print(Console.WarningType.Error, "Tried to render player facing an unknown direction!");
				}
			}
		}
	}
}
