package main.java.lotf.commands;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.items.util.ItemInfo;

public class CommandAddCollectable extends Command {

	private static final Map<Integer, List<ArgumentType>> ARGS = new HashMap<Integer, List<ArgumentType>>();
	
	static {
		ARGS.put(0, Arrays.asList(ArgumentType.String));
		ARGS.put(1, Arrays.asList(ArgumentType.Integer));
	}
	
	public CommandAddCollectable() {
		super("addcollectable", ARGS, false);
	}
	
	@Override
	protected String setUsage() {
		return "Adds collectables";
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		EntityPlayer p = Main.getMain().getWorldHandler().getPlayer();
		
		ItemInfo info = ItemInfo.find("collectable_" + argString.get(0));
		if (info == null) {
			Main.getMain().getCommandConsole().addLine("Unknown collectable : " + argString.get(0), Color.RED);
			return;
		}
		
		p.addCollectable(info, argInt.get(0));
	}
}
