package cn.zlg.excel;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelReader {

	/**
	 * if no sheets specified,sheet 0 will be default
	 * @param file
	 * @param callback
	 */
	public static void readXLS(String file,ExcelReadCallback callback){
		readXLS(file,new int[]{0},callback);
	}
	public static void readXLS(String file,int minSheetIndex,int maxSheetIndex,ExcelReadCallback callback){
		int i= 0;
		try{
			POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream(file));   
			HSSFWorkbook wb = new HSSFWorkbook(fs);   
			if(maxSheetIndex>=0&&maxSheetIndex<minSheetIndex){
				throw new IllegalArgumentException("maxSheetIndex<minSheetIndex");
			}
			if(maxSheetIndex<0||maxSheetIndex>wb.getNumberOfSheets()){
				maxSheetIndex = wb.getNumberOfSheets();
			}
			int sheetNum;
			
			for(sheetNum=minSheetIndex;sheetNum<maxSheetIndex;sheetNum++){
				HSSFSheet sheet = wb.getSheetAt(sheetNum);  
				for(i=0;i<=sheet.getLastRowNum();i++){
//					for(i=0;i<sheet.getPhysicalNumberOfRows();i++){
					HSSFRow row = sheet.getRow(i);   
					callback.readRow(sheetNum,i, row);
				}
			}
		} catch (Exception e) {   
			e.printStackTrace();   
		}   
	}
	public static void readXLS(String file,int[] sheets,ExcelReadCallback callback){
		int i= 0;
		try{
			POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream(file));   
			HSSFWorkbook wb = new HSSFWorkbook(fs);   
			for(int sheetNum:sheets){
				HSSFSheet sheet = wb.getSheetAt(sheetNum);  
				for(i=0;i<=sheet.getLastRowNum();i++){
//					for(i=0;i<sheet.getPhysicalNumberOfRows();i++){
					HSSFRow row = sheet.getRow(i);   
					callback.readRow(sheetNum,i, row);
				}
			}
		} catch (Exception e) {   
			e.printStackTrace();   
		}   
		
	}
	/**
	 * 还未实现
	 * @param file
	 * @param callback
	 */
	public static void readXLSX(String file,ExcelReadCallback callback){
		throw new UnsupportedOperationException();
	}
}
