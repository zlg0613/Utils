package cn.zlg.util.jdbc.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import cn.zlg.util.New;
import cn.zlg.util.jdbc.base.ConnectionFactory;

public class QueryUtils {

	public static int getMaxIdFrom(Connection con,String key,String table){
		String sql = "select max("+key+") from "+table;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.first()){
				return rs.getInt(1);
			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Integer.MIN_VALUE;
	}
	public static int getMaxIdFrom(String key,String table){
		return getMaxIdFrom(ConnectionFactory.getConnection(),key,table);
	}
	
	public static void executeAnySQL(String sql){
		Connection con = ConnectionFactory.getConnection();
		try {
			Statement st = con.createStatement();
			st.execute(sql);
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static <T> List<T> query(String sql,RowMapper<T> mapper){
		Connection con = ConnectionFactory.getConnection();
		return query(con,sql,mapper);
	}
	public static <T> List<T> query(Connection con,String sql,RowMapper<T> mapper){
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			List<T> list = New.arraylist();
			int i=0;
			while(rs.next()){
				list.add(mapper.map(rs, i++));
			}
			st.close();
			con.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static long truncateTable(String tableName){
		Connection con = ConnectionFactory.getConnection();
		String sql = "truncate table "+tableName;
		int j  = 0;
		try{
			Statement statement = con.createStatement();
			j = statement.executeUpdate(sql);
			con.close();
		} catch (SQLException e){
			e.printStackTrace();
		}
		return j;
	}
	/**
	 * delete all records in a table
	 * @param tableName
	 * @return
	 */
	public static long cleanTable(String tableName){
		Connection con = ConnectionFactory.getConnection();
		String sql = "delete from "+tableName;
		int j  = 0;
		try{
			Statement statement = con.createStatement();
			j = statement.executeUpdate(sql);
			con.close();
		} catch (SQLException e){
			e.printStackTrace();
		}
		return j;
	}
	
	public int queryForCountAll(String table){
		Connection con = ConnectionFactory.getConnection();
		String sql = "select count(*) from "+table;
		int i=-1;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.first()){
				i = rs.getInt(1);
			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public static void query(String sql,DoWhileLoadCallBack cb){
		Connection con = ConnectionFactory.getConnection();
		 query(con,sql,cb);
	}
	public static void query(Connection con,String sql,DoWhileLoadCallBack cb){
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			int i=0;
			while(rs.next()){
				cb.doSomething(rs, i++);
			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
