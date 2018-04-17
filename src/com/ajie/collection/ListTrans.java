package com.ajie.collection;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * 将V集合转换成E 继承AbstractCollection 代表ListTrans是一个集合对象 有size等属性 不然就是一个对象而已 根本无法遍历
 * 
 * @author ajie
 * 
 */
public abstract class ListTrans<E, V> extends AbstractList<E> {

	protected List<V> origin;

	public ListTrans(List<V> v) {
		if (null != v) {
			origin = v;
		}
	}

	// 实现必须重写次方法 方法返回E 而 E依赖V 所以在遍历那里会调用get函数 而get函数最终是依赖V但返回E 所以实现了转换
	public abstract E trans(V v);

	@Override
	public E get(int index) {
		return trans(origin.get(index));
	}

	@Override
	public int size() {
		return origin.size();
	}

	public static void main(String[] args) {
		User u1 = new User("zhangsan", 21);
		User u2 = new User("lisi", 23);
		User u3 = new User("wangwu", 34);
		List<User> list = new ArrayList<User>();
		list.add(u1);
		list.add(u2);
		list.add(u3);
		ListTrans<UserVo, User> tran = new ListTrans<UserVo, User>(list) {
			@Override
			public UserVo trans(User v) {
				// TODO Auto-generated method stub
				return new UserVo(v);
			}
		};
		for (UserVo userVo : tran) {
			System.out.println(userVo.getUserInfo());
		}

	}

}

class User {
	private String name;
	private int age;

	public User() {
	}


	public String getName() {
		return name;
	}

	public User(String name, int age) {
		this.name = name;
		this.age = age;
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
}

class UserVo {
	User u;

	public UserVo(User u) {
		this.u = u;
	}

	public String getUserInfo() {
		return "{name:" + u.getName() + "age: " + u.getAge() + "}";
	}

}
