package cn.zlg.excel.parser;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

import cn.zlg.excel.parser.bean.ExcelCell;
import cn.zlg.excel.parser.bean.ExcelConfig;
import cn.zlg.excel.parser.bean.ExcelRow;

public class POIExcelReader implements ExcelReader {

	@Override
	public List<ExcelRow> readRows(ExcelConfig ec) {
		int i= 0;
		try{
			POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream(ec.getFile()));   
			HSSFWorkbook wb = new HSSFWorkbook(fs);   
//			wb.set
			HSSFSheet sheet = wb.getSheetAt(ec.getSheet());  
			if(ec.getEndRow()<0||ec.getEndRow()>sheet.getPhysicalNumberOfRows()){
				ec.setEndRow(sheet.getLastRowNum()+1);
			}
//			System.out.println("readRows:"+ec);
			List<ExcelRow> list = new ArrayList<ExcelRow>();
			for(i=ec.getStartRow();i<ec.getEndRow();i++){
				HSSFRow row = sheet.getRow(i);  
				if(row==null){
					continue;
				}
				ExcelRow er = new ExcelRow();
				er.setRowNo(i);
				ExcelCell[] cells = new ExcelCell[ec.getMappedColumns().length];
				for(int j=0;j<ec.getMappedColumns().length;j++){
					int targetColumn = ec.getMappedColumns()[j];
					HSSFCell cell = row.getCell(targetColumn);
//					System.out.println("readRows:"+row.getRowNum());
					cells[j] = convert(cell);
				}
				er.setCells(cells);
				list.add(er);
//				if(i==ec.getStartRow()+10){
//					System.out.println(Arrays.toString(cells));
//				}
			}
//			System.out.println("file:"+ec.getFile()+";totalRows="+(sheet.getLastRowNum()+1)+"; validRows="+sheet.getPhysicalNumberOfRows()+"; filteredRows="+list.size());
			return list;
		} catch (Exception e) {   
			e.printStackTrace();   
			System.out.println(ec);
			System.out.println("i="+i);
		}   
		
		return null;
	}
	private ExcelCell convert(HSSFCell cell) {
		if(cell==null){
			return null;
		}
		ExcelCell ec = new ExcelCell();
		ec.setColumn(cell.getColumnIndex());
		ec.setRow(cell.getRowIndex());
		if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
			ec.setContent(cell.getBooleanCellValue()+"");
		}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
				ec.setContent((int)cell.getNumericCellValue()+"");
//				System.out.println(ec.getContent());
		}else if(cell.getCellType() == Cell.CELL_TYPE_BLANK){
			ec.setContent("");
		}else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){
//			ec.setContent(cell.getCellFormula());
		}else{
			ec.setContent(cell.getStringCellValue());
		}
		if(cell.getCellStyle()!=null){
			Color c = new Color(cell.getCellStyle().getFillBackgroundColor());
			ec.setBackgroundColor(c);
		}
		return ec;
	}
}
