package main.java.lotf.commands;

import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;

public class CommandDamage extends Command {

	private static final Command.ArgumentType[] types = {Command.ArgumentType.Integer};
	
	public CommandDamage() {
		super("damage", types, false);
	}
	
	@Override
	public String setUsage() {
		return "Damages the player the specified amount";
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		if (Main.getWorldHandler().getPlayer() != null) {
			Main.getWorldHandler().getPlayer().getHearts().damage(argInt.get(0));
		}
	}
}
