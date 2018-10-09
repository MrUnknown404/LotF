package main.java.lotf.commands;

import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;

public class CommandNoclip extends Command {
	
	private static final Command.ArgumentType[] types = {};
	
	public CommandNoclip() {
		super("noclip", types, true);
	}

	@Override
	protected String setUsage() {
		return "Toggles collision";
	}

	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		if (Main.getWorldHandler().getPlayer() != null) {
			Main.getWorldHandler().getPlayer().toggleCollision();
		}
	}
}
