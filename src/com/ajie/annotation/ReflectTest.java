package com.ajie.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author niezhenjie
 * 
 */
public class ReflectTest {

	public static void main(String[] args) {
		ReflectTest rt = new ReflectTest();
		// 获取所有的属性
		Field[] fields = rt.getClass().getDeclaredFields();
		// 获取所有的方法
		Method[] methods = ReflectTest.class.getMethods();
		for (Field field : fields) {
			System.out.println("属性名：" + field.getName());
			System.out.println("属性类型：" + field.getType());
		}
		for(Method m : methods){
			System.out.println("方法名："+m.getName());
			System.out.println("返回类型："+m.getReturnType());
			
		}
	}

	private String name;
	private String myInfo;
	private int age;

	public ReflectTest() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return myInfo;
	}

	public void setInfo(String info) {
		this.myInfo = info;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
