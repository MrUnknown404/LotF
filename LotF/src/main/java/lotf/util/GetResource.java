package main.java.lotf.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public final class GetResource {
	
	private static final String BASE_LOCATION = "/main/resources/lotf/assets/textures/";
	private static final String FILE_TYPE = ".png";
	
	public static BufferedImage getTexture(ResourceType location, String textureName) {
		InputStream f = null;;
		if (GetResource.class.getResourceAsStream(BASE_LOCATION + location.toString().toLowerCase() + "/" + textureName + FILE_TYPE) == null) {
			Console.print(Console.WarningType.Error, "Cannot find texture : " + BASE_LOCATION + location.toString().toLowerCase() + "/" + textureName + FILE_TYPE);
			f = GetResource.class.getResourceAsStream(BASE_LOCATION + "missing" + FILE_TYPE);
		} else {
			f = GetResource.class.getResourceAsStream(BASE_LOCATION + location.toString().toLowerCase() + "/" + textureName + FILE_TYPE);
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
