package cn.zlg.excel.newmodel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import cn.zlg.excel.newmodel.model.Model;
import cn.zlg.excel.newmodel.parser.ModelParser;
import cn.zlg.util.New;

public class ExcelNewModelParserUtils {

	public static List<Model> parse(String propsFile,String excelFile){
		return parse(propsFile,excelFile,new int[]{0});
	}
	
	public static List<Model> parse(String propsFile,String excelFile,int sheet){
		return parse(propsFile,excelFile,new int[]{sheet});
	}
	
	public static List<Model> parse(String propsFile,String excelFile,int[] sheets){
		Map<Integer,String> map = parseMap(propsFile);
		ModelParser mp = new ModelParser(map);
		return mp.parse(excelFile, sheets);
	}

	private static Map<Integer, String> parseMap(String propsFile) {
		Properties p ;
		p = new Properties();
		try {
			if(propsFile.contains("/")){
					p.load(new FileInputStream(propsFile));
			}else{
				p.load(ExcelNewModelParserUtils.class.getClassLoader().getResourceAsStream(propsFile)); 
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map<Integer,String> map = New.hashMap();
		for(Entry e:p.entrySet()){
			try{
				Integer i = Integer.parseInt((String) e.getKey());
				map.put(i, (String) e.getValue());
			}catch(Exception e1){
				
			}
		}
		
		return map;
	}
}
