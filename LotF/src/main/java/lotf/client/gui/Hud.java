package main.java.lotf.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.java.lotf.Main;
import main.java.lotf.entity.EntityPlayer;
import main.java.lotf.util.Console;
import main.java.lotf.util.GetResource;

public class Hud {

	private static Font font;
	
	private BufferedImage baseHud, smallX, money, arrow, bomb, key;
	private BufferedImage[] hearts = new BufferedImage[4];
	
	public void getTextures() {
		Console.print(Console.WarningType.Info, "Starting texture registering...");
		
		baseHud = registerGUITexture("baseHud");
		smallX = registerGUITexture("x");
		money = registerGUITexture("money");
		arrow = registerGUITexture("arrow");
		bomb = registerGUITexture("bomb");
		key = registerGUITexture("key");
		
		hearts[0] = registerGUITexture("hearts/heart_0");
		hearts[1] = registerGUITexture("hearts/heart_1");
		hearts[2] = registerGUITexture("hearts/heart_2");
		hearts[3] = registerGUITexture("hearts/heart_3");
		
		Console.print(Console.WarningType.Info, "Finished texture registering!");
	}
	
	public void getFonts() {
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, GetResource.class.getResourceAsStream("/main/resources/lotf/assets/fonts/font1.ttf")).deriveFont(5f);
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
			
			Console.print(Console.WarningType.TextureDebug, "Registered font for Hud : " + "fonts/font1.ttf!");
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private BufferedImage registerGUITexture(String name) {
		BufferedImage img = GetResource.getTexture(GetResource.ResourceType.gui, name);
		
		if (img != null) {
			Console.print(Console.WarningType.TextureDebug, name + " was registered!");
			return img;
		} else {
			Console.print(Console.WarningType.TextureDebug, name + " was not registered!");
			return null;
		}
	}
	
	public void render(Graphics2D g) {
		EntityPlayer p = Main.getMain().getWorld().getPlayer();
		
		if (p != null) {
			g.drawImage(baseHud, 0, 0, 256, 16, null);
			g.setColor(Color.BLACK);
			g.setFont(font);
			
			g.drawImage(money, 105, 2, 7, 7, null);
			g.drawImage(smallX, 113, 6, 3, 3, null);
			g.drawString(setupNumberString(p.getMoney(), 6), 116, 8);
			
			g.drawImage(arrow, 142, 2, 7, 7, null);
			g.drawImage(smallX, 150, 6, 3, 3, null);
			g.drawString(setupNumberString(p.getArrows(), 2), 153, 8);
			
			g.drawImage(bomb, 165, 1, 6, 8, null);
			g.drawImage(smallX, 172, 6, 3, 3, null);
			g.drawString(setupNumberString(p.getBombs(), 2), 175, 8);
			
			g.drawImage(key, 187, 1, 5, 8, null);
			g.drawImage(smallX, 193, 6, 3, 3, null);
			g.drawString("0", 196, 8); //TODO update with proper key count
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
