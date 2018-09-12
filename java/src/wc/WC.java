package wc;

import java.io.File;
import java.io.FileInputStream;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileSystemView;

public class WC {
	private String encode = "UTF-8"; 
	private String txt = "";
	private int fileCount = 0;
	public WC(String fname) {
		getFile(fname);
	}
	public WC(String[] pars){
		argsDeals(pars);
	}
	
	public void setEncode(String c) {
		encode = c;
	}
	public int getFileCount() {
		return fileCount;
	}
	
	public int getFile(String fname) {
		File f = new File(fname);
		if(!f.isFile()) {
			return -1;
		}
		fileCount += 1 ;
		byte[] buf = new byte[(int) f.length()];
		try {
			FileInputStream fr = new FileInputStream(f);
			fr.read(buf);
			this.txt = new String(buf,encode);
			fr.close();
		} catch (Exception e) {
			System.out.println("获取文件内容失败或编码格式错误");
			e.printStackTrace();
		}
		return 0;
	}
	
	public long  c() { //返回文件 file.c 的字符数
		txt = txt.replaceAll("\r", "");  //根据视觉习惯以及从多软件的潜规则，这里把windows换行中的\r去掉
		System.out.println("字符数: " + txt.length());
		return txt.length();
	}
	
	public long w() { //返回文件 file.c 的词的数目
		long num = 0;
		String pattern =  "[a-zA-Z_][\\w]{0,}|[\\u4e00-\\u9fa5]"; //"[\\u4e00-\\u9fa5\\w]+"这个可以识别单词，但不能把中文一个个分离出来，只能一句一句
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(txt);
		while(m.find()) {
			num++;
		}
		System.out.println("词的数目: " + num);
		return num;
	}
	public int l() { //返回文件 file.c 的行数
		int lines = 0;
		
		if(txt.length() != 0) {
			for(byte s :txt.getBytes()) {
				if(s == 10)
					lines++;
			}
			lines++;
		}
		System.out.println("行数: " + lines);
		return lines; 
	}
	
	public int[] a() { //返回更复杂的数据（代码行 / 空行 / 注释行）
		boolean noteflag = false;
		int[] a = {0,0,0}; //emptyline,codeline,noteline
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
	public int dealWithRecursion(String dir , String[] pars) { //递归处理目录下符合条件的文件
		int filecount = 0;
		File d = new File(dir);
		if(!d.isDirectory()) {
			getFile(dir);
			System.out.println("文件: " + dir);
			for(String par:pars) {
				switch(par) {
				case "-c":
				case "-C":
					c();
					continue;
				case "-w":
				case "-W":
					w();
					continue;
				case "-l":
				case "-L":
					l();
					continue;
				case "-a":
				case "-A":
					a();
					continue;
				}
			}
			return 1;
		}
		for(String fname:d.list()) {
			filecount += dealWithRecursion(dir + "\\" + fname,pars);
		}
		return filecount;
	}
	
	public boolean isParameter(String args) {
		switch(args) {
		case "-c":
		case "-C":
		case "-w":
		case "-W":
		case "-l":
		case "-L":
		case "-s":
		case "-S":
		case "-a":
		case "-A":
		case "":
			return true;
		}
		return false;
	}
	
	public void deal(String fname,String[] pars) {
		File f = new File(fname);
		if(f.exists()) {
			if(!f.isDirectory()) {
				getFile(fname);
				System.out.println("文件: " + fname);
				for(String par:pars) {
					switch(par) {
					case "-c":
					case "-C":
						c();
						continue;
					case "-w":
					case "-W":
						w();
						continue;
					case "-l":
					case "-L":
						l();
						continue;
					case "-a":
					case "-A":
						a();
						continue;
					}
				}
			}
		}
	}
	
	public void argsDeals(String[] pars) {
		boolean isRecursion = false;
		String fname = pars[pars.length - 1];
		Set<String> set = new TreeSet<String>();
		File f = new File(fname);
		
		if(pars.length == 1 && (pars[0].equals("-X") || pars[0].equals("-x"))) {
			chooseFile();
			return;
		}
		pars[pars.length - 1] = ""; //为方便后面单纯地处理参数，转移文件名
		for(String par:pars) {
			set.add(par);
		}
		set.remove("");
		pars =  (String[]) set.toArray(new String[0]);
		if(!f.exists()) {
			System.out.println("文件不存在");
			return ;
		}
		for(int n = 0; n < pars.length ; n++) {
			if(!isParameter(pars[n])) {
				System.out.println("参数\"" + pars[n] + "\"错误");
				return;
			}
			if(pars[n].equals("-s") || pars[n].equals("-S")) {
				isRecursion = true;
			}
			if(pars[n].equals("-x") || pars[n].equals("-X")) {
				System.out.println("\"-x\"参数必须独立使用");
				return;
			}
		}
		
		if(isRecursion) {
			dealWithRecursion(fname,pars);
		}
		else {
			if(f.isDirectory()) {
				for(String name:f.list()) {
					deal(fname + "\\" + name,pars);
				}
			}
			deal(fname,pars);
		}
		
	}
	public void chooseFile() {
		JFileChooser jfc=new JFileChooser();  
        //设置当前路径为桌面路径,否则将我的文档作为默认路径
        FileSystemView fsv = FileSystemView .getFileSystemView();
        jfc.setCurrentDirectory(fsv.getHomeDirectory());
        //JFileChooser.FILES_AND_DIRECTORIES 选择路径和文件
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
        //弹出的提示框的标题
        jfc.showDialog(new JLabel(), "确定");  
        //用户选择的路径或文件
        File file=jfc.getSelectedFile();
        String[] args = {"-c","-w","-l","-a",file.getPath()};
        argsDeals(args);
	}
	
	public static void main(String[] args) throws Exception {
		/*
		for(String arg:args) {
			System.out.println(arg);
		}*/
		new WC(args);
	}
}
