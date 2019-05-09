package main.java.lotf.commands;

import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;
import main.java.lotf.util.math.MathHelper;

public class CommandSetBombs extends Command {

	public CommandSetBombs() {
		super("setbombs", new ArgumentType[] {ArgumentType.Integer}, false);
	}
	
	@Override
	protected String setUsage() {
		return "Sets the player's bombs";
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		Main.getMain().getWorldHandler().getPlayer().setBombs(MathHelper.clamp(argInt.get(0), 0, Main.getMain().getWorldHandler().getPlayer().getMaxBombs()));
	}
}
