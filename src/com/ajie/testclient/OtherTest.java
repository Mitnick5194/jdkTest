package com.ajie.testclient;

/**
 * @author niezhenjie
 * 
 */
public class OtherTest {
	int cursor;
	int size = 10;
	String str;
	boolean b;
	public boolean hasNext(){
		System.out.println(b);
		return cursor !=size;
	}
	public static void main(String[] args) {
		OtherTest ot = new OtherTest();
		System.out.println(ot.hasNext());
	}

}
