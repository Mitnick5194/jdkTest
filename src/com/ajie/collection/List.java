package com.ajie.collection;

import java.util.Collection;

/**
 * @author niezhenjie
 * 
 */
public interface List<E> extends Collection<E> {

	int size();
	E get(int i);
	boolean add(E e);
	boolean add(int i , E e);
	boolean addAll(Collection<? extends E> c);
	E remove(int i);
	boolean remove(Object o);
	boolean removeAll(Collection<?> c);
	E update(int i , E e);
	boolean contains(Object o);
	boolean containsAll(Collection<?> c);
	void clear();
	boolean isEmpty();
	List<E> subList(int fromIndex , int toIndex);
	boolean retainAll(Collection<?> c);
	Object[] toArray();
	<T> T[] toArray(T[] a);
	int indexOf(Object o);
	
}
