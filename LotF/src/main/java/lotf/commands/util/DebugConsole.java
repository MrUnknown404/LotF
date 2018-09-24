package main.java.lotf.commands.util;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.CommandHelp;

public final class DebugConsole {
	public List<Command> commands = new ArrayList<Command>();
	
	private static final int MAX_ARGS = Command.ArgumentType.values().length, MAX_LINES = 7;
	public boolean isConsoleOpen;
	public int curLine;
	public String input = "";
	public List<String> lines = new ArrayList<String>(), writtenLines = new ArrayList<String>();
	
	public DebugConsole() {
		lines.add("");
		writtenLines.add("");
	}
	
	public void addKey(Character c) {
		input += c;
	}
	
	public void removeKey() {
		input = input.substring(0, input.length() - 1);
	}
	
	public void addLine(String line) {
		if (lines.size() > MAX_LINES) {
			lines.remove(lines.size() - 1);
		}
		lines.add(1, line);
	}
	
	public void clearInput() {
		input = "";
	}
	
	public Command findCommand(String name) {
		for (int i = 0; i < commands.size(); i++) {
			if (commands.get(i).getName().equals(name)) {
				return commands.get(i);
			}
		}
		return null;
	}
	
	private void printError(CommandError error) {
		String errorStr = null;
		switch (error) {
			case noSlash:
				errorStr = "Commands must start with a /";
				break;
			case noCommand:
				errorStr = "No command was written";
				break;
			case noArgs:
				errorStr = "No arguments were written";
				break;
			case notEnoughArgs:
				errorStr = "Not enough arguments were written";
				break;
			case tooManyArgs:
				errorStr = "Too many arguments were written";
				break;
			case wrongArg:
				errorStr = "Atleast one of the written arguments is not the correct type";
				break;
			case notACommand:
				errorStr = "No valid command was written";
				break;
			default:
				errorStr = "Null! something broke!";
				break;
		}
		
		addLine(Main.getCommandConsole().input.trim());
		clearInput();
		addLine("* " + errorStr.trim());
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
			printError(CommandError.noSlash);
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
			printError(CommandError.noCommand);
			return;
		}
		
		String cmdName = null;
		if (cmd.indexOf(" ") != -1) {
			cmdName = cmd.substring(1, cmd.indexOf(" "));
		} else {
			cmdName = cmd.substring(1, cmd.length());
		}
		
		Command command = null;
		for (int i = 0; i < commands.size(); i++) {
			if (commands.get(i).getName().equals(cmdName)) {
				command = commands.get(i);
				break;
			}
		}
		if (command == null) {
			printError(CommandError.notACommand);
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
					printError(CommandError.notEnoughArgs);
					return;
				} else if (formatedArgs.size() > MAX_ARGS || formatedArgs.size() > command.getAmountOfArgs()) {
					printError(CommandError.tooManyArgs);
					return;
				}
			} else {
				if (formatedArgs.size() > MAX_ARGS || formatedArgs.size() > command.getAmountOfArgs()) {
					printError(CommandError.tooManyArgs);
					return;
				}
			}
			
			for (int i = 0; i < formatedArgs.size(); i++) {
				if (command.getArgumentType()[i].equals(Command.ArgumentType.Integer)) {
					try {
						intArgs.add(Integer.parseInt(formatedArgs.get(i)));
					} catch (NumberFormatException e) {
						printError(CommandError.wrongArg);
						return;
					}
				} else if (command.getArgumentType()[i].equals(Command.ArgumentType.Float)) {
					try {
						floatArgs.add(Float.parseFloat(formatedArgs.get(i)));
					} catch (NumberFormatException e) {
						printError(CommandError.wrongArg);
						return;
					}
				} else if (command.getArgumentType()[i].equals(Command.ArgumentType.Double)) {
					try {
						doubleArgs.add(Double.parseDouble(formatedArgs.get(i)));
					} catch (NumberFormatException e) {
						printError(CommandError.wrongArg);
						return;
					}
				} else if (command.getArgumentType()[i].equals(Command.ArgumentType.Boolean)) {
					if (formatedArgs.get(i).equals("true")) {
						boolArgs.add(true);
					} else if (formatedArgs.get(i).equals("false")) {
						boolArgs.add(false);
					} else {
						printError(CommandError.wrongArg);
						return;
					}
				} else if (command.getArgumentType()[i].equals(Command.ArgumentType.String)) {
					try {
						Integer.parseInt(formatedArgs.get(i));
						printError(CommandError.wrongArg);
						return;
					} catch (NumberFormatException e1) {
						try {
							Float.parseFloat(formatedArgs.get(i));
							printError(CommandError.wrongArg);
							return;
						} catch (NumberFormatException e2) {
							try {
								Double.parseDouble(formatedArgs.get(i));
								printError(CommandError.wrongArg);
								return;
							} catch (NumberFormatException e3) {
								if (!formatedArgs.get(i).equals("true") && !formatedArgs.get(i).equals("false")) {
									stringArgs.add(formatedArgs.get(i));
								} else {
									printError(CommandError.wrongArg);
									return;
								}
							}
						}
					}
				} else {
					printError(CommandError.nil);
					return;
				}
			}
		} else if (command.getAmountOfArgs() != 0 && !command.isArgsOptional) {
			printError(CommandError.noArgs);
			return;
		}
		
		if (!(command instanceof CommandHelp)) {
			addLine(cmd);
		}
		command.doCommand(intArgs, floatArgs, doubleArgs, boolArgs, stringArgs);
		clearInput();
	}
	
	public static int getMaxLines() {
		return MAX_LINES;
	}
	
	public enum CommandError {
		nil          (0),
		noSlash      (1),
		noCommand    (2),
		noArgs       (3),
		notEnoughArgs(4),
		tooManyArgs  (5),
		wrongArg     (6),
		notACommand  (7);
		
		private final int fId;
		
		private CommandError(int id) {
			fId = id;
		}

		public static CommandError getNumber(int id) {
			for (CommandError type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
