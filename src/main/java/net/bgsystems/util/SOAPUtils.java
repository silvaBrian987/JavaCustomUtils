package net.bgsystems.util;

import java.util.Calendar;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

public class SOAPUtils {
	public static void setContext_JAXWS(Object port, String endpoint, String username, String password) {
		Map<String, Object> req_ctx = ((BindingProvider) port).getRequestContext();
		req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
		req_ctx.put(BindingProvider.USERNAME_PROPERTY, username);
		req_ctx.put(BindingProvider.PASSWORD_PROPERTY, password);
	}
	
	public static XMLGregorianCalendar getXMLGregorianCalendar(Calendar calendar) throws DatatypeConfigurationException {
		XMLGregorianCalendar newXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
		newXMLGregorianCalendar.setYear(calendar.get(Calendar.YEAR));
		newXMLGregorianCalendar.setMonth(calendar.get(Calendar.MONTH) + 1);
		newXMLGregorianCalendar.setDay(calendar.get(Calendar.DAY_OF_MONTH));
		newXMLGregorianCalendar.setHour(calendar.get(Calendar.HOUR));
		newXMLGregorianCalendar.setMinute(calendar.get(Calendar.MINUTE));
		newXMLGregorianCalendar.setSecond(calendar.get(Calendar.SECOND));
		newXMLGregorianCalendar.setMillisecond(calendar.get(Calendar.MILLISECOND));
		return newXMLGregorianCalendar;
	}
}
