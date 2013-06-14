package cn.zlg.util;

public class ArrayUtils {
	
	public static int[] slice(int length,int pieces){
		int [] res = new int[pieces+1];
		int avgLength = length/pieces;
		int mod = length%pieces;
		res[0] = 0;
		for(int i=1;i<pieces;i++){
			if(i<mod+1){
				res[i] = res[i-1]+avgLength+1;
			}else{
				res[i] = res[i-1]+avgLength;
			}
		}
		res[pieces] = length;
		return res;
	}
	
	public static boolean nullOrEmpty(int[] arr){
		if(arr==null||arr.length==0){
			return true;
		}
		return false;
	}
	public static boolean nullOrEmpty(byte[] arr){
		if(arr==null||arr.length==0){
			return true;
		}
		return false;
	}
}
