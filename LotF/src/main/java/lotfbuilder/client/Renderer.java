package main.java.lotfbuilder.client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.java.lotf.entity.Entity;
import main.java.lotf.entity.EntityMonster;
import main.java.lotf.entity.EntityNPC;
import main.java.lotf.inventory.Inventory;
import main.java.lotf.items.ItemEmpty;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.Console;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.ImageList;
import main.java.lotf.world.Room;
import main.java.lotfbuilder.MainBuilder;
import main.java.lotfbuilder.room.RoomBuilder;
import main.java.lotfbuilder.util.ItemEntity;
import main.java.lotfbuilder.util.ItemTile;

public final class Renderer {
	
	private List<ImageList> tiles = new ArrayList<ImageList>(Tile.TileType.values().length);
	private List<ImageList> entities = new ArrayList<ImageList>(EntityMonster.MonsterType.values().length + EntityNPC.NPCType.values().length);
	
	private BufferedImage slot, slotSel;
	
	public void loadTextures() {
		slot = GetResource.getTexture(GetResource.ResourceType.gui, "slot");
		Console.print(Console.WarningType.Texture, "Registered texture for Renderer : " + "gui/slot.png!");
		slotSel = GetResource.getTexture(GetResource.ResourceType.gui, "slotselected");
		Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/slotselected.png!");
		
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
		
		for (int i = 0; i < EntityMonster.MonsterType.values().length; i++) {
			ImageList iL = new ImageList();
			
			iL.images.add(GetResource.getTexture(GetResource.ResourceType.entity, EntityMonster.MonsterType.getFromNumber(i) + "/" + EntityMonster.MonsterType.getFromNumber(i).toString() + "_0"));
			iL.stringKey = EntityMonster.MonsterType.getFromNumber(i).toString();
			entities.add(iL);
			
			Console.print(Console.WarningType.Texture, "Registered texture for Entity : " + "entity/" + EntityMonster.MonsterType.getFromNumber(i) + "/" + EntityMonster.MonsterType.getFromNumber(i) + "_0.png!");
		}
		
		for (int i = 0; i < EntityNPC.NPCType.values().length; i++) {
			ImageList iL = new ImageList();
			
			iL.images.add(GetResource.getTexture(GetResource.ResourceType.entity, EntityNPC.NPCType.getFromNumber(i) + "/" + EntityNPC.NPCType.getFromNumber(i).toString() + "_0"));
			iL.stringKey = EntityNPC.NPCType.getFromNumber(i).toString();
			entities.add(iL);
			
			Console.print(Console.WarningType.Texture, "Registered texture for Entity : " + "entity/" + EntityNPC.NPCType.getFromNumber(i) + "/" +  EntityNPC.NPCType.getFromNumber(i) + "_0.png!");
		}
	}
	
	public void render(Graphics2D g) {
		if (MainBuilder.getDoesRoomExist()) {
			Room r = MainBuilder.getRoomBuilder().getRoom();
			
			List<Tile> ts = new ArrayList<>();
			
			for (int i = 0; i < r.getTileLayer0().size(); i++) {
				if (!r.getTileLayer1().get(i).getTileType().shouldRenderBehind) {
					ts.add(r.getTileLayer0().get(i));
				}
				ts.add(r.getTileLayer1().get(i));
			}
			
			for (int i = 0; i < r.getTileLayer0().size(); i++) {
				Tile t = r.getTileLayer0().get(i);
				g.drawImage(tiles.get(0).images.get(0), t.getPositionX(), t.getPositionY(), Tile.TILE_SIZE, Tile.TILE_SIZE, null);
			}
			
			for (int i = 0; i < ts.size(); i++) {
				Tile t = ts.get(i);
				
				if (t.getTileType() == Tile.TileType.air) {
					continue;
				}
				
				for (int j = 0; j < tiles.size(); j++) {
					if (t.getName().equals(tiles.get(j).stringKey)) {
						g.drawImage(tiles.get(j).images.get(t.getMeta()), t.getPositionX(), t.getPositionY(), t.getWidth(), t.getHeight(), null);
					}
				}
			}
			
			for (int i = 0; i < r.getMonsters().size(); i++) { 
				EntityMonster e = r.getMonsters().get(i);
				
				for (int j = 0; j < entities.size(); j++) {
					if (e.getName().equals(tiles.get(j).stringKey)) {
						g.drawImage(entities.get(j).images.get(e.getMeta()), e.getPositionX(), e.getPositionY(), e.getWidth(), e.getHeight(), null);
					}
				}
			}
			
			for (int i = 0; i < r.getNPCs().size(); i++) { 
				EntityNPC e = r.getNPCs().get(i);
				
				for (int j = 0; j < entities.size(); j++) {
					if (e.getName().equals(tiles.get(j).stringKey)) {
						g.drawImage(entities.get(j).images.get(e.getMeta()), e.getPositionX(), e.getPositionY(), e.getWidth(), e.getHeight(), null);
					}
				}
			}
		}
	}
	
	public void renderHud(Graphics2D g) {
		if (MainBuilder.getDoesRoomExist()) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, MainBuilder.getHudWidth(), 48);
			
			RoomBuilder br = MainBuilder.getRoomBuilder();
			Inventory selInv = MainBuilder.getRoomBuilder().selInv;
			Inventory inv = br.getSelectedPage();
			
			if (br.isOpen) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(0, 48, MainBuilder.getHudWidth(), MainBuilder.getHudHeight() - 48);
				
