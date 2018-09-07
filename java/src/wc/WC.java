package wc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WC {
	
	public static String getFileString(String fname) {
		File f = new File(fname);
		if(!f.isFile()) {
			return null;
		}
		byte[] buf = new byte[(int) f.length()];
		try {
			FileInputStream fr = new FileInputStream(f);
			fr.read(buf);
			fr.close();
			return new String(buf,"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static long  c(String fname) {
		String txt = null;
		if((txt = getFileString(fname)) == null) {
			return -1;
		}
		txt = txt.replace("\r", "");  //根据视觉习惯以及从多软件的潜规则，这里把windows换行中的\r去掉
		return txt.length();
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
		String txt = null;
		int lines = 0;
		if((txt = getFileString(fname)) == null) 
			return -1;
		if(txt.length() == 0)
			return 0;
		lines = txt.split("\n").length;
		if(txt.lastIndexOf("\n") == txt.length()-1)
			lines++;
		return lines; 
	}
	public static void main(String[] args) throws Exception {
		
		File f = new File("d:/1.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String c = "";
		while((c = br.readLine()) != null ) {
			System.out.println(c.length());
		}
		System.out.println(f.length());
		
		
		br.close();
		System.out.println("" == null);
	}
}
