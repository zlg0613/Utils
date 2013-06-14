package cn.zlg.util;

import cn.zlg.util.format.NumberFormat;

public class MemoryInfo {

	public static final int BYTE_FORMAT  = 0X01;
	public static final int KB_FORMAT  = 0X02;
	public static final int MB_FORMAT  = 0X03;
	public static final int GB_FORMAT  = 0X04;
	
	private static int format = MB_FORMAT;
	public static String toKB(long bytes){
		double kb = bytes/1024.0;
		return NumberFormat.format(kb, 2)+"KB";
	}
	
	public static String toMB(long bytes){
		double mb = bytes/1024.0/1024.0;
		return NumberFormat.format(mb, 2)+"MB";
	}
	
	public static String toGB(long bytes){
		double mb = bytes/1024.0/1024.0/1024.0;
		return NumberFormat.format(mb, 4)+"GB";
	}
	
	public static String getMemoryString(long bytes){
		String s;
		switch(format){
		case BYTE_FORMAT:
			s = String.valueOf(bytes)+"B";
			break;
		case KB_FORMAT:
			s = toKB(bytes);
			break;
		case MB_FORMAT:
			s = toMB(bytes);
			break;
		case GB_FORMAT:
			s = toGB(bytes);
			break;
		default:
			s = toMB(bytes);
			break;
		}
		return s;
	}
	public static String memoryInfo(int mFormat){
		format = mFormat;
		Runtime rt = Runtime.getRuntime();
		StringBuilder sb = new StringBuilder(70);
		sb.append("TotalMemory:");
		sb.append(getMemoryString(rt.totalMemory()));
		sb.append(";");
		sb.append("FreeMemory:");
		sb.append(getMemoryString(rt.freeMemory()));
		sb.append(";");
		sb.append("MaxMemory:");
		sb.append(getMemoryString(rt.maxMemory()));
		return sb.toString();
	}
	public static String memoryInfo(){
		return memoryInfo(MB_FORMAT);
	}
	public static void print(){
		System.out.println(memoryInfo());
	}
	public static void print(int format){
		System.out.println(memoryInfo(format));
	}
}
