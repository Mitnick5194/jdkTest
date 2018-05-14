package com.ajie.annotation;

import java.lang.reflect.Method;

/**
 * 测试客户端
 * 
 * @author niezhenjie
 * 
 */
public class TestClient {

	public static void main(String[] args) {
		// 测试 检测某个类下面是否有使用指定的注解 如果使用了 注解里的值分别是什么
		Class<TestClient> clazz = TestClient.class; // 需要检测类
		for (Method m : clazz.getMethods()) { // 遍历改类的所有方法
			// 调用方法的getAnnotation 传入需要检测的注解类型
			MyAnnotation annotation = m.getAnnotation(MyAnnotation.class);
			// 如果不为空 则表示该方法有使用指定的注解类型
			if (null != annotation) {
				System.out.println("method name: " + m.getName());// 打印方法名字
				System.out.println("name: " + annotation.name());// 打印注解的name属性的值
				System.out.println("age: " + annotation.age());// 打印注解的age属性的值
			}
		}
	}

	@MyAnnotation(age = 20)
	public void introduce(String name, int age) {
		System.out.println("name=" + name + " age=" + age);
	}
}
