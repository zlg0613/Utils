package cn.zlg.util.io;

public interface ReadFileCallBackWithReturnValue {

	/**
	 * 有返回值的Callback,如果返回false,将停止继续读文件,返回true则继续
	 * @param s
	 * @param rowNum
	 * @return
	 */
	public boolean doWhileRead(String s,int rowNum);
}
