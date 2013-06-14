package cn.zlg.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Wrapper the common used BufferReader,BufferWriter class,simplify the process of 
 * creating them
 * @author m618
 *
 */
public class IOUtils {

	public static BufferedReader bufferedReaderFromInputStream(InputStream is){
		return new BufferedReader(new InputStreamReader(is));
	}
	
	public static BufferedWriter bufferedWriterFromOutputStream(OutputStream os){
		return new BufferedWriter(new OutputStreamWriter(os));
	}
	
	public static BufferedReader bufferReaderFromFile(String fileName){
		return bufferReaderFromFile(new File(fileName));
	}
	
	public static BufferedReader bufferReaderFromFile(File file){
		try {
			FileInputStream fis = new FileInputStream(file);
			return bufferedReaderFromInputStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedReader bufferReaderFromSystemIn(){
		BufferedReader br = bufferedReaderFromInputStream(System.in);
		return br;
	}
	
	public static BufferedWriter bufferWriterToFile(String fileName){
		return bufferWriterToFile(new File(fileName));
	}
	
	public static BufferedWriter bufferWriterToFile(File file){
		try {
			FileOutputStream fos = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			return bw;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static BufferedInputStream bufferStreamFromFile(String fileName){
		return bufferStreamFromFile(new File(fileName));
	}
	
	public static BufferedInputStream bufferStreamFromFile(File file){
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream br = new BufferedInputStream(fis);
			return br;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedOutputStream bufferOutputStreamToFile(String fileName){
		return bufferOutputStreamToFile(new File(fileName));
	}
	
	public static BufferedOutputStream bufferOutputStreamToFile(File file){
		try {
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			return bos;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
