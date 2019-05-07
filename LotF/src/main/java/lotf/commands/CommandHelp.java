package main.java.lotf.commands;

import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.util.math.MathHelper;

public class CommandHelp extends Command {
	
	private static final Command.ArgumentType[] types = {Command.ArgumentType.Integer};
	
	public CommandHelp() {
		super("help", types, true);
	}
	
	@Override
	public String setUsage() {
		return "Prints all commands and their usage";
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		DebugConsole console = Main.getMain().getCommandConsole();
		
		int ti = 0, tii = 1;
		if (!argInt.isEmpty()) {
			ti = MathHelper.clamp(DebugConsole.getMaxLines() * argInt.get(0), 0, Integer.MAX_VALUE);
			tii = MathHelper.clamp(argInt.get(0) + 1, 1, Integer.MAX_VALUE);
		}
		
		for (int i = ti; i < InitCommands.getAmountOfCommands(); i++) {
			Command cmd = InitCommands.getCommand(i);
			
			if (i == DebugConsole.getMaxLines() * tii) {
				break;
			}
			
			console.addLine(cmd.getUsage());
		}
	}
}
