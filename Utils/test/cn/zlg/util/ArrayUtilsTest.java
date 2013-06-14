package cn.zlg.util;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class ArrayUtilsTest {

	@Test
	public void testSlice() {
		int length = 57;
		int piece = 6;
		int[] sl = ArrayUtils.slice(length, piece);
		assertArrayEquals(sl,new int[]{0,10,20,30,39,48,57});
	}

}
