package cn.zlg.util.jdbc.insert;

import java.sql.SQLException;
import java.util.List;


public interface InsertRecords
{
	public <T> void addBatch(T t,ParameterSetter<T> p);
	public void flush();
	public void close();
	public void setBatchSize(int size);
	public <T> void addBatch(List<T> list, ParameterSetter<T> p);
	public void setSql(String sql)  ;
}
