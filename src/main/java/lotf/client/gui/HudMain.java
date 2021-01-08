package main.java.lotf.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import main.java.lotf.Main;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.init.Items;
import main.java.lotf.items.potions.Potion;
import main.java.lotf.items.rings.Ring;
import main.java.lotf.items.util.Item;
import main.java.lotf.util.GetResource;
import main.java.ulibs.utils.math.MathH;

public class HudMain extends Hud {
	
	private static Font smallNumbers;
	
	private BufferedImage manaBar, baseHud;
	private BufferedImage[] hearts = new BufferedImage[5];
	
	private Map<String, BufferedImage> items = new HashMap<String, BufferedImage>();
	
	@Override
	public void setup() {
		smallNumbers = getFont("small_numbers", 5);
		
		baseHud = registerGUITexture("base_hud");
		manaBar = registerGUITexture("mana_bar");
		
		hearts[0] = registerGUITexture("hearts/heart_0");
		hearts[1] = registerGUITexture("hearts/heart_1");
		hearts[2] = registerGUITexture("hearts/heart_2");
		hearts[3] = registerGUITexture("hearts/heart_3");
		hearts[4] = registerGUITexture("hearts/heart_4");
		
		for (Item item : Items.getAll()) {
			if (!(item instanceof Ring) && !(item instanceof Potion)) {
				items.put(item.getKey(), registerItemTexture(item));
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		EntityPlayer p = Main.getMain().getWorldHandler().getPlayer();
		
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
		}
	}
	
	private BufferedImage registerItemTexture(Item item) {
		return registerTexture(item instanceof Ring ? GetResource.ResourceType.ring : GetResource.ResourceType.item, item.getKey());
	}
}
