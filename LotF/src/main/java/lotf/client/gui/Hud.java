package main.java.lotf.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.java.lotf.Main;
import main.java.lotf.entity.EntityPlayer;
import main.java.lotf.init.InitItems;
import main.java.lotf.inventory.PlayerInventory;
import main.java.lotf.items.ItemBombBag;
import main.java.lotf.items.ItemBow;
import main.java.lotf.items.ItemRCBombBag;
import main.java.lotf.items.ItemSpellBook;
import main.java.lotf.items.util.Ammo;
import main.java.lotf.items.util.ItemDungeon;
import main.java.lotf.util.Console;
import main.java.lotf.util.EnumDungeonType;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.ImageList;
import main.java.lotf.util.math.MathHelper;
import main.java.lotf.world.Map;
import main.java.lotf.world.World;

public class Hud {

	private Font font;
	
	private List<BufferedImage> hearts = new ArrayList<BufferedImage>(5);
	private List<BufferedImage> magicBars = new ArrayList<BufferedImage>(17);
	private List<BufferedImage> slots = new ArrayList<BufferedImage>(2);
	private List<BufferedImage> dungeonItems = new ArrayList<BufferedImage>(EnumDungeonType.values().length);
	private List<BufferedImage> compasses = new ArrayList<BufferedImage>(16);
	private List<BufferedImage> pages = new ArrayList<BufferedImage>(5);
	
	private List<ImageList> items = new ArrayList<ImageList>(InitItems.getItems().size());
	private List<ImageList> ammos = new ArrayList<ImageList>(Ammo.AmmoType.values().length);
	
	private BufferedImage rupee, X, key, map, slot, slotSel, missingItemSmall, missingItemBig, selSmall, blankMapIcon, overworldMap, underworldMap;
	
	public void loadTextures() {
		for (int i = 0; i < 5; i++) {
			hearts.add(GetResource.getTexture(GetResource.ResourceType.gui, "hearts/heart_" + i));
			Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/hearts/heart_" + i + ".png!");
		}
		
		for (int i = 0; i < 17; i++) {
			magicBars.add(GetResource.getTexture(GetResource.ResourceType.gui, "magicBars/magicBar_" + i));
			Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/magicBars/magicBar_" + i + ".png!");
		}
		
		for (int i = 0; i < 2; i++) {
			slots.add(GetResource.getTexture(GetResource.ResourceType.gui, "slots/slot_" + i));
			Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/slots/slot_" + i + ".png!");
		}
		
		for (int i = 0; i < 12; i++) {
			dungeonItems.add(GetResource.getTexture(GetResource.ResourceType.gui, "dungeonItems/dungeonItem_" + i));
			Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/dungeonItems/dungeonItem_" + i + ".png!");
		}
		
		for (int i = 0; i < 16; i++) {
			int c = new Random().nextInt(16);
			compasses.add(GetResource.getTexture(GetResource.ResourceType.gui, "compasses/compass_" + c));
			Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/compasses/compass_" + c + ".png!");
		}
		
		blankMapIcon = GetResource.getTexture(GetResource.ResourceType.gui, "maps/blankMapIcon");
		Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/mapss/blankMapIcon.png!");
		overworldMap = GetResource.getTexture(GetResource.ResourceType.gui, "maps/overworldMap");
		Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/maps/overworldMap.png!");
		underworldMap = GetResource.getTexture(GetResource.ResourceType.gui, "maps/underworldMap");
		Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/mapIcons/underworldMap.png!");
		
		rupee = GetResource.getTexture(GetResource.ResourceType.gui, "rupee");
		Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/rupee.png!");
		X = GetResource.getTexture(GetResource.ResourceType.gui, "x");
		Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/x.png!");
		key = GetResource.getTexture(GetResource.ResourceType.gui, "key");
		Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/key.png!");
		map = GetResource.getTexture(GetResource.ResourceType.gui, "map");
		Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/map.png!");
		slot = GetResource.getTexture(GetResource.ResourceType.gui, "slot");
		Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/slot.png!");
		slotSel = GetResource.getTexture(GetResource.ResourceType.gui, "slotselected");
		Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/slotselected.png!");
		missingItemSmall = GetResource.getTexture(GetResource.ResourceType.gui, "missingItemSmall");
		Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/missingItemSmall.png!");
		missingItemBig = GetResource.getTexture(GetResource.ResourceType.gui, "missingItemBig");
		Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/missingItemBig.png!");
		selSmall = GetResource.getTexture(GetResource.ResourceType.gui, "selSmall");
		Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/selSmall.png!");
		
		for (int i = 0; i < 5; i++) {
			pages.add(GetResource.getTexture(GetResource.ResourceType.item, "spellPages/spellPage_" + i));
			Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "item/spellPages/spellPage_" + i + ".png!");
		}
		
