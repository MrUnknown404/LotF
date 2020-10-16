package main.java.lotf.client.gui;

import java.awt.Font;
import java.awt.image.BufferedImage;

import main.java.lotf.client.renderer.IRenderer;
import main.java.lotf.util.GetResource;
import main.java.ulibs.utils.Console;

public abstract class Hud implements IRenderer {
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
	
	@Override
	public boolean isHud() {
		return true;
	}
}
