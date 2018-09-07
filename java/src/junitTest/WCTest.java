package junitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import wc.WC;

class WCTest {

	@Test
	public void test() {
		long a = WC.c("D:\\WCTestFile\\c.c");
		assertEquals(5,a);
	}

}
