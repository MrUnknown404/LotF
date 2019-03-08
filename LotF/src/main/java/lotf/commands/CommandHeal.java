package main.java.lotf.commands;

import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;

public class CommandHeal extends Command {

	private static final Command.ArgumentType[] types = {Command.ArgumentType.Integer};
	
	public CommandHeal() {
		super("heal", types, false);
	}
	
	@Override
	public String setUsage() {
		return "Heals the player the specified amount";
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		if (Main.getWorldHandler().getPlayer() != null) {
			Main.getWorldHandler().getPlayer().heal(argInt.get(0));
		}
	}
}
