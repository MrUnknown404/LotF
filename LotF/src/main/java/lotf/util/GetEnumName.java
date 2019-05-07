package main.java.lotf.util;

import java.util.Arrays;

public class GetEnumName {
	public static String[] getNames(Class<? extends Enum<?>> e) {
		return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
	}
}
