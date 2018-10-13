package net.bgsystems.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class SystemUtils {
	private static final Logger LOGGER = LogManager.getLogManager().getLogger(SystemUtils.class.getName());
	private static final Random random = new Random();
	
	public static void printSystemStatus() {
		/* Total number of processors or cores available to the JVM */
		LOGGER.info("Available processors (cores): " + Runtime.getRuntime().availableProcessors());

		/* Total amount of free memory available to the JVM */
		LOGGER.info("Free memory (bytes): " + Runtime.getRuntime().freeMemory());

		/* This will return Long.MAX_VALUE if there is no preset limit */
		long maxMemory = Runtime.getRuntime().maxMemory();
		/* Maximum amount of memory the JVM will attempt to use */
		LOGGER.info("Maximum memory (bytes): " + (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));

		/* Total memory currently in use by the JVM */
		LOGGER.info("Total memory (bytes): " + Runtime.getRuntime().totalMemory());
	}

	public static long printTime(long difference) {
		long nanoTime = System.nanoTime();
		LOGGER.info("Time (seconds): " + nanoTime / 1000 / 1000 / 1000);
		if (difference > 0) {
			LOGGER.info("Has passed " + ((nanoTime - difference) / 1000 / 1000 / 1000) + " seconds");
		}
		return nanoTime;
	}

	public static void shutdown(SystemEndCode code) {
		System.exit(code.getValue());
	}

	public static String getSystemPropertiesString() {
		Properties properties = System.getProperties();
		Enumeration<Object> keys = properties.keys();
		StringBuilder sb = new StringBuilder();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			sb.append(key.toString() + "=" + properties.get(key) + ";");
		}
		return sb.toString();
	}

	public static String getSystemEnvironmentVariablesString() {
		Map<String, String> environment = System.getenv();
		Set<String> keys = environment.keySet();
		StringBuilder sb = new StringBuilder();
		for (String key : keys) {
			sb.append(key + "=" + environment.get(key) + ";");
		}
		return sb.toString();
	}

	public enum SystemEndCode {
		OK(0), ERROR(1), RE_RUN(2);
		int value;

		SystemEndCode(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public static Map<String, String> argsToMap(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		for (String arg : args) {
			if (arg.contains("=")) {
				String[] aux = arg.split("=");
				map.put(aux[0], aux[1]);
			}
		}
		return map;
	}

	public static String getClassFilePath(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			return clazz.getResource(clazz.getSimpleName() + ".class").toString();
		} catch (Exception e) {
			return className + " not exist in this classpath";
		}
	}
	
	public static int getRandom() {
		return random.nextInt();
	}
	
	public static int getRandom(int min, int max) {
		return random.nextInt((max - min) + 1) + min;
	}
}
