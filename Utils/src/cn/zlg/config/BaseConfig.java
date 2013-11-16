package cn.zlg.config;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import cn.zlg.util.New;

public class BaseConfig {
	protected Map<String,String> params = New.hashMap();
	
	
	public BaseConfig(){}
	
	public BaseConfig(Properties p){
		try {
			Iterator<Entry<Object,Object>> it = p.entrySet().iterator();
			while(it.hasNext()){
				Entry<Object,Object> e = it.next();
				params.put((String)e.getKey(),(String)e.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BaseConfig(String propertiesFile){
		this(BaseConfig.class.getClassLoader().getResourceAsStream(propertiesFile));
	}
	
	public BaseConfig(InputStream is){
		Properties p = new Properties();
		try {
			p.load(is);
			Iterator<Entry<Object,Object>> it = p.entrySet().iterator();
			while(it.hasNext()){
				Entry<Object,Object> e = it.next();
				params.put((String)e.getKey(),(String)e.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void add(String key,String value){
		this.params.put(key,value);
	}
	
	public void addAll(Map<String,String> values){
		this.params.putAll(values);
	}
	public Map<String,String> getParamMap(){
		return params;
	}
	
	public Long getLong(String key){
		String v = params.get(key);
		return v==null?null:Long.parseLong(v);
	}
	
	/**
	 * 带有默认值的方法，如果获取不到配置项，则取默认值
	 * @param key 配置项值
	 * @param defaultValue 配置项不存在时的默认值
	 * @return
	 */
	public Long getLong(String key,Long defaultValue){
		String v = params.get(key);
		return v==null?defaultValue:Long.parseLong(v);
	}
	
	public Boolean getBoolean(String key){
		String v = params.get(key);
		return v==null?null:Boolean.parseBoolean(v);
	}
	public Boolean getBoolean(String key,Boolean defaultValue){
		String v = params.get(key);
		return v==null?defaultValue:Boolean.parseBoolean(v);
	}
	public Short getShort(String key){
		String v = params.get(key);
		return v==null?null:Short.parseShort(v);
	}
	public Short getShort(String key,Short defaultValue){
		String v = params.get(key);
		return v==null?defaultValue:Short.parseShort(v);
	}
	public Double getDouble(String key){
		String v = params.get(key);
		return v==null?null:Double.parseDouble(v);
	}
	public Double getDouble(String key,Double defaultValue){
		String v = params.get(key);
		return v==null?defaultValue:Double.parseDouble(v);
	}
	public Float getFloat(String key){
		String v = params.get(key);
		return v==null?null:Float.parseFloat(v);
	}
	public Float getFloat(String key,Float defaultValue){
		String v = params.get(key);
		return v==null?defaultValue:Float.parseFloat(v);
	}
	public Byte getByte(String key){
		String v = params.get(key);
		return v==null?null:Byte.parseByte(v);
	}
	public Byte getByte(String key,Byte defaultValue){
		String v = params.get(key);
		return v==null?defaultValue:Byte.parseByte(v);
	}
	public Integer getInt(String key){
		String v = params.get(key);
		return v==null?null:Integer.parseInt(v);
	}
	public Integer getInt(String key,Integer defaultValue){
		String v = params.get(key);
		return v==null?defaultValue:Integer.parseInt(v);
	}
	
	public String get(String key){
		return params.get(key);
	}
	
	public String get(String key,String defaultValue){
		String string = params.get(key);
		return string==null?defaultValue:string;
	}
}
