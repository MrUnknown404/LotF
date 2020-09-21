package main.java.lotf.init;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.java.lotf.commands.CommandAddCollectible;
import main.java.lotf.commands.CommandDamage;
import main.java.lotf.commands.CommandDebug;
import main.java.lotf.commands.CommandHeal;
import main.java.lotf.commands.CommandHelp;
import main.java.lotf.commands.CommandSetArrows;
import main.java.lotf.commands.CommandSetBombs;
import main.java.lotf.commands.CommandSetMana;
import main.java.lotf.commands.CommandSetMoney;
import main.java.lotf.commands.CommandToggleDebug;
import main.java.lotf.commands.util.Command;
import main.java.ulibs.utils.Console;
import main.java.ulibs.utils.Console.WarningType;

public class Commands {
	private static final List<Command> COMMANDS = new ArrayList<Command>();
	
	public static void registerAll() {
		Console.print(WarningType.Info, "Started registering " + Commands.class.getSimpleName() + "!");
		
		registerCommand(new CommandHelp());
		registerCommand(new CommandSetMoney());
		registerCommand(new CommandSetArrows());
		registerCommand(new CommandSetBombs());
		registerCommand(new CommandDamage());
		registerCommand(new CommandHeal());
		registerCommand(new CommandAddCollectible());
		registerCommand(new CommandSetMana());
		registerCommand(new CommandToggleDebug());
		
		registerCommand(new CommandDebug());
		
		Collections.sort(COMMANDS, new Comparator<Command>() {
			@Override
			public int compare(Command c1, Command c2) {
				return c1.getName().compareTo(c2.getName());
			}
		});
	}
	
	public static void registerCommand(Command cmd) {
		COMMANDS.add(cmd);
		Console.print(Console.WarningType.RegisterDebug, "'" + cmd.getName() + "' was registered!");
	}
	
	public static Command getCommand(int id) {
		return COMMANDS.get(id);
	}
	
	public static int getAmountOfCommands() {
		return COMMANDS.size();
	}
}
