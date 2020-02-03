package main.java.lotf.commands;

import java.util.Arrays;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;

public class CommandDamage extends Command {

	private static final List<List<ArgumentType>> ARGS = Arrays.asList(Arrays.asList(ArgumentType.Integer));
	
	public CommandDamage() {
		super("damage", ARGS, false);
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		Main.getMain().getWorldHandler().getPlayer().damage(argInt.get(0));
	}
}
