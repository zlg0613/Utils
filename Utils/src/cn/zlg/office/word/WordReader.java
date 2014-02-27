package cn.zlg.office.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.hwpf.usermodel.Fields;
import org.apache.poi.hwpf.usermodel.Range;

import cn.zlg.office.word.impl.WordDocReader;
import cn.zlg.office.word.impl.WordDocxStringReader;

/**
 * 实现java用poi实现对word读取和修改操作
 * 
 * @author fengcl
 * 
 */
public class WordReader {

	/**
	 * 实现对word读取和修改操作
	 * 
	 * @param filePath
	 *            word模板路径和名称
	 * @param map
	 *            待填充的数据，从数据库读取
	 */
	public static void readWord(String filePath) {
		IWordStringExtractor iwr = null;
		if(filePath.endsWith("doc")){
			iwr = new WordDocReader();
		}else if(filePath.endsWith("docx")){
			iwr = new WordDocxStringReader();
		}else{
			return;
		}
		iwr.read(filePath);
		
	}
}