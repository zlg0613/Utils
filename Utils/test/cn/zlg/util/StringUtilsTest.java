package cn.zlg.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilsTest {

	String s = "-+-+hehe-+-+";
	@Test
	public void testTrim() {
		assertEquals("hehe",StringUtils.trim(s, "-+"));
		assertEquals("+-+hehe-+-+",StringUtils.trim(s, "-"));
	}

}
