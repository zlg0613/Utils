package cn.zlg.util;

import static org.junit.Assert.fail;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ZLGCollectionsTest {

	
	List<Integer> list = New.arraylist();
	
	@Before
	public void setup(){
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		list.add(10);
	}
	@Test
	public void test() {
		System.out.println(list);
		ZLGCollections.rearrangeOrderRandom(list);
		System.out.println(list);
	}

}
