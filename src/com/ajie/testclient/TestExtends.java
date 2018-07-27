package com.ajie.testclient;

/**
 * @author niezhenjie
 * 
 */
public class TestExtends {

	public static void main(String[] args) {
		Person p = new Person();
		p.setAge(24);
		p.setName("ajie");
		System.out.println(p.toString());
		Person mitnick = new Mitnick();
		mitnick.setAge(25);
		mitnick.setName("haha");
		System.out.println(mitnick.toString());
	}
}

class Person {
	private String name;
	private int age;

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return this.age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String toString() {
		return name + "  " + age;
	}
}

class Mitnick extends Person {

	private String name;
	private int age;

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return this.age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return name + "  " + age;
	}

}
