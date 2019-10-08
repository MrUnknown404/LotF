package main.java.lotf.commands.util;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import main.java.lotf.util.GetResource;
import main.java.lotf.util.LangKey;
import main.java.lotf.util.LangKey.LangKeyType;
import main.java.lotf.util.LangKey.LangType;

public abstract class Command {

	protected final boolean isArgsOptional;
	protected final String name;
	protected final LangKey key;
	
	protected final Map<Integer, List<ArgumentType>> typeOfArgs;
	
	/**
	 * @param name
	 * @param typeOfArgs
	 * @param isArgsOptional
	 */
	public Command(String name, @Nullable Map<Integer, List<ArgumentType>> typeOfArgs, boolean isArgsOptional) {
		this.name = name;
		this.key = new LangKey(LangType.command, name, LangKeyType.desc);
		this.isArgsOptional = isArgsOptional;
		this.typeOfArgs = typeOfArgs;
	}
	
	public String getUsage() {
		return "/" + name + " : " + GetResource.getStringFromLangKey(key, LangKeyType.desc) + "!";
	}
	
	public abstract void doCommand(List<Integer> argInt, List<Float> argFloat, List<Double> argDouble, List<Boolean> argBool, List<String> argString);
	
	public String getName() {
		return name;
	}
	
	public int getAmountOfArgs() {
		return typeOfArgs.size();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public Map<Integer, List<ArgumentType>> getArgumentType() {
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
