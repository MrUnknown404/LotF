package main.java.lotf.init;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.entity.Entity;
import main.java.lotf.entity.EntityPlayer;
import main.java.lotf.util.Console;

public final class InitEntities {
	
	private static List<Entity> regEntity = new ArrayList<Entity>();
	
	public static void registerAll() {
		registerEntity(new EntityPlayer(2, 2, true));
	}
	
	public static void registerEntity(Entity entity) {
		if (!regEntity.isEmpty()) {
			for (int i = 0; i < regEntity.size(); i++) {
				if (regEntity.get(i).getStringID().equals(entity.getStringID())) {
					Console.print(Console.WarningType.Error, "Entity already registered with this ID : " + entity.getStringID());
					return;
				}
			}
		}
		
		regEntity.add(entity);
		
		Console.print(Console.WarningType.Register, "Registered entity with IDs : \"" + entity.getStringID() + "\"!");
	}
	
	public static Entity get(String stringID) {
		for (int i = 0; i < regEntity.size(); i++) {
			if (regEntity.get(i).getStringID().equals(stringID)) {
				return regEntity.get(i);
			}
		}
		
		Console.print(Console.WarningType.Error, "Cannot find " + stringID + "!");
		return null;
	}
	
	public static Entity get(int id) {
		if (regEntity.size() < id) {
			Console.print(Console.WarningType.Error, id + " is an invalid ID!");
			return null;
		}
		
		return regEntity.get(id);
	}
	
	public static List<Entity> getEntities() {
		return regEntity;
	}
}
