package cn.zlg.office.word.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.model.PAPBinTable;
import org.apache.poi.hwpf.model.PAPX;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.hwpf.usermodel.Fields;
import org.apache.poi.hwpf.usermodel.Range;

import cn.zlg.office.word.IWordStringExtractor;
import cn.zlg.util.New;

public class WordDocReader implements IWordStringExtractor {

	@Override
	public List<String> read(String file) {
		 
		// 读取word模板
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		HWPFDocument hdt = null;
		try {
			hdt = new HWPFDocument(in);
			PAPBinTable pd = hdt.getParagraphTable();
			Iterator<PAPX> ps = pd.getParagraphs().iterator();
			while(ps.hasNext()){
				PAPX n = ps.next();
//				System.out.println(n.toString());
				
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("---------------------------------");
		// 读取word文本内容
		Range range = hdt.getRange();
		String[] ss = range.text().split("\\r");
		List<String> list = New.arraylist(ss.length);
		for(String s:ss){
			list.add(s);
//			System.out.println(s);
		}
//		System.out.println("length="+ss.length);
		return list;
	}

}
