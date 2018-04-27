package com.ajie.collection;

import java.util.Collection;

/**
 * @author niezhenjie
 * 
 */
public interface List<E> extends Collection<E> {

	int size();

	/**
	 * 根据下标获取元素
	 * 
	 * @param i
	 * @return
	 */
	E get(int i);

	boolean add(E e);

	/**
	 * 在指定位置添加一个元素
	 * 
	 * @param i
	 * @param e
	 * @return
	 */
	boolean add(int i, E e);

	boolean addAll(Collection<? extends E> c);

	E remove(int i);

	boolean remove(Object o);

	boolean removeAll(Collection<?> c);

	E update(int i, E e);

	boolean contains(Object o);

	boolean containsAll(Collection<?> c);

	void clear();

	boolean isEmpty();

	List<E> subList(int fromIndex, int toIndex);

	/**
	 * 求两个集合的交集
	 */
	boolean retainAll(Collection<?> c);

	Object[] toArray();

	<T> T[] toArray(T[] a);

	int indexOf(Object o);

}
