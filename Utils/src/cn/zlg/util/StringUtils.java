package cn.zlg.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StringUtils {
	
	public static long MS_IN_HOUR = 60*60*1000;
	public static long MS_IN_MINUTE = 60*1000;
	public static long MS_IN_SECOND = 1000;
	
	public static String firstCharToUpperCase(String w){
		if(w.charAt(0)>='a'&&w.charAt(0)<='z'){
			String nw = (char)(w.charAt(0)-32)+w.substring(1);
			return nw;
		}
		return w;
	}
	
	public static void assertNotNullOrEmpty(String value,String msg){
		if(value==null||value.trim().equals("")){
			throw new RuntimeException(msg);
		}
	}
	
	public static boolean nullOrEmpty(String s){
		if(s==null || s.trim().equals("")){
			return true;
		}
		return false;
	}
	
	public static boolean notEmptyString(String s){
		if(s==null || s.trim().equals("")){
			return false;
		}
		return true;
	}
	
	public static String firstCharUppercase(String s){
		if(s==null || s.equals("")){
			return s;
		}
		char c = s.charAt(0);
		if(c>='a' && c<='z'){
			c = (char) (c - 32);
			char[] array = s.toCharArray();
			array[0] = c;
			return new String(array);
		}
		return s;
	}
	
	public static String trim(String src,String toTrim){
		while(src.startsWith(toTrim)){
			src = src.substring(toTrim.length());
		}
		while(src.endsWith(toTrim)){
			src = src.substring(0,src.length()-toTrim.length());
		}
		return src;
	}
	
	/**
	 * 
	 * @param map 需要拼接字符串的Map
	 * @param kvConnector key,value之间的连接符
	 * @param separator   一个key-value对之间的链接符
	 * @param reverse   是否将key-value对颠倒
	 * @return
	 */
	public static String concat(Map map,String kvConnector,String separator,boolean reverse){
		if(map==null){
			return null;
		}
		StringBuilder sb = new StringBuilder(map.size()*10);
		Iterator it= map.keySet().iterator();
		String k;
		String v;
		while(it.hasNext()){
			Object key = it.next();
			if(reverse){
				k = map.get(key).toString();
				v = key.toString();
			}else{
				v = map.get(key).toString();
				k = key.toString();
			}
			sb.append(k).append(kvConnector).append(v).append(separator);
		}
		return sb.toString();
	}
	
	public static String concat(Collection col,String separator){
		return concat(col.toArray(),separator);
	}
	public static String concat(List list,char separator){
		return concat(list,separator+"");
	}
	public static String concat(List list,String separator){
		if(list==null||list.size()==0)
			return null;
		return concat(list.toArray(),separator);
	}
	public static String concat(Object[] list,char separator){
		return concat(list,separator+"");
	}
	public static String concat(Object[] list,String separator){
		if(list==null||list.length==0)
			return null;
		StringBuilder sb = new StringBuilder(list.length*20);
		int i;
		for(i=0;i<list.length-1;i++){
			sb.append(list[i].toString());
			sb.append(separator);
		}
		sb.append(list[i]);
		return sb.toString();
	}
	
	/**
	 * 格式化时间，输入毫秒值，输出  x小时y分钟z秒p毫秒
	 * @param ms 输入的毫秒值
	 * @return 格式化的时间字符串
	 */
	public static String formatTime(long ms){
		StringBuilder sb = new StringBuilder(20);
		long hour = ms/MS_IN_HOUR;
		if(hour>0){
			sb.append(hour).append("小时");
			ms = ms%MS_IN_HOUR;
		}
		long minute = ms/MS_IN_MINUTE;
		if(minute>0||hour>0){
			sb.append(minute).append("分钟");
			ms = ms%MS_IN_MINUTE;
		}
		long second = ms/MS_IN_SECOND;
		if(second>0||hour>0||minute>0){
			sb.append(second).append("秒");
			ms = ms%MS_IN_SECOND;
		}
		sb.append(ms).append("毫秒");
		return sb.toString();
	}
	
	public static String getTimestamp(){
		return getTimestamp(new Date());
	}
	
	public static String getTimestamp(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(date);
		return timestamp;
	}
	
	public static List<String> split(String str,String separatorRegex){
		if(nullOrEmpty(str)){
			return null;
		}
		String[] ss = str.split(separatorRegex);
		return Arrays.asList(ss);
	}
}
