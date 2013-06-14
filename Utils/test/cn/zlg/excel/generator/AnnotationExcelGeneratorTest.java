package cn.zlg.excel.generator;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Before;
import org.junit.Test;

import cn.zlg.util.New;

public class AnnotationExcelGeneratorTest {

	List<TestBean> data = New.arraylist();
	int rows = 20;
	AnnotationExcelGenerator generator  = new AnnotationExcelGenerator();
	@Before
	public void setup(){
		for(int i=0;i<rows;i++){
			TestBean t = new TestBean();
			t.setAge(i+10);
			t.setId(i);
			t.setName("小本"+i);
			t.setSalary(202490.00);
			data.add(t);
		}
	}
	
	@Test
	public void testGenerate() {
		generator.generate(data,new MyCallBack(), "test.xls");
	}
	
}

class MyCallBack implements AddToCellCallBack{

	@Override
	public void execute(RowDef cellDef) {
			HSSFCell cell = cellDef.getCell("名字");
			HSSFFont font = cell.getSheet().getWorkbook().createFont();
			font.setColor(IndexedColors.RED.index);
			HSSFRichTextString str = new HSSFRichTextString(cell.getStringCellValue());
			str.applyFont(0, 3, font);
			cell.setCellValue(str);
	}
	
}