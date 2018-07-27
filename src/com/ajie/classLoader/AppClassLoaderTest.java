package com.ajie.classLoader;

/**
 * 加载java.class.path配置下的路径 测试该路径是什么
 * 
 * @author niezhenjie
 * 
 */
public class AppClassLoaderTest {
	public static void main(String[] args) {
		String path = System.getProperty("java.class.path");
		System.out.println(path);
	}

}
