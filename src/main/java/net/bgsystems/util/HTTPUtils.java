package net.bgsystems.util;

import java.util.HashMap;
import java.util.Map;

public class HTTPUtils {
	public static Map<String, String> getNoCacheHeaders() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.put("Pragma", "no-cache");
		headers.put("Expires", "0");

		return headers;
	}

	public static Map<String, String> getCORSHeaders() {
		Map<String, String> headers = new HashMap<String, String>();

		headers.put("Access-Control-Allow-Origin", "*");
		headers.put("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		headers.put("Access-Control-Allow-Credentials", "true");
		headers.put("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");

		return headers;
	}
}
