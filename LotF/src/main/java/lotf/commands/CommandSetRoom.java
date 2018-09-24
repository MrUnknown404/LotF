package main.java.lotf.commands;

import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.Command;
import main.java.lotf.commands.util.DebugConsole;

public class CommandSetRoom extends Command {

	private static final Command.ArgumentType[] types = {Command.ArgumentType.Integer};
	
	public CommandSetRoom() {
		super("setroom", types, false);
	}
	
	@Override
	public String setUsage() {
		return " Moves the player to the specified room";
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		DebugConsole console = Main.getCommandConsole();
		
		if (Main.getRoomHandler().getPlayerRoom().equals(Main.getRoomHandler().getRooms().get(argInt.get(0)))) {
			console.addLine("* Player is already in that room");
			return;
		}
		
		Main.getRoomHandler().getPlayer().setRoom(Main.getRoomHandler().getRooms().get(argInt.get(0)));
	}
}
