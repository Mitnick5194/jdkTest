package com.ajie.testclient;

/**
 * @author niezhenjie
 * 
 */
public class MainFunc {

	public static void main(String[] args) {
		/*String s = "1234";
		try {
			int i = Integer.valueOf(s);
			System.out.println("'"+s+"' 是数字");
		} catch (Exception e) {
			System.out.println("'"+s+"' 不是数字");
		}*/
		/*_loop: for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (j == 2)
					break _loop;// break; 只会跳出当前循环 可以跳出外层循环
				System.out.println("i=" + i + "  j=" + j);
			}

		}*/
		/*	List<Parent> list = new ArrayList<Parent>();
			UserBean u1 = new UserBean(24 , "男");
			UserBean u2 = new UserBean(23 , "女");
			Parent p = new Parent();
			p.setName("ajie");
			list.add(u1);
			list.add(u2);
			list.add(p);
			System.out.println(list.size());*/
		// System.out.println(MainFunc.class.getPackage().getImplementationVersion());
		// String i = "0xa";
		// System.out.println(Integer.toHexString(i));
		/*	String url = "http://l-drt.navboy.com:5670/drt/baoche/b.j;218";
			int idx = url.lastIndexOf(";");
			String ret =  url.substring(0, idx);
			String serviceId = url.substring(idx+1);
			System.out.println(ret+ "  "+serviceId);*/

		/*System.out.println(Integer.toHexString(65));*/

		/*StringBuilder sb = new StringBuilder();
		sb.append("hello");
		sb.append("world");
		System.out.println(sb.length());*/
		/*System.out.println(System.getProperty("maven_home"));
		System.out.println(System.getenv("PATH"));*/
		/*System.setProperty("my_user_home", "/home/mitnick");
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(System.getProperty("my_user_home"));
			}
		}).start();*/

		String[] strs = new String[3];
		strs[0] = "a";
		strs[1] = "b";
		strs[2] = "c";
		Object[] str2 = new Object[2];
		str2[0] = strs;
		str2[1] = "a";
		System.out.println(str2[1]);
	}
}
