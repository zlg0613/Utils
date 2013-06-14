package cn.zlg.performence;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CglibAnnotationPerformanceHandler {

	private static CglibProxy proxy = new CglibProxy();
	
	public static <T> void watch(T t){
		watch(t,null);
	}
	
	public static <T> List<Pair> buildPair(T origin,T proxy,Object[][] args){
		Class pc = proxy.getClass();
		Class oc = origin.getClass();
		
		Method[] originMethods = oc.getDeclaredMethods();
		Method[] proxyMethods = pc.getDeclaredMethods();
		int i = 0;
		List<Pair> list = new ArrayList<Pair>();
		for(Method m:originMethods){
			PerformanceWatch pw = m.getAnnotation(PerformanceWatch.class);
			if(pw != null&& pw.value()){
				for(Method pm:proxyMethods){
					if(judgeMethod(m,pm)){
						if( m.getParameterTypes().length > 0){
							list.add(new Pair(pm,args[i]));
							i++;
						}else{
							list.add(new Pair(pm,null));
						}
						break;
					}
				}
			}
//			System.out.println(m.getName());
		}
		return list;
	}
	
	private static boolean judgeMethod(Method m, Method pm) {
		if(!m.getName().equals(pm.getName())){
			return false;
		}
		Class[] mp = m.getParameterTypes();
		Class[] pmp = pm.getParameterTypes();
		if(mp.length!=pmp.length){
			return false;
		}
		for(int i=0;i<mp.length;i++){
			if(!mp[i].equals(pmp[i])){
				return false;
			}
		}
		return true;
	}

	public static <T> void watch(T t,Object[][] args){
		Class c = t.getClass();
		T target = (T)proxy.getProxy(c);
		List<Pair> list = buildPair(t,target,args);
		for(Pair p:list){
			invoke(target,p.m,p.args);
		}
	}
	private static <T> Object invoke(T target ,Method m,Object[] args){
    	Object obj = null;
    	try {
			obj = m.invoke(target, args);
		} catch (Exception e) {
			e.printStackTrace();
		}// ③-2通过反射方法调用业务类的目标方法  
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
