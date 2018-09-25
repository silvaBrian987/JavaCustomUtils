package net.bgsystems.util.spring;

import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;

public abstract class SpringBeanUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
	public static Object getBean(String name) {
		return context.getBean(name);
	}
	
	public static <T> T getBean(String name, Class<T> clazz) {
		return (T) context.getBean(clazz);
	}

	public static <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}
	
	public static <T> T getBean(Class generic, Class<T> entity) {
		String[] names = context.getBeanNamesForType(ResolvableType.forClassWithGenerics(generic, entity));
		return (T) context.getBean(names[0], generic);
	}
	
	public static <T> T getOriginalBean(T proxy) throws Exception {
		return (T) ((Advised)proxy).getTargetSource().getTarget();
	}
}