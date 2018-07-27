package com.ajie.classLoader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

/**
 * @author niezhenjie
 * 
 */
public class ClassLoadTest {
	public static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "lib";

	public static void main(String[] args) {
		System.out.println(ClassLoadTest.ROOT_PATH);
		File file = new File(ROOT_PATH);// 打开文件夹
		// 构造url数组 因为URLClassLoader构造时接受的是url数组
		URL[] urls = new URL[1];
		// 第一个参数是协议 这里是文件协议 第二个参数是host 这里为null 第三个参数是文件的位置 注意 要以/结束
		URL url = null;
		try {
			url = new URL("file", null, file.getCanonicalPath() + File.separator);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		urls[0] = url;
		URLClassLoader load = new URLClassLoader(urls);
		try {
			@SuppressWarnings("unchecked")
			Class<Servlet> clazz = (Class<Servlet>) load.loadClass("PrimitiveServlet");
			try {
				Servlet instance = clazz.newInstance();
				try {
					instance.service(null, null);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				load.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
