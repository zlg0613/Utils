package cn.zlg.performence;

import java.lang.reflect.Method;

import cn.zlg.util.Timer;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {

	 private Enhancer enhancer = new Enhancer();  
	 
	 /**
	  * 获取Cglib动态代理实例
	  * @param clazz 希望代理的类
	  * @return
	  */
	    public Object getProxy(Class clazz) {  
	        enhancer.setSuperclass(clazz); //① 设置需要创建子类的类  
	        enhancer.setCallback(this);   
	        return enhancer.create(); //②通过字节码技术动态创建子类实例  
	    }  
	    
	@Override
	public Object intercept(Object target, Method method, Object[] arg2,
			MethodProxy proxy) throws Throwable {
		PerformanceWatch pw = method.getAnnotation(PerformanceWatch.class);
		if(pw!=null){
			Timer t = new Timer();
//			String name = target.getClass().getName()+"."+ method. getName();
			System.out.println("*****Start invoke "+ method.toString() + "*****" );
//			System.out.println("*****"+ method.toString() + "()*****" );
			Object result=proxy.invokeSuper(target,arg2 );
			t.end();
			System.out.println("*****Invoke "+ method.toString() + " use "+t.time()+" ms*****");
			return result;
		}else{
			return proxy.invokeSuper(target, arg2);
		}
	}

}
