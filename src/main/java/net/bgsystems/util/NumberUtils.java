package net.bgsystems.util;

public class NumberUtils {
	public static boolean tryParseInt(String value, Integer out) {
		if (value == null)
			return false;

		try {
			out = Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean tryParseDouble(String value, Double out) {
		if (value == null)
			return false;

		try {
			out = Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean tryParseFloat(String value, Float out) {
		if (value == null)
			return false;

		try {
			out = Float.parseFloat(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean tryParseLong(String value, Long out) {
		if (value == null)
			return false;

		try {
			out = Long.parseLong(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
