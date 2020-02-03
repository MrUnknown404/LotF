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
import main.java.lotf.init.InitCollectibles;
import main.java.lotf.init.InitItems;
import main.java.lotf.inventory.PlayerInventory.EnumSelectables;
import main.java.lotf.items.potions.Potion;
import main.java.lotf.items.rings.Ring;
import main.java.lotf.items.util.Collectible;
import main.java.lotf.items.util.Item;
import main.java.lotf.util.Console;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.MathH;
import main.java.lotf.world.World;

public class Hud implements ITickable {
	
	private static Font smallNumbers, lotfFont;
	
	private BufferedImage mapBlock, mapLocationMarker, selectedSlotItem, selectedSlotRing, selectedMap, map, compass, manaBar;
	private BufferedImage baseHud, invHud0, invHud1, invHud2;
	private BufferedImage[] hearts = new BufferedImage[5];
	private Map<String, BufferedImage> items = new HashMap<String, BufferedImage>(), rings = new HashMap<String, BufferedImage>(),
			collectibles = new HashMap<String, BufferedImage>();
	private Map<EnumWorldType, BufferedImage> maps = new HashMap<EnumWorldType, BufferedImage>();
	
	public void getTextures() {
		Console.print(Console.WarningType.Info, "Starting texture registering...");
		
		baseHud = registerGUITexture("base_hud");
		invHud0 = registerGUITexture("inv_background_0");
		invHud1 = registerGUITexture("inv_background_1");
		invHud2 = registerGUITexture("inv_background_2");
		selectedSlotItem = registerGUITexture("selected_slot_item");
		selectedSlotRing = registerGUITexture("selected_slot_ring");
		mapBlock = registerGUITexture("map_block");
		selectedMap = registerGUITexture("map_selected_icon");
		mapLocationMarker = registerGUITexture("map_location_icon");
		map = registerGUITexture("map");
		compass = registerGUITexture("compass");
		manaBar = registerGUITexture("mana_bar");
		
		hearts[0] = registerGUITexture("hearts/heart_0");
		hearts[1] = registerGUITexture("hearts/heart_1");
		hearts[2] = registerGUITexture("hearts/heart_2");
		hearts[3] = registerGUITexture("hearts/heart_3");
		hearts[4] = registerGUITexture("hearts/heart_4");
		
		for (EnumWorldType type : EnumWorldType.values()) {
			maps.put(type, registerGUITexture("maps/" + type.toString().toLowerCase() + "_map"));
		}
		
		for (Item item : InitItems.ITEMS) {
			if (item instanceof Ring) {
				rings.put(item.getKey(), registerItemTexture(item));
			} else {
				items.put(item.getKey(), registerItemTexture(item));
			}
		}
		
		for (Collectible col : InitCollectibles.COLLECTIBLES) {
			collectibles.put(col.getKey(), registerCollectibleTexture(col));
		}
		
		Console.print(Console.WarningType.Info, "Finished texture registering!");
	}
	
	public void getFonts() {
		smallNumbers = GetResource.getFont("small_numbers", 5);
		Console.print(Console.WarningType.RegisterDebug, "Registered font for Hud : " + "fonts/small_numbers.ttf!");
		lotfFont = GetResource.getFont("lotf", 8);
		Console.print(Console.WarningType.RegisterDebug, "Registered font for Hud : " + "fonts/lotf.ttf!");
	}
	
