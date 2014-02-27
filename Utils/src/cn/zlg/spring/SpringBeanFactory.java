package cn.zlg.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBeanFactory {
	
	private static final ApplicationContext context;
	
	static{
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	public static <T> T getBean(Class<T> c){
		return context.getBean(c);
	}
	
	public static Object getBean(String name){
		return context.getBean(name);
	}
	
	public static ApplicationContext getContext(){
		return context;
	}
}
