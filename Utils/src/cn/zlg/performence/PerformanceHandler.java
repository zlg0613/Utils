package cn.zlg.performence;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import cn.zlg.util.Timer;

/**
 * 使用JDK动态代理创建代理实例,缺点是只能对接口进行创建代理
 * @author m618
 *
 */
public class PerformanceHandler implements InvocationHandler {

	    private Object target;  
	    public PerformanceHandler(Object target){ //②target为目标的业务类  
	        this.target = target;  
	    }  
	    
	    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {  
	    	Timer t = new Timer();
	    	String name = target.getClass().getName()+"."+ method. getName();
	        Object obj = method.invoke(target, args);// ③-2通过反射方法调用业务类的目标方法  
	        t.end();
	        System.out.println("invoke "+ name + " use "+t.time()+" ms");
	        return obj;  
	    } 

}
