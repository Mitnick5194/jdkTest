package com.ajie.testclient;

/**
 * @author niezhenjie
 * 
 */
public class OtherTest {
	public OtherTest() {
	}

	public OtherTest(String s) {
		sayHello(s); // 不知为什么 linkedList里的有参构造要调用一下无参的构造
	}

	int cursor;
	int size = 10;
	String str;
	boolean b;

	public boolean hasNext() {
		System.out.println(b);
		return cursor != size;
	}

	public void sayHello(String s) {
		System.out.println(s + " say hello world");
	}

	public static void main(String[] args) {
		/*OtherTest ot = new OtherTest();
		System.out.println(ot.hasNext());*/
		OtherTest ot = new OtherTest("ajie");
	}

}
