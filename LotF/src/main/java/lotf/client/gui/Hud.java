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
import main.java.lotf.items.ItemSpellBook;
import main.java.lotf.items.util.Ammo;
import main.java.lotf.items.util.ItemDungeon;
import main.java.lotf.util.Console;
import main.java.lotf.util.EnumDungeonType;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.ImageList;

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
	
	@SuppressWarnings("unused") //guiMissing is currently unused
	private BufferedImage guiMissing, rupee, x, key, map, slot, slotSel, missingItemSmall, missingItemBig, selSmall;
	
	private int pM = 2, imgSize = 7 * pM, both = (imgSize + pM), hW = both * 10, nW = 38 + hW + (3 * pM);
	
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
		
		guiMissing = GetResource.getTexture(GetResource.ResourceType.gui, "missing");
		Console.print(Console.WarningType.Texture, "Registered missing texture for Gui : " + "gui/missing.png!");
		rupee = GetResource.getTexture(GetResource.ResourceType.gui, "rupee");
		Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "gui/rupee.png!");
		x = GetResource.getTexture(GetResource.ResourceType.gui, "x");
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
		
		int j = 0;
		for (int i = 0; i < InitItems.getItems().size(); i++) {
			if (i == 0) {
				items.add(new ImageList());
				
				items.get(j).images.add(GetResource.getTexture(GetResource.ResourceType.item, "missing"));
				items.get(j).stringKey = "missing";
				items.get(j).meta = 0;
				j++;
				
				Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "item/missing.png!");
			} else {
				if (!(InitItems.getItems().get(i) instanceof ItemDungeon)) {
					items.add(new ImageList());
					
					if (InitItems.getItems().get(i).getMaxMeta() == 0) {
						items.get(j).images.add(GetResource.getTexture(GetResource.ResourceType.item, InitItems.getItems().get(i).getName()));
					} else {
						items.get(j).images.add(GetResource.getTexture(GetResource.ResourceType.item, InitItems.getItems().get(i).getName() + "s/" + InitItems.getItems().get(i).getName() + "_" + InitItems.getItems().get(i).getMeta()));
					}
					
					items.get(j).stringKey = InitItems.getItems().get(i).getName();
					items.get(j).meta = InitItems.getItems().get(i).getMeta();
					j++;
					
					if (InitItems.getItems().get(i).getMaxMeta() == 0) {
						Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "item/" + InitItems.getItems().get(i).getName() + ".png!");
					} else {
						Console.print(Console.WarningType.Texture, "Registered texture for Hud : " + "item/" + InitItems.getItems().get(i).getName() + "s/" + InitItems.getItems().get(i).getName() + "_" + InitItems.getItems().get(i).getMeta() + ".png!");
					}
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
		EntityPlayer player = Main.getRoomHandler().getPlayer();
		PlayerInventory inv = player.getInventory();
		
		g.setFont(font);
		g.setColor(Color.LIGHT_GRAY);
		
		if (inv.isInventoryOpen) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 16 * pM, 224 * pM, 110 * pM);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(1 * pM, 17 * pM, 222 * pM, 108 * pM);
		}
		
		g.fillRect(0, 0, 224 * pM, 16 * pM);
		g.setColor(Color.BLACK);
		
		for (int i = 0; i < player.getHearts().getHearts().size(); i++) {
			int h = player.getHearts().getHearts().get(i);
			
			if (i >= 10) {
				g.drawImage(hearts.get(h), pM * (i + 1) + i * imgSize - both * 10, imgSize + pM, imgSize, imgSize, null);
			} else {
				g.drawImage(hearts.get(h), pM * (i + 1) + i * imgSize, 0 + pM, imgSize, imgSize, null);
			}
		}
		
		g.drawImage(rupee, hW + 3  * pM,     pM, imgSize, imgSize, null);
		g.drawImage(x,     hW + 11 * pM, 5 * pM, 3 * pM, 3 * pM, null);
		
		StringBuilder b = new StringBuilder();
		if (String.valueOf(player.getMoney()).length() < 5) {
			for (int i = 5 - String.valueOf(player.getMoney()).length(); i > 0; i--) {
				b.append(0);
			}
		}
		
		g.drawString(b.toString() + player.getMoney(), hW + 3 * pM - 1, 8 * pM + 10);
		
		g.drawImage(magicBars.get(player.getMana()), nW + 3 * pM, 9 * pM, 34 * pM, 5 * pM, null);
		
		g.drawImage(key, nW + 5  * pM, 0, 5 * pM, 8 * pM, null);
		g.drawImage(x,   nW + 11 * pM, 5    * pM, 3 * pM, 3 * pM, null);
		g.drawString("" + player.getKeys(), nW + 15 * pM - 1, 7 * pM);
		
		g.drawImage(slots.get(1), nW + 40 * pM, 0, 22 * pM, 16 * pM, null);
		if (!inv.getSelectedSword().equals(InitItems.EMPTY)) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).stringKey.equals(inv.getSelectedSword().getName())) {
					if (items.get(i).meta == inv.getSelectedSword().getMeta()) {
						g.drawImage(items.get(i).images.get(0), nW + 43 * pM, 0, 16 * pM, 16 * pM, null);
					}
				}
			}
		}
		
		g.drawImage(slots.get(0), nW + 64 * pM, 0, 27 * pM, 16 * pM, null);
		if (!inv.getSelectedLeft().equals(InitItems.EMPTY)) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).stringKey.equals(inv.getSelectedLeft().getName())) {
					if (items.get(i).meta == inv.getSelectedLeft().getMeta()) {
						if (inv.getSelectedLeft().getUseAmmo()) {
							g.drawImage(items.get(i).images.get(0), nW + 67 * pM, 0, 16 * pM, 16 * pM, null);
						} else {
							g.drawImage(items.get(i).images.get(0), nW + 70 * pM - 1, 0, 16 * pM, 16 * pM, null);
						}
					}
				}
			}
		}
		
		g.drawImage(slots.get(0), nW + 93 * pM, 0, 27 * pM, 16 * pM, null);
		if (!inv.getSelectedRight().equals(InitItems.EMPTY)) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).stringKey.equals(inv.getSelectedRight().getName())) {
					if (items.get(i).meta == inv.getSelectedRight().getMeta()) {
						if (inv.getSelectedRight().getUseAmmo()) {
							g.drawImage(items.get(i).images.get(0), nW + 96 * pM, 0, 16 * pM, 16 * pM, null);
						} else {
							g.drawImage(items.get(i).images.get(0), nW + 98 * pM + 1, 0, 16 * pM, 16 * pM, null);
						}
					}
				}
			}
		}
		
		if (inv.hasMap()) {
			g.drawImage(map, nW + 20 * pM, pM, imgSize, imgSize, null);
		}
		
		if (inv.hasCompass()) {
			g.drawImage(compasses.get(0), nW + 28 * pM, pM, imgSize, imgSize, null);
		}
		
		/*
		**  #INVENTORY#  //To-do : draw count
		*/
		
		if (inv.isInventoryOpen) {
			for (int i = 0; i < inv.getItems().size(); i++) {
				if (inv.getSelectedInv() == 0 && inv.getSelectedSlot() == i) {
					g.drawImage(slotSel, inv.getSlotsList().get(i).getX(), inv.getSlotsList().get(i).getY(), 20 * pM, 20 * pM, null);
				} else {
					g.drawImage(slot, inv.getSlotsList().get(i).getX(), inv.getSlotsList().get(i).getY(), 20 * pM, 20 * pM, null);
				}
				
				for (int j = 0; j < items.size(); j++) {
					if (!inv.getItems().get(i).equals(InitItems.EMPTY)) {
						if (items.get(j).stringKey.equals(inv.getItems().get(i).getName())) {
							if (items.get(j).meta == inv.getItems().get(i).getMeta()) {
								g.drawImage(items.get(j).images.get(0), inv.getSlotsList().get(i).getX() + 4, inv.getSlotsList().get(i).getY() + 4, 16 * pM, 16 * pM, null);
							}
						}
					}
				}
			}
			
			for (int i = 0; i < inv.getSwordInv().getItems().size(); i++) {
				if (inv.getSelectedInv() == 1 && inv.getSelectedSlot() == i) {
					g.drawImage(slotSel, inv.getSwordInv().getSlotsList().get(i).getX() + 208, inv.getSwordInv().getSlotsList().get(i).getY(), 20 * pM, 20 * pM, null);
				} else {
					g.drawImage(slot, inv.getSwordInv().getSlotsList().get(i).getX() + 208, inv.getSwordInv().getSlotsList().get(i).getY(), 20 * pM, 20 * pM, null);
				}
				
				for (int j = 0; j < items.size(); j++) {
					if (!inv.getSwordInv().getItems().get(i).equals(InitItems.EMPTY)) {
						if (items.get(j).stringKey.equals(inv.getSwordInv().getItems().get(i).getName())) {
							if (items.get(j).meta == inv.getSwordInv().getItems().get(i).getMeta()) {
								g.drawImage(items.get(j).images.get(0), inv.getSlotsList().get(i).getX() + 212, inv.getSlotsList().get(i).getY() + 4, 16 * pM, 16 * pM, null);
							}
						}
					}
				}
			}
			
			for (int i = 0; i < inv.getRingInv().getItems().size(); i++) {
				if (inv.getSelectedInv() == 2 && inv.getSelectedSlot() == i) {
					g.drawImage(slotSel, inv.getRingInv().getSlotsList().get(i).getX() + 256, inv.getRingInv().getSlotsList().get(i).getY(), 20 * pM, 20 * pM, null);
				} else {
					g.drawImage(slot, inv.getRingInv().getSlotsList().get(i).getX() + 256, inv.getRingInv().getSlotsList().get(i).getY(), 20 * pM, 20 * pM, null);
				}
				
				for (int j = 0; j < items.size(); j++) {
					if (!inv.getRingInv().getItems().get(i).equals(InitItems.EMPTY)) {
						if (items.get(j).stringKey.equals(inv.getRingInv().getItems().get(i).getName())) {
							if (items.get(j).meta == inv.getRingInv().getItems().get(i).getMeta()) {
								g.drawImage(items.get(j).images.get(0), inv.getSlotsList().get(i).getX() + 212, inv.getSlotsList().get(i).getY() + 4, 16 * pM, 16 * pM, null);
							}
						}
					}
				}
			}
			
			//Mapping
			for (int i = 0; i < 12; i++) {
				if (i < 6) {
					if (player.getDungeon().fId == i) {
						g.drawImage(selSmall, 153 * pM, (26 * pM) + (i * (16 * pM)), 9 * pM, 9 * pM, null);
						g.drawImage(selSmall, 179 * pM, (26 * pM) + (i * (16 * pM)), 9 * pM, 9 * pM, null);
					}
					
					if (inv.hasMap(EnumDungeonType.getFromNumber(i))) {
						g.drawImage(map, 154 * pM, (27 * pM) + (i * (16 * pM)), imgSize, imgSize, null);
					} else {
						g.drawImage(missingItemSmall, 154 * pM, (27 * pM) + (i * (16 * pM)), imgSize, imgSize, null);
					}
					
					if (inv.hasCompass(EnumDungeonType.getFromNumber(i))) {
						g.drawImage(compasses.get(i), 180 * pM, (27 * pM) + (i * (16 * pM)), imgSize, imgSize, null);
					} else {
						g.drawImage(missingItemSmall, 180 * pM, (27 * pM) + (i * (16 * pM)), imgSize, imgSize, null);
					}
					
					if (inv.hasDungeonItem(EnumDungeonType.getFromNumber(i))) {
						g.drawImage(dungeonItems.get(i), 163 * pM, (23 * pM) + (i * (16 * pM)), 15 * pM, 15 * pM, null);
					} else {
						g.drawImage(missingItemBig, 163 * pM, (23 * pM) + (i * (16 * pM)), 15 * pM, 15 * pM, null);
					}
				} else {
					if (player.getDungeon().fId == i) {
						g.drawImage(selSmall, 187 * pM, (26 * pM) + ((i - 6) * (16 * pM)), 9 * pM, 9 * pM, null);
						g.drawImage(selSmall, 213 * pM, (26 * pM) + ((i - 6) * (16 * pM)), 9 * pM, 9 * pM, null);
					}
					
					if (inv.hasMap(EnumDungeonType.getFromNumber(i))) {
						g.drawImage(map, 188 * pM, (27 * pM) + ((i - 6) * (16 * pM)), imgSize, imgSize, null);
					} else {
						g.drawImage(missingItemSmall, 188 * pM, (27 * pM) + ((i - 6) * (16 * pM)), imgSize, imgSize, null);
					}
					
					if (inv.hasCompass(EnumDungeonType.getFromNumber(i))) {
						g.drawImage(compasses.get(i), 214 * pM, (27 * pM) + ((i - 6) * (16 * pM)), imgSize, imgSize, null);
					} else {
						g.drawImage(missingItemSmall, 214 * pM, (27 * pM) + ((i - 6) * (16 * pM)), imgSize, imgSize, null);
					}
					
					if (inv.hasDungeonItem(EnumDungeonType.getFromNumber(i))) {
						g.drawImage(dungeonItems.get(i), 197 * pM, (23 * pM) + ((i - 6) * (16 * pM)), 15 * pM, 15 * pM, null);
					} else {
						g.drawImage(missingItemBig, 197 * pM, (23 * pM) + ((i - 6) * (16 * pM)), 15 * pM, 15 * pM, null);
					}
				}
			}
			
			//Spell book
			if (inv.isSelectingPage) {
				g.setColor(new Color(0, 0, 0, 225));
				g.fillRect(1 * pM, 17 * pM, 222 * pM, 108 * pM);
				
				for (int i = 0; i < 5; i++) {
					if (((ItemSpellBook) inv.findItem("spellBook", 0)).getSpellPageList().getSelectedPageInt() == i) {
						g.drawImage(slotSel, 108 + 48 * i, 122, 20 * pM, 20 * pM, null);
					} else {
						g.drawImage(slot, 108 + 48 * i, 122, 20 * pM, 20 * pM, null);
					}
					
					if (((ItemSpellBook) inv.findItem("spellBook", 0)).getSpellPageList().getHas().get(i)) {
						g.drawImage(pages.get(i), 112 + 48 * i, 126, 16 * pM, 16 * pM, null);
					}
				}
			}
		}
	}
}
