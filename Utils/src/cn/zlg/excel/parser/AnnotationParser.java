package cn.zlg.excel.parser;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.ConvertUtils;

import cn.zlg.excel.parser.annotation.ExcelColumn;
import cn.zlg.excel.parser.annotation.ExcelMapperBean;
import cn.zlg.excel.parser.bean.ExcelCell;
import cn.zlg.excel.parser.bean.ExcelConfig;
import cn.zlg.excel.parser.bean.ExcelRow;
import cn.zlg.util.StringUtils;

public class AnnotationParser<T> {

	private ExcelReader excelReader = new POIExcelReader();
//	private ExcelReader excelReader = new JXLExcelReader();
	private Map<Integer,Field> fieldMap ;
	private Map<Integer,Method> methodMap;
	private ExcelConfig ec;
	private Class c;
	
	public AnnotationParser(Class t){
		this.c = t;
		this.fieldMap = resolveFieldMap(t);
		this.methodMap = resolveMethodMap(t);
		this.ec = getConfig(t);
	}
	public AnnotationParser(T t){
		this(t.getClass());
	}
	private  Map<Integer,Field> resolveFieldMap(Class c){
		Map<Integer,Field> columnFieldMap = new HashMap<Integer,Field>();
		Field[] fields = c.getDeclaredFields();
		for(Field f:fields){
			ExcelColumn ec = f.getAnnotation(ExcelColumn.class);
			if(ec!=null){
				f.setAccessible(true);
				if(!StringUtils.nullOrEmpty(ec.col())){
					columnFieldMap.put(parseStringMarkedColumn(ec.col()), f);
				}else{
					columnFieldMap.put(ec.value(), f);
				}
			}
		}
		return columnFieldMap;
	}
	
	private Pattern p = Pattern.compile("[A-Za-z]+");
	
	public int parseStringMarkedColumn(String column){
		int c = -1;
		if(p.matcher(column).matches()){
			c=0;
			column = column.toUpperCase();
			if(column.length()>1){
				c += column.charAt(column.length()-1)-'A';
				for(int i=column.length()-2;i>=0;i--){
					c += (column.charAt(i)-'A'+1)*(Math.pow(26, column.length()-1-i));
				}
			}else{
				c += column.charAt(0) - 'A';
			}
		}else{
			throw new IllegalArgumentException("不正确的列标号"+column+"，只能用字母标识");
		}
		return c;
	}
	private Map<Integer,Method> resolveMethodMap(Class c){
		Map<Integer,Method> columnMethodMap = new HashMap<Integer,Method>();
		Method[] methods = c.getDeclaredMethods();
		for(Method m:methods){
			ExcelColumn ec = m.getAnnotation(ExcelColumn.class);
			if(ec!=null){
				m.setAccessible(true);
				columnMethodMap.put(ec.value(), m);
			}
		}
		return columnMethodMap;
	}
	public ExcelConfig getConfig(Class c){
		boolean flag = c.isAnnotationPresent(ExcelMapperBean.class);
		if(flag){
			ExcelConfig ec = new ExcelConfig();
			ExcelMapperBean emb = (ExcelMapperBean)c.getAnnotation(ExcelMapperBean.class);
			ec.setFile(emb.file());
			ec.setStartRow(emb.startRow());
			ec.setEndRow(emb.endRow());
			ec.setSheet(emb.sheet());
			ec.setOutputType(emb.outputType());
			Set<Integer> colSet = new HashSet<Integer>();
			if(fieldMap!=null){
				colSet.addAll(fieldMap.keySet());
			}
			if(methodMap!=null){
				colSet.addAll(methodMap.keySet());
			}
			Integer[] cols = colSet.toArray(new Integer[0]);
			Arrays.sort(cols);
			ec.setMappedColumns(cols);
			return ec;
		}
		return null;
	}
	
	public List<T> parse(RowFilter filter){
		boolean flag = c.isAnnotationPresent(ExcelMapperBean.class);
		if(flag){
			List<T> list = new ArrayList<T>();
			List<ExcelRow> erList = excelReader.readRows(ec);
			for(ExcelRow er:erList){
				if(filter.accept(er.getCells(), er.getRowNo())){
					T newT = map(er);
					list.add(newT);
				}
			}
			return list;
		}
		return null;
	}
	
	public List<T> parse(){
		boolean flag = c.isAnnotationPresent(ExcelMapperBean.class);
		if(flag){
			List<T> list = new ArrayList<T>();
			List<ExcelRow> erList = excelReader.readRows(ec);
			for(ExcelRow er:erList){
				T newT = map(er);
				list.add(newT);
			}
			return list;
		}
		return null;
	}
	
	private T map(ExcelRow er) {
		ExcelCell[] cells = er.getCells();
		try{
			T newT = (T) this.c.newInstance();
			for(ExcelCell cell:cells){
				if(cell==null){
					continue;
				}
				Field f = fieldMap.get(cell.getColumn());
				if(f!=null){
					f.set(newT, ConvertUtils.convert(cell.getContent(), f.getType()));
				}
				Method m = methodMap.get(cell.getColumn());
				if(m!=null){
					m.invoke(newT, ConvertUtils.convert(cell.getContent(),m.getParameterTypes()[0]));
				}
			}
			return newT;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public ExcelConfig getExcelConfig(){
		return ec;
	}
}
