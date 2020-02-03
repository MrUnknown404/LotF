package main.java.lotf.commands;

import java.util.Arrays;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;
import main.java.lotf.util.math.MathH;

public class CommandSetArrows extends Command {

	private static final List<List<ArgumentType>> ARGS = Arrays.asList(Arrays.asList(ArgumentType.Integer));
	
	public CommandSetArrows() {
		super("setarrows", ARGS, false);
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		Main.getMain().getWorldHandler().getPlayer().setArrows(MathH.clamp(argInt.get(0), 0, Main.getMain().getWorldHandler().getPlayer().getMaxArrows()));
	}
}
