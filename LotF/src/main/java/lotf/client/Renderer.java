package main.java.lotf.client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.entity.EntityMonster;
import main.java.lotf.entity.EntityNPC;
import main.java.lotf.entity.EntityPlayer;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.Console;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.ImageList;
import main.java.lotf.world.Room;
import main.java.lotf.world.WorldHandler;

public final class Renderer {

	private WorldHandler handler;
	
	private BufferedImage[] playerTexture = new BufferedImage[4];
	
	private List<ImageList> tiles = new ArrayList<ImageList>(Tile.TileType.values().length);
	private List<ImageList> entities = new ArrayList<ImageList>();
	
	public Renderer() {
		handler = Main.getWorldHandler();
	}
	
	public void loadTextures() {
		for (int i = 0; i < 4; i++) {
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
		
		entities.add(new ImageList());
		entities.get(0).images.add(GetResource.getTexture(GetResource.ResourceType.entity, "missing"));
		entities.get(0).stringKey = "missing";
		entities.get(0).meta = 0;
		Console.print(Console.WarningType.Texture, "Registered missing texture Entity : entity/missing.png!");
		
		for (int i = 0; i < EntityMonster.MonsterType.values().length; i++) {
			ImageList img = new ImageList();
			
			for (int j = 0; j < EntityMonster.MonsterType.getFromNumber(i).count * 4; j++) {
				img.images.add(GetResource.getTexture(GetResource.ResourceType.entity, EntityMonster.MonsterType.getFromNumber(i).toString() + "/" + EntityMonster.MonsterType.getFromNumber(i).toString() + "_" + j));
				
				Console.print(Console.WarningType.Texture, "Registered texture for Entity : " + "entity/" + EntityMonster.MonsterType.getFromNumber(i).toString() + "/" + EntityMonster.MonsterType.getFromNumber(i).toString() + "_" + j + ".png!");
			}
			
			img.stringKey = EntityMonster.MonsterType.getFromNumber(i).toString();
			img.meta = EntityMonster.MonsterType.getFromNumber(i).count;
			entities.add(img);
		}
		
		for (int i = 0; i < EntityNPC.NPCType.values().length; i++) {
			ImageList img = new ImageList();
			
			for (int j = 0; j < EntityNPC.NPCType.getFromNumber(i).count * 4; j++) {
				img.images.add(GetResource.getTexture(GetResource.ResourceType.entity, EntityNPC.NPCType.getFromNumber(i).toString() + "/" + EntityNPC.NPCType.getFromNumber(i).toString() + "_" + j));
				
				Console.print(Console.WarningType.Texture, "Registered texture for Entity : " + "entity/" + EntityNPC.NPCType.getFromNumber(i).toString() + "/" + EntityNPC.NPCType.getFromNumber(i).toString() + "_" + j + ".png!");
			}
			
			img.stringKey = EntityNPC.NPCType.getFromNumber(i).toString();
			img.meta = EntityNPC.NPCType.getFromNumber(i).count;
			
			entities.add(img);
		}
	}
	
	public void render(Graphics g2) {
		Graphics2D g = (Graphics2D) g2;
		
		
		if (!handler.getPlayer().getInventory().isInventoryOpen) {
			List<Room> rooms = new ArrayList<>();
			
			if (handler.getPlayerRoom() != null) {
				rooms.add(handler.getPlayerRoom());
			}
			if (handler.getPlayerRoomToBe() != null) {
				rooms.add(handler.getPlayerRoomToBe());
			}
			
			for (int i = 0; i < rooms.size(); i++) {
				Room r = rooms.get(i);
				
				List<Tile> ts = new ArrayList<>();
				
				for (int j = 0; j < r.getTileLayer0().size(); j++) {
					if (!r.getTileLayer1().get(j).getTileType().shouldRenderBehind) {
						ts.add(r.getTileLayer0().get(j));
					}
					ts.add(r.getTileLayer1().get(j));
				}
				
				for (int j = 0; j < ts.size(); j++) {
					Tile t = ts.get(j);
					
					if (t.getTileType() == Tile.TileType.air) {
						continue;
					}
					
					for (int k = 0; k < tiles.size(); k++) {
						if (t.getName().equals(tiles.get(k).stringKey)) {
							if (t.getMaxMeta() == 0) {
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
				
				for (int j = 0; j < r.getMonsters().size(); j++) {
					EntityMonster e = r.getMonsters().get(j);
					
					for (int k = 0; k < entities.size(); k++) {
						if (e.getName().equals(entities.get(k).stringKey)) {
							g.drawImage(entities.get(k).images.get(e.getMeta()), e.getPositionX(), e.getPositionY(), e.getWidth(), e.getHeight(), null);
						}
					}
				}
				
				for (int j = 0; j < r.getNPCs().size(); j++) {
					EntityNPC e = r.getNPCs().get(j);
					
					for (int k = 0; k < entities.size(); k++) {
						if (e.getName().equals(entities.get(k).stringKey)) {
							g.drawImage(entities.get(k).images.get(e.getMeta()), e.getPositionX(), e.getPositionY(), e.getWidth(), e.getHeight(), null);
						}
					}
				}
			}
			
			EntityPlayer player = handler.getPlayer();
			if (player != null) {
				g.drawImage(playerTexture[player.getFacing().fId - 1], player.getPositionX(), player.getPositionY(), player.getWidth(), player.getHeight(), null);
			}
		}
	}
}
