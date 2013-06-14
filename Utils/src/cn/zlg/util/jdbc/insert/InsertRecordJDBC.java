package cn.zlg.util.jdbc.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;


public class InsertRecordJDBC extends AbstractInsertRecords {

	private Connection con;
	private PreparedStatement pst;
	
	public InsertRecordJDBC() {
		super();
	}
	public InsertRecordJDBC(Connection con) {
		this.con = con;
	}
	
	public void setSql(String sql){
		super.sql = sql;
		try {
			pst = con.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		count=0;
	}
	@Override
	public <T> void addBatch(T t, ParameterSetter<T> p) {
		p.setParameter(t, pst);
		try {
			pst.addBatch();
			count++;
			if(count>=super.batchSize){
				flush();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public <T> void addBatch(List<T> list, ParameterSetter<T> p) {
		if(list==null||list.size()==0)
			return;
		Iterator<T> it = list.listIterator();
		while(it.hasNext()){
			this.addBatch(it.next(), p);
		}
	}
	@Override
	public void close() {
		try {
			if(con!=null&&!con.isClosed()){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void flush() {
		try {
			pst.executeBatch();
			pst.clearBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		count=0;

	}

}
