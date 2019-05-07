package main.java.lotf.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.java.lotf.Main;

public class GetResource {
	
	private static final String FILE_TYPE = ".png";
	
	public static BufferedImage getTexture(ResourceType location, String textureName) {
		InputStream f = null;;
		String loc = Main.getBaseLocationTextures();
		String newLoc = location.toString().toLowerCase();
		
		if (location == ResourceType.none) {
			newLoc = "";
		}
		
		if (GetResource.class.getResourceAsStream(loc + newLoc + "/" + textureName + FILE_TYPE) == null) {
			Console.print(Console.WarningType.Error, "Cannot find texture : " + loc + newLoc + "/" + textureName + FILE_TYPE);
		} else {
			f = GetResource.class.getResourceAsStream(loc + newLoc + "/" + textureName + FILE_TYPE);
		}
		BufferedImage i = null;
		
		if (f == null) {
			return null;
		}
		
		try {
			i = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public static BufferedImage getTexture(String textureName) {
		return getTexture(ResourceType.none, textureName);
	}
	
	public static Font getFont(String fontName) {
		InputStream i = null;
		Font font = null;
		String loc = "/main/resources/lotf/assets/fonts/";
		
		if (GetResource.class.getResourceAsStream(loc + fontName + ".ttf") == null) {
			Console.print(Console.WarningType.Error, "Cannot find font : " + loc + fontName + ".ttf");
		} else {
			i = GetResource.class.getResourceAsStream("/main/resources/lotf/assets/fonts/" + fontName + ".ttf");
		}
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, i).deriveFont(5f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return font;
	}
	
	public enum ResourceType {
		none,
		tile,
		entity,
		gui,
		item;
	}
}
