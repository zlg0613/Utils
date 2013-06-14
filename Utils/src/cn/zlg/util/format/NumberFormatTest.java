package cn.zlg.util.format;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NumberFormatTest {

	float f = 2.15234f;
	double d = 3.26;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFormatFloatInt() {
	   assertEquals("2.1523400",NumberFormat.format(f, 7));
	   assertEquals("2.15",NumberFormat.format(f, 2));
	   assertEquals("2.15234",NumberFormat.format(f, 5));
	   assertEquals("2",NumberFormat.format(f, 0));
	   assertEquals("2",NumberFormat.format(f, -1));
	}

	@Test
	public void testFormatDoubleInt() {
		 assertEquals("3.2600000",NumberFormat.format(d, 7));
		   assertEquals("3.26",NumberFormat.format(d, 2));
		   assertEquals("3.2",NumberFormat.format(d, 1));
		   assertEquals("3",NumberFormat.format(d, 0));
		   assertEquals("3",NumberFormat.format(d, -1));
	}

}
