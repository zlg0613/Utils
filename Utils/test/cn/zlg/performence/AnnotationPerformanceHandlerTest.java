package cn.zlg.performence;

import static org.junit.Assert.*;

import org.junit.Test;

public class AnnotationPerformanceHandlerTest {

	@Test
	public void testWatch() {
		SimpleImpl si = new SimpleImpl();
		try {
			Object[][] args = new Object[1][];
			args[0] = new Object[]{"zlg"};
			AnnotationPerformanceHandler.watch(si, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
