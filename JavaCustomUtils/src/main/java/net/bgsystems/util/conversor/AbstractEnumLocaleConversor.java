package net.bgsystems.util.conversor;

import java.util.Properties;
import java.util.Map.Entry;

public abstract class AbstractEnumLocaleConversor {
	protected Properties props;
	
	public String enumToString(Class<?> clazz, Enum<?> enumerator) {
		return props.getProperty(clazz.getName() + "." + enumerator.name());
	}

	public <E extends Enum<E>> Enum<E> stringToEnum(Class<E> clazz, String stringValue) {
		Object[] enumConstants = clazz.getEnumConstants();
		if (clazz.getEnumConstants() == null)
			throw new ClassCastException("");
		if (!props.containsValue(stringValue))
			throw new NullPointerException("");

		for (Entry<Object, Object> entry : props.entrySet()) {
			if (entry.getKey().toString().startsWith(clazz.getName())
					&& entry.getValue().toString().equals(stringValue)) {
				for(Object cons : enumConstants) {
					Enum<E> enumValue = (Enum<E>) cons;
					if(entry.getKey().toString().endsWith(enumValue.name()))
						return enumValue;
				}
			}
		}
		
		return null;
	}
}
