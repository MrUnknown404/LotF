package main.java.lotf.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.WordUtils;

import main.java.lotf.Main;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.init.InitItems;
import main.java.lotf.inventory.PlayerInventory.EnumSelectables;
import main.java.lotf.items.Item;
import main.java.lotf.items.rings.Ring;
import main.java.lotf.util.Console;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.MathH;
import main.java.lotf.world.World;

public class Hud {

	private static Font smallNumbers, lotfFont;
	
	private BufferedImage baseHud, barDecal, mapBlock, mapLocationMarker, selectedSlotItem, selectedSlotRing, selectedMap;
	private BufferedImage invHud0, invHud1, invHud2;
	private BufferedImage[] hearts = new BufferedImage[5];
	private Map<String, BufferedImage> items = new HashMap<String, BufferedImage>();
	private Map<String, BufferedImage> rings = new HashMap<String, BufferedImage>();
	private Map<EnumWorldType, BufferedImage> maps = new HashMap<EnumWorldType, BufferedImage>();
	
	public void getTextures() {
		Console.print(Console.WarningType.Info, "Starting texture registering...");
		
		baseHud = registerGUITexture("baseHud");
		invHud0 = registerGUITexture("inv_background_0");
		invHud1 = registerGUITexture("inv_background_1");
		invHud2 = registerGUITexture("inv_background_2");
		selectedSlotItem = registerGUITexture("selectedSlotItem");
		selectedSlotRing = registerGUITexture("selectedSlotRing");
		selectedMap = registerGUITexture("selectedMap");
		barDecal = registerGUITexture("magicBarDecal");
		mapBlock = registerGUITexture("mapBlock");
		mapLocationMarker = registerGUITexture("mapLocationMarker");
		
		hearts[0] = registerGUITexture("hearts/heart_0");
		hearts[1] = registerGUITexture("hearts/heart_1");
		hearts[2] = registerGUITexture("hearts/heart_2");
		hearts[3] = registerGUITexture("hearts/heart_3");
		hearts[4] = registerGUITexture("hearts/heart_4");
		
		for (EnumWorldType type : EnumWorldType.values()) {
			maps.put(type, registerGUITexture("maps/" + type.toString().toLowerCase() + "_map"));
		}
		
		for (int i = 0; i < InitItems.getItemsSize(); i++) {
			if (InitItems.getItem(i) != null) {
				items.put(InitItems.getItem(i).getName(), registerItemTexture(InitItems.getItem(i)));
			}
		}
		
		for (int i = 0; i < InitItems.getRingsSize(); i++) {
			if (InitItems.getRing(i) != null) {
				rings.put(InitItems.getRing(i).getName(), registerRingTexture(InitItems.getRing(i)));
			}
		}
		
		Console.print(Console.WarningType.Info, "Finished texture registering!");
	}
	
	public void getFonts() {
		smallNumbers = GetResource.getFont("small_numbers", 5);
		Console.print(Console.WarningType.TextureDebug, "Registered font for Hud : " + "fonts/small_numbers.ttf!");
		lotfFont = GetResource.getFont("lotf", 8);
		Console.print(Console.WarningType.TextureDebug, "Registered font for Hud : " + "fonts/lotf.ttf!");
	}
	
