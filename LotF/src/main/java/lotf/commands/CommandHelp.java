package main.java.lotf.commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.init.InitCommands;
import main.java.lotf.util.math.MathH;

public class CommandHelp extends Command {
	
	private static final Map<Integer, List<ArgumentType>> ARGS = new HashMap<Integer, List<ArgumentType>>();
	
	static {
		ARGS.put(0, Arrays.asList(ArgumentType.Integer, ArgumentType.String));
	}
	
	public CommandHelp() {
		super("help", ARGS, true);
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		DebugConsole console = Main.getMain().getCommandConsole();
		
		if (argString.isEmpty()) {
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
		} else {
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
}
