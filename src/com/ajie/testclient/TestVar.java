package com.ajie.testclient;

/**
 * 测试private属性的访问
 * 
 * 当前对象下的方法参数是当前对象的事例 那么可以直接使用参数的private属性
 * 
 * @author niezhenjie
 * 
 */
public class TestVar {
	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public TestVar(String name, int age) {
		this.name = name;
		this.age = age;
	}

	/**
	 * 当前对象下的方法参数是当前对象的事例 那么可以直接使用参数的private属性
	 * 
	 * @param tv
	 */
	public void getPrivateVar(TestVar tv) {
		this.name = tv.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String toString() {
		return "name=" + name + " age=" + age;
	}

	public static void main(String[] args) {
		TestVar tv = new TestVar("ajie", 24);
		TestVar tv2 = new TestVar("Mitnick", 25);
		System.out.println(tv.toString());
		System.out.println(tv2.toString());
		tv.getPrivateVar(tv2);
		System.out.println(tv.toString());

	}
}
