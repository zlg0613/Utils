package cn.zlg.util;


public class ToBytesUtils {
	
	public static final int BIG_ENDIAN = 0;
	public static final int LITTLE_ENDIAN = 1;
	
	public static byte[] addBytes(byte[] bs,byte[] toAdd){
		if(toAdd==null||toAdd.length==0)
			return bs;
		if(bs!=null){
			byte[] newByteArray = new byte[bs.length+toAdd.length];
			System.arraycopy(bs, 0, newByteArray, 0, bs.length);
			System.arraycopy(toAdd, 0, newByteArray, bs.length, toAdd.length);
			return newByteArray;
		}
		return toAdd;
	}
	
	public static byte[] addShort(byte[] bs,short x){
		byte[] toAdd = getBytes(new Short(x));
		return addBytes(bs,toAdd);
	}
	
	public static byte[] addInt(byte[] bs,int x){
         byte[] toAdd = getBytes(new Integer(x));
         return addBytes(bs,toAdd);
	}
	public static byte[] addFloat(byte[] bs,float x){
		byte[] toAdd = getBytes(new Float(x));
		return addBytes(bs,toAdd);
	}
	public static byte[] addLong(byte[] bs,long x){
		byte[] toAdd = getBytes(new Long(x));
		return addBytes(bs,toAdd);
	}
	public static byte[] addDouble(byte[] bs,double x){
		byte[] toAdd = getBytes(new Double(x));
		return addBytes(bs,toAdd);
	}
	public static byte[] addByte(byte[] bs,byte b){
		return addBytes(bs,new byte[]{b});
	}
	
	
	public static void putByteToBuffer(byte[] bb, byte x, int index) {
    	bb[index] = x;
	}
	
	public static void putShortToBufferLittleEndian(byte[] dest,int off,short f){
			dest[off+0] = (byte)f;
			dest[off+1] = (byte)(f>>8);
	}
	public static void putShortToBufferBigEndian(byte[] dest,int off,short f){
		dest[off+1] = (byte)f;
		dest[off] = (byte)(f>>8);
}
	
	/**
	 * 
	 * @param bb 要向其中插入数据的数组
	 * @param x 要插入的数据
	 * @param offset 在数组中插入的起始位置
	 * What's for? :小端模式存储整形字节
	 *@return
	 */
	public static void putIntToBufferLittleEndian(byte[] bb, int offset, int x) {
		   	bb[offset + 3] = (byte) (x >> 24);
		   	bb[offset + 2] = (byte) (x >> 16);
	    	bb[offset + 1] = (byte) (x >> 8);
	    	bb[offset + 0] = (byte) (x);
	}
	
	/**
	 * 
	 * @param bb
	 * @param x
	 * @param offset
	 * What's for? :小端模式存储整形字节
	 *@return
	 */
	public static void putIntToBufferBigEndian(byte[] bb, int offset, int x) {
		   	bb[offset] = (byte) (x >> 24);
		   	bb[offset + 1] = (byte) (x >> 16);
	    	bb[offset + 2] = (byte) (x >> 8);
	    	bb[offset + 3] = (byte) (x);
	}
	
	public static void putFloatToBufferLittleEndian(byte[] dest,int off,float f){
		int i =  Float.floatToIntBits(f);
		for(int j=0;j<4;j++){
			dest[off+j] = (byte)i;
			i = i>>8;
		}
	}
	
	public static void putFloatToBufferBigEndian(byte[] dest,int off,float f){
		int i =  Float.floatToIntBits(f);
		for(int j=3;j>=0;j--){
			dest[off+j] = (byte)i;
			i = i>>8;
		}
	}
	
	public static void putLongToBufferLittleEndian(byte[] bb, int index, long x) {
			bb[index + 7] = (byte) (x >> 56);
			bb[index + 6] = (byte) (x >> 48);
			bb[index + 5] = (byte) (x >> 40);
			bb[index + 4] = (byte) (x >> 32);
			bb[index + 3] = (byte) (x >> 24);
			bb[index + 2] = (byte) (x >> 16);
			bb[index + 1] = (byte) (x >> 8);
			bb[index + 0] = (byte) (x >> 0);
	} 
	

	public static void putLongToBufferBigEndian(byte[] bb, int index, long x) {
			bb[index + 0] = (byte) (x >> 56);
			bb[index + 1] = (byte) (x >> 48);
			bb[index + 2] = (byte) (x >> 40);
			bb[index + 3] = (byte) (x >> 32);
			bb[index + 4] = (byte) (x >> 24);
			bb[index + 5] = (byte) (x >> 16);
			bb[index + 6] = (byte) (x >> 8);
			bb[index + 7] = (byte) (x >> 0);
	} 
	
