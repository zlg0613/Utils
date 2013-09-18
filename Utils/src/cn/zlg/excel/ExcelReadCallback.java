package cn.zlg.excel;

public interface ExcelReadCallback {

	public void readRow(int sheetNum,int rowNum,Object row);
}
