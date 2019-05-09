package main.java.lotf.commands;

import java.awt.Color;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.commands.util.InitCommands;

public class CommandCHelp extends Command {

	public CommandCHelp() {
		super("chelp", new ArgumentType[] {ArgumentType.String}, false);
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
				console.addLine(cmd.getUsage(), Color.GREEN);
				tb = true;
			}
		}
		
		if (!tb) {
			console.addLine("* Could not find command called " + argString.get(0) + "!", Color.GREEN);
		}
	}
}
