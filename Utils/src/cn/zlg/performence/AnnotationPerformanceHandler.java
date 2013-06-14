package cn.zlg.performence;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.zlg.util.Timer;

public class AnnotationPerformanceHandler {

	public static <T> void watch(T t) throws Exception{
		watch(t,null);
	}
	public static <T> void watch(T t,Object[][] args) throws Exception{
		Class c = t.getClass();
		Method[] ms = c.getDeclaredMethods();
		int i = 0;
		List<Pair> list = new ArrayList<Pair>();
		for(Method m:ms){
			PerformanceWatch pw = m.getAnnotation(PerformanceWatch.class);
			if(pw != null && m.getParameterTypes().length > 0){
				list.add(new Pair(m,args[i]));
				i++;
			}else if(pw != null ){
				list.add(new Pair(m,null));
			}
		}
		for(Pair p:list){
			invoke(t,p.m,p.args);
		}
	}
	
	private static <T> Object invoke(T target ,Method m,Object[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Timer t = new Timer();
    	String name = target.getClass().getName()+"."+ m. getName();
    	System.out.println("invoke "+ name );
    	Object obj;
//    	if(args == null){
//    		obj = m.invoke(target,new Object[0]);
//    	}else{
    		obj = m.invoke(target, args);// ③-2通过反射方法调用业务类的目标方法  
//    	}
        t.end();
        System.out.println("invoke "+ name + " use "+t.time()+" ms");
        return obj;
	}
	
	private static class Pair{
		Pair(Method m,Object[] args){
			this.m = m;
			this.args = args;
		}
		Method m;
		Object[] args;
	}
}
