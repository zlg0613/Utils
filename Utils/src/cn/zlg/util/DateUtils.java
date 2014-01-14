package cn.zlg.util;

public class DateUtils {

	private static int[] daysInMonth = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
	
	public static int getDaysInMonth(int year,int month){
		if(month<1&&month>12){
			return -1;
		}
		if(month==2&&isLeapYear(year)){
			return 29;
		}
		return daysInMonth[month-1];
	}
	
	public static boolean isLeapYear(int year){
		return year%4==0&&(year%100!=0 || year%400==0);
	}
}
