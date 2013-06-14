package cn.zlg.excel.generator;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class RowDef {

	private HSSFRow row;
	private Map<String,Integer> columnTitleToIndexMap;
	private Map<Integer,String> indexToColumnTitleMap;
	
	public int getRowIndex(){
		return row.getRowNum();
	}
	public HSSFRow getRow() {
		return row;
	}
	public void setRow(HSSFRow row) {
		this.row = row;
	}
	public void setColumnTitleToIndexMap(Map<String, Integer> columnTitleToIndexMap) {
		this.columnTitleToIndexMap = columnTitleToIndexMap;
	}
	public void setIndexToColumnTitleMap(Map<Integer, String> indexToColumnTitleMap) {
		this.indexToColumnTitleMap = indexToColumnTitleMap;
	}
	
	public HSSFCell getCell(int col){
		return row.getCell(col);
	}
	
	public HSSFCell getCell(String colName){
		return row.getCell(columnTitleToIndexMap.get(colName));
	}
	
	public String getColumnTitle(int col){
		return indexToColumnTitleMap.get(col);
	}
}
