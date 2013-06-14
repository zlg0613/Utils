package cn.zlg.excel.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class AnnotationParserTest {

	@Test
	public void testParseStringMarkedColumn() {
		String co = "R";
		AnnotationParser ap = new AnnotationParser(this.getClass());
		System.out.println(ap.parseStringMarkedColumn(co));
	}

}
