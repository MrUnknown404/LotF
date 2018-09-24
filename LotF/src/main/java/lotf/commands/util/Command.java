package main.java.lotf.commands.util;

import java.util.List;

import javax.annotation.Nullable;

public abstract class Command {

	protected boolean isArgsOptional;
	protected String name;
	private int amountOfArgs;
	private ArgumentType[] typeOfArgs;
	
	protected String stringID;
	protected int id = -1;
	
	public Command(String name, @Nullable ArgumentType[] typeOfArgs, boolean isArgsOptional) {
		this.name = name;
		this.amountOfArgs = typeOfArgs.length;
		this.isArgsOptional = isArgsOptional;
		this.stringID = "CMD_" + name;
		
		if (typeOfArgs != null) {
			this.typeOfArgs = typeOfArgs;
		}
	}
	
	protected abstract String setUsage();
	
	public String getUsage() {
		StringBuilder b = new StringBuilder();
		if (isArgsOptional) {
			b.append("(optional) ");
		}
		
		for (Command.ArgumentType type : typeOfArgs) {
			b.append("<" + type.toString() + "> ");
		}
		
		return "/" + name + " : usage -> /" + name + " " + b.toString() + ": " + setUsage() + "!";
	}
	
	public abstract void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString);
	
	public String getName() {
		return name;
	}
	
	public int getAmountOfArgs() {
		return amountOfArgs;
	}
	
	public String getStringID() {
		return stringID;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	@Override
	public String toString() {
		return "(" + name + ", " + stringID + ", " + id + ")";
	}
	
	public ArgumentType[] getArgumentType() {
		return typeOfArgs;
	}
	
	public enum ArgumentType {
		Integer(0),
		Float  (1),
		Double (2),
		Boolean(3),
		String (4);
		
		private final int fId;
		
		private ArgumentType(int id) {
			fId = id;
		}
		
		public static ArgumentType getNumber(int id) {
			for (ArgumentType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
