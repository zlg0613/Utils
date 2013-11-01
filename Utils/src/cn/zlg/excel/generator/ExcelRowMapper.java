package cn.zlg.excel.generator;

import java.util.Map;

public interface ExcelRowMapper<T> {

	public Map<Integer,Object> map(T t);
	
	public int getCols(T t);
}
