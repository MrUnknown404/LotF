package main.java.lotf.commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import main.java.lotf.commands.util.Command;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.init.Commands;
import main.java.ulibs.utils.math.MathH;

public class CommandHelp extends Command {
	
	private static final List<List<ArgumentType>> ARGS = Arrays.asList(Arrays.asList(ArgumentType.Integer, ArgumentType.String));
	
	public CommandHelp() {
		super("help", ARGS, true);
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		if (argString.isEmpty()) {
			if (!argInt.isEmpty() && argInt.get(0) > MathH.floor(Commands.getAmountOfCommands() / DebugConsole.getMaxLines())) {
				console.addLine("* Could not find page '" + argInt.get(0) + "'", Color.GREEN);
				return;
			}
			
			int ti = 0, tii = 1;
			if (!argInt.isEmpty()) {
				ti = Math.max((DebugConsole.getMaxLines() - 1) * argInt.get(0), 0);
				tii = Math.max(argInt.get(0) + 1, 1);
			}
			
			console.addLine("---", Color.GREEN);
			for (int i = ti; i < Commands.getAmountOfCommands(); i++) {
				Command cmd = Commands.getCommand(i);
				
				if (i == (DebugConsole.getMaxLines() - 1) * tii) {
					break;
				}
				
				console.addLine(cmd.getUsage(), Color.GREEN);
			}
		} else {
			boolean tb = false;
			for (int i = 0; i < Commands.getAmountOfCommands(); i++) {
				Command cmd = Commands.getCommand(i);
				if (cmd.getName().equals(argString.get(0))) {
					console.addLine(cmd.getUsage(), Color.GREEN);
					tb = true;
				}
			}
			
			if (!tb) {
				console.addLine("* Could not find command called '" + argString.get(0) + "'!", Color.GREEN);
			}
		}
	}
}
