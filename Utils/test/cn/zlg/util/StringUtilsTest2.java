package cn.zlg.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilsTest2 {
	String s = "youTare";
	@Test
	public void testFirstCharUppercase() {
		assertEquals("YouTare",StringUtils.firstCharUppercase(s));
	}

}
