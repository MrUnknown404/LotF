package main.java.lotf.commands;

import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.entity.EntityPlayer;
import main.java.lotf.init.InitItems;
import main.java.lotf.inventory.PlayerInventory;
import main.java.lotf.items.ItemSpellBook;
import main.java.lotf.items.util.Item;

public class CommandGive extends Command {

	private static final Command.ArgumentType[] types = {Command.ArgumentType.String, Command.ArgumentType.Integer};
	
	public CommandGive() {
		super("give", types, true);
	}

	@Override
	protected String setUsage() {
		return "Gives the player the specified item";
	}

	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		if (Main.getWorldHandler().getPlayer() != null) {
			DebugConsole console = Main.getCommandConsole();
			EntityPlayer player = Main.getWorldHandler().getPlayer();
			PlayerInventory p = player.getInventory();
			
			if (argInt.isEmpty()) {
				argInt.add(0);
			}
			
			if (argString.isEmpty()) {
				console.addLine("* Must provide item name!");
				return;
			} else if (argString.get(0).equals("empty")) {
				console.addLine("* Invalid item \"" + argString.get(0) + "\"!");
				return;
			}
			
			if (argString.get(0).equals("heart")) {
				if (argInt.get(0) == 0) {
					argInt.set(0, 1);
				}
				
				int ti = 0;
				for (int i = 0; i < argInt.get(0); i++) {
					if (player.getHearts().canAddHeartContainer()) {
						player.getHearts().addHeartContainer();
					} else {
						ti = argInt.get(0) - i;
						break;
					}
				}
				
				if (ti <= 0) {
					console.addLine("$ Gave the player \"heart\" x " + argInt.get(0) + "!");
				} else {
					console.addLine("$ Gave the player \"heart\" x " + (argInt.get(0) - ti) + "!");
				}
				
				return;
			} else if (argString.get(0).equals("spellPageWarp")) {
				if (p.findItem("spellBook", 0) == null) {
					console.addLine("* Player does not have the spellBook");
					return;
				} else if (((ItemSpellBook) p.findItem("spellBook", 0)).getSpellPageList().getHas().get(1)) {
					console.addLine("* Player already has that page");
					return;
				}
				
				((ItemSpellBook) p.findItem("spellBook", 0)).getSpellPageList().addSpell(1);
				console.addLine("$ Gave the player spellPageWarp:1!");
				return;
			} else if (argString.get(0).equals("spellPageBuild")) {
				if (p.findItem("spellBook", 0) == null) {
					console.addLine("* Player does not have the spellBook");
					return;
				} else if (((ItemSpellBook) p.findItem("spellBook", 0)).getSpellPageList().getHas().get(2)) {
					console.addLine("* Player already has that page");
					return;
				}
				
				((ItemSpellBook) p.findItem("spellBook", 0)).getSpellPageList().addSpell(2);
				console.addLine("$ Gave the player spellPageBuild:2!");
				return;
			} else if (argString.get(0).equals("spellPageIce")) {
				if (p.findItem("spellBook", 0) == null) {
					console.addLine("* Player does not have the spellBook");
					return;
				} else if (((ItemSpellBook) p.findItem("spellBook", 0)).getSpellPageList().getHas().get(3)) {
					console.addLine("* Player already has that page");
					return;
				}
				
				((ItemSpellBook) p.findItem("spellBook", 0)).getSpellPageList().addSpell(3);
				console.addLine("$ Gave the player spellPageIce:3!");
				return;
			} else if (argString.get(0).equals("spellPageMagnet")) {
				if (p.findItem("spellBook", 0) == null) {
					console.addLine("* Player does not have the spellBook");
					return;
				} else if (((ItemSpellBook) p.findItem("spellBook", 0)).getSpellPageList().getHas().get(4)) {
					console.addLine("* Player already has that page");
					return;
				}
				
				((ItemSpellBook) p.findItem("spellBook", 0)).getSpellPageList().addSpell(4);
				console.addLine("$ Gave the player spellPageMagnet:4!");
				return;
			} else if (argString.get(0).equals("money")) {
				player.addMoney(argInt.get(0));
				console.addLine("$ Gave the player \"money\" x " + argInt.get(0) + "!");
				return;
			}
			
			if (InitItems.get(argString.get(0), argInt.get(0)).equals(InitItems.EMPTY)) {
				console.addLine("* Could not find item called \"" + argString.get(0) + "\"!");
				return;
			}
			
			Item item = InitItems.get(argString.get(0), argInt.get(0));
			
			if (p.findItem(argString.get(0), argInt.get(0)) == null) {
				p.addItem(item);
				console.addLine("$ Gave the player " + item.getName() + ":" + item.getMeta() + "!");
			} else {
				console.addLine("* Player already has that item!");
				return;
			}
		}
	}
}
