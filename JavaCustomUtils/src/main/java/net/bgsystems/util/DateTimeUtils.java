package net.bgsystems.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
	
	public static String dateToString(Date date) {
		return DateTimeUtils.dateToString(date, DEFAULT_DATE_FORMAT);
	}
	
	public static String dateToString(Date date, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}
}
