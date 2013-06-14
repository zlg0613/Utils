package cn.zlg.util.jdbc.insert;

import java.util.List;

import cn.zlg.util.jdbc.base.ConnectionFactory;


public class SimpleInsertUtils {

	public static <T> void jdbcBatchInsert(String sql,int batchSize,List<T> list,ParameterSetter<T> setter){
		InsertRecordJDBC irj = new InsertRecordJDBC(ConnectionFactory.getConnection());
		irj.setSql(sql);
		if(batchSize>0){
			irj.setBatchSize(batchSize);
		}
		irj.addBatch(list, setter);
		irj.flush();
		irj.close();
	}
}
