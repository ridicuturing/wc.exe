package junitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import wc.WC;

class WCTest {
	String root = "D:\\git\\wc.exe\\WCTestFile\\";
	@Test
	public void testc() {
		long a = WC.c(root + "Achar.c");
		assertEquals(1,a);
		a = WC.c(root + "Aword.c");
		assertEquals(5,a);
		a = WC.c(root + "classicfile.c");
		assertEquals(126,a);
		a = WC.c(root + "emptyline1.txt");
		assertEquals(6,a);
		a = WC.c(root + "emptyline2.txt");
		assertEquals(6,a);
		a = WC.c(root + "null.c");
		assertEquals(0,a);
	}	
	
	@Test
	public void testl() {
		long a = WC.l(root + "Achar.c");
		assertEquals(1,a);
		a = WC.l(root + "Aword.c");
		assertEquals(1,a);
		a = WC.l(root + "classicfile.c");
		assertEquals(12,a);
		a = WC.l(root + "emptyline1.txt");
		assertEquals(5,a);
		a = WC.l(root + "emptyline2.txt");
		assertEquals(5,a);
		a = WC.l(root + "null.c");
		assertEquals(0,a);
	}
	
	@Test
	public void testw() {
		long a = WC.w(root + "Achar.c");
		assertEquals(1,a);
		a = WC.w(root + "Aword.c");
		assertEquals(1,a);
		a = WC.w(root + "classicfile.c");
		assertEquals(16,a);
		a = WC.w(root + "emptyline1.c");
		assertEquals(1,a);
		a = WC.w(root + "emptyline2.c");//为什么txt能通过？
		assertEquals(1,a);
		a = WC.w(root + "null.c");
		assertEquals(0,a);
	}
	
	@Test
	public void testa() {
		int[] a= WC.a(root + "Achar.c");
		assertArrayEquals(new int[] {1,0,0}, a);
		a = WC.a(root + "Aword.c");
		assertArrayEquals(new int[] {0,1,0},a);
		a = WC.a(root + "classicfile.c");
		assertArrayEquals(new int[] {4,5,3},a);
		a = WC.a(root + "emptyline1.c");
		assertArrayEquals(new int[] {5,0,0},a);
		a = WC.a(root + "emptyline2.c");
		assertArrayEquals(new int[] {5,0,0},a);
		a = WC.a(root + "null.c");
		assertArrayEquals(null,a);
	}
	

}
