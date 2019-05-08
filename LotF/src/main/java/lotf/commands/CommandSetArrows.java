package main.java.lotf.commands;

import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;
import main.java.lotf.util.math.MathHelper;

public class CommandSetArrows extends Command {

	public CommandSetArrows() {
		super("setarrows", new ArgumentType[] {ArgumentType.Integer}, false);
	}
	
	@Override
	protected String setUsage() {
		return "Sets the player's arrows";
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		Main.getMain().getWorld().getPlayer().setArrows(MathHelper.clamp(argInt.get(0), 0, Main.getMain().getWorld().getPlayer().getMaxArrows()));
	}
}
