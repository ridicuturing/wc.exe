package wc;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WC {
	private static String encode = "UTF-8"; 
	
	public static void setEncode(String c) {
		encode = c;
	}
	
	public static String getFile(String fname) {
		File f = new File(fname);
		if(!f.isFile()) {
			return null;
		}
		byte[] buf = new byte[(int) f.length()];
		try {
			FileInputStream fr = new FileInputStream(f);
			fr.read(buf);
			fr.close();
			return new String(buf,encode);
		} catch (Exception e) {
			System.out.println("获取文件内容失败或编码格式错误");
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] getFile(String fname,int a) {
		File f = new File(fname);
		if(!f.isFile()) {
			return null;
		}
		byte[] buf = new byte[(int) f.length()];
		try {
			FileInputStream fr = new FileInputStream(f);
			fr.read(buf);
			fr.close();
			return buf;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static long  c(String fname) { //返回文件 file.c 的字符数
		String txt = null;
		if((txt = getFile(fname)) == null) {
			return -1;
		}
		txt = txt.replaceAll("\r", "");  //根据视觉习惯以及从多软件的潜规则，这里把windows换行中的\r去掉
		return txt.length();
	}
	
	public static long w(String fname) { //返回文件 file.c 的词的数目
		long num = 0;
		String pattern =  "[a-zA-Z_][\\w]{0,}|[\\u4e00-\\u9fa5]"; //"[\\u4e00-\\u9fa5\\w]+"这个可以识别单词，但不能把中文一个个分离出来，只能一句一句
		String txt = "";
		if((txt = getFile(fname)) == null) {
			return -1;
		}
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(txt);
		while(m.find()) {
			num++;
		}
		return num;
	}
	public static int l(String fname) { //返回文件 file.c 的行数
		byte[] content = null;
		int lines = 0;
		if((content = getFile(fname,1)) == null) 
			return -1;
		if(content.length == 0)
			return 0;
		for(byte s :content) {
			if(s == 10)
				lines++;
		}
		return ++lines; 
	}
	
	public static int[] a(String fname) { //返回更复杂的数据（代码行 / 空行 / 注释行）
		String txt = "";
		boolean noteflag = false;
		int[] a = {0,0,0}; //emptyline,codeline,noteline
		if((txt = getFile(fname)) == null) {
			System.out.println("a参数调用文件" + fname + "错误!");
			return null;
		}
		if(txt.length() != 0) {
			String lines[] = txt.replaceAll("\r","").split("\n");
			for(String line:lines) {
				//emptyline
				line = line.replaceAll(" ","").replaceAll("\t","");
				if(line.length() < 2) {
					a[0]++;
					continue;
				}
				//codeline
				line = line.replaceAll("//.{0,}", "");
				
				if(line.length() < 2) { //特殊行（ "//）开头  
					a[2]++;
					continue;
				}
				if(line.indexOf("/*") != -1) {
					if(line.indexOf("*/") != -1) {
						line = line.replaceAll("/\\*.{0,}\\*/", "");
					}else {
						line = line.split("/*")[0];
						noteflag = true;
					}
				}
				if(noteflag && line.indexOf("*/") == -1){
					a[2]++;
					continue;
				}else if(noteflag){
					String[] tmp = line.split("\\*/");
					if(tmp.length > 1) {
						line = tmp[1];
					}
					else {
						line = "";
					}
					noteflag = false;
				}
				if(line.length() > 1 ) {
					a[1]++;
					continue;
				}
				//noteline
				a[2]++;
			}
			txt = txt.replaceAll("\r", "");
			Pattern r = Pattern.compile("\n+$");;
			Matcher m = r.matcher(txt);
			if(m.find()) {
				a[0] += m.group(0).length();
			}
		}
		System.out.println("\n" +
				"文件名字: " + fname + "\n" + 
				"空行: " + a[0] + "\n" +
				"代码行: " + a[1] + "\n" +
				"注释行: " + a[2]);
		return a;
	}
	public static int s(String dir) { //递归处理目录下符合条件的文件
		int filecount = 0;
		File d = new File(dir);
		if(!d.exists()) {
			System.out.println(dir + " 路径不存在");
			return -1;
		}
		if(!d.isDirectory()) {
			WC.a(dir);
			return 1;
		}
		for(String fname:d.list()) {
			filecount += WC.s(dir + "\\" + fname);
		}
		return filecount;
	}
	public static void main(String[] args) throws Exception {
		String fname = "D:\\git\\wc.exe\\WCTestFile\\Achar.c";
			
	}
}
