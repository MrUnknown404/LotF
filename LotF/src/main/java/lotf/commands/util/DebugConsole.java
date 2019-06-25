package main.java.lotf.commands.util;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.lotf.Main;
import main.java.lotf.commands.CommandHelp;
import main.java.lotf.init.InitCommands;

public class DebugConsole {
	private static final int MAX_ARGS = Command.ArgumentType.values().length, MAX_LINES = 7;
	private boolean isConsoleOpen;
	private int curLine;
	private String input = "";
	private List<Map<String, Color>> lines = new ArrayList<Map<String, Color>>();
	private List<String> writtenLines = new ArrayList<String>();
	
	public DebugConsole() {
		lines.add(Map.of("", Color.WHITE));
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
		
		lines.add(1, Map.of(line, color));
	}
	
	public void clearInput() {
		input = "";
	}
	
	public Command findCommand(String name) {
		for (int i = 0; i < InitCommands.getAmountOfCommands(); i++) {
			if (InitCommands.getCommand(i).getName().equals(name)) {
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
		
		Command command = null;
		for (int i = 0; i < InitCommands.getAmountOfCommands(); i++) {
			if (InitCommands.getCommand(i).getName().equals(cmdName)) {
				command = InitCommands.getCommand(i);
				break;
			}
		}
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
					} catch (NumberFormatException e) {}
				}
				
				if (command.getArgumentType().get(i).contains(Command.ArgumentType.Float)) {
					try {
						floatArgs.add(Float.parseFloat(formatedArgs.get(i)));
					} catch (NumberFormatException e) {}
				}
				
				if (command.getArgumentType().get(i).contains(Command.ArgumentType.Double)) {
					try {
						doubleArgs.add(Double.parseDouble(formatedArgs.get(i)));
					} catch (NumberFormatException e) {}
				}
				
				if (command.getArgumentType().get(i).contains(Command.ArgumentType.Boolean)) {
					if (formatedArgs.get(i).equals("true")) {
						boolArgs.add(true);
					} else if (formatedArgs.get(i).equals("false")) {
						boolArgs.add(false);
					} else {
						CommandError.wrongArg.printError();
						return;
					}
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
								if (!formatedArgs.get(i).equals("true") && !formatedArgs.get(i).equals("false")) {
									stringArgs.add(formatedArgs.get(i));
								} else {
									CommandError.wrongArg.printError();
									return;
								}
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
	
	public List<Map<String, Color>> getLines() {
		return lines;
	}
	
	public static int getMaxLines() {
		return MAX_LINES;
	}
	
	public enum CommandError {
		nil          ("Null! something broke!"),
		noSlash      ("Commands must start with a /"),
		noCommand    ("No command was written"),
		noArgs       ("No arguments were written"),
		notEnoughArgs("Not enough arguments were written"),
		tooManyArgs  ("Too many arguments were written"),
		wrongArg     ("At least one of the written arguments is not the correct type"),
		notACommand  ("No valid command was written");
		
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
