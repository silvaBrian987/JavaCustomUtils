package net.bgsystems.util;

public class ArrayUtils {
	public static <T> T getValue(T[] array, int pos) {
		if (array.length > pos)
			return array[pos];
		return null;
	}
}
