package main.java.lotf.commands;

import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;

public class CommandDamage extends Command {

	public CommandDamage() {
		super("damage", new ArgumentType[] {ArgumentType.Integer}, false);
	}
	
	@Override
	protected String setUsage() {
		return "Damages the player";
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		Main.getMain().getWorldHandler().getPlayer().damage(argInt.get(0));
	}
}
