package cn.zlg.performence;

public class SimpleImpl implements Simple {

	@Override
	@PerformanceWatch
	public void sayHello(String name) {
		System.out.println(name+" say hello");
	}

	@Override
	@PerformanceWatch
	public void sayHello() {
		System.out.println("System say hello");
	}

}
