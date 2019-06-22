package main.java.lotf.util.math;

import main.java.lotf.util.Console;

public class MathH {
	
	public static int floor(float value) {
		int i = (int) value;
		return value < (float) i ? i - 1 : i;
	}
	
	public static int ceil(float value) {
		int i = (int)value;
		return value > (float) i ? i + 1 : i;
	}
	
	public static int fClamp(float num, float min, float max) {
		return floor(clamp(num, min, max));
	}
	
	public static float clamp(float num, float min, float max) {
		return num < min ? min : (num > max ? max : num);
	}
	
	public static int clamp(int num, int min, int max) {
		return fClamp(num, min, max);
	}
	
	public static float roundTo(float number, int decimal) {
		double tempDecimal = 1;
		for (int i = 0; i < decimal; i++) {
			tempDecimal *= 10;
		}
		
		return (float) (Math.round(number * tempDecimal) / tempDecimal);
	}
	
	public static float normalize(float number, float max) {
		if (number > max) {
			Console.print(Console.WarningType.Error, "the specified number is more then the specified max!");
			return max;
		} else if (number < 0) {
			Console.print(Console.WarningType.Error, "the specified number cannot be less than zero!");
			return 0;
		}
		
		return 1 - ((number - 0) / (max - 0));
	}
	
	public static float percentage(float number, float max) {
		return (number / max) * 100f;
	}
}
