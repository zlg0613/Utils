package cn.zlg.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class ZLGCollections {

	public static boolean nullOrEmpty(Collection c){
		return c==null||c.size()==0;
	}
	
	public static void rearrangeOrderRandom(List c){
		List bak = new ArrayList();
		bak.addAll(c);
		c.clear();
		Random r = new Random();
		while(bak.size()>0){
			int i = r.nextInt(bak.size());
			c.add(bak.get(i));
			bak.remove(i);
		}
	}
}
