package net.bgsystems.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ReflectionUtils {
	private static final Class<?>[] DIRECT_ASSIGN_CLASSES = { Calendar.class, String.class, Integer.class, Long.class,
			Double.class, Date.class, Float.class };

	public static String getMethodName(Method method) {
		return method.getName().substring(3, method.getName().length());
	}

	public static <T, I> I mapToEntity(T entity, Class<I> toClass, boolean ignoreInexistentMethods) throws Exception {
		I newEntity = null;
		try {
			newEntity = (I) toClass.newInstance();
			Class<?> fromClass = entity.getClass();

			Method[] fromClassMethods = fromClass.getMethods();
			for (Method fromClassMethod : fromClassMethods) {
				String name = fromClassMethod.getName().trim().toLowerCase().substring(0, 3);
				if (name.equals("set")) {
					Method toClassMethod = null;

					try {
						toClassMethod = toClass.getMethod(fromClassMethod.getName(),
								fromClassMethod.getParameterTypes());
					} catch (NoSuchMethodException e) {
						if (ignoreInexistentMethods)
							throw e;
					}

					if (toClassMethod != null) {
						String fromClassGetMethodName = fromClassMethod.getName();
						try {
							fromClassGetMethodName = "get"
									+ fromClassGetMethodName.substring(3, fromClassGetMethodName.length());
							Method fromClassGetMethod = fromClass.getMethod(fromClassGetMethodName, (Class<?>[]) null);
							toClassMethod.invoke(entity,
									new Object[] { fromClassGetMethod.invoke(entity, (Object[]) null) });
						} catch (NoSuchMethodException e) {
							// Esto es para los casos de tipo boolean
							fromClassGetMethodName = "is"
									+ fromClassGetMethodName.substring(3, fromClassGetMethodName.length());
							Method fromClassGetMethod = fromClass.getMethod(fromClassGetMethodName, (Class<?>[]) null);
							toClassMethod.invoke(entity,
									new Object[] { fromClassGetMethod.invoke(entity, (Object[]) null) });
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return newEntity;
	}

	public static <T> void merge(T original, T update) throws Exception {
		merge(original, update, null);
	}

	public static <T> void merge(T original, T update, Class<?>[] directAssignReturnTypes) throws Exception {
		try {
			// Arreglo para evitar analisis innecesario
			if (original == null) {
				original = update;
				return;
			} else if (update == null) {
				update = original;
				return;
			}

			Class<?> clazz = update.getClass();
			Class<?> comparativeClazz = original.getClass();
			List<Method> methods = new ArrayList<>(Arrays.asList(clazz.getMethods()));

			for (Method method : methods) {
				try {
					comparativeClazz.getMethod(method.getName(), method.getParameterTypes());
					String methodAction = method.getName().trim().toLowerCase().substring(0, 3);
					if (methodAction.equals("set")) {
						Method setMethod = method;
						Method getMethod = null;
						String methodName = ReflectionUtils.getMethodName(method);

						try {
							String getMethodName = "get" + methodName;
							getMethod = clazz.getMethod(getMethodName, (Class<?>[]) null);
						} catch (NoSuchMethodException e) {
							// Esto es para los casos de tipo boolean
							String getMethodName = "is" + methodName;
							getMethod = clazz.getMethod(getMethodName, (Class<?>[]) null);
						}

						if (getMethod.getParameterTypes() == null || getMethod.getParameterTypes().length == 0) {
							merge_methodSetter(original, update, original, getMethod, setMethod,
									directAssignReturnTypes);
						}
					}
				} catch (NoSuchMethodException e) {
					// Nothing
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public static <T> T mergeAndCopy(T original, T update) throws Exception {
		return mergeAndCopy(original, update, null);
	}

	public static <T> T mergeAndCopy(T original, T update, Class<?>[] directAssignReturnTypes) throws Exception {
		T merged = (T) original;
		ReflectionUtils.merge(merged, update, directAssignReturnTypes);
		return merged;
	}

	private static void merge_methodSetter(Object original, Object update, Object merged, Method getMethod,
			Method setMethod, Class<?>[] directAssignReturnTypes) throws Exception {
		Object originalGetResult = getMethod.invoke(original, (Object[]) null);
		Object updateGetResult = getMethod.invoke(update, (Object[]) null);

		if (updateGetResult == null) {
			setMethod.invoke(merged, new Object[] { originalGetResult });
		} else if (originalGetResult == null) {
			setMethod.invoke(merged, new Object[] { updateGetResult });
		} else if (!isEqual(originalGetResult, updateGetResult)) {
			Class<?> clazz = getMethod.getReturnType();

			List<Class<?>> list_directAssignReturnTypes = new ArrayList<>();
			list_directAssignReturnTypes.addAll(Arrays.asList(DIRECT_ASSIGN_CLASSES));
			if (directAssignReturnTypes != null)
				list_directAssignReturnTypes.addAll(Arrays.asList(directAssignReturnTypes));

			for (Class<?> c : list_directAssignReturnTypes) {
				if (c.isAssignableFrom(clazz)) {
					setMethod.invoke(merged, new Object[] { updateGetResult });
					return;
				}
			}

			if (clazz.isArray()) {
				Object[] originalGetArray = (Object[]) originalGetResult;
				Object[] updateGetArray = (Object[]) updateGetResult;
				List<Object> mergedGetList = new ArrayList<>();
				for (Object originalElement : originalGetArray) {
					for (Object updatedElement : updateGetArray) {
						mergedGetList.add(ReflectionUtils.mergeAndCopy(originalElement, updatedElement));
					}
				}
				setMethod.invoke(merged, new Object[] { mergedGetList.toArray() });
			} else if (Collection.class.isAssignableFrom(clazz)) {
				Collection<?> originalGetCollection = (Collection<?>) originalGetResult;
				Collection<?> updateGetCollection = (Collection<?>) updateGetResult;
				Collection<Object> mergedGetList = (Collection<Object>) originalGetResult.getClass().newInstance();
				for (Object originalElement : originalGetCollection) {
					for (Object updatedElement : updateGetCollection) {
						mergedGetList.add(ReflectionUtils.mergeAndCopy(originalElement, updatedElement));
					}
				}
				setMethod.invoke(merged, new Object[] { mergedGetList });
			} else if (clazz.isPrimitive() || clazz.isEnum()) {
				setMethod.invoke(merged, new Object[] { updateGetResult });
			} else {
				setMethod.invoke(merged,
						new Object[] { ReflectionUtils.mergeAndCopy(originalGetResult, updateGetResult) });
			}
		} else {
			setMethod.invoke(merged, new Object[] { originalGetResult });
		}
	}

	private static boolean isEqual(Object o1, Object o2) {
		if (o1 == null || o2 == null) {
			return false;
		}

		if (!o1.getClass().equals(o2.getClass())) {
			return false;
		}

		if (o1.getClass().isArray()) {
			return Arrays.deepEquals((Object[]) o1, (Object[]) o2);
		}

		if (o1 instanceof Collection<?>) {
			return ((Collection<?>) o1).containsAll((Collection<?>) o2)
					&& ((Collection<?>) o2).containsAll((Collection<?>) o1);
		}

		return o1.equals(o2);
	}

	public static <T, X> T copy(T from, Class<T> to, boolean ignoreNonExistingMethods) throws Exception {
		String[] strGetterPrefix = { "get", "is" };
		String[] strSetterPrefix = { "set" };

		try {
			List<Method> fromMethods = Arrays.asList(from.getClass().getMethods());

			T toInstance = to.newInstance();

			for (Method fromMethod : fromMethods) {
				String fromMethodName = fromMethod.getName();

				String getterPrefix = null;
				for (String prefix : strGetterPrefix) {
					if (fromMethodName.startsWith(prefix)) {
						getterPrefix = prefix;
						break;
					}
				}

				if (getterPrefix != null) {
					String setterSufix = fromMethodName.substring(getterPrefix.length(), fromMethodName.length());
					Method toMethod = null;
					for (String prefix : strSetterPrefix) {
						try {
							String toMethodName = prefix + setterSufix;
							toMethod = to.getMethod(toMethodName, new Class<?>[] { fromMethod.getReturnType() });
							break;
						} catch (NoSuchMethodException e) {
							if (!ignoreNonExistingMethods)
								throw e;
						}
					}

					if (toMethod != null) {
						toMethod.invoke(toInstance, fromMethod.invoke(from, new Object[] {}));
					}
				}
			}
			return toInstance;
		} catch (Exception t) {
			throw t;
		}
	}

	public static <T> T clone(T from) throws Exception {
		return ReflectionUtils.copy(from, (Class<T>) from.getClass(), false);
	}
}
