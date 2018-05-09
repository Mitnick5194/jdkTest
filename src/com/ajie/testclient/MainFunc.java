package com.ajie.testclient;

/**
 * @author niezhenjie
 * 
 */
public class MainFunc {

	public static void main(String[] args) {
		String s = "1234";
		try {
			int i = Integer.valueOf(s);
			System.out.println("'"+s+"' 是数字");
		} catch (Exception e) {
			System.out.println("'"+s+"' 不是数字");
		}
	}
}