		for (int i = 0; i < Ammo.AmmoType.values().length; i++) {
			ammos.add(new ImageList());
			
			ammos.get(i).images.add(GetResource.getTexture(GetResource.ResourceType.item, "ammo/" + Ammo.AmmoType.getFromNumber(i).toString()));
			ammos.get(i).stringKey = Ammo.AmmoType.getFromNumber(i).toString();
			
			Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "item/ammo/" + Ammo.AmmoType.getFromNumber(i).toString() + ".png!");
		}
		
		firstLoop:
		for (int i = 0; i < InitItems.getItems().size(); i++) {
			if (i == 0) {
				ImageList iL = new ImageList();
				
				
				iL.images.add(GetResource.getTexture(GetResource.ResourceType.item, "missing"));
				iL.stringKey = "missing";
				
				items.add(iL);
				Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "item/missing.png!");
			} else {
				if (!(InitItems.getItems().get(i) instanceof ItemDungeon)) {
					for (int j = 0; j < items.size(); j++) {
						if (items.get(j).stringKey == InitItems.getItems().get(i).getName()) {
							items.get(j).images.add(GetResource.getTexture(GetResource.ResourceType.item, InitItems.getItems().get(i).getName() + "s/" + InitItems.getItems().get(i).getName() + "_" + InitItems.getItems().get(i).getMeta()));
							
							continue firstLoop;
						}
					}
					
					ImageList iL = new ImageList();
					
					if (InitItems.getItems().get(i).getMaxMeta() == 0) {
						iL.images.add(GetResource.getTexture(GetResource.ResourceType.item, InitItems.getItems().get(i).getName()));
						Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "item/" + InitItems.getItems().get(i).getName() + ".png!");
					} else {
						iL.images.add(GetResource.getTexture(GetResource.ResourceType.item, InitItems.getItems().get(i).getName() + "s/" + InitItems.getItems().get(i).getName() + "_" + InitItems.getItems().get(i).getMeta()));
						Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "item/" + InitItems.getItems().get(i).getName() + "s/" + InitItems.getItems().get(i).getName() + "_" + InitItems.getItems().get(i).getMeta() + ".png!");
					}
					
					iL.stringKey = InitItems.getItems().get(i).getName();
					items.add(iL);
				}
			}
		}
	}
	
	public void loadFonts() {
		InputStream i = GetResource.class.getResourceAsStream("/main/resources/lotf/assets/fonts/font1.ttf");
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, i).deriveFont(10f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Console.print(Console.WarningType.Texture, "Registered font for Hud : " + "fonts/font1.ttf!");
	}
	
	public void render(Graphics2D g) {
		EntityPlayer player = Main.getWorldHandler().getPlayer();
		PlayerInventory inv = player.getInventory();
		
		g.setFont(font);
		g.setColor(Color.LIGHT_GRAY);
		
		if (inv.isInventoryOpen) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 32, 448, 220);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(2, 34, 444, 216);
		}
		
		g.fillRect(0, 0, 448, 32);
		g.setColor(Color.BLACK);
		
		for (int i = 0; i < player.getHearts().getHearts().size(); i++) {
			int h = player.getHearts().getHearts().get(i);
			
			if (i >= 10) {
				g.drawImage(hearts.get(h), 2 * (i + 1) + i * 16 - 160, 16, 14, 14, null);
			} else {
				g.drawImage(hearts.get(h), 2 * (i + 1) + i * 14, 0, 14, 14, null);
			}
		}
		
		g.drawImage(rupee, 166, 3, 14, 14, null);
		g.drawImage(X, 182, 11, 6, 6, null);
		
		StringBuilder b = new StringBuilder();
		if (String.valueOf(player.getMoney()).length() < 5) {
			for (int i = 5 - String.valueOf(player.getMoney()).length(); i > 0; i--) {
				b.append(0);
			}
		}
		
		g.drawString(b.toString() + player.getMoney(), 165, 27);
		
		g.drawImage(magicBars.get(player.getMana()), 210, 19, 68, 10, null);
		
		g.drawImage(key, 214, 2, 10, 16, null);
		g.drawImage(X, 226, 10, 6, 6, null);
		g.drawString("" + player.getKeys(), 233, 14);
		
		g.drawImage(slots.get(1), 284, 0, 44, 32, null);
		if (!inv.getSelectedSword().equals(InitItems.EMPTY)) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).stringKey.equals(inv.getSelectedSword().getName())) {
					g.drawImage(items.get(i).images.get(inv.getSelectedSword().getMeta()), 290, 0, 32, 32, null);
				}
			}
		}
		
		g.drawImage(slots.get(0), 204 + 64 * 2, 0, 27 * 2, 16 * 2, null);
		if (!inv.getSelectedLeft().equals(InitItems.EMPTY)) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).stringKey.equals(inv.getSelectedLeft().getName())) {
					if (inv.getSelectedLeft().getUseAmmo()) {
						g.drawImage(items.get(i).images.get(0), 338, 0, 32, 32, null);
						
						int topCount = MathHelper.floor(inv.getSelectedLeft().getAmmo().getCount() / 10);
						
						g.drawString("" + topCount, 372, 13);
						g.drawString("" + (inv.getSelectedLeft().getAmmo().getCount() - (topCount * 10)), 372, 25);
					} else {
						g.drawImage(items.get(i).images.get(inv.getSelectedLeft().getMeta()), 343, 0, 32, 32, null);
					}
				}
			}
		}
		
		g.drawImage(slots.get(0), 204 + 93 * 2, 0, 27 * 2, 16 * 2, null);
		if (!inv.getSelectedRight().equals(InitItems.EMPTY)) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).stringKey.equals(inv.getSelectedRight().getName())) {
					if (inv.getSelectedRight().getUseAmmo()) {
						g.drawImage(items.get(i).images.get(0), 396, 0, 32, 32, null);
						
						int topCount = MathHelper.floor(inv.getSelectedRight().getAmmo().getCount() / 10);
						
						g.drawString("" + topCount, 430, 13);
						g.drawString("" + (inv.getSelectedRight().getAmmo().getCount() - (topCount * 10)), 430, 25);
					} else {
						g.drawImage(items.get(i).images.get(inv.getSelectedRight().getMeta()), 401, 0, 32, 32, null);
					}
				}
			}
		}
		
		if (inv.hasMap()) {
			g.drawImage(map, 244, 2, 14, 14, null);
		}
		
		if (inv.hasCompass()) {
			g.drawImage(compasses.get(0), 260, 2, 14, 14, null);
		}
		
		if (inv.isInventoryOpen) {
			//Mapping
			for (int i = 0; i < 12; i++) {
				if (i < 6) {
					if (player.getWorld().getDungeonType().fId == i) {
						g.drawImage(selSmall, 306, 52 + (i * 32), 18, 18, null);
						g.drawImage(selSmall, 358, 52 + (i * 32), 18, 18, null);
					}
					
					if (inv.hasMap(EnumDungeonType.getFromNumber(i))) {
						g.drawImage(map, 308, 54 + (i * 32), 14, 14, null);
					} else {
						g.drawImage(missingItemSmall, 308, 54 + (i * 32), 14, 14, null);
					}
					
					if (inv.hasCompass(EnumDungeonType.getFromNumber(i))) {
						g.drawImage(compasses.get(i), 360, 54 + (i * 32), 14, 14, null);
					} else {
						g.drawImage(missingItemSmall, 360, 54 + (i * 32), 14, 14, null);
					}
					
					if (inv.hasDungeonItem(EnumDungeonType.getFromNumber(i))) {
						g.drawImage(dungeonItems.get(i), 326, 46 + (i * 32), 30, 30, null);
					} else {
						g.drawImage(missingItemBig, 326, 46 + (i * 32), 30, 30, null);
					}
				} else {
					if (player.getWorld().getDungeonType().fId == i) {
						g.drawImage(selSmall, 374, 52 + ((i - 6) * 32), 18, 18, null);
						g.drawImage(selSmall, 426, 52 + ((i - 6) * 32), 18, 18, null);
					}
					
					if (inv.hasMap(EnumDungeonType.getFromNumber(i))) {
						g.drawImage(map, 376, 54 + ((i - 6) * 32), 14, 14, null);
					} else {
						g.drawImage(missingItemSmall, 376, 54 + ((i - 6) * 32), 14, 14, null);
					}
					
					if (inv.hasCompass(EnumDungeonType.getFromNumber(i))) {
						g.drawImage(compasses.get(i), 428, 54 + ((i - 6) * 32), 14, 14, null);
					} else {
						g.drawImage(missingItemSmall, 428, 54 + ((i - 6) * 32), 14, 14, null);
					}
					
					if (inv.hasDungeonItem(EnumDungeonType.getFromNumber(i))) {
						g.drawImage(dungeonItems.get(i), 394, 46 + ((i - 6) * 32), 30, 30, null);
					} else {
						g.drawImage(missingItemBig, 394, 46 + ((i - 6) * 32), 30, 30, null);
					}
				}
			}
			
			if (inv.getSelectedScreen() == 0) {
				for (int i = 0; i < inv.getItems().size(); i++) {
					if (inv.getSelectedInv() == 0 && inv.getSelectedSlot() == i) {
						g.drawImage(slotSel, inv.getSlotsList().get(i).getX(), inv.getSlotsList().get(i).getY(), 40, 40, null);
					} else {
						g.drawImage(slot, inv.getSlotsList().get(i).getX(), inv.getSlotsList().get(i).getY(), 40, 40, null);
					}
					
					for (int j = 0; j < items.size(); j++) {
						if (!inv.getItems().get(i).equals(InitItems.EMPTY)) {
							if (items.get(j).stringKey.equals(inv.getItems().get(i).getName())) {
								if (inv.getItems().get(i) instanceof ItemBow || inv.getItems().get(i) instanceof ItemRCBombBag || inv.getItems().get(i) instanceof ItemBombBag) {
									g.drawImage(items.get(j).images.get(0), inv.getSlotsList().get(i).getX() + 4, inv.getSlotsList().get(i).getY() + 4, 32, 32, null);
								} else {
									g.drawImage(items.get(j).images.get(inv.getItems().get(i).getMeta()), inv.getSlotsList().get(i).getX() + 4, inv.getSlotsList().get(i).getY() + 4, 32, 32, null);
								}
							}
						}
					}
				}
				
				for (int i = 0; i < inv.getSwordInv().getItems().size(); i++) {
					if (inv.getSelectedInv() == 1 && inv.getSelectedSlot() == i) {
						g.drawImage(slotSel, inv.getSwordInv().getSlotsList().get(i).getX() + 208, inv.getSwordInv().getSlotsList().get(i).getY(), 40, 40, null);
					} else {
						g.drawImage(slot, inv.getSwordInv().getSlotsList().get(i).getX() + 208, inv.getSwordInv().getSlotsList().get(i).getY(), 40, 40, null);
					}
					
					for (int j = 0; j < items.size(); j++) {
						if (!inv.getSwordInv().getItems().get(i).equals(InitItems.EMPTY)) {
							if (items.get(j).stringKey.equals(inv.getSwordInv().getItems().get(i).getName())) {
								g.drawImage(items.get(j).images.get(inv.getSwordInv().getItems().get(i).getMeta()), inv.getSwordInv().getSlotsList().get(i).getX() + 212, inv.getSwordInv().getSlotsList().get(i).getY() + 4, 32, 32, null);
							}
						}
					}
				}
				
				for (int i = 0; i < inv.getRingInv().getItems().size(); i++) {
					if (inv.getSelectedInv() == 2 && inv.getSelectedSlot() == i) {
						g.drawImage(slotSel, inv.getRingInv().getSlotsList().get(i).getX() + 256, inv.getRingInv().getSlotsList().get(i).getY(), 40, 40, null);
					} else {
						g.drawImage(slot, inv.getRingInv().getSlotsList().get(i).getX() + 256, inv.getRingInv().getSlotsList().get(i).getY(), 40, 40, null);
					}
					
					for (int j = 0; j < items.size(); j++) {
						if (!inv.getRingInv().getItems().get(i).equals(InitItems.EMPTY)) {
							if (items.get(j).stringKey.equals(inv.getRingInv().getItems().get(i).getName())) {
								g.drawImage(items.get(j).images.get(inv.getRingInv().getItems().get(i).getMeta()), inv.getRingInv().getSlotsList().get(i).getX() + 212, inv.getRingInv().getSlotsList().get(i).getY() + 4, 32, 32, null);
							}
						}
					}
				}
				
				//Spell book
				if (inv.isSelectingPage) {
					g.setColor(new Color(0, 0, 0, 225));
					g.fillRect(2, 34, 444, 216);
					
					for (int i = 0; i < 5; i++) {
						if (((ItemSpellBook) inv.findItem("spellBook", 0)).getSpellPageList().getSelectedPageInt() == i) {
							g.drawImage(slotSel, 108 + 48 * i, 122, 40, 40, null);
						} else {
							g.drawImage(slot, 108 + 48 * i, 122, 40, 40, null);
						}
						
						if (((ItemSpellBook) inv.findItem("spellBook", 0)).getSpellPageList().getHas().get(i)) {
							g.drawImage(pages.get(i), 112 + 48 * i, 126, 32, 32, null);
						}
					}
				}
			} else {
				//Map
				Map m;
				if (player.getWorld().getWorldType() != World.WorldType.inside) {
					m = player.getWorld().getMap();
				} else {
					m = Main.getWorldHandler().getWorlds().get(player.getRoom().getExitWorldID()).getMap();
				}
				
				if (m.getWorldType() == World.WorldType.overworld) {
					for (int i = 0; i < m.getRooms().size(); i++) {
						int size = World.WorldType.overworld.size;
						g.drawImage(overworldMap, 51, 38, 209, 209, null);
						if (!m.getRooms().get(i)) {
							g.drawImage(blankMapIcon, 52 + (i * 13) - ((i / size) * (size * 13)), 39 + ((i / size) * 13), 12, 12, null);
						}
					}
				} else if (m.getWorldType() == World.WorldType.underworld) {
					for (int i = 0; i < m.getRooms().size(); i++) {
						int size = World.WorldType.underworld.size;
						g.drawImage(underworldMap, 51, 38, 209, 209, null);
						if (!m.getRooms().get(i)) {
							g.drawImage(blankMapIcon, 52 + (i * 13) - ((i / size) * (size * 13)), 39 + ((i / size) * 13), 12, 12, null);
						}
					}
				} else if (m.getWorldType() == World.WorldType.dungeon) {
					int drawDungeonMapLater;
				}
			}
		}
	}
}
