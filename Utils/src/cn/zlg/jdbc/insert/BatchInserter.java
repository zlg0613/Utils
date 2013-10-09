package cn.zlg.jdbc.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import cn.zlg.util.jdbc.insert.ParameterSetter;


public class BatchInserter {
	private static int DEFAULT_BATCH_SIZE = 50;
	
	public static <T> int batchInsert(Connection con,String sql,List<T> data,ParameterSetter<T> setter){
		return batchInsert(con,sql,data,setter,DEFAULT_BATCH_SIZE);
	}
	public static <T> int batchInsert(Connection con,String sql,List<T> data,ParameterSetter<T> setter,int batchSize){
		try {
			int count = 0;
			PreparedStatement pst = con.prepareStatement(sql);
			count = executeBatch(pst,data,setter,batchSize);
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static <T> void batchInsertTransactional(Connection con,String sql,List<T> data,ParameterSetter<T> setter) throws BatchInsertException{
		batchInsertTransactional(con,sql,data,setter,DEFAULT_BATCH_SIZE,true);
	}
	
	public static <T> void batchInsertTransactional(Connection con,String sql,List<T> data,ParameterSetter<T> setter,int batchSize,boolean closeConnection) throws BatchInsertException{
		try {
			con.setAutoCommit(false);
			PreparedStatement pst = con.prepareStatement(sql);
			executeBatch(pst,data,setter,batchSize);
			con.commit();
			if(closeConnection){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BatchInsertException("插入数据出现异常",e);
		}
	}
	
	private static <T> int executeBatch(PreparedStatement pst,List<T> list, ParameterSetter<T> p,int batchSize) throws SQLException {
		if(list==null||list.size()==0)
			return 0;
		int count = 0;
		int i=0;
		Iterator<T> it = list.listIterator();
		while(it.hasNext()){
			p.setParameter(it.next(), pst);
			pst.addBatch();
			i++;
			if(i>=batchSize){
				int[] results = pst.executeBatch();
				count += countPositiveNum(results);
			}
		}
		int[] results = pst.executeBatch();
		count += countPositiveNum(results);
		return count;
	}
	
	private static int countPositiveNum(int[] arr){
		int result = 0;
		for(int a:arr){
			if(a>0){
				result ++;
			}
		}
		return result;
	}
}
