package main.java.lotf.commands;

import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;
import main.java.lotf.util.math.MathHelper;

public class CommandSetMoney extends Command {

	public CommandSetMoney() {
		super("setmoney", new ArgumentType[] {ArgumentType.Integer}, false);
	}
	
	@Override
	protected String setUsage() {
		return "Sets the player's money";
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		Main.getMain().getWorld().getPlayer().setMoney(MathHelper.clamp(argInt.get(0), 0, 999999));
	}
}
