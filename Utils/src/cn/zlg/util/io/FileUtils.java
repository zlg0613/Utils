package cn.zlg.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class FileUtils {

	public static boolean copy(String org,String out,boolean force) {
		File or = new File(org);
		File ou = new File(out);
		if(!or.exists()){
			throw new RuntimeException(new FileNotFoundException("找不到源文件:"+org));
		}
		if(ou.exists()&&(!force||!ou.canWrite())){
			throw new IllegalArgumentException("目标文件已经存在");
		}
//		byte[]
		return false;
	}
	
	public static boolean append(String file,String content){
		return saveFileWithBuffer(content,1024*2,file);
	}
	
	/**
	 * 
	 * @see getFileNamePrefix(String fname)
	 * @param f 需要提取前缀的文件
	 * @return
	 */
	public static String getFileNamePrefix(File f){
		return getFileNamePrefix(f.getName());
	}
	
	public static boolean mkdirs(String dir){
		File f = new File(dir);
		boolean re = false;
		if(!f.exists()){
			re = f.mkdirs();
		}else{
			if(f.isDirectory()){
				re = true;
			}else{
				re = false;
			}
		}
		return re;
	}
	/**
	 * 返回文件名称的前缀，文件名称是文件的名字，不包含路径信息
	 * 文件的名字和后缀是以 "."分割的
	 * @param fname  需要提取前缀的文件名称
	 * @return 文件名的前缀
	 */
	public static String getFileNamePrefix(String fname){
		int i = fname.lastIndexOf(".");
		if(i!=-1){
			return fname.substring(0, i);
		}
		return fname;
	}
	
	/**
	 * @see getFileNameSuffix(String fname) 
	 * @param f  需要提取后缀的文件
	 * @return 文件名的后缀
	 */
	public static String getFileNameSuffix(File f){
		return getFileNameSuffix(f.getName());
	}
	
	/**
	 * 返回文件名称的后缀，文件名称是文件的名字，不包含路径信息
	 * 文件的名字和后缀是以 "."分割的,如果文件没有后缀将返回null
	 * @param fname  需要提取后缀的文件名称
	 * @return 文件名的后缀
	 */
	public static String getFileNameSuffix(String fname){
		int i = fname.lastIndexOf(".");
		if(i!=-1){
			return fname.substring(i+1);
		}
		return null;
	}
	
	public static List<File> listFiles(String dirName,FilenameFilter ff){
		File dir = new File(dirName);
		return listFiles(dir,ff);
	}
	
	public static List<File> listFiles(File dir,FilenameFilter ff){
		if(!dir.isDirectory()){
			throw new IllegalArgumentException(dir + "不是有效的目录");
		}
		List<File> files = new ArrayList<File>();
		File[] fs = dir.listFiles();
		for(File f:fs){
			if(f.isDirectory()){
				files.addAll(listFiles(f.getAbsolutePath(),ff));
			}else{
				String fileName = f.getName();
				if(ff.accept(dir, fileName)){
					files.add(f);
				}
			}
		}
		return files;
	}
	
	public static List<File> listFiles(String dir,final Pattern p){
		return listFiles(new File(dir),p);
	}
	public static List<File> listFiles(File dir,final Pattern p){
		FilenameFilter ff = new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				return p.matcher(name).matches();
			}
			
		};
		return listFiles(dir,ff);
	}
	/**
	 * 
	 * @param dir
	 * @param fileNameRegex
	 * @return
	 */
	public static List<File> listFiles(File dir,final String fileNameRegex){
		FilenameFilter ff = new FilenameFilter(){
			
			@Override
			public boolean accept(File dir, String name) {
				return name.matches(fileNameRegex);
			}
			
		};
		return listFiles(dir,ff);
	}
	
	
	public static List<File> listFiles(String dirName,String allowedSuffix){
		return listFiles(dirName,new String[]{allowedSuffix});
	}
	
	/**
	 * 递归遍历一个目录下的文件，检测指定后缀的文件，如果后缀名为null或者为空，则为允许任何文件
	 * @param dirName
	 * @param allowedSuffixes
	 * @return
	 */
	public static List<File> listFiles(String dirName,String[] allowedSuffixes){
		File dir = new File(dirName);
		if(!dir.isDirectory()){
			throw new IllegalArgumentException(dir + "不是有效的目录");
		}
		List<File> files = new ArrayList<File>();
		File[] fs = dir.listFiles();
		for(File f:fs){
			if(f.isDirectory()){
				files.addAll(listFiles(f.getAbsolutePath(),allowedSuffixes));
			}else{
				if(allowedSuffixes==null||allowedSuffixes.length==0){
					files.add(f);
				}else{
					String fileName = f.getName();
					int index = fileName.indexOf(".");
					if(index != -1){
						String suffix = fileName.substring(index+1);
						if(contains(suffix,allowedSuffixes)){
							files.add(f);
						}
					}
				}
			}
		}
		return files;
	}
	
	/**
	 *判断一个客串数组中是否包含某个字符串 
	 * @param s 查找字符串
	 * @param strings  字符串数组
	 * @return
	 */
	public static boolean contains(String s,String[] strings){
		for(String str :strings){
			if(str.equals(s)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 遍历一个文件夹下的所有普通文件
	 * @see listFiles(String dirName,String[] allowedSuffixes)
	 * @param dir 扫描的目录
	 * @return
	 */
	public static List<File> list( String dir){
		return listFiles(dir,new String[0]);
	}
	public static List<File> list(File dir){
		return list(dir.getAbsolutePath());
	}
	
	/**
	 *使用普通方式保存文件，不使用缓存，适合于文件比较小的场合，
	 *如果文件较大，请使用 saveFileWithBuffer()
	 * @param content  要保存的文件内容
	 * @param name	  要保存的文件名称
	 * @return 返回文件是否保存成功
	 */
	public static boolean saveFileWithNoBuffer(byte[] content,String name){
		if(content==null)
			return true;
		try {
			File f = new File(name);
			if(!mkdirs(f.getParent())){
				throw new RuntimeException("创建文件目录失败");
			}
			FileOutputStream fos = new FileOutputStream(name);
			fos.write(content);
			fos.flush();
			fos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean saveFileWithNoBuffer(String content,String name){
		return saveFileWithNoBuffer(content.getBytes(),name);
	}
	
	
	/**
	 * 使用缓存的方式保存文件，适合于保存文件比较大的场合。保存文件的会根据append的值决定是覆盖原文件还是在原本 的文件后追加
	 * 保存文件后会关闭输出流，只适合于使用一次
	 * @param content 需要保存的内容字节
	 * @param bufferSize  缓存大小
	 * @param name 文件名称
	 * @param append 指定是否追加文件，true 在文件后追加，false 覆盖原文件
	 * @return 文件是否返回成功,true 成功，false 失败
	 */
	public static boolean saveFileWithBuffer(byte[] content,int bufferSize,String name,boolean append){
		try {
			File f = new File(name);
			if(!mkdirs(f.getParent())){
				throw new RuntimeException("创建文件目录失败");
			}
			FileOutputStream fos = new FileOutputStream(name,append);
			BufferedOutputStream bos = new BufferedOutputStream(fos,bufferSize);
			
			bos.write(content);
			bos.flush();
			bos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 使用缓存的方式保存文件，适合于保存文件比较大的场合,如果文件存在，将覆盖文件，<br／>
	 * 如果想在文件后面追加，请使用 saveFileWithBuffer(byte[] content,int bufferSize,String name,boolean append) <br／>
	 * 保存文件后会关闭输出流，只适合于使用一次 <br／>
	 * @param content 需要保存的内容字节
	 * @param bufferSize  缓存大小 
	 * @param name 文件名称 
	 * @return 文件是否返回成功,true 成功，false 失败
	 */
	public static boolean saveFileWithBuffer(byte[] content,int bufferSize,String name){
		return saveFileWithBuffer(content,bufferSize,name,false);
	}
	/**
	 * 使用缓存的方式保存文件，适合于保存文件比较大的场合,如果文件存在，将覆盖文件，
	 * 如果想在文件后面追加，请使用 saveFileWithBuffer(byte[] content,int bufferSize,String name,boolean append)
	 * 保存文件后会关闭输出流，只适合于使用一次
	 * @param content 需要保存的字符串内容
	 * @param bufferSize  缓存大小
	 * @param name 文件名称
	 * @return 文件是否返回成功,true 成功，false 失败
	 */
	public static boolean saveFileWithBuffer(String content,int bufferSize,String name){
		return saveFileWithBuffer(content.getBytes(),bufferSize,name,false);
	}
	
	public static boolean saveFileWithBuffer(String content,int bufferSize,String name,boolean append){
		return saveFileWithBuffer(content.getBytes(),bufferSize,name,append);
	}
	
	/**
	 * 
	 * 本方法的主要目的是简化文件的操作，使用 ReadFileCallBack在读取文本文件的每一行时进行操作<br/>
	 * 本方法只适用于文本文件，在行上进行操作，不适合于字节流文件
	 * @param file 需要读取的文件
	 * @param cb 对文件的行进行操作的接口实现
	 * @throws ReadFileCallBackException
	 */
	public static void readFile(File file,ReadFileCallBack cb) throws ReadFileCallBackException{
		readFile(file.getAbsolutePath(),cb);
	}
	
	/**
	 * 本方法的主要目的是简化文件的操作，使用 ReadFileCallBack在读取文本文件的每一行时进行操作
	 * 本方法只适用于文本文件，在行上进行操作，不适合于字节流文件
	 * @param fileName 需要读取的文件名
	 * @param cb 对文件的行进行操作的接口实现
	 * @throws ReadFileCallBackException
	 */
	public static void readFile(String fileName,ReadFileCallBack cb) throws ReadFileCallBackException{
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			int rowNum=0;
			String s ;
			while((s=br.readLine())!=null){
				cb.doWhileRead(s,rowNum);
				rowNum++;
			}
			br.close();
		} catch (Exception e) {
			throw new ReadFileCallBackException("读取文件出错",e);
		}
	}
	
	public static void readFile(String fileName,ReadFileCallBackWithReturnValue cb) throws ReadFileCallBackException{
		readFile(new File(fileName),cb);
	}
	
	/**
	 * 本方法的主要目的是简化文件的操作，使用 ReadFileCallBack在读取文本文件的每一行时进行操作
	 * 本方法只适用于文本文件，在行上进行操作，不适合于字节流文件
	 * @param file 需要读取的文件
	 * @param cb 对文件的行进行操作的接口实现
	 * @throws ReadFileCallBackException
	 */
	public static void readFile(File file,ReadFileCallBackWithReturnValue cb) throws ReadFileCallBackException{
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			int rowNum=0;
			String s ;
			boolean continue_ = true;
			while(continue_&&(s=br.readLine())!=null){
				continue_ = cb.doWhileRead(s,rowNum);
				rowNum++;
			}
			br.close();
		} catch (Exception e) {
			throw new ReadFileCallBackException("读取文件出错",e);
		}
	}
	
	/**
	 * 
	 * @param file 要读取的文件
	 * @param bufferSize 缓存大小
	 * @param scb 读取内容时的操作
	 * @throws ReadFileCallBackException
	 */
	public static void readFileStream(String file,int bufferSize,StreamReadCallBack scb) throws ReadFileCallBackException{
		readFileStream(new File(file),bufferSize,scb);
	}
	
	public static void readFileStream(File file,int bufferSize,StreamReadCallBack scb) throws ReadFileCallBackException{
		try {
			readStream(new FileInputStream(file),bufferSize,scb);
		} catch (Exception e) {
			throw new ReadFileCallBackException("读取文件出错",e);
		}
	}
	
	/**
	 * 从流中读取，使用BufferedInputStream 读取。本方法是对流做一个封装，省略打开流，关闭流的操作
	 * @param is 要读取的流
	 * @param bufferSize 缓存大小
	 * @param scb 读取内容时的操作
	 * @throws ReadFileCallBackException
	 */
	public static void readStream(InputStream is,int bufferSize,StreamReadCallBack scb) throws ReadFileCallBackException{
		try{
			if(bufferSize<=0){
				bufferSize = 1024*16;
			}
			BufferedInputStream bis = new BufferedInputStream(is,bufferSize);
			long position = 0;
			long length = 0;
			byte[] buffer = new byte[bufferSize];
			while((length=bis.read(buffer))!=-1){
				scb.read(buffer, position, length);
				position += length;
			}
			bis.close();
		}catch (Exception e){
			throw new ReadFileCallBackException("读取流出错",e);
		}
	}
	
	/**
	 * 拷贝文件，使用FileChannel进行操作
	 * @param src 需要拷贝的源文件
	 * @param dst  目标文件
	 * @throws Exception
	 */
	public static void copy(File src, File dst) throws Exception {
		FileChannel in,out;
			in = new FileInputStream(src).getChannel();
			out = new FileOutputStream(dst).getChannel();
//			in.lock();
//			out.lock();
			in.transferTo(0, in.size(), out);
			in.close();
			out.close();
//			
	}
	public static void copy(String src,String dst) throws Exception{
		copy(new File(src),new File(dst));
	}

}
