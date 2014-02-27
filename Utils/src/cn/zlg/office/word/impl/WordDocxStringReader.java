package cn.zlg.office.word.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import cn.zlg.office.word.IWordStringExtractor;
import cn.zlg.util.New;

public class WordDocxStringReader implements IWordStringExtractor {

	@Override
	public List<String> read(String file) {
        List<String> list = New.arraylist();
        InputStream istream=null;  
        try {  
            istream = new FileInputStream(file);  
             XWPFDocument document=new XWPFDocument(istream);  
             Iterator<IBodyElement> bit = document.getBodyElementsIterator();
             while(bit.hasNext()){
            	 IBodyElement n = bit.next();
            	 if(n.getElementType()==BodyElementType.PARAGRAPH){
            		 XWPFParagraph xp = (XWPFParagraph)n;
            		 list.add(xp.getParagraphText());
//            		 System.out.println(xp.getParagraphText());
//            		 System.out.println(xp.getText());
            	 }else if(n.getElementType()==BodyElementType.TABLE){
            		 
            	 }else{
            		 
            	 }
             }
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            if(istream!=null)  
                try {  
                    istream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
        }   
//        System.out.println(content);
        return list;  

	}

}
