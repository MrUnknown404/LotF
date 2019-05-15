package main.java.lotf.commands;

import java.awt.Color;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.commands.util.InitCommands;
import main.java.lotf.util.math.MathH;

public class CommandHelp extends Command {
	
	public CommandHelp() {
		super("help", new ArgumentType[] {ArgumentType.Integer}, true);
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
			ti = MathH.clamp(DebugConsole.getMaxLines() * argInt.get(0), 0, Integer.MAX_VALUE);
			tii = MathH.clamp(argInt.get(0) + 1, 1, Integer.MAX_VALUE);
		}
		
		for (int i = ti; i < InitCommands.getAmountOfCommands(); i++) {
			Command cmd = InitCommands.getCommand(i);
			
			if (i == DebugConsole.getMaxLines() * tii) {
				break;
			}
			
			console.addLine(cmd.getUsage(), Color.GREEN);
		}
	}
}
