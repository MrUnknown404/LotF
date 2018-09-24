package main.java.lotf.util.math;

import main.java.lotf.util.Console;

public final class MathHelper {
	
	/** Returns the greatest integer less than or equal to the double argument */
	public static int floor(double value) {
		int i = (int) value;
		return value < (double) i ? i - 1 : i;
	}
	
	/** Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and third parameters */
	public static double clamp(double num, double min, double max) {
		return num < min ? min : (num > max ? max : num);
	}
	
	/** Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and third parameters */
	public static float clamp(float num, float min, float max) {
		return num < min ? min : (num > max ? max : num);
	}
	
	/** Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and third parameters */
	public static int clamp(int num, int min, int max) {
		return num < min ? min : (num > max ? max : num);
	}
	
	/** Returns the number rounded to the specified decimal */
	public static double roundTo(double number, int decimal) {
		double tempDecimal = 1;
		for (int i = 0; i < decimal; i++) {
			tempDecimal *= 10;
		}
		return Math.round(number * tempDecimal) / tempDecimal;
	}
	
	/** Returns the number rounded to the specified decimal */
	public static float roundTo(float number, int decimal) {
		double tempDecimal = 1;
		for (int i = 0; i < decimal; i++) {
			tempDecimal *= 10;
		}
		return (float) (Math.round(number * tempDecimal) / tempDecimal);
	}
	
	/** Returns a normalized version of the given number */
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
