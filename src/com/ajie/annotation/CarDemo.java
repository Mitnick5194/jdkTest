package com.ajie.annotation;

import java.lang.reflect.Field;

/**
 * @author niezhenjie
 * 
 */
public class CarDemo {
	@MyTag(name = "汉兰达加长版", price = 340000.88f)
	private Car myCar;

	@MyTag(name = "雅阁", price = 220000f)
	private Car otherCar;

	public Car getMyCar() {
		return myCar;
	}

	public void setMyCar(Car myCar) {
		this.myCar = myCar;
	}

	@MyTag(name = "途观L", price = 270000f)
	public void valueOf(String name, float price) {
		if (null == myCar) {
			myCar = new Car(name, price);
			return;
		}
		myCar.setName(name);
		myCar.setPrice(price);
	}

	public String getOtherCar() {
		if (null == otherCar)
			return null;
		return "品牌：" + otherCar.getName() + " 价格：" + otherCar.getPrice();
	}

	@Override
	public String toString() {
		// 不能null判断 因为new是发生在外部 所以这里的值始终为null
		/*	if (null == otherCar)
				return null;*/
		return "品牌：" + myCar.getName() + " 价格：" + myCar.getPrice();
	}

	public static void main(String[] args) {
		CarDemo carDemo = new CarDemo(); // 构造一个实体
		// 获取实体所有的属性（这里是属性 不是方法）
		Field[] fields = carDemo.getClass().getDeclaredFields();
		for (Field field : fields) {
			// 传入指定的注解 如果返回不为null则表明该属性使用了指定的注解（方法Method也有该方法）
			// 也可以使用 field.isAnnotationPresent(MyTag.class) 返回true||false
			MyTag myTag = field.getAnnotation(MyTag.class);
			if (null != myTag) {
				carDemo.setMyCar(new Car(myTag.name(), myTag.price()));
			}
		}
		System.out.println(carDemo.toString());
		System.out.println(carDemo.getOtherCar());
	}
}
