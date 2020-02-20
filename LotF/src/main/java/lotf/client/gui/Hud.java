package main.java.lotf.client.gui;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.java.lotf.util.Console;
import main.java.lotf.util.GetResource;

public abstract class Hud {
	public abstract void draw(Graphics2D g);
	public void setupFonts() {}
	protected void onSetupTextures() {}
	
	public final void setupTextures() {
		Console.print(Console.WarningType.Info, "Starting texture registering for " + getClass().getSimpleName() + "...");
		onSetupTextures();
		Console.print(Console.WarningType.Info, "Finished texture registering for " + getClass().getSimpleName() + "!");
	}
	
	protected Font getFont(String fontName, int size) {
		Console.print(Console.WarningType.RegisterDebug, "Registered font for " + getClass().getSimpleName() + " : " + "fonts/" + fontName + ".ttf!");
		return GetResource.getFont(fontName, size);
	}
	
	protected BufferedImage registerGUITexture(String name) {
		return registerTexture(GetResource.ResourceType.gui, name);
	}
	
	protected BufferedImage registerTexture(GetResource.ResourceType type, String name) {
		BufferedImage img = GetResource.getTexture(type, name);
		
		if (img != GetResource.nil) {
			Console.print(Console.WarningType.TextureDebug, "'" + name + "' was registered!");
		} else {
			Console.print(Console.WarningType.TextureDebug, "'" + name + "' was not registered!");
		}
		
		return img;
	}
	
	protected String setupNumberString(int number, int max) {
		StringBuilder b = new StringBuilder();
		if (String.valueOf(number).length() < max) {
			for (int i = max - String.valueOf(number).length(); i > 0; i--) {
				b.append(0);
			}
		}
		
		return b.toString() + number;
	}
}
