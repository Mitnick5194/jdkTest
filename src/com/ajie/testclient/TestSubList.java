package com.ajie.testclient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author niezhenjie
 * 
 */
public class TestSubList {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		List<String> subList = list.subList(0, 1);
		for (String string : subList) {
			System.out.println(string);
		}
	}
}
