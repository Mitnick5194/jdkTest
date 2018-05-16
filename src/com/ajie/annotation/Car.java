package com.ajie.annotation;

/**
 * 此类的属性将由注解注入
 * 
 * @author niezhenjie
 * 
 */
public class Car {
	private String name;
	private float price;

	public Car(String name, float price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
