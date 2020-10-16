package main.java.lotf.client.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.java.lotf.util.GetResource;
import main.java.lotf.util.GetResource.ResourceType;
import main.java.ulibs.utils.Console;
import main.java.ulibs.utils.Console.WarningType;

public interface IRenderer {
	public void setup();
	public void render(Graphics2D g);
	public boolean isHud();
	
	public default BufferedImage getTexture(ResourceType type, String name) {
		BufferedImage i = GetResource.getTexture(type, name);
		
		if (i == GetResource.nil) {
			Console.print(WarningType.TextureDebug, "Unable to register '" + name + "' for " + getClass().getSimpleName());
		} else {
			Console.print(WarningType.TextureDebug, "Registered '" + name + "' for " + getClass().getSimpleName());
		}
		
		return i;
	}
}
