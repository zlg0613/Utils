package cn.zlg.util;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import cn.zlg.util.io.FileUtils;
import cn.zlg.util.io.ReadFileCallBack;
import cn.zlg.util.io.ReadFileCallBackException;

public class LineCounter {

	public static int count(String fileName){
		return count(fileName,(Pattern)null);
	}
	/**
	 * 统计一个文件中文件的行数，可以使用exludePattern过滤掉不想要的模式
	 * @param fileName 要统计的文件数
	 * @param excludePattern 不需要纳入统计的模式
	 * @return
	 */
	public static int count(String fileName,final Pattern excludePattern){
		final List<Integer> list = New.arraylist(1);
		list.add(0);
		try {
			FileUtils.readFile(fileName, new ReadFileCallBack() {
				
				@Override
				public void doWhileRead(String s, int rowNum) {
					boolean flag = false;
					if(excludePattern!=null){
						if(!excludePattern.matcher(s).matches()){
							flag = true;
						}
					}else{
						flag = true;
					}
					if(flag){
						int c = list.get(0)+1;
						list.clear();
						list.add(c);
					}
				}
			});
		} catch (ReadFileCallBackException e) {
			e.printStackTrace();
		}
		return list.get(0);
	}
		public static int count(String fileName,final String excludeRegex){
		Pattern p = excludeRegex==null?null:Pattern.compile(excludeRegex);
		return count(fileName,p);
	}
		
	public static int countForDir(String dir,String[] allowdSuffixes,String excludePattern){
		List<File> files = FileUtils.listFiles(dir,allowdSuffixes);
		int count  = 0;
		for(File f:files){
			int sc = count(f.getAbsolutePath(),excludePattern);
			count += sc;
		}
		return count;
	}
	public  static void main(String[] args){
		String dir = "/home/m618/workspace/git/bus-newmodel/bus-newmodel/src";
		int count = countForDir(dir,new String[]{"java"},"^\\s*$|^\\s*}\\s*$");
//		int count = countForDir(dir,new String[]{"java"},"null");
		int count1 = count("/home/m618/workspace/git/Utils/Utils/src/cn/zlg/util/LineCounter.java");
		int count2 = count("/home/m618/workspace/git/Utils/Utils/src/cn/zlg/util/LineCounter.java","^\\s*$|^\\s*}\\s*$");
		System.out.println(count);
		System.out.println(count1);
		System.out.println(count2);
	}
}