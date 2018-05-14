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
		_loop: for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (j == 2)
					break _loop;// break; 只会跳出当前循环 可以跳出外层循环
				System.out.println("i=" + i + "  j=" + j);
			}

		}
	}
}
