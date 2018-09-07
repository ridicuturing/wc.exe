package junitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import wc.WC;

class WCTest {
	String root = "D:\\git\\wc.exe\\WCTestFile\\";
	@Test
	public void testc() {
		long a = WC.c(root + "c\\1.c");
		assertEquals(5,a);
	}
	
	@Test
	public void testl() {
		long a = WC.l(root + "l\\1.c");
		assertEquals(10,a);
	}
	
	@Test
	public void testw() {
		long a = WC.w(root + "w\\1.c");
		assertEquals(4,a);
	}
	

}
