package cn.zlg.performence;

import java.lang.reflect.Proxy;

public class ProxyBuilder {

	/**
	 * 创建动态代理的实例
	 * @param t
	 * @return
	 */
	public static <T> T build(T t){
		PerformanceHandler handler = new PerformanceHandler(t);
		return (T) Proxy.newProxyInstance(    
                t.getClass().getClassLoader(),  
                t.getClass().getInterfaces(),  
                handler);  
	}	
}
