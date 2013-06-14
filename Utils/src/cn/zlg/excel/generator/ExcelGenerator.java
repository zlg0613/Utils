package cn.zlg.excel.generator;

import java.util.List;


public interface ExcelGenerator {

	public <T> void generate(List<T> data,String outputFile);
	
	public <T> void generate(List<T> data,AddToCellCallBack callback,String outputFile);
	
}
