package main.java.lotf.commands;

import java.util.Collections;
import java.util.List;

import main.java.lotf.client.gui.HudDebug;
import main.java.lotf.commands.util.Command;

public class CommandToggleDebug extends Command {
	
	public CommandToggleDebug() {
		super("toggledebug", Collections.emptyList(), false);
	}
	
	@Override
	public void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString) {
		HudDebug.shouldRender = !HudDebug.shouldRender;
	}
}
