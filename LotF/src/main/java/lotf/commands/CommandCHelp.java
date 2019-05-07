package main.java.lotf.commands;

import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;
import main.java.lotf.commands.util.DebugConsole;

public class CommandCHelp extends Command {

	private static final Command.ArgumentType[] types = {Command.ArgumentType.String};
	
	public CommandCHelp() {
		super("chelp", types, false);
	}
	
	@Override
	protected String setUsage() {
		return "Prints the specified commands and it's usage";
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		DebugConsole console = Main.getMain().getCommandConsole();
		
		boolean tb = false;
		for (int i = 0; i < InitCommands.getAmountOfCommands(); i++) {
			Command cmd = InitCommands.getCommand(i);
			if (cmd.getName().equals(argString.get(0))) {
				console.addLine(cmd.getUsage());
				tb = true;
			}
		}
		
		if (!tb) {
			console.addLine("* Could not find command called " + argString.get(0) + "!");
		}
	}
}
