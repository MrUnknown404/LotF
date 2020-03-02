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

public class GetResource {
	public static final BufferedImage nil = getTexture("nil");
	
	private static final String IMAGE_TYPE = ".png";
	private static List<String> langKeys = new ArrayList<String>();
	
	public static BufferedImage getTexture(ResourceType location, String textureName) {
		InputStream f = null;
		String newLoc = location.toString().toLowerCase();
		
		if (location == ResourceType.none) {
			newLoc = "";
		}
		newLoc += "/";
		
		if (GetResource.class.getResourceAsStream(Main.TEXTURE_FOLDER_LOCATION + newLoc + textureName + IMAGE_TYPE) == null) {
			Console.print(Console.WarningType.Error, "Cannot find texture : '" + Main.TEXTURE_FOLDER_LOCATION + newLoc + textureName + IMAGE_TYPE + "'");
			return nil;
		}
		
		f = GetResource.class.getResourceAsStream(Main.TEXTURE_FOLDER_LOCATION + newLoc + textureName + IMAGE_TYPE);
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
	
	public static Font getFont(String fontName, float size) {
		InputStream i = null;
		Font font = null;
		
		if (GetResource.class.getResourceAsStream(Main.FONT_FOLDER_LOCATION + fontName + ".ttf") == null) {
			Console.print(Console.WarningType.Error, "Cannot find font : '" + Main.FONT_FOLDER_LOCATION + fontName + ".ttf'");
		} else {
			i = GetResource.class.getResourceAsStream(Main.FONT_FOLDER_LOCATION + fontName + ".ttf");
		}
		
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
		
		Console.print(WarningType.FatalError, "Could not find the lang key '" + langKey.getLangType() + "." + langKey.getKey() + "." + keyType +
				"\" in \"" + Locale.getDefault() + "'");
		return "nil";
	}
	
	public enum ResourceType {
		none,
		tile,
		entity,
		gui,
		item,
		ring,
		potion,
		collectible;
	}
}
