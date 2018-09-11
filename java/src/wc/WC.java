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
		System.out.println("字符数: " + txt.length());
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
		System.out.println("词的数目: " + num);
		return num;
	}
	public static int l(String fname) { //返回文件 file.c 的行数
		byte[] content = null;
		int lines = 0;
		if((content = getFile(fname,1)) == null) {
			System.out.println("调用文件错误");
			return -1;
		}
		if(content.length != 0) {
			for(byte s :content) {
				if(s == 10)
					lines++;
			}
			lines++;
		}
		System.out.println("行数: " + lines);
		return lines; 
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
		System.out.println(
				"空行: " + a[0] + "\n" +
				"代码行: " + a[1] + "\n" +
				"注释行: " + a[2]);
		return a;
	}
	public static int dealWithRecursion(String dir , String[] pars) { //递归处理目录下符合条件的文件
		int filecount = 0;
		File d = new File(dir);
		if(!d.isDirectory()) {
			System.out.println("文件1: " + dir);
			for(String par:pars) {
				switch(par) {
				case "-c":
				case "-C":
					c(dir);
					continue;
				case "-w":
				case "-W":
					w(dir);
					continue;
				case "-l":
				case "-L":
					l(dir);
					continue;
				case "-a":
				case "-A":
					a(dir);
					continue;
				case "-x":
				case "-X":
				}
			}
			return 1;
		}
		for(String fname:d.list()) {
			filecount += WC.dealWithRecursion(dir + "\\" + fname,pars);
		}
		return filecount;
	}
	
	public static boolean isParameter(String args) {
		switch(args) {
		case "-c":
		case "-w":
		case "-l":
		case "-s":
		case "-a":
		case "-x":
		case "":
			return true;
		}
		return false;
	}
	
	public static void deal(String fname,String[] pars) {
		for(String par:pars) {
			System.out.println("文件: " + fname);
			switch(par) {
			case "-c":
			case "-C":
				c(fname);
				continue;
			case "-w":
			case "-W":
				w(fname);
				continue;
			case "-l":
			case "-L":
				l(fname);
				continue;
			case "-a":
			case "-A":
				a(fname);
				continue;
			case "-x":
			case "-X":
			}
		}
	}
	
	public static void argsDeals(String[] pars) {
		boolean isRecursion = false;
		String fname = pars[pars.length - 1];
		pars[pars.length - 1] = " "; //为方便后面单纯地处理参数，转移文件名
		if(!new File(fname).exists()) {
			System.out.println("文件不存在");
			return ;
		}
		for(int n = 0; n < pars.length-1 ; n++) {
			if(!isParameter(pars[n])) {
				System.out.println("参数\"" + pars[n] + "\"错误");
				return;
			}
			if(pars[n] == "-s") {
				isRecursion = true;
			}
		}
		if(isRecursion) {
			dealWithRecursion(fname,pars);
		}
		else
			deal(fname,pars);
		
	}
	
	public static void main(String[] args) throws Exception {
		String[] aaa = {"-l","-c","-s","D:\\git\\wc.exe\\WCTestFile"};
		WC.argsDeals(aaa);
			
			
	}
}
