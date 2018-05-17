package com.ajie.testclient;

/**
 * @author niezhenjie
 * 
 */
public class UserBean extends Parent {
	private int age;
	private String sex;
	public UserBean(int age , String sex){
		this.age = age;
		this.sex = sex;
	}
	public int getGet() {
		return age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setGet(int age) {
		this.age = age;
	}

}

class Parent {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
