package com.ajie.jmx;

/**
 * @author niezhenjie
 * 
 */
public class HelloJmx implements HelloJmxMBean {

	public String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void sayHello() {
		System.out.println(name + " say hello !!");
	}

}
