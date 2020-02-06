package main.java.lotf.commands.util;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.CommandHelp;
import main.java.lotf.init.InitCommands;
import main.java.lotf.util.DoubleValue;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.LangKey;
import main.java.lotf.util.LangKey.LangKeyType;
import main.java.lotf.util.LangKey.LangType;

public class DebugConsole {
	private static final int MAX_ARGS = Command.ArgumentType.values().length, MAX_LINES = 7;
	private boolean isConsoleOpen;
	private int curLine;
	private String input = "";
	private List<DoubleValue<String, Color>> lines = new ArrayList<DoubleValue<String, Color>>();
	private List<String> writtenLines = new ArrayList<String>();
	
	public DebugConsole() {
		lines.add(new DoubleValue<String, Color>("", Color.WHITE));
		writtenLines.add("");
	}
	
	public void addKey(Character c) {
		input += c;
	}
	
	public void removeKey() {
		input = input.substring(0, input.length() - 1);
	}
	
	public void addLine(String line, Color color) {
		if (lines.size() > MAX_LINES) {
			lines.remove(lines.size() - 1);
		}
		
		lines.add(1, new DoubleValue<String, Color>(line, color));
	}
	
	public void clearInput() {
		input = "";
	}
	
	public Command findCommand(String name) {
		for (int i = 0; i < InitCommands.getAmountOfCommands(); i++) {
			if (InitCommands.getCommand(i).getName().equalsIgnoreCase(name)) {
				return InitCommands.getCommand(i);
			}
		}
		
		return null;
	}
	
	public void finishCommand() {
		String cmd = input.trim();
		if (cmd.equals("")) {
			isConsoleOpen = false;
			curLine = 0;
			return;
		}
		
		if (writtenLines.size() > MAX_LINES) {
			writtenLines.remove(writtenLines.size() - 1);
		}
		writtenLines.add(1, cmd);
		curLine = 0;
		
		if (!cmd.startsWith("/")) {
			CommandError.noSlash.printError();
			return;
		}
		
		boolean didFind = false;
		for (int i = 0; i < cmd.length(); i++) {
			if (cmd.charAt(i) != KeyEvent.VK_SLASH && input.trim().charAt(i) != 0) {
				didFind = true;
				break;
			}
		}
		
		if (!didFind) {
			CommandError.noCommand.printError();
			return;
		}
		
		String cmdName = null;
		if (cmd.indexOf(" ") != -1) {
			cmdName = cmd.substring(1, cmd.indexOf(" "));
		} else {
			cmdName = cmd.substring(1, cmd.length());
		}
		
		Command command = findCommand(cmdName);
		if (command == null) {
			CommandError.notACommand.printError();
			return;
		}
		
		List<Integer> intArgs = new ArrayList<Integer>();
		List<Float> floatArgs = new ArrayList<Float>();
		List<Double> doubleArgs = new ArrayList<Double>();
		List<Boolean> boolArgs = new ArrayList<Boolean>();
		List<String> stringArgs = new ArrayList<String>();
		
		String[] unformatedArgs = null;
		List<String> formatedArgs = new ArrayList<String>();
		if (cmd.indexOf(" ") != -1) {
			unformatedArgs = cmd.substring(cmd.indexOf(" "), cmd.length()).split(" ");
			
			for (int i = 0; i < unformatedArgs.length; i++) {
				if (!unformatedArgs[i].isEmpty()) {
					formatedArgs.add(unformatedArgs[i]);
				}
			}
			
			if (!command.isArgsOptional) {
				if (formatedArgs.size() < command.getAmountOfArgs()) {
					CommandError.notEnoughArgs.printError();
					return;
				} else if (formatedArgs.size() > MAX_ARGS || formatedArgs.size() > command.getAmountOfArgs()) {
					CommandError.tooManyArgs.printError();
					return;
				}
			} else {
				if (formatedArgs.size() > MAX_ARGS || formatedArgs.size() > command.getAmountOfArgs()) {
					CommandError.tooManyArgs.printError();
					return;
				}
			}
			
			for (int i = 0; i < formatedArgs.size(); i++) {
				if (command.getArgumentType().get(i).contains(Command.ArgumentType.Integer)) {
					try {
						intArgs.add(Integer.parseInt(formatedArgs.get(i)));
						continue;
					} catch (NumberFormatException e) {}
				}
				
				if (command.getArgumentType().get(i).contains(Command.ArgumentType.Float)) {
					try {
						floatArgs.add(Float.parseFloat(formatedArgs.get(i)));
						continue;
					} catch (NumberFormatException e) {}
				}
				
				if (command.getArgumentType().get(i).contains(Command.ArgumentType.Double)) {
					try {
						doubleArgs.add(Double.parseDouble(formatedArgs.get(i)));
						continue;
					} catch (NumberFormatException e) {}
				}
				
				if (command.getArgumentType().get(i).contains(Command.ArgumentType.Boolean)) {
					if (formatedArgs.get(i).equals("true") || formatedArgs.get(i).equals("false")) {
						boolArgs.add(Boolean.parseBoolean(formatedArgs.get(i)));
						continue;
					}
					
					CommandError.wrongArg.printError();
					return;
				}
				
				if (command.getArgumentType().get(i).contains(Command.ArgumentType.String)) {
					try {
						Integer.parseInt(formatedArgs.get(i));
						CommandError.wrongArg.printError();
						return;
					} catch (NumberFormatException e1) {
						try {
							Float.parseFloat(formatedArgs.get(i));
							CommandError.wrongArg.printError();
							return;
						} catch (NumberFormatException e2) {
							try {
								Double.parseDouble(formatedArgs.get(i));
								CommandError.wrongArg.printError();
								return;
							} catch (NumberFormatException e3) {
								stringArgs.add(formatedArgs.get(i));
								continue;
							}
						}
					}
				}
				
				if (intArgs.isEmpty() && floatArgs.isEmpty() && doubleArgs.isEmpty() && boolArgs.isEmpty() && stringArgs.isEmpty()) {
					CommandError.wrongArg.printError();
					return;
				}
			}
		} else if (command.getAmountOfArgs() != 0 && !command.isArgsOptional) {
			CommandError.noArgs.printError();
			return;
		}
		
		if (!(command instanceof CommandHelp)) {
			addLine(cmd, Color.GREEN);
		}
		command.doCommand(intArgs, floatArgs, doubleArgs, boolArgs, stringArgs);
		clearInput();
	}
	
