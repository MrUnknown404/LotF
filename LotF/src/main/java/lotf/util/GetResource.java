package main.java.lotf.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.java.lotf.Main;

public final class GetResource {
	
	private static final String FILE_TYPE = ".png";
	
	public static BufferedImage getTexture(ResourceType location, String textureName) {
		InputStream f = null;;
		String loc = Main.getBaseLocationTextures();
		
		if (GetResource.class.getResourceAsStream(loc + location.toString().toLowerCase() + "/" + textureName + FILE_TYPE) == null) {
			Console.print(Console.WarningType.Error, "Cannot find texture : " + loc + location.toString().toLowerCase() + "/" + textureName + FILE_TYPE);
			f = GetResource.class.getResourceAsStream(loc + "missing" + FILE_TYPE);
		} else {
			f = GetResource.class.getResourceAsStream(loc + location.toString().toLowerCase() + "/" + textureName + FILE_TYPE);
		}
		BufferedImage i = null;
		
		try {
			i = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public enum ResourceType {
		tile  (0),
		entity(1),
		gui   (2),
		item  (3);
		
		private final int fId;
		
		private ResourceType(int id) {
			fId = id;
		}
		
		public static ResourceType getFromNumber(int id) {
			for (ResourceType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
