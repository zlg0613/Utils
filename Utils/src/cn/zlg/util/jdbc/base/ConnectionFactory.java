package cn.zlg.util.jdbc.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	static String url ;
	static String user ;
	static String password ;
	static String driverName ;
	private static Properties p ;
	static{
		try {
			p = new Properties();
			p.load(ConnectionFactory.class.getClassLoader().getResourceAsStream("jdbc.properties"));
			url = p.getProperty("jdbc.url");
			user = p.getProperty("jdbc.username");
			password = p.getProperty("jdbc.password");
			driverName = p.getProperty("jdbc.driverClassName");
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection(){
		try {
			Connection con = DriverManager.getConnection(url,user,password);
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