				for (int i = 0; i < inv.getSlotsList().size(); i++) {
					g.drawImage(slot, inv.getSlotsList().get(i).getX() - 2, inv.getSlotsList().get(i).getY() + 6, 40, 40, null);
					
					if (!(inv.getItems().get(i) instanceof ItemEmpty)) {
						if (inv.getItems().get(i) instanceof ItemTile) {
							ItemTile t = (ItemTile) inv.getItems().get(i);
							
							for (int k = 0; k < tiles.size(); k++) {
								if (t.getTileType().toString().equals(tiles.get(k).stringKey)) {
									if (t.getMaxMeta() == 0) {
										g.drawImage(tiles.get(k).images.get(0), inv.getSlotsList().get(i).getX() + 2, inv.getSlotsList().get(i).getY() + 10, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
									} else {
										g.drawImage(tiles.get(k).images.get(t.getMeta()), inv.getSlotsList().get(i).getX() + 2, inv.getSlotsList().get(i).getY() + 10, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
									}
								}
							}
						} else {
							ItemEntity t = (ItemEntity) inv.getItems().get(i);
							
							for (int k = 0; k < entities.size(); k++) {
								if (t.getEntityType() == Entity.EntityType.monster) {
									if (t.getMonsterType().toString().equals(entities.get(k).stringKey)) {
										g.drawImage(entities.get(k).images.get(0), inv.getSlotsList().get(i).getX() + 2, inv.getSlotsList().get(i).getY() + 10, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
									}
								} else {
									if (t.getNPCType().toString().equals(entities.get(k).stringKey)) {
										g.drawImage(entities.get(k).images.get(0), inv.getSlotsList().get(i).getX() + 2, inv.getSlotsList().get(i).getY() + 10, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
									}
								}
							}
						}
					}
				}
			}
			
			for (int i = 0; i < selInv.getSlotsList().size(); i++) {
				if (i == br.selectedSlot) {
					g.drawImage(slotSel, selInv.getSlotsList().get(i).getX() - 2, selInv.getSlotsList().get(i).getY() - 38, 40, 40, null);
				} else {
					g.drawImage(slot, selInv.getSlotsList().get(i).getX() - 2, selInv.getSlotsList().get(i).getY() - 38, 40, 40, null);
				}
				
				if (!(selInv.getItems().get(i) instanceof ItemEmpty)) {
					if (selInv.getItems().get(i) instanceof ItemTile) {
						for (int k = 0; k < tiles.size(); k++) {
							ItemTile t = (ItemTile) selInv.getItems().get(i);
							
							if (t.getTileType().toString().equals(tiles.get(k).stringKey)) {
								g.drawImage(tiles.get(k).images.get(t.getMeta()), selInv.getSlotsList().get(i).getX() + 2, selInv.getSlotsList().get(i).getY() - 34, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
							}
						}
					} else if (selInv.getItems().get(i) instanceof ItemEntity) {
						for (int k = 0; k < entities.size(); k++) {
							ItemEntity t = (ItemEntity) selInv.getItems().get(i);
							
							if (t.getEntityType() == Entity.EntityType.monster) {
								if (t.getMonsterType().toString().equals(entities.get(k).stringKey)) {
									g.drawImage(entities.get(k).images.get(t.getMeta()), selInv.getSlotsList().get(i).getX() + 2, selInv.getSlotsList().get(i).getY() - 34, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
								}
							} else {
								if (t.getNPCType().toString().equals(entities.get(k).stringKey)) {
									g.drawImage(entities.get(k).images.get(t.getMeta()), selInv.getSlotsList().get(i).getX() + 2, selInv.getSlotsList().get(i).getY() - 34, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
								}
							}
						}
					}
				}
			}
			
			if (br.hand != null) {
				if (br.hand instanceof ItemTile) {
					for (int k = 0; k < tiles.size(); k++) {
						if (((ItemTile) br.hand).getTileType().toString().equals(tiles.get(k).stringKey)) {
							if (br.hand.getMaxMeta() == 0) {
								g.drawImage(tiles.get(k).images.get(0), MouseInput.getHudVec().getX(), MouseInput.getHudVec().getY(), Tile.TILE_SIZE, Tile.TILE_SIZE, null);
							} else {
								g.drawImage(tiles.get(k).images.get(br.hand.getMeta()), MouseInput.getHudVec().getX(), MouseInput.getHudVec().getY(), Tile.TILE_SIZE, Tile.TILE_SIZE, null);
							}
						}
					}
				} else {
					for (int k = 0; k < entities.size(); k++) {
						if (((ItemEntity) br.hand).getEntityType() == Entity.EntityType.monster) {
							if (((ItemEntity) br.hand).getMonsterType().toString().equals(entities.get(k).stringKey)) {
								if (br.hand.getMaxMeta() == 0) {
									g.drawImage(entities.get(k).images.get(0), MouseInput.getHudVec().getX(), MouseInput.getHudVec().getY(), Tile.TILE_SIZE, Tile.TILE_SIZE, null);
								} else {
									g.drawImage(entities.get(k).images.get(br.hand.getMeta()), MouseInput.getHudVec().getX(), MouseInput.getHudVec().getY(), Tile.TILE_SIZE, Tile.TILE_SIZE, null);
								}
							}
						} else {
							if (((ItemEntity) br.hand).getNPCType().toString().equals(entities.get(k).stringKey)) {
								if (br.hand.getMaxMeta() == 0) {
									g.drawImage(entities.get(k).images.get(0), MouseInput.getHudVec().getX(), MouseInput.getHudVec().getY(), Tile.TILE_SIZE, Tile.TILE_SIZE, null);
								} else {
									g.drawImage(entities.get(k).images.get(br.hand.getMeta()), MouseInput.getHudVec().getX(), MouseInput.getHudVec().getY(), Tile.TILE_SIZE, Tile.TILE_SIZE, null);
								}
							}
						}
					}
				}
			}
		}
	}
}
