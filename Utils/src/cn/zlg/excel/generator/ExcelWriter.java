package cn.zlg.excel.generator;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ExcelWriter {

	public static <T> void write(List<T> data,ExcelRowMapper<T> rm,String file){
		try {
			FileOutputStream dicFos = new FileOutputStream(file);
			HSSFWorkbook writeWB = new HSSFWorkbook();
			HSSFSheet writeSheet = writeWB.createSheet();
			int currentRow = 0;
			for(T t:data){
				addToRow(t,currentRow,writeSheet,rm);
				currentRow ++;
			}
			writeWB.write(dicFos);
			dicFos.flush();
			dicFos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static <T> void addToRow(T obj,int currentRow,HSSFSheet sheet,ExcelRowMapper<T> callback){
		HSSFRow row = createRow(sheet,currentRow,callback.getCols(obj));
		Map<Integer, Object> map = callback.map(obj);
		for(Integer col:map.keySet()){
			try {
				row.getCell(col).setCellValue(map.get(col).toString());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static HSSFRow createRow(HSSFSheet writeSheet,int rowNo,int cols){
		HSSFRow row = writeSheet.getRow(rowNo);
		if(row == null){
			row = writeSheet.createRow(rowNo);
		}
		HSSFCell[] cellArr = new HSSFCell[cols];
		for(int i=0;i<cols;i++){
			cellArr[i] = row.createCell(i, HSSFCell.CELL_TYPE_STRING);
			cellArr[i].setCellValue("");
		}
		return row;
	}
}
