package junitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import wc.WC;

class WCTest {
	String root = "D:\\git\\wc.exe\\WCTestFile\\";
	WC f1 = new WC(root + "Achar.c");
	WC f2 = new WC(root + "Aword.c");
	WC f3 = new WC(root + "classicfile.c");
	WC f4 = new WC(root + "emptyline1.c");
	WC f5 = new WC(root + "emptyline2.c");
	WC f6 = new WC(root + "null.c");
	@Test
	public void testc() {
		long a = f1.c();
		assertEquals(1,a);
		a =  f2.c();
		assertEquals(5,a);
		a = f3.c();
		assertEquals(128,a);
		a = f4.c();
		assertEquals(6,a);
		a = f5.c();
		assertEquals(6,a);
		a = f6.c();
		assertEquals(0,a);
	}	
	
	@Test
	public void testl() {
		long a = f1.l();
		assertEquals(1,a);
		a = f2.l();
		assertEquals(1,a);
		a = f3.l();
		assertEquals(13,a);
		a = f4.l();
		assertEquals(5,a);
		a = f5.l();
		assertEquals(5,a);
		a = f6.l();
		assertEquals(0,a);
	}
	
	@Test
	public void testw() {
		long a = f1.w();
		assertEquals(1,a);
		a = f2.w();
		assertEquals(1,a);
		a = f3.w();
		assertEquals(26,a);
		a = f4.w();
		assertEquals(1,a);
		a = f5.w();
		assertEquals(1,a);
		a = f6.w();
		assertEquals(0,a);
	}
	
	@Test
	public void testa() {
		int[] a= f1.a();
		assertArrayEquals(new int[] {1,0,0}, a);
		a = f2.a();
		assertArrayEquals(new int[] {0,1,0},a);
		a = f3.a();
		assertArrayEquals(new int[] {4,6,3},a);
		a = f4.a();
		assertArrayEquals(new int[] {5,0,0},a);
		a = f5.a();
		assertArrayEquals(new int[] {5,0,0},a);
		a = f6.a();
		assertArrayEquals(new int[] {0,0,0},a);
	}
	@Test
	public void tests() {
		int a= new WC(new String[] {"-l","-s",root}).getFileCount();
		assertEquals(9, a);
		
	}
	
	

}
