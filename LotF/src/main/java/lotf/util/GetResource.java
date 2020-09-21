package main.java.lotf.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;

import com.google.gson.JsonIOException;

import main.java.lotf.Main;
import main.java.lotf.util.LangKey.LangKeyType;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.world.Room;
import main.java.lotfbuilder.MainBuilder;
import main.java.ucrypt.UCrypt;
import main.java.ulibs.utils.Console;
import main.java.ulibs.utils.Console.WarningType;

public class GetResource {
	public static final BufferedImage nil = getTexture(false, ResourceType.none, "nil");
	
	private static List<String> langKeys = new ArrayList<String>();
	
	public static BufferedImage getTexture(boolean isBuilder, ResourceType location, String textureName) {
		String newLoc = location == ResourceType.none ? "" : location.toString().toLowerCase() + "/";
		String loc = isBuilder ? MainBuilder.TEXTURE_FOLDER_LOCATION : Main.ASSETS_LOCATION + "textures/";
		
		if (GetResource.class.getResourceAsStream(loc + newLoc + textureName + ".png") == null) {
			Console.print(Console.WarningType.Error, "Cannot find texture : '" + loc + newLoc + textureName + ".png'");
			return nil;
		}
		
		try {
			return ImageIO.read(GetResource.class.getResourceAsStream(loc + newLoc + textureName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
			return nil;
		}
	}
	
	public static BufferedImage getTexture(ResourceType type, String textureName) {
		return getTexture(Main.isBuilder, type, textureName);
	}
	
	public static BufferedImage getTexture(String textureName) {
		return getTexture(Main.isBuilder, ResourceType.none, textureName);
	}
	
	public static Font getFont(String fontName, float size) {
		if (GetResource.class.getResourceAsStream(Main.ASSETS_LOCATION + "fonts/" + fontName + ".ttf") == null) {
			Console.print(Console.WarningType.Error, "Cannot find font : '" + Main.ASSETS_LOCATION + "fonts/" + fontName + ".ttf'");
			return null;
		}
		
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, GetResource.class.getResourceAsStream(Main.ASSETS_LOCATION + "fonts/" + fontName + ".ttf")).deriveFont(size);
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return font;
	}
	
	public static void getLangFile() {
		Locale l = Locale.getDefault();
		
		if (GetResource.class.getResourceAsStream(Main.ASSETS_LOCATION + "lang/" + l + ".lang") == null) {
			Console.print(WarningType.FatalError, "Could not find the lang file '" + l + "'");
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(GetResource.class.getResourceAsStream(Main.ASSETS_LOCATION + "lang/" + l + ".lang")));
		
		try {
			String st;
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
							s = "nil. please report error!";
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
	
	/** Does not call {@link Room#onCreate()}!*/
	public static Room getRoom(EnumWorldType worldType, int roomID) {
		if (GetResource.class.getResourceAsStream(Main.ASSETS_LOCATION + "rooms/" + worldType + "/room_" + roomID + ".lotfr") == null) {
			Console.print(Console.WarningType.Error, "Cannot find room : '" + Main.ASSETS_LOCATION + "rooms/" + worldType + "/room_" + roomID + ".lotfr'");
			return null;
		}
		
		try {
			Room r = Main.getMain().getGson()
					.fromJson(UCrypt.decode("room_" + roomID + ".lotfr", new String(
							GetResource.class.getResourceAsStream(Main.ASSETS_LOCATION + "rooms/" + worldType + "/room_" + roomID + ".lotfr").readAllBytes())),
							Room.class);
			
			return r;
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public enum ResourceType {
		none, tile, entity, gui, item, ring, potion, collectible;
	}
}
