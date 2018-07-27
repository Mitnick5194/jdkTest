package com.ajie.classLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author niezhenjie
 * 
 */
public class MyClassLoader extends ClassLoader {

	/** 加载的文件所在的目录绝对路径 */
	private final String libPath;

	public MyClassLoader(String path) {
		libPath = path;
	}

	/**
	 * 将文件以io的方式读入，然后调用ClassLoader的definedClass方法
	 */
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String path = getPath();
		String fileName = getFileName(name);
		File file = new File(path, fileName);
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int n = 0;
			while ((n = is.read()) != -1) {
				baos.write(n);
			}
			baos.close();
			byte[] data = baos.toByteArray();
			return defineClass(fileName, data, 0, data.length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// 忽略
			}
		}
		return super.findClass(name);
	}

	/**
	 * 返回全限路径的文件名的class类型 eg: com.ajie.Hello --> Hello.class
	 * 
	 * @param name
	 * @return
	 */
	public String getFileName(String name) {
		if (null == name)
			return null;
		int idx = name.lastIndexOf(".");
		if (idx == -1) {
			return name + ".class";
		} else {
			return name.substring(idx + 1) + ".class";
		}
	}

	public String getPath() {
		return libPath;
	}

	public static void main(String[] args) {
		MyClassLoader loader = new MyClassLoader("/home/mitnick/temp/");
		ClassLoader parent = loader.getParent();
		ClassLoader parent2 = parent.getParent();
		ClassLoader parent3 = parent2.getParent();
		System.out.println("当前类加载器：" + loader.toString()); // com.ajie.classLoader.MyClassLoader@35e5ebbf
		System.out.println("一级父类加载器：" + parent.toString()); // sun.misc.Launcher$AppClassLoader@20e90906
		System.out.println("二级父加载器" + parent2.toString()); // sun.misc.Launcher$ExtClassLoader@234f79cb
		System.out.println("三级父加载器" + parent3); // null
		try {
			Class<?> clazz = loader.loadClass("com.ajie.classLoader.Hello");
			Object obj = clazz.newInstance(); // 使用反射机制获取对象
			Method method = clazz.getDeclaredMethod("say", String.class, int.class);
			method.invoke(obj, "ajie", 24);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
