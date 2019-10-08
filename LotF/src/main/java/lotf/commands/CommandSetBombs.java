package main.java.lotf.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;
import main.java.lotf.util.math.MathH;

public class CommandSetBombs extends Command {

	private static final Map<Integer, List<ArgumentType>> ARGS = new HashMap<Integer, List<ArgumentType>>();
	
	static {
		ARGS.put(0, Arrays.asList(ArgumentType.Integer));
	}
	
	public CommandSetBombs() {
		super("setbombs", ARGS, false);
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		Main.getMain().getWorldHandler().getPlayer().setBombs(MathH.clamp(argInt.get(0), 0, Main.getMain().getWorldHandler().getPlayer().getMaxBombs()));
	}
}
