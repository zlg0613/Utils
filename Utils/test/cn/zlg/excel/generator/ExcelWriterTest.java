package cn.zlg.excel.generator;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ExcelWriterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testWrite() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(1);
		list.add(1);
		list.add(1);
		ExcelWriter.write(list, new ExcelRowMapper<Integer>(){

			@Override
			public Map<Integer,Object> map(Integer t) {
				Map<Integer,Object> m = new HashMap<Integer,Object>();
				m.put(1, "zlg");
				m.put(2, "zlg2");
				m.put(3, "zlg3");
				m.put(4, "zlg4");
				return m;
			}

			@Override
			public int getCols(Integer t) {
				return 5;
			}
			
		}, "/home/m618/temp/test.xls");
	}

}
