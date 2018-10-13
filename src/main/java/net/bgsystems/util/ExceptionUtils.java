package net.bgsystems.util;

import java.util.HashMap;
import java.util.Map;

public class ExceptionUtils {
	public static String getMessage(Throwable e) {
		String message = e.getMessage();
		if (message == null) {
			if (e.getCause() != null) {
				message = ExceptionUtils.getMessage(e.getCause());
			} else {
				if (e instanceof NullPointerException) {
					message = "Valor nulo";
				} else {
					message = "Mensaje no definido";
				}
			}
		}
		return message;
	}
	
	public static String getSimpleMessage(Throwable t, boolean withTrack) {
		String msg = (t instanceof NullPointerException) ? "Error por nulidad" : t.getMessage();
		if (withTrack)
			msg += " en " + t.getStackTrace()[0].getFileName() + ":" + t.getStackTrace()[0].getLineNumber();
		return msg;
	}

	public static Map<String, Object> customJsonError(Throwable t) {
		Map<String, Object> jsonError = new HashMap<String, Object>();

		jsonError.put("type", t.getClass().getSimpleName());
		jsonError.put("stackTrace", t.getStackTrace());
		if (t.getCause() != null) {
			jsonError.put("cause", customJsonError(t.getCause()));
		}
		jsonError.put("message", ExceptionUtils.getSimpleMessage(t, false));

		return jsonError;
	}
}
