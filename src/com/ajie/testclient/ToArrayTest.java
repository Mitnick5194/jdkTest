package com.ajie.testclient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author niezhenjie
 * 
 */
public class ToArrayTest {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>() {
			private static final long serialVersionUID = 1L;
			{ // 这相当与执行一个静态块
				add("h");
				add("e");
				add("l");
				add("l");
				add("o");
			}
		};
		// 这里只能返回Object类型的数据 如果强转换 String[] array = (String[]) list.toArray()
		// 则运行时会抛类型转换异常
		Object[] array = list.toArray();
		for (Object object : array) {
			//要转换 只能一个个转
			String s  = (String) object;
			System.out.println(s);
		}
		System.out.println("==============楚河汉界==================");
		//下面方法可以转换成特定类型的数组 list.toArray(new String[list.size()]);
		String[] s = new String[list.size()+3];
		String[] strs = list.toArray(s);
		for (String string : strs) {
			System.out.println(string);
		}

	}

}
