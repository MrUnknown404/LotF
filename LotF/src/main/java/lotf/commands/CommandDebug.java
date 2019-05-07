package main.java.lotf.commands;

import java.util.List;

import main.java.lotf.commands.util.Command;
import main.java.lotf.util.Console;

public class CommandDebug extends Command {

	private static final Command.ArgumentType[] types = {Command.ArgumentType.Float, Command.ArgumentType.String};
	
	public CommandDebug() {
		super("debug", types, false);
	}
	
	@Override
	public String setUsage() {
		return "Does nothing used for testing";
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		StringBuilder b = new StringBuilder();
		if (!argInt.isEmpty()) {
			b.append(argInt.get(0) + ", ");
		}
		if (!argFloat.isEmpty()) {
			b.append(argFloat.get(0) + ", ");
		}
		if (!argDouble.isEmpty()) {
			b.append(argDouble.get(0) + ", ");
		}
		if (!argBool.isEmpty()) {
			b.append(argBool.get(0) + ", ");
		}
		if (!argString.isEmpty()) {
			b.append(argString.get(0) + "!");
		}
		Console.print("SUCCESSFUL!" + " : " + b);
	}
}
