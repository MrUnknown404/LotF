package main.java.lotf.commands.util;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.commands.CommandDamage;
import main.java.lotf.commands.CommandDebug;
import main.java.lotf.commands.CommandHeal;
import main.java.lotf.commands.CommandHelp;
import main.java.lotf.commands.CommandSetArrows;
import main.java.lotf.commands.CommandSetBombs;
import main.java.lotf.commands.CommandSetMoney;

public class InitCommands {
	private static List<Command> commands = new ArrayList<Command>();
	
	public static void registerAll() {
		registerCommand(new CommandHelp());
		registerCommand(new CommandSetMoney());
		registerCommand(new CommandSetArrows());
		registerCommand(new CommandSetBombs());
		registerCommand(new CommandDamage());
		registerCommand(new CommandHeal());
		
		registerCommand(new CommandDebug());
	}
	
	public static void registerCommand(Command cmd) {
		for (Command c : commands) {
			if (c.getName().equalsIgnoreCase(cmd.getName())) {
				
			}
		}
		
		commands.add(cmd);
	}
	
	public static Command getCommand(int id) {
		return commands.get(id);
	}
	
	public static int getAmountOfCommands() {
		return commands.size();
	}
}
