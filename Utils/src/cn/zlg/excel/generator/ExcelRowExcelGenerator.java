package cn.zlg.excel.generator;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.zlg.excel.parser.annotation.ExcelColumn;
import cn.zlg.excel.parser.annotation.ExcelMapperBean;
import cn.zlg.util.New;
import cn.zlg.util.StringUtils;

public class ExcelRowExcelGenerator implements ExcelGenerator {

	Map<Integer,ColumnDef> columFieldMap = New.treeMap();
	boolean hasTitleRow = false;
	private int maxColumnNo = 0;
	Map<Integer,String> indexToColumnTitleMap = New.hashMap();
	Map<String,Integer> columnTitleToIndexMap = New.hashMap();
	
	void parseMapperInfo(Class c){
		Field[] fs = c.getDeclaredFields();
		for(Field f:fs){
			ExcelColumn ec = f.getAnnotation(ExcelColumn.class);
			if(ec != null){
				ColumnDef def = new ColumnDef();
				def.column = ec.value();
				if(maxColumnNo <def.column){
					maxColumnNo = def.column;
				}
				def.field = f;
				if(!f.isAccessible()){
					f.setAccessible(true);
				}
				def.title = ec.title();
				columFieldMap.put(ec.value(), def);
				indexToColumnTitleMap.put(ec.value(), ec.title());
				columnTitleToIndexMap.put(ec.title(), ec.value());
				if(!StringUtils.nullOrEmpty(ec.title())){
					hasTitleRow = true;
				}
			}
		}
	}
	
	@Override
	public <T> void generate(List<T> data,String outputFile) {
		generate(data,null,outputFile);
	}

	private <T> void addToRow(T obj,int currentRow,HSSFSheet sheet,AddToCellCallBack callback){
		HSSFRow row = createRow(sheet,currentRow);
		for(Integer col:columFieldMap.keySet()){
			try {
				row.getCell(col).setCellValue(columFieldMap.get(col).field.get(obj).toString());
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if(callback != null){
			RowDef rd = new RowDef();
			rd.setRow(row);
			rd.setColumnTitleToIndexMap(columnTitleToIndexMap);
			rd.setIndexToColumnTitleMap(indexToColumnTitleMap);
			callback.execute(rd);
		}
	}
	
	private void addTitleRow(HSSFSheet writeSheet) {
		HSSFRow row = createRow(writeSheet,0);
		for(Integer col:columFieldMap.keySet()){
			row.getCell(col).setCellValue(columFieldMap.get(col).title);
		}
	}

	private HSSFRow createRow(HSSFSheet writeSheet,int rowNo){
		HSSFRow row = writeSheet.getRow(rowNo);
		if(row == null){
			row = writeSheet.createRow(rowNo);
		}
		HSSFCell[] cellArr = new HSSFCell[maxColumnNo+1];
		for(int i=0;i<=maxColumnNo;i++){
			cellArr[i] = row.createCell(i, HSSFCell.CELL_TYPE_STRING);
			cellArr[i].setCellValue("");
		}
		return row;
	}

	private class ColumnDef{
		public Field field;
		public int column;
		public String title;
	}

	@Override
	public <T> void generate(List<T> data, AddToCellCallBack callback,
			String outputFile) {
		if(data == null || data.size() == 0){
			return;
		}
		Class c = data.get(0).getClass();
		ExcelMapperBean mapper = (ExcelMapperBean) c.getAnnotation(ExcelMapperBean.class);
		if(mapper != null){
			try {
				FileOutputStream dicFos = new FileOutputStream(outputFile);
				HSSFWorkbook writeWB = new HSSFWorkbook();
				HSSFSheet writeSheet = writeWB.createSheet();
				parseMapperInfo(c);
				int currentRow = 0;
				if(hasTitleRow){
					addTitleRow(writeSheet);
					currentRow ++ ;
				}
				for(T t:data){
					addToRow(t,currentRow,writeSheet,callback);
					currentRow ++;
				}
				writeWB.write(dicFos);
				dicFos.flush();
				dicFos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
