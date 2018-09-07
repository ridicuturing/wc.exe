package wc;

import java.io.File;

public class WC {
	
	public static long  c(String fname) {
		File f = new File(fname);
		if(!f.exists() || !f.isFile())
			return -1;
		return f.length();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(WC.c("d:\\1.c"));
	}
}
