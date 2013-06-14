package cn.zlg.util.io;

public interface StreamReadCallBack {

//	public void setBufferSize(int buffer);
	
	/**
	 * 
	 * @param content  读取的字节缓存，从第0字节开始，长度为length
	 * @param position 目前为止，已经从流中读取的字节数，相当于把整个流当成一个文件，当前所处的位置，而不是byte[] content 开始的位置
	 * @param length  长度，content中有效内容的长度，
	 */
	public void read(byte[] content,long position,long length);
	
}
