package com.ajie.classLoader;

import com.ajie.collection.impl.ArrayList;

/**
 * 项目下面的class文件 使用AppClassLoader加载 AppClassLoader的父类加载器是：ExtClassLoader
 * 
 * @author niezhenjie
 * 
 */
public class ClassLoaderDemo {

	public static void main(String[] args) {
		// 在我们这个项目下面找一个包 测试它的类加载器
		// 注意 这是这个项目下自己编写的ArrayList 不是util包下面的
		ClassLoader classLoader = ArrayList.class.getClassLoader();
		// 运行结果：sun.misc.Launcher$AppClassLoader@234f79cb
		System.out.println(classLoader.toString());
		// classLoader有父类加载器
		// 运行结果：sun.misc.Launcher$ExtClassLoader@234f79cb
		ClassLoader parent = classLoader.getParent();
		System.out.println(parent.toString());
		// 但是执行结果 parent2不是意料中的BootStrap ClassLoader 而是null
		ClassLoader parent2 = parent.getParent();
		System.out.println(parent2);

	}

}
