package main.java.lotf.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import main.java.lotf.Main;

public class ConfigHandler {

	public static void loadConfigs(IConfigurable<?>... configs) {
		Gson g = Main.getMain().getGson();
		
		for (IConfigurable<?> config : configs) {
			File f = new File(Main.SAVE_LOCATION + config.getFileName() + ".json");
			
			if (!f.exists()) {
				Console.print(Console.WarningType.Warning, "Could not find config '" + config.getFileName() + "' (Creating now)");
				
				try {
					FileWriter fr = new FileWriter(f);
					
					g.toJson(config.getDefaultSave(), fr);
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try {
				FileReader fr = new FileReader(f);
				Object obj = null;
				
				try {
					obj = g.fromJson(fr, config.getType());
				} catch (JsonSyntaxException e) {
					obj = loadDefault(f, g, config);
				}
				
				if (obj == null) {
					obj = loadDefault(f, g, config);
				}
				
				config.load(obj);
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static Object loadDefault(File f, Gson g, IConfigurable<?> config) {
		Console.print(Console.WarningType.Warning, "Invalid config named : " + config.getFileName() + "!");
		
		try {
			FileWriter fw = new FileWriter(f);
			Object obj = config.getDefaultSave();
			g.toJson(obj, fw);
			fw.close();
			
			return obj;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return config.getDefaultSave();
	}
}
