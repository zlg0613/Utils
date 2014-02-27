package cn.zlg.net;

import static org.junit.Assert.*;

import org.junit.Test;

public class NetUtilsTest {

	@Test
	public void testGetLocalIp() {
		System.out.println(NetUtils.getRealIp());
	}

	@Test
	public void testGetMachineName() {
		System.out.println(NetUtils.getMachineName());
	}

}