	public void render(Graphics2D g) {
		EntityPlayer p = Main.getMain().getWorldHandler().getPlayer();
		World w = Main.getMain().getWorldHandler().getPlayerWorld();
		
		if (p != null) {
			g.drawImage(baseHud, 0, 0, 256, 16, null);
			g.setColor(Color.BLACK);
			g.setFont(smallNumbers);
			
			g.drawString(setupNumberString(p.getMoney(), 6), 116, 8);
			g.drawString(setupNumberString(p.getArrows(), 2), 153, 8);
			g.drawString(setupNumberString(p.getBombs(), 2), 175, 8);
			g.drawString("" + p.getKeys(), 196, 8);
			
			//TODO draw bar
			g.drawImage(barDecal, 104, 11, 97, 3, null);
			
			for (int i = 0; i < p.getHearts().size(); i++) {
				if (i < 12) {
					g.drawImage(hearts[p.getHearts().get(i)], 1 + (i * 8), 1, 7, 7, null);
				} else {
					g.drawImage(hearts[p.getHearts().get(i)], 5 + (i * 8) - (12 * 8), 8, 7, 7, null);
				}
			}
			
			if (p.getInventory().isOpen()) {
				g.drawImage(maps.get(p.getWorldType()), 132, 20, 120, 120, null);
				
				int iLocation = 0;
				for (int i = 0; i < World.WORLD_SIZE.getBothMulti(); i++) {
					if (w.getRoom(i) != null) {
						if (!p.didExploredRoom(i)) {
							g.drawImage(mapBlock, 132 + i % World.WORLD_SIZE.getX() * 7, 20 + MathH.floor(i / World.WORLD_SIZE.getX()) * 7, 8, 8, null);
						} else if (p.getRoom().getRoomID() == i) {
							iLocation = i;
						}
					}
				}
				g.drawImage(mapLocationMarker, 132 + iLocation % World.WORLD_SIZE.getX() * 7, 20 + MathH.floor(iLocation / World.WORLD_SIZE.getX()) * 7, 8, 8, null);
				
				if (p.getInventory().getCurrentScreen() == 0) {
					g.drawImage(invHud0, 0, 16, 256, 128, null);
					
					if (p.getInventory().getSelectedThing() == EnumSelectables.NormalInventory) {
						g.drawImage(selectedSlotItem, 4 + p.getInventory().getSelectedSlot() % p.getInventory().getNormalInventory().getSizeX() * 18,
								19 + MathH.floor(p.getInventory().getSelectedSlot() / p.getInventory().getNormalInventory().getSizeX()) * 18, 18, 18, null);
					} else if (p.getInventory().getSelectedThing() == EnumSelectables.SwordInventory) {
						g.drawImage(selectedSlotItem, 104, 19 + p.getInventory().getSelectedSlot() * 18, 18, 18, null);
					}
					
					for (int i = 0; i < p.getInventory().getNormalInventory().getItemSize(); i++) {
						Item item = p.getInventory().getNormalInventory().getItem(i);
						
						if (item != null) {
							g.drawImage(items.get(item.getName()), 6 + i % p.getInventory().getNormalInventory().getSizeX() * 18,
									21 + MathH.floor(i / p.getInventory().getNormalInventory().getSizeX()) * 18, 14, 14, null);
						}
					}
					
					for (int i = 0; i < p.getInventory().getSwordInventory().getItemSize(); i++) {
						Item item = p.getInventory().getSwordInventory().getItem(i);
						
						if (item != null) {
							g.drawImage(items.get(item.getName()), 106, 21 + i * 18, 14, 14, null);
						}
					}
				} else if (p.getInventory().getCurrentScreen() == 1) {
					g.drawImage(invHud1, 0, 16, 256, 128, null);
					
					if (p.getInventory().getSelectedThing() == EnumSelectables.RingInventory) {
						g.drawImage(selectedSlotRing, 3 + p.getInventory().getSelectedSlot() * 20, 19, 20, 20, null);
					}
					
					if (p.getInventory().getSelectedThing() == EnumSelectables.PotionInventory) {
						g.drawImage(selectedSlotItem, 4 + p.getInventory().getSelectedSlot() * 20, 47, 18, 18, null);
					}
					
					if (p.getInventory().getSelectedThing() == EnumSelectables.SpecialInventory) {
						g.drawImage(selectedSlotItem, 4 + p.getInventory().getSelectedSlot() * 20, 70, 18, 18, null);
					}
					
					for (int i = 0; i < p.getInventory().getRingInventory().getSelectedRingSize(); i++) {
						Ring ring = p.getInventory().getRingInventory().getSelectedRing(i);
						
						if (ring != null) {
							g.drawImage(rings.get(ring.getName()), 5 + i * 20, 21, 16, 16, null);
						}
					}
					
					for (int i = 0; i < p.getInventory().getPotionInventory().getItemSize(); i++) {
						Item item = p.getInventory().getPotionInventory().getItem(i);
						
						if (item != null) {
							g.drawImage(items.get(item.getName()), 6 + i * 20, 49, 14, 14, null);
						}
					}
					
					int draw_special_items;
				} else if (p.getInventory().getCurrentScreen() == 2) {
					g.drawImage(invHud2, 0, 16, 256, 128, null);
					
					int draw_dungeon__item_screen;
				}
				
				int animate_the_description;
				Item itemForDesc = p.getInventory().getSelectedItem();
				if (itemForDesc != null) {
					g.setFont(lotfFont);
					drawStringMultiLine(g, WordUtils.capitalize(itemForDesc.getName()) + " : " + itemForDesc.getDescription(), 124, 2, 124);
				}
				
				if (p.getInventory().getSelectedThing() == EnumSelectables.Map) {
					g.drawImage(selectedMap, 130, 18, 124, 124, null);
				}
			}
		}
	}
	
	private void drawStringMultiLine(Graphics2D g, String str, int width, int x, int y) {
		FontMetrics m = g.getFontMetrics();
		if (m.stringWidth(str) < width) {
			g.drawString(str, x, y);
		} else {
			String[] words = str.split(" ");
			String currentLine = words[0];
			
			for (int i = 1; i < words.length; i++) {
				if (m.stringWidth(currentLine + words[i]) < width) {
					currentLine += " " + words[i];
				} else {
					g.drawString(currentLine, x, y);
					y += m.getHeight();
					currentLine = words[i];
				}
			}
			
			if (currentLine.trim().length() > 0) {
				g.drawString(currentLine, x, y);
			}
		}
	}
	
	private BufferedImage registerGUITexture(String name) {
		return registerTexture(GetResource.ResourceType.gui, name);
	}
	
	private BufferedImage registerItemTexture(Item item) {
		return registerTexture(GetResource.ResourceType.item, item.getName());
	}
	
	private BufferedImage registerRingTexture(Ring ring) {
		return registerTexture(GetResource.ResourceType.ring, ring.getName());
	}
	
	private BufferedImage registerTexture(GetResource.ResourceType type, String name) {
		BufferedImage img = GetResource.getTexture(type, name);
		
		if (img != null) {
			Console.print(Console.WarningType.TextureDebug, name + " was registered!");
			return img;
		} else {
			Console.print(Console.WarningType.TextureDebug, name + " was not registered!");
			return null;
		}
	}
	
	private String setupNumberString(int number, int max) {
		StringBuilder b = new StringBuilder();
		if (String.valueOf(number).length() < max) {
			for (int i = max - String.valueOf(number).length(); i > 0; i--) {
				b.append(0);
			}
		}
		
		return b.toString() + number;
	}
}
