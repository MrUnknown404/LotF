package main.java.lotf.commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.init.Collectibles;
import main.java.lotf.items.util.Collectible;

public class CommandAddCollectible extends Command {

	private static final List<List<ArgumentType>> ARGS = Arrays.asList(Arrays.asList(ArgumentType.String), Arrays.asList(ArgumentType.Integer));
	
	public CommandAddCollectible() {
		super("addcollectible", ARGS, false);
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		EntityPlayer p = Main.getMain().getWorldHandler().getPlayer();
		
		Collectible col = Collectibles.find(argString.get(0));
		if (col == null) {
			console.addLine("Unknown collectible : " + argString.get(0), Color.RED);
			return;
		}
		
		p.addCollectible(col, argInt.get(0));
	}
}
