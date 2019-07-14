package main.java.lotf.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import main.java.lotf.Main;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.init.InitItems;
import main.java.lotf.inventory.PlayerInventory.EnumSelectables;
import main.java.lotf.items.rings.Ring;
import main.java.lotf.items.util.CollectableInfo;
import main.java.lotf.items.util.ItemBase;
import main.java.lotf.items.util.ItemInfo;
import main.java.lotf.util.Console;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.MathH;
import main.java.lotf.world.World;

public class Hud implements ITickable {

	private static Font smallNumbers, lotfFont;
	
	private BufferedImage baseHud, barDecal, mapBlock, mapLocationMarker, selectedSlotItem, selectedSlotRing, selectedMap, map, compass;
	private BufferedImage invHud0, invHud1, invHud2;
	private BufferedImage[] hearts = new BufferedImage[5];
	private Map<String, BufferedImage> items = new HashMap<String, BufferedImage>(), rings = new HashMap<String, BufferedImage>(), collectables = new HashMap<String, BufferedImage>();
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
		map = registerGUITexture("map");
		compass = registerGUITexture("compass");
		
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
		
		for (ItemInfo info : ItemInfo.values()) {
			if (info.toString().startsWith("collectable_")) {
				collectables.put(info.toString(), registerCollectableTexture(info));
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
			
			if (p.getInventory().getLeftItem() != null) {
				g.drawImage(items.get(p.getInventory().getLeftItem().getName()), 206, 1, 14, 14, null);
			}
			
			if (p.getInventory().getRightItem() != null) {
				g.drawImage(items.get(p.getInventory().getRightItem().getName()), 223, 1, 14, 14, null);
			}
			
			if (p.getInventory().getSelectedSword() != null) {
				g.drawImage(items.get(p.getInventory().getSelectedSword().getName()), 240, 1, 14, 14, null);
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
						ItemBase item = p.getInventory().getNormalInventory().getItem(i);
						
						if (item != null) {
							g.drawImage(items.get(item.getName()), 6 + i % p.getInventory().getNormalInventory().getSizeX() * 18,
									21 + MathH.floor(i / p.getInventory().getNormalInventory().getSizeX()) * 18, 14, 14, null);
						}
					}
					
					for (int i = 0; i < p.getInventory().getSwordInventory().getItemSize(); i++) {
						ItemBase item = p.getInventory().getSwordInventory().getItem(i);
						
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
						ItemBase item = p.getInventory().getPotionInventory().getItem(i);
						
						if (item != null) {
							g.drawImage(items.get(item.getName()), 6 + i * 20, 49, 14, 14, null);
						}
					}
					
					Map<EnumWorldType, Boolean> compassMap = p.getCompassMap(), mapMap = p.getMapMap();
					
					for (int i = 3; i < EnumWorldType.values().length; i++) {
						EnumWorldType type = EnumWorldType.values()[i];
						
						int j = i - 3;
						
						if (compassMap.containsKey(type)) {
							if (j <= 4) {
								if (compassMap.get(type)) {
									g.drawImage(compass, 5 + j * 10, 95, 7, 7, null);
								}
							} else if (j >= 5 && j <= 6) {
								if (compassMap.get(type)) {
									g.drawImage(compass, 10 + j * 9, 95, 7, 7, null);
								}
							} else {
								if (compassMap.get(type)) {
									g.drawImage(compass, 4 + j * 10, 95, 7, 7, null);
								}
							}
						}
						
						if (mapMap.containsKey(type)) {
							if (j <= 4) {
								if (mapMap.get(type)) {
									g.drawImage(map, 5 + j * 10, 103, 7, 7, null);
								}
							} else if (j >= 5 && j <= 6) {
								if (mapMap.get(type)) {
									g.drawImage(map, 10 + j * 9, 103, 7, 7, null);
								}
							} else {
								if (mapMap.get(type)) {
									g.drawImage(map, 4 + j * 10, 103, 7, 7, null);
								}
							}
						}
					}
				} else if (p.getInventory().getCurrentScreen() == 2) {
					g.drawImage(invHud2, 0, 16, 256, 128, null);
					
					if (p.getInventory().getSelectedThing() == EnumSelectables.CollectablesInventory) {
						g.drawImage(selectedSlotItem, 7 + p.getInventory().getSelectedSlot() % p.getInventory().getCollectablesInventory().getSizeX() * 39,
								19 + MathH.floor(p.getInventory().getSelectedSlot() / p.getInventory().getCollectablesInventory().getSizeX()) * 18, 18, 18, null);
					}
					
					for (int i = 0; i < p.getInventory().getCollectablesInventory().getCollectables().size(); i++) {
						CollectableInfo col = p.getInventory().getCollectablesInventory().getCollectables().get(i);
						
						g.drawString(setupNumberString(col.getAmount(), 3), 29 + i % p.getInventory().getCollectablesInventory().getSizeX() * 39,
								33 + MathH.floor(i / p.getInventory().getCollectablesInventory().getSizeX()) * 18);
						if (col.has()) {
							g.drawImage(collectables.get(col.getInfo().toString()), 9 + i % p.getInventory().getCollectablesInventory().getSizeX() * 41,
									21 + MathH.floor(i / p.getInventory().getCollectablesInventory().getSizeX()) * 18, 14, 14, null);
						}
					}
				} else if (p.getInventory().getCurrentScreen() == 3) {
					int draw_dungeon_item_screen;
				}
				
				String desc = "";
				
				if (p.getInventory().getSelectedThing() == EnumSelectables.Map) {
					g.drawImage(selectedMap, 132 + p.getInventory().getSelectedSlot() % World.WORLD_SIZE.getX() * 7,
							20 + MathH.floor(p.getInventory().getSelectedSlot() / World.WORLD_SIZE.getX()) * 7, 8, 8, null);
					
					if (w.getRoom(p.getInventory().getSelectedSlot()) != null && w.getRoom(p.getInventory().getSelectedSlot()).getDescription() != null &&
							!w.getRoom(p.getInventory().getSelectedSlot()).getDescription().isEmpty()) {
						desc = w.getRoom(p.getInventory().getSelectedSlot()).getDescription();
					}
				} else {
					ItemBase itemForDesc = p.getInventory().getSelectedItem();
					
					if (itemForDesc != null) {
						if (itemForDesc.getName().equalsIgnoreCase(buffer)) {
							desc = itemForDesc.getName() + " : " + itemForDesc.getDescription();
						} else {
							forceReset = true;
						}
						
						if (itemForDesc.getName().equalsIgnoreCase(buffer)) {
							g.setFont(lotfFont);
							drawStringMultiLine(g, (itemForDesc.getName() + " : " + itemForDesc.getDescription()).substring(0, descCharacter), 124, 2, 124);
						} else {
							forceReset = true;
						}
					}
				}
				
				if (!desc.isEmpty()) {
					g.setFont(lotfFont);
					drawStringMultiLine(g, desc.substring(0, descCharacter), 124, 2, 124);
				}
			}
		}
	}
	
	private final int maxDescTick = 1;
	private int descTick, descCharacter;
	private String buffer;
	private boolean forceReset = false;
	
	@Override
	public void tick() {
		EntityPlayer p = Main.getMain().getWorldHandler().getPlayer();
		if (p != null) {
			String desc = "";
			if (p.getInventory().getSelectedThing() != EnumSelectables.Map) {
				ItemBase itemForDesc = p.getInventory().getSelectedItem();
				if (itemForDesc != null) {
					desc = itemForDesc.getName() + " : " + itemForDesc.getDescription();
					buffer = itemForDesc.getName();
				}
			} else {
				World w = Main.getMain().getWorldHandler().getPlayerWorld();
				if (w.getRoom(p.getInventory().getSelectedSlot()) != null && w.getRoom(p.getInventory().getSelectedSlot()).getDescription() != null &&
						!w.getRoom(p.getInventory().getSelectedSlot()).getDescription().isEmpty()) {
					desc = w.getRoom(p.getInventory().getSelectedSlot()).getDescription();
					buffer = desc;
				}
			}
			
			if (forceReset) {
				forceReset = false;
				descCharacter = 0;
			} else {
				if (!desc.isEmpty()) {
					if (descTick == 0) {
						descTick = maxDescTick;
						
						if (descCharacter < desc.length()) {
							while (desc.charAt(descCharacter) == ' ') {
								descCharacter++;
							}
							
							descCharacter++;
						}
					} else {
						descTick--;
					}
				} else {
					descCharacter = 0;
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
	
	private BufferedImage registerItemTexture(ItemBase item) {
		return registerTexture(GetResource.ResourceType.item, item.getInfo().toString().replaceAll("item_", "").replaceAll("sword_", ""));
	}
	
	private BufferedImage registerCollectableTexture(ItemInfo item) {
		return registerTexture(GetResource.ResourceType.item, "collectables/" + item.toString().replaceAll("collectable_", ""));
	}
	
	private BufferedImage registerRingTexture(Ring ring) {
		return registerTexture(GetResource.ResourceType.ring, ring.getInfo().toString().replaceAll("ring_", ""));
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
