package cn.zlg.performence;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.zlg.util.Timer;

public class ProxyBuilderTest {

	@Test
	public void testBuild() {
		Simple t = ProxyBuilder.build(new SimpleImpl());
		t.sayHello();
		t.sayHello("zlg");
	}
}
