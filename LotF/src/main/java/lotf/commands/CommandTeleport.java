package main.java.lotf.commands;

import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.math.Vec2i;

public class CommandTeleport extends Command {

	private static final Command.ArgumentType[] types = {Command.ArgumentType.Integer, Command.ArgumentType.Integer};
	
	public CommandTeleport() {
		super("tp", types, false);
	}
	
	@Override
	public String setUsage() {
		return "Teleports the player to the specified coordinates";
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		DebugConsole console = Main.getCommandConsole();
		
		if (argInt.get(0) <= 0) {
			console.addLine("* X cannot be zero or below");
			return;
		} else if (argInt.get(1) <= 0) {
			console.addLine("* Y cannot be zero or below");
			return;
		} else if (argInt.get(0) > Main.getWorldHandler().getPlayer().getRoom().getRoomSize().getX()) {
			console.addLine("* X is bigger then the room size");
			return;
		} else if (argInt.get(1) > Main.getWorldHandler().getPlayer().getRoom().getRoomSize().getY()) {
			console.addLine("* Y is bigger then the room size");
			return;
		}
		
		Vec2i vec = new Vec2i((argInt.get(0) - 1) * Tile.TILE_SIZE, (argInt.get(1) - 1) * Tile.TILE_SIZE);
		
		Main.getWorldHandler().getPlayer().setRelativePos(vec);
	}
}
