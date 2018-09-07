package wc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WC {
	
	
	public static long  c(String fname) {
		File f = new File(fname);
		if(!f.isFile()) {
			return -1;
		}
		return f.length();
	}
	
	public static long w(String fname) {
		long num = 0;
		String pattern =  "[\\w]+"; //"[\\u4e00-\\u9fa5\\w]+"这个可以识别单词，但不能把中文一个个分离出来，只能一句一句
		String line = "";
		Pattern r = Pattern.compile(pattern);
		try {
			BufferedReader br = new BufferedReader(new FileReader(fname));
			while((line = br.readLine()) != null) {
				Matcher m = r.matcher(line);
				while(m.find()) {
					num++;
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return num;
	}
	public static int l(String fname) {
		int lines = 1;
		if(!new File(fname).isFile())
			return -1;
		try {
			BufferedReader br= new BufferedReader(new FileReader(fname));
			while(br.readLine() != null)
				lines++;
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	public static void main(String[] args) throws Exception {
		System.out.println(WC.w("D:\\git\\wc.exe\\WCTestFile\\w\\1.c"));
	}
}
