package cn.zlg.util.jdbc.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import cn.zlg.util.New;

public class MultiSourceConnectionFactory {
	static Map<String,ConDef> dataSourceMap = New.hashMap();
	private static Properties p ;
	
	static{
		init();
	}
	static void addToMap(String sourceName,String type,String val){
		if(dataSourceMap.get(sourceName)==null){
			ConDef con = new ConDef();
			addToCon(con,type,val);
			dataSourceMap.put(sourceName, con);
		}else{
			addToCon(dataSourceMap.get(sourceName),type,val);
		}
	}
	private static void addToCon(ConDef con,String type, String val) {
		if("url".equals(type)){
			con.url = val;
		}else if("password".equals(type)){
			con.password = val;
		}else if("username".equals(type)){
			con.user = val;
		}else if("driverClassName".equals(type)){
			con.driverName = val;
			try {
				Class.forName(val);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	static void init(){
		try {
			p = new Properties();
			p.load(MultiSourceConnectionFactory.class.getClassLoader().getResourceAsStream("jdbc.properties"));
			for(Entry e:p.entrySet()){
				String key = (String)e.getKey();
				String val = (String)e.getValue();
				String[] ss = key.split("\\.");
				String sourceName = "default";
				if(!"jdbc".equals(ss[0])){
					sourceName = ss[0];
				}
//				if("jdbc".equals(ss[1])){
//					addToMap(sourceName,ss[ss.length-1],val);
//				}
					addToMap(sourceName,ss[ss.length-1],val);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取默认的数据库连接，默认的连接配置形式如下：
	 * jdbc.driverClassName=com.mysql.jdbc.Driver
		jdbc.url=jdbc:mysql://10.105.22.202:3306/bus?characterEncoding=utf8
		jdbc.username=bus
		jdbc.password=bus
	 * @return
	 */
	public static Connection getConnection(){
		return getConnection("default");
	}
	
	private static Connection getConnection(ConDef cd){
		try {
			Connection conn = DriverManager.getConnection(cd.url,cd.user,cd.password);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取指定名称的数据源的数据库连接，如连接配置形式如下：
	 * zlg.jdbc.driverClassName=com.mysql.jdbc.Driver
		zlg.jdbc.url=jdbc:mysql://10.105.22.202:3306/bus?characterEncoding=utf8
		zlg.jdbc.username=bus
		zlg.jdbc.password=bus
		可用MultiSourceConnectionFactory.getConnection("zlg")获取zlg数据源的连接
	 * @return
	 */
	public static Connection getConnection(String sourceName){
		ConDef cd = dataSourceMap.get(sourceName);
		return getConnection(cd);
	}
	
	static class ConDef{
		String url ;
		String user ;
		String password ;
		String driverName ;
	}
}