	public static void putDoubleToBufferLittleEndian(byte[] bb, int index, double d) {
		long x = Double.doubleToLongBits(d);
		putLongToBufferLittleEndian(bb,index,x);	
	} 
	
	public static void putDoubleToBufferBigEndian(byte[] bb, int index, double d) {
			long x = Double.doubleToLongBits(d);
			System.out.println("putDoubleToBufferBigEndian x="+x);
			putLongToBufferBigEndian(bb,index,x);
	} 
	
	
	public static short getShort(byte[] bs ,int endian){
		return getShort(bs,0,endian);
	}
	public static int getInt(byte[] bs ,int offset,int endian){
		int s = 0;
		int byteMask = 0xff;
		switch(endian){
		case BIG_ENDIAN:
			for(int i=3;i>=0;i--){
				s |= (bs[i+offset]<<(3-i)*8)&byteMask;
				byteMask <<= 8; 
			}
			break;
		case LITTLE_ENDIAN:
			for(int i=0;i<4;i++){
				s |= (bs[i+offset]<<i*8)&byteMask;
				byteMask <<= 8; 
			}
			break;
		default:
			return getInt(bs,offset,BIG_ENDIAN);
		}
		return s;
	}
	public static int getInt(byte[] bs ,int endian){
		return getInt(bs,0,endian);
	}
	public static float getFloat(byte[] bs ,int offset,int endian){
		int i = getInt(bs,offset,endian);
		return Float.intBitsToFloat(i);
	}
	public static float getFloat(byte[] bs ,int endian){
		return getFloat(bs,0,endian);
	}
	public static long getLong(byte[] bs,int endian){
		return getLong(bs,0,endian);
	}
	/**
	 * 需要注意的是，对byte进行操作的时候要进行强制类型转换，默认会转换成int型，在数据大于int 的表示范围时，会得到错误的结果
	 * @param bs
	 * @param offset
	 * @param endian
	 * @return
	 */
	public static long getLong(byte[] bs ,int offset ,int endian){
		long s = 0;
		long byteMask = 0xff;
		switch(endian){
		case BIG_ENDIAN:
			for(int i=7;i>=0;i--){
				s |= (((long)(bs[i+offset]))<<(7-i)*8)&byteMask;
				byteMask <<= 8; 
			}
			break;
		case LITTLE_ENDIAN:
			for(int i=0;i<8;i++){
				s |= (((long)(bs[i+offset]))<<i*8)&byteMask;
				byteMask <<= 8; 
			}
			break;
		default:
			return getLong(bs,offset,BIG_ENDIAN);
		}
		return s;
	}
	public static double getDouble(byte[] bs ,int endian){
		return getDouble(bs,0,endian);
	}
	public static double getDouble(byte[] bs ,int offset,int endian){
		long i = getLong(bs,offset,endian);
		System.out.println(i);
		return Double.longBitsToDouble(i);
	}
	
	public static short getShort(byte[] bs,int offset,int endian){
		short s;
		switch(endian){
		case BIG_ENDIAN:
			s = (short)(0xff00&bs[0+offset]<<8 | 0xff&bs[1+offset]);
			break;
		case LITTLE_ENDIAN:
			s = (short)(0xff00&bs[1+offset]<<8 | 0xff&bs[0+offset]);
			break;
		default:
			return getShort(bs,offset,BIG_ENDIAN);
		}
		return s;
	}
	/**
	 * 
	 * @param o 需要转换成字节的元素，一般为基本类型数据的包装类，不包含Boolean
	 * @return 返回小端模式存储的字节
	 * 目标 :
	 */
	public static byte[] getBytes(Object o){
		byte[] bs = null;
		int len = 0;
		if(o instanceof Integer){
			len = 4;
			bs = new byte[len];
			for(int j=0;j<len;j++){
				bs[j] = (byte)(((Integer)o)>>(len-j-1)*8);
			}
		}else if(o instanceof Short){
			len = 2;
			bs = new byte[len];
			for(int j=0;j<len;j++){
				bs[j] = (byte)(((Short)o)>>(len-j-1)*8);
			}
		}else if(o instanceof Long){
			len = 8;
			bs = new byte[len];
			for(int j=0;j<len;j++){
				bs[j] = (byte)(((Long)o)>>(len-j-1)*8);
			}
		}else if(o instanceof Float){
			len = 4;
			bs = new byte[len];
			int value = Float.floatToIntBits((Float) o);
			for(int j=0;j<len;j++){
				bs[j] = (byte)((value)>>(len-j-1)*8);
			}
		}else if(o instanceof Double){
			len = 8;
			bs = new byte[len];
			long value = Double.doubleToLongBits((Double) o);
			for(int j=0;j<len;j++){
				bs[j] = (byte)((value)>>(len-j-1)*8);
			}
		}
		return bs;
	}
}
