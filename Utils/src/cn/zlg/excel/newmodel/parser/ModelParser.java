/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.zlg.excel.newmodel.parser;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFRow;

import cn.zlg.excel.ExcelReadCallback;
import cn.zlg.excel.ExcelReader;
import cn.zlg.excel.newmodel.model.Model;
import cn.zlg.util.New;

/**
 *
 * @author zlg2
 */
public class ModelParser {

    private Map<Integer,String>  columnAttrsMap ;
    
    public ModelParser(Map<Integer,String>  columnAttrsMap){
             this.columnAttrsMap = columnAttrsMap;
    }
    
    public  List<Model> parse(File file){
           return parse(file.getAbsolutePath());
    }
    
    public  List<Model> parse(File file,int sheet){
    	return parse(file.getAbsoluteFile(),new int[]{sheet});
    }
    
    public  List<Model> parse(File file,int[] sheets){
    	return parse(file.getAbsoluteFile(),sheets);
    }
    
    public  List<Model> parse(String file){
        return parse(file,new int[]{0});
    }
    
    public  List<Model> parse(String file,int sheet){
    	return parse(file,new int[]{sheet});
    }
    
    public  List<Model> parse(String file,int[] sheets){
    	final List<Model> result = New.arraylist();
    	ExcelReader.readXLS(file, sheets,new ExcelReadCallback(){
    		
    		@Override
    		public void readRow(int sheet, int rowNum, Object o) {
    			if(o==null){
    				return;
    			}
    			HSSFRow row = (HSSFRow )o;
    			Model m = new Model();
    			for(Entry<Integer,String>  e:columnAttrsMap.entrySet()){
    				if(row.getCell(e.getKey())!=null){
    					m.set(e.getValue(), row.getCell(e.getKey()).getStringCellValue());
    				}
    			}
    			result.add(m);
    		}
    		
    	});
    	return result;
    }
}