	public void setConsoleOpen(boolean isConsoleOpen) {
		this.isConsoleOpen = isConsoleOpen;
	}
	
	public void setCurLine(int curLine) {
		this.curLine = curLine;
	}
	
	public void setInput(String input) {
		this.input = input;
	}
	
	public boolean isConsoleOpen() {
		return isConsoleOpen;
	}
	
	public int getCurLine() {
		return curLine;
	}
	
	public String getInput() {
		return input;
	}
	
	public List<String> getWrittenLines() {
		return writtenLines;
	}
	
	public List<DoubleValue<String, Color>> getLines() {
		return lines;
	}
	
	public static int getMaxLines() {
		return MAX_LINES;
	}
	
	private enum CommandError {
		nil          (GetResource.getStringFromLangKey(new LangKey(LangType.command, "nil", LangKeyType.desc), LangKeyType.desc)),
		noSlash      (GetResource.getStringFromLangKey(new LangKey(LangType.command, "noSlash", LangKeyType.desc), LangKeyType.desc)),
		noCommand    (GetResource.getStringFromLangKey(new LangKey(LangType.command, "noCommand", LangKeyType.desc), LangKeyType.desc)),
		noArgs       (GetResource.getStringFromLangKey(new LangKey(LangType.command, "noArgs", LangKeyType.desc), LangKeyType.desc)),
		notEnoughArgs(GetResource.getStringFromLangKey(new LangKey(LangType.command, "notEnoughArgs", LangKeyType.desc), LangKeyType.desc)),
		tooManyArgs  (GetResource.getStringFromLangKey(new LangKey(LangType.command, "tooManyArgs", LangKeyType.desc), LangKeyType.desc)),
		wrongArg     (GetResource.getStringFromLangKey(new LangKey(LangType.command, "wrongArg", LangKeyType.desc), LangKeyType.desc)),
		notACommand  (GetResource.getStringFromLangKey(new LangKey(LangType.command, "notACommand", LangKeyType.desc), LangKeyType.desc));
		
		private final String error;
		
		private CommandError(String error) {
			this.error = error;
		}
		
		private void printError() {
			Main.getMain().getCommandConsole().addLine(Main.getMain().getCommandConsole().input.trim(), Color.GREEN);
			Main.getMain().getCommandConsole().clearInput();
			Main.getMain().getCommandConsole().addLine(error, Color.RED);
		}
	}
}