	public void render(Graphics2D g) {
		EntityPlayer p = Main.getMain().getWorldHandler().getPlayer();
		World w = p.getWorld();
		
		if (p != null) {
			g.drawImage(baseHud, 0, 0, 256, 16, null);
			g.setColor(Color.BLACK);
			g.setFont(smallNumbers);
			
			g.drawString(setupNumberString(p.getMoney(), 6), 116, 8);
			g.drawString(setupNumberString(p.getArrows(), 2), 153, 8);
			g.drawString(setupNumberString(p.getBombs(), 2), 175, 8);
			g.drawString("" + p.getKeys(), 196, 8);
			
			if (p.getMana() > 0) {
				int wi = MathH.ceil(MathH.normalize(p.getMana(), 0, p.getMaxMana()) * 97);
				g.drawImage(manaBar.getSubimage(0, 0, wi, 3), 104, 11, wi, 3, null);
			}
			
			for (int i = 0; i < p.getHearts().size(); i++) {
				if (i < 12) {
					g.drawImage(hearts[p.getHearts().get(i)], 1 + (i * 8), 1, 7, 7, null);
				} else {
					g.drawImage(hearts[p.getHearts().get(i)], 5 + (i * 8) - (12 * 8), 8, 7, 7, null);
				}
			}
			
			if (p.getInventory().getLeftItem() != null) {
				g.drawImage(items.get(p.getInventory().getLeftItem().getKey()), 206, 1, 14, 14, null);
			}
			
			if (p.getInventory().getRightItem() != null) {
				g.drawImage(items.get(p.getInventory().getRightItem().getKey()), 223, 1, 14, 14, null);
			}
			
			if (p.getInventory().getEquipedSword() != null) {
				g.drawImage(items.get(p.getInventory().getEquipedSword().getKey()), 240, 1, 14, 14, null);
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
				g.drawImage(mapLocationMarker, 132 + iLocation % World.WORLD_SIZE.getX() * 7, 20 + MathH.floor(iLocation / World.WORLD_SIZE.getX()) * 7, 8, 8,
						null);
				
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
							g.drawImage(items.get(item.getKey()), 6 + i % p.getInventory().getNormalInventory().getSizeX() * 18,
									21 + MathH.floor(i / p.getInventory().getNormalInventory().getSizeX()) * 18, 14, 14, null);
						}
					}
					
					for (int i = 0; i < p.getInventory().getSwordInventory().getItemSize(); i++) {
						Item item = p.getInventory().getSwordInventory().getItem(i);
						
						if (item != null) {
							g.drawImage(items.get(item.getKey()), 106, 21 + i * 18, 14, 14, null);
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
					
					if (p.getInventory().getEquipedRing() != null) {
						for (int i = 0; i < p.getInventory().getRingInventory().getSelectedRingSize(); i++) {
							if (p.getInventory().getEquipedRing() == p.getInventory().getRingInventory().getSelectedRing(i)) {
								g.drawRect(3 + i * 20, 19, 20, 20); //TODO change to texture
								break;
							}
						}
					}
					
					for (int i = 0; i < p.getInventory().getRingInventory().getSelectedRingSize(); i++) {
						Ring ring = p.getInventory().getRingInventory().getSelectedRing(i);
						
						if (ring != null) {
							g.drawImage(rings.get(ring.getKey()), 5 + i * 20, 21, 16, 16, null);
						}
					}
					
					for (int i = 0; i < p.getInventory().getPotionInventory().getItemSize(); i++) {
						Potion potion = p.getInventory().getPotionInventory().getItem(i);
						
						if (potion != null) {
							g.drawImage(items.get(potion.getKey()), 6 + i * 20, 49, 14, 14, null);
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
					
					if (p.getInventory().getSelectedThing() == EnumSelectables.CollectiblesInventory) {
						g.drawImage(selectedSlotItem, 7 + p.getInventory().getSelectedSlot() % p.getInventory().getCollectiblesInventory().getSizeX() * 39,
								19 + MathH.floor(p.getInventory().getSelectedSlot() / p.getInventory().getCollectiblesInventory().getSizeX()) * 18, 18, 18, null);
					}
					
					for (int i = 0; i < p.getInventory().getCollectiblesInventory().getCollectibles().size(); i++) {
						Collectible col = p.getInventory().getCollectiblesInventory().getCollectibles().get(i);
						
						g.drawString(setupNumberString(col.getAmount(), 3), 29 + i % p.getInventory().getCollectiblesInventory().getSizeX() * 39,
								33 + MathH.floor(i / p.getInventory().getCollectiblesInventory().getSizeX()) * 18);
						if (col.has()) {
							g.drawImage(collectibles.get(col.getKey()), 9 + i % p.getInventory().getCollectiblesInventory().getSizeX() * 39,
									21 + MathH.floor(i / p.getInventory().getCollectiblesInventory().getSizeX()) * 18, 14, 14, null);
						}
					}
				} else if (p.getInventory().getCurrentScreen() == 3) {
					//TODO draw_dungeon_item_screen;
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
					Item itemForDesc = p.getInventory().getSelectedItem();
					
					if (itemForDesc != null) {
						if (itemForDesc.getKey().equalsIgnoreCase(buffer)) {
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
				Item itemForDesc = p.getInventory().getSelectedItem();
				if (itemForDesc != null) {
					desc = itemForDesc.getName() + " : " + itemForDesc.getDescription();
					buffer = itemForDesc.getKey();
				}
			} else {
				World w = p.getWorld();
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
	
	private BufferedImage registerItemTexture(Item item) {
		return registerTexture(item instanceof Ring ? GetResource.ResourceType.ring : GetResource.ResourceType.item, item.getKey());
	}
	
	private BufferedImage registerCollectibleTexture(Collectible item) {
		return registerTexture(GetResource.ResourceType.collectibles, item.getKey());
	}
	
	private BufferedImage registerTexture(GetResource.ResourceType type, String name) {
		BufferedImage img = GetResource.getTexture(type, name);
		
		if (img != null) {
			Console.print(Console.WarningType.TextureDebug, name + " was registered!");
			return img;
		}
		
		Console.print(Console.WarningType.TextureDebug, name + " was not registered!");
		return null;
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
