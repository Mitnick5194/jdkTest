package com.ajie.collection;

import java.util.HashMap;

/**
 * @author niezhenjie
 * 
 */
public class Client {

	public static void main(String[] args) {
		/*
		 试验数组花括号初始化
		 Object[] obj = {"","",""};
		obj[1] = "aa";
		System.out.println(obj[1]);
		*/
		/*		
		 		回顾一下map遍历
				User u1 = new User("a",1);
				User u2 = new User("b",2);
				User u3 = new User("c",3);
				Map<String , User> map = new HashMap<String, User>();
				map.put("u1", u1);
				map.put("u2", u2);
				map.put("u3", u3);
				for(String key : map.keySet()){
					User user = map.get(key);
					System.out.println(user.getName());
				}*/

		/*Object [] obj;
		Object[] obj2 = {1,2};
		obj = obj2; //这样是可以的 obj不用定义大小和初始化 直接把obj赋予给它
		//一下是不可以的 因为{}表示数组已经初始化 而且obj3的大小为0
		Object[] obj3 = {};
		obj3[0] = 1;
		obj3[1] = 2;
		System.out.println(obj[0] +"  "+obj[1]);*/

		// test JosnObject
		/*String info = "{'name':'1','age':'23','sex':'male'}";
		JSONObject obj = new JSONObject(info);
		String name = null;
		try {
			name = obj.getString("name1");
		} catch (Exception e) {
			name = "";
		}
		System.out.println(name);*/
		java.util.Map<String , String > map = new HashMap<String , String>();
		map.put("h", "1");
		map.put("e", "2");
		map.put("l", "3");
		map.put("9", "nine");
		String ret = map.get(1);
		String res = map.get(9);
		System.out.println(ret);
		System.out.println(res);

	}

}
