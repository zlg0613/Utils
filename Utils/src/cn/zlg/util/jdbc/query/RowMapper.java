package cn.zlg.util.jdbc.query;

import java.sql.ResultSet;

public interface RowMapper<T> {

	public T map(ResultSet rs,int orderNum);
}
