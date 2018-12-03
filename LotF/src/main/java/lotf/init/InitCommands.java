package main.java.lotf.init;

import main.java.lotf.Main;
import main.java.lotf.commands.CommandCHelp;
import main.java.lotf.commands.CommandDamage;
import main.java.lotf.commands.CommandDebug;
import main.java.lotf.commands.CommandGive;
import main.java.lotf.commands.CommandHeal;
import main.java.lotf.commands.CommandHelp;
import main.java.lotf.commands.CommandNoclip;
import main.java.lotf.commands.CommandRefillAmmo;
import main.java.lotf.commands.CommandSetRoom;
import main.java.lotf.commands.CommandTeleport;
import main.java.lotf.commands.util.Command;
import main.java.lotf.util.Console;

public final class InitCommands {
	
	public static void registerAll() {
		registerCommand(new CommandHelp());
		registerCommand(new CommandCHelp());
		registerCommand(new CommandTeleport());
		registerCommand(new CommandSetRoom());
		registerCommand(new CommandHeal());
		registerCommand(new CommandDamage());
		registerCommand(new CommandGive());
		registerCommand(new CommandNoclip());
		registerCommand(new CommandRefillAmmo());
		
		registerCommand(new CommandDebug(), 99);
	}
	
	public static void registerCommand(Command cmd) {
		if (!Main.getCommandConsole().commands.isEmpty()) {
			for (int i = 0; i < Main.getCommandConsole().commands.size(); i++) {
				if (Main.getCommandConsole().commands.get(i).getStringID().equals(cmd.getStringID())) {
					Console.print(Console.WarningType.Error, "Command already registered with this ID : " + cmd.getStringID());
					return;
				}
			}
		}
		
		cmd.setID(Main.getCommandConsole().commands.size());
		Main.getCommandConsole().commands.add(cmd);
		
		Console.print(Console.WarningType.Register, "Registered command with IDs : \"" + cmd.getStringID() + "\" & " + cmd.getID() + "!");
	}
	
	public static void registerCommand(Command cmd, int id) {
		if (!Main.getCommandConsole().commands.isEmpty()) {
			if (Main.getCommandConsole().commands.size() >= id) {
				if (Main.getCommandConsole().commands.get(id) != null) {
					Console.print(Console.WarningType.Error, "Command already registered with this id : " + id);
					return;
				}
			}
			
			for (int i = 0; i < Main.getCommandConsole().commands.size(); i++) {
				if (Main.getCommandConsole().commands.get(i).getStringID().equals(cmd.getStringID())) {
					Console.print(Console.WarningType.Error, "Command already registered with this id : " + cmd.getStringID());
					return;
				}
			}
		}
		
		cmd.setID(id);
		Main.getCommandConsole().commands.add(cmd);
		
		Console.print(Console.WarningType.Register, "Registered command with IDs : \"" + cmd.getStringID() + "\" & " + cmd.getID() + "!");
	}
	
	public static Command get(String stringID) {
		for (int i = 0; i < Main.getCommandConsole().commands.size(); i++) {
			if (Main.getCommandConsole().commands.get(i).getStringID().equals(stringID)) {
				return Main.getCommandConsole().commands.get(i);
			}
		}
		
		Console.print(Console.WarningType.Error, "Cannot find " + stringID + "!");
		return null;
	}
	
	public static Command get(int id) {
		if (Main.getCommandConsole().commands.size() < id) {
			Console.print(Console.WarningType.Error, id + " is an invalid ID!");
			return null;
		}
		
		return Main.getCommandConsole().commands.get(id);
	}
}
