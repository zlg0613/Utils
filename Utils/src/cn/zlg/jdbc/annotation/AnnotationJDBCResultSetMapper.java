package cn.zlg.jdbc.annotation;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.zlg.util.New;
import cn.zlg.util.StringUtils;
import cn.zlg.util.jdbc.query.DoWhileLoadCallBack;
import cn.zlg.util.jdbc.query.QueryUtils;

public class AnnotationJDBCResultSetMapper<T> {

	private Map<String,Field> map = New.hashMap();
	private String tableName;
	private String condition;
	private Class c ;
	private String keyColumn;
	
	public Map queryForMap(T t){
		parseFieldMap(t);
		if(StringUtils.nullOrEmpty(keyColumn)){
			throw new IllegalArgumentException("没有定义Map的键");
		}
		List<T> list = queryForList(t);
		if(list==null||list.size()==0){
			return null;
		}
		Map result = New.hashMap();
		for(T obj:list){
			try {
				result.put(map.get(keyColumn).get(obj), obj);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	void parseFieldMap(T t){
		c = t.getClass();
		map = getColumnFieldMap();
		 
		if(map==null||map.size() == 0){
			throw new IllegalArgumentException("无有效的列映射");
		}
	}
	
	
	List<T> queryForList(T t){
		final List<T> list = New.arraylist();
		String sql = resolveSql();
		QueryUtils.query(sql, new DoWhileLoadCallBack() {
			
			@Override
			public void doSomething(ResultSet rs, int rowNum) {
				try {
					T t =  (T) c.newInstance();
					Iterator<String> it = map.keySet().iterator();
					while(it.hasNext()){
						String col = it.next();
						Field f = map.get(col);
						Class type = f.getType();
						if(type.equals(int.class)||type.equals(Integer.class)){
							f.setInt(t, rs.getInt(col));
						}else if(type.equals(String.class)){
							f.set(t, rs.getString(col));
						}else if(type.equals(short.class)||type.equals(Short.class)){
							f.setShort(t, rs.getShort(col));
						}else if(type.equals(boolean.class)||type.equals(Boolean.class)){
							f.setBoolean(t, rs.getBoolean(col));
						}else if(type.equals(long.class)||type.equals(Long.class)){
							f.setLong(t, rs.getLong(col));
						}else if(type.equals(byte.class)||type.equals(Byte.class)){
							f.setByte(t, rs.getByte(col));
						}else if(type.equals(float.class)||type.equals(Float.class)){
							f.setFloat(t, rs.getFloat(col));
						}else if(type.equals(double.class)||type.equals(Double.class)){
							f.setDouble(t, rs.getDouble(col));
						}else{
							f.set(t, rs.getString(col));
						}
					}
					list.add(t);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return list;
	}
	
	/**
	 * 映射的类型必须是基本类型或其包装类型或者String类型
	 * @param t
	 * @return
	 */
	public List<T> query(T t){
		parseFieldMap(t);
		return queryForList(t);
	}
	
	public List<T> query(T t,String tableName){
		parseFieldMap(t);
		this.tableName = tableName;
		return queryForList(t);
	}
	
	private String resolveSql(){
		StringBuilder sb = new StringBuilder("select ");
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			sb.append(it.next()).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(" from ").append(tableName);
		if(!StringUtils.nullOrEmpty(tableName)){
			sb.append(" ").append(condition);
		}
		return sb.toString();
	}
	
	private Map<String,Field> getColumnFieldMap(){
		Table table = (Table) c.getAnnotation(Table.class);
		if(table != null){
			 tableName = table.name();
			 condition = table.condition();
			Field[] fs = c.getDeclaredFields();
			for(Field f:fs){
				Column col = f.getAnnotation(Column.class);
				if(col != null){
					map.put(col.value(), f);
					if(!f.isAccessible()){
						f.setAccessible(true);
					}
					if(col.key()){
						keyColumn = col.value();
					}
				}
			}
			return map;
		}
		return null;
	}
}
