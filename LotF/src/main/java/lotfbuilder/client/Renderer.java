package main.java.lotfbuilder.client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.java.lotf.inventory.Inventory;
import main.java.lotf.items.ItemEmpty;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.Console;
import main.java.lotf.util.EnumCollisionType;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.ImageList;
import main.java.lotf.world.Room;
import main.java.lotfbuilder.MainBuilder;
import main.java.lotfbuilder.room.RoomBuilder;
import main.java.lotfbuilder.util.ItemTile;

public final class Renderer {
	
	private List<ImageList> tiles = new ArrayList<ImageList>(Tile.TileType.values().length);
	
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
	}
	
	public void render(Graphics2D g) {
		if (MainBuilder.getDoesRoomExist()) {
			Room r = MainBuilder.getRoomBuilder().getRoom();
			
			List<Tile> ts = new ArrayList<>();
			
			for (int j = 0; j < r.getTileLayer0().size(); j++) {
				if (r.getTileLayer1().get(j).getCollisionType() != EnumCollisionType.whole) {
					ts.add(r.getTileLayer0().get(j));
				}
				ts.add(r.getTileLayer1().get(j));
			}
			
			for (int i = 0; i < r.getTileLayer0().size(); i++) {
				Tile t = r.getTileLayer0().get(i);
				g.drawImage(tiles.get(0).images.get(0), t.getPositionX(), t.getPositionY(), Tile.TILE_SIZE, Tile.TILE_SIZE, null);
			}
			
			for (int j = 0; j < ts.size(); j++) {
				Tile t = ts.get(j);
				
				if (t.getTileType() == Tile.TileType.air) {
					continue;
				}
				
				for (int i = 0; i < tiles.size(); i++) {
					if (t.getName().equals(tiles.get(i).stringKey)) {
						if (t.getMaxMeta() == 0) {
							g.drawImage(tiles.get(i).images.get(0), t.getPositionX(), t.getPositionY(), Tile.TILE_SIZE, Tile.TILE_SIZE, null);
						} else {
							g.drawImage(tiles.get(i).images.get(t.getMeta()), t.getPositionX(), t.getPositionY(), t.getWidth(), t.getHeight(), null);
						}
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
					
					for (int k = 0; k < tiles.size(); k++) {
						if (!(inv.getItems().get(i) instanceof ItemEmpty)) {
							ItemTile t = (ItemTile) inv.getItems().get(i);
							if (t.getTileType().toString().equals(tiles.get(k).stringKey)) {
								if (t.getMaxMeta() == 0) {
									g.drawImage(tiles.get(k).images.get(0), inv.getSlotsList().get(i).getX() + 2, inv.getSlotsList().get(i).getY() + 10, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
								} else {
									g.drawImage(tiles.get(k).images.get(t.getMeta()), inv.getSlotsList().get(i).getX() + 2, inv.getSlotsList().get(i).getY() + 10, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
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
				
				for (int k = 0; k < tiles.size(); k++) {
					if (!(selInv.getItems().get(i) instanceof ItemEmpty)) {
						ItemTile t = (ItemTile) selInv.getItems().get(i);
						
						if (t.getTileType().toString().equals(tiles.get(k).stringKey)) {
							if (t.getMaxMeta() == 0) {
								g.drawImage(tiles.get(k).images.get(0), selInv.getSlotsList().get(i).getX() + 2, selInv.getSlotsList().get(i).getY() - 34, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
							} else {
								g.drawImage(tiles.get(k).images.get(t.getMeta()), selInv.getSlotsList().get(i).getX() + 2, selInv.getSlotsList().get(i).getY() - 34, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
							}
						}
					}
				}
			}
			
			if (br.hand != null) {
				for (int k = 0; k < tiles.size(); k++) {
					if (br.hand.getTileType().toString().equals(tiles.get(k).stringKey)) {
						if (br.hand.getMaxMeta() == 0) {
							g.drawImage(tiles.get(k).images.get(0), MouseInput.getHudVec().getX(), MouseInput.getHudVec().getY(), Tile.TILE_SIZE, Tile.TILE_SIZE, null);
						} else {
							g.drawImage(tiles.get(k).images.get(br.hand.getMeta()), MouseInput.getHudVec().getX(), MouseInput.getHudVec().getY(), Tile.TILE_SIZE, Tile.TILE_SIZE, null);
						}
					}
				}
			}
		}
	}
}
