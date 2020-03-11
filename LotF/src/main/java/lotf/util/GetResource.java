package main.java.lotf.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;

import main.java.lotf.Main;
import main.java.lotf.util.Console.WarningType;
import main.java.lotf.util.LangKey.LangKeyType;
import main.java.lotfbuilder.MainBuilder;

public class GetResource {
	public static final BufferedImage nil = getTexture(false, ResourceType.none, "nil");
	
	private static final String IMAGE_TYPE = ".png";
	private static List<String> langKeys = new ArrayList<String>();
	
	public static BufferedImage getTexture(boolean isBuilder, ResourceType location, String textureName) {
		String newLoc = location == ResourceType.none ? "" : location.toString().toLowerCase() + "/";
		String loc = isBuilder ? MainBuilder.TEXTURE_FOLDER_LOCATION : Main.TEXTURE_FOLDER_LOCATION;
		
		if (GetResource.class.getResourceAsStream(loc + newLoc + textureName + IMAGE_TYPE) == null) {
			Console.print(Console.WarningType.Error, "Cannot find texture : '" + loc + newLoc + textureName + IMAGE_TYPE + "'");
			return nil;
		}
		
		InputStream f = GetResource.class.getResourceAsStream(loc + newLoc + textureName + IMAGE_TYPE);
		
		if (f == null) {
			return null;
		}
		
		BufferedImage i = null;
		try {
			i = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return i;
	}
	
	public static BufferedImage getTexture(ResourceType type, String textureName) {
		return getTexture(Main.isBuilder, type, textureName);
	}
	
	public static BufferedImage getTexture(String textureName) {
		return getTexture(Main.isBuilder, ResourceType.none, textureName);
	}
	
	public static Font getFont(String fontName, float size) {
		InputStream i = null;
		
		if (GetResource.class.getResourceAsStream(Main.FONT_FOLDER_LOCATION + fontName + ".ttf") == null) {
			Console.print(Console.WarningType.Error, "Cannot find font : '" + Main.FONT_FOLDER_LOCATION + fontName + ".ttf'");
		} else {
			i = GetResource.class.getResourceAsStream(Main.FONT_FOLDER_LOCATION + fontName + ".ttf");
		}
		
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, i).deriveFont(size);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return font;
	}
	
	public static void getLangFile() {
		Locale l = Locale.getDefault();
		
		if (GetResource.class.getResourceAsStream(Main.LANG_FOLDER_LOCATION + l + ".lang") == null) {
			Console.print(WarningType.FatalError, "Could not find the lang file '" + l + "'");
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(GetResource.class.getResourceAsStream(Main.LANG_FOLDER_LOCATION + l + ".lang")));
		
		String st;
		try {
			while ((st = br.readLine()) != null) {
				if (!st.startsWith("#")) {
					langKeys.add(st);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getStringFromLangKey(LangKey langKey, LangKeyType keyType) {
		for (String s : langKeys) {
			if (s.startsWith(langKey.getLangType().toString()) && s.charAt(langKey.getLangType().toString().length()) == '.') {
				s = s.substring(langKey.getLangType().toString().length() + 1);
				
				if (s.startsWith(langKey.getKey()) && s.charAt(langKey.getKey().length()) == '.') {
					s = s.substring(langKey.getKey().length() + 1);
					
					if (s.startsWith(keyType.toString()) && s.charAt(keyType.toString().length()) == '=') {
						s = s.substring(keyType.toString().length() + 1);
						if (s.isEmpty()) {
							s = "nil";
						}
						
						return s;
					}
				}
			}
		}
		
		Console.print(WarningType.FatalError,
				"Could not find the lang key '" + langKey.getLangType() + "." + langKey.getKey() + "." + keyType + "\" in \"" + Locale.getDefault() + "'");
		return "nil";
	}
	
	public enum ResourceType {
		none, tile, entity, gui, item, ring, potion, collectible;
	}
}
