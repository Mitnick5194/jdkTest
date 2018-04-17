package com.ajie.testclient;

import java.util.ArrayList;
import java.util.List;


/**
 * @author niezhenjie
 * 
 */
public class TestListAdd {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>(){
			private static final long serialVersionUID = 1L;
			{
				add("h");
				add("e");
				add("l");
				add("l");
				add("o");
			}
		};
		list.add(2,"p");
		for (String string : list) {
		//	System.out.println(string);
		}
		
		List<String> list2 = new ArrayList<String>(){
			private static final long serialVersionUID = 1L;
			{
				add("e");
				add("e");
			}
		};
		boolean contains = list.containsAll(list2); //抛异常
		System.out.println(contains);
		
	}

}
