package main.java.lotf.commands;

import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.entity.EntityPlayer;

public class CommandRefillAmmo extends Command {

	private static final Command.ArgumentType[] types = {};
	
	public CommandRefillAmmo() {
		super("refillammo", types, true);
	}
	
	@Override
	protected String setUsage() {
		return "Refills the player's ammo";
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		DebugConsole console = Main.getCommandConsole();
		EntityPlayer p = Main.getWorldHandler().getPlayer();
		
		for (int i = 0; i < p.getInventory().getItems().size(); i++) {
			if (p.getInventory().getItems().get(i).getUseAmmo()) {
				p.getInventory().getItems().get(i).getAmmo().maxOutAmmo();
			}
		}
		
		console.addLine("Refilled the player's ammo!");
	}
}
