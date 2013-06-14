package cn.zlg.util.office;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;

public class ExcelParseUtils{
	private static final Logger log  = Logger.getLogger(ExcelParseUtils.class);

	public static <T>List<T> parseExcel(T t,Map attrMap,String excelFile){
		return parseExcel(t,attrMap,new File(excelFile));
	}
	@SuppressWarnings("unchecked")
	/**
	 * 解析Excel文件，思想为编写一次代码可用于多种情况，只需传入一个需要转换成为的类的实例，
	 * 及类中属性和Excel中属性名称对应的Map，相应的excel文件即可，返回一个链表
	 */
	public static <T>List<T> parseExcel(T t,Map attrMap,File excelFile){
		List<T> list = new ArrayList<T>();
		try {
			Workbook wb = Workbook.getWorkbook(excelFile);
			Sheet sheet = wb.getSheet(0);
			Class cls = Class.forName(t.getClass().getName());
			HashMap<String,Integer> attrAndColumn = new HashMap<String,Integer>();
			Field[] fields = t.getClass().getDeclaredFields();
			
			int attrRow = 0; //
			int i = 0,j=0;
				for(i=0;i<fields.length;i++){
					if(attrMap.containsKey(fields[i].getName())){
						String arrName = (String)attrMap.get(fields[i].getName());
//						System.out.print(arrName+"  ");
						Cell cell = sheet.findCell(arrName);
						if(cell != null){
							attrRow = cell.getRow();
							attrAndColumn.put(fields[i].getName(), cell.getColumn());
						}
					}
				}
//				log.debug("正在导入Excel文件……");
			for(j=attrRow+1;j<sheet.getRows();j++){
//				System.out.println(j);
				  t = (T) cls.newInstance();
				for(i=0;i<fields.length;i++){
					if(attrAndColumn.containsKey(fields[i].getName())){
					int column = attrAndColumn.get(fields[i].getName());
					Cell cell = sheet.getCell(column, j);
					//System.out.print(cell.getContents()+"  ");
					fields[i].setAccessible(true);
					if(fields[i].getType() == String.class){  //只支持字符串、整数及日期
						fields[i].set(t, cell.getContents());
					}else if(fields[i].getType() == Integer.class){
						System.out.print(cell.getContents()+"  ");
						fields[i].set(t, Integer.parseInt(cell.getContents()));
					}else if(fields[i].getType() == Date.class){
						fields[i].set(t, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cell.getContents()));
					}
				}
				}
				//System.out.println();
				list.add(t);
			}
		} catch (BiffException e) {
			log.error("导入Excel文件出错……");
			e.printStackTrace();
		} catch (IOException e) {
			log.error("导入Excel文件出错……");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			log.error("导入Excel文件出错……");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			log.error("导入Excel文件出错……");
			e.printStackTrace();
		} catch (InstantiationException e) {
			log.error("导入Excel文件出错……");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(list.size());
		return list;
	}
	
	/**
	 * 生成Excel文件，思想为编写一次代码可用于多种情况，只需传入一个需要提取的类的class，
	 * 及类中属性和Excel中属性名称对应的Map，attrMap为要生成的Excel属性标题栏和类中属性对应的Map，
	 * 例如 StudentinfoBean 中有属性 class_,studentId，并且在生成Excel时想让class_在studentId前，
	 * 由一定要先在map中put("班级","class_"),使用的是LinkedHashMap，确保放入的顺序和实际的顺序一致
	 * 
	 * @param fileName 为要生成的文件名，
	 * @param title 为Excel的标题名
	 * @param cls 为需要提取信息的类
	 * @param objectList 为对象实体集合
	 * @param attrMap 为Excel中属性名称和类中属性名称对应的Map
	 */
	public static boolean createExcel(Class cls,List objectList,LinkedHashMap<String,String> attrMap,String fileName,String title){
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
			WritableSheet sheet = workbook.createSheet("sheet 1",0);
			WritableFont times16font = new WritableFont(WritableFont.TIMES, 16, WritableFont.BOLD, false); 
			WritableCellFormat times16format = new WritableCellFormat (times16font); 
			Label label = new Label(0, 0, title,times16format); 
			sheet.addCell(label); 
			int currentRow = 1; //
			int currentColumn = 0; //
			int rowIndex = 1;
			int i =0,j=0;
			Object[] keyArray = attrMap.keySet().toArray();
			Label index = new Label(currentColumn++, 1, "序号");  //添加属性行
			sheet.addCell(index); 
			for(i=0;i<keyArray.length;i++){
				String attr =(String) keyArray[i];
				Label lab = new Label(currentColumn++, 1, attr); 
				sheet.addCell(lab); 
			}
			log.debug("正在生成Excel文件……");
			currentColumn = 0;
			currentRow ++; //change to 2
			log.debug("信息有效行数："+objectList.size());
			for(j=0;j<objectList.size();j++){
				Object someone = objectList.get(j);
				//cls.cast(someone);
				Label lab = new Label(0, currentRow, ""+rowIndex ); //设置每一行的行号
				sheet.addCell(lab); 
				currentColumn = 1;
				for(i=0;i<keyArray.length;i++){
					Field field = cls.getDeclaredField((String)attrMap.get(keyArray[i]));
//					System.out.println(field.getName());
					field.setAccessible(true);
					String attr =(String) field.get(someone);
//					System.out.println(attr);
					Label newlabel = new Label(currentColumn++, currentRow, attr); 
					sheet.addCell(newlabel); 
				}
				currentRow++;
				rowIndex++;  //to increase the row index 
			}
			workbook.write();
			workbook.close();
		} catch (IOException e) {
			log.error("生成Excel文件出错……");
			e.printStackTrace();
			return false;
		} catch (RowsExceededException e) {
			log.error("生成Excel文件出错……");
			e.printStackTrace();
			return false;
		} catch (WriteException e) {
			log.error("生成Excel文件出错……");
			e.printStackTrace();
			return false;
		} catch (SecurityException e) {
			log.error("生成Excel文件出错……");
			e.printStackTrace();
			return false;
		} catch (NoSuchFieldException e) {
			log.error("生成Excel文件出错……");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			log.error("生成Excel文件出错……");
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) {
			log.error("生成Excel文件出错……");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
		public static void main(String[] args) throws FileNotFoundException{
		}
}
