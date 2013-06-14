package cn.zlg.util.format;

/**
 * 提供浮点数和双精度数的格式化，可以指定小数的位数，多删少补。0或者负数的小数位数，将被转换成整数
 * 当前版本还不具有四舍五入的功能
 * @author m618
 * 2012-11-14 10:32
 */
public class NumberFormat {

	public static String format(float f,int digitLength){
		String s = String.valueOf(f);
		return format(s,digitLength);
	}
	
	public static String format(String s,int digitLength){
		String result;
		int i = s.indexOf(".");
		if(digitLength<=0){
			if(i>0)
				return s.substring(0,i);
			return s;
		}
		if(i>0){
			if((i+digitLength)<s.length()-1){
				result = s.substring(0, i+digitLength+1);
			}else if((i+digitLength)>s.length()-1){
				result = append0(s,i+digitLength+1-s.length());
			}else{
				result = s;
			}
			return result;
		}else{
			StringBuilder sb = new StringBuilder(20);
			sb.append(s);
			sb.append(".");
			return append0(sb,digitLength);
		}
	}
	public static String format(double d,int digitLength){
		String s = String.valueOf(d);
		return format(s,digitLength);
	}
	public static String append0(String s,int num){
		return append0(new StringBuilder(s),num);
	}
	public static String append0(StringBuilder sb,int num){
		for(int j=0;j<num;j++)
			sb.append("0");
		return sb.toString();
	}
}
