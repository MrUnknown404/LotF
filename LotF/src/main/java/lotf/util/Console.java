package main.java.lotf.util;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import main.java.lotf.MainConsole;
import main.java.lotf.Main;

public final class Console {
	
	private static final WarningType[] DISABLED_TYPES = {};
	private static Color[] colors = new Color[WarningType.values().length];
	
	/** Prints date info to the console Example: <p> [12:34:56:789] [Info] [ExampleClass.exampleMethod.69] [Hour/Minute/Second/Millisecond] */
	public static void getTimeExample() {
		if (Main.getIsDebug()) {
			MainConsole.print(new Color(100, 100, 100), "[" + new SimpleDateFormat("hh:mm:ss:SSS").format(new Date()) + "] [" + WarningType.Info + "] [" + getCallerInfo(Console.class.getName()) + "] [Hour/Minute/Second/Millisecond]");
		}
		
		System.out.println("[" + new SimpleDateFormat("hh:mm:ss:SSS").format(new Date()) + "] [" + WarningType.Info + "] [" + getCallerInfo(Console.class.getName()) + "] [Hour/Minute/Second/Millisecond]");
		
		setColors();
	}
	
	/** Prints date info plus the given string to the console Example: <p> [12:34:56:789] [Debug] [ExampleClass.exampleMethod.69] : Hello! */
	public static void print(WarningType type, String string) {
		if (DISABLED_TYPES.length != 0) {
			for (int i = 0; i < DISABLED_TYPES.length; i++) {
				if (DISABLED_TYPES[i] == type) {
					return;
				}
			}
		}
		
		if (Main.getIsDebug()) {
			MainConsole.print(colors[type.fId], "[" + new SimpleDateFormat("hh:mm:ss:SSS").format(new Date()) + "] [" + type.toString() +  "] [" + getCallerInfo(Console.class.getName()) + "] : " + string);
		}
		
		if (type == WarningType.Error || type == WarningType.FatalError) {
			System.err.println("[" + new SimpleDateFormat("hh:mm:ss:SSS").format(new Date()) + "] [" + type.toString() +  "] [" + getCallerInfo(Console.class.getName()) + "] : " + string);
		} else {
			System.out.println("[" + new SimpleDateFormat("hh:mm:ss:SSS").format(new Date()) + "] [" + type.toString() +  "] [" + getCallerInfo(Console.class.getName()) + "] : " + string);
		}
	}
	
	/** Prints date info plus the given string to the console Example: <p> [12:34:56:789] [Debug] [ExampleClass.exampleMethod.69] : Hello! */
	public static void print(String string) {
		print(WarningType.Debug, string);
	}
	
	public static void setColors() {
		colors[0] = new Color(100, 100, 100);
		colors[1] = new Color(255, 255, 255);
		colors[2] = new Color(255, 255, 0);
		colors[3] = new Color(250, 50, 50);
		colors[4] = new Color(150, 0, 0);
		colors[5] = new Color(175, 175, 175);
		colors[6] = new Color(175, 175, 175);
		
		print("Registered console colors");
	}
	
	/** Gets what class is called */
	public static String getCallerInfo(String className) {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		for (int i = 1; i < stElements.length; i++) {
			StackTraceElement ste = stElements[i];
			if (!ste.getClassName().equals(className) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
				return (ste.getClassName().replaceAll(ste.getClassName().substring(0, ste.getClassName().lastIndexOf('.') + 1), "") + "." + ste.getMethodName() + "." + ste.getLineNumber()).replace("$", ".").replace("<", "").replace(">", "");
			}
		}
		return null;
	}
	
	public enum WarningType {
		Debug     (0),
		Info      (1),
		Warning   (2),
		Error     (3),
		FatalError(4),
		Texture   (5),
		Register  (6);
		
		private final int fId;
		
		private WarningType(int id) {
			fId = id;
		}
		
		public static WarningType getNumber(int id) {
			for (WarningType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
