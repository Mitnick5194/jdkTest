package com.ajie.collection;

import java.util.Iterator;

/**
 * @author niezhenjie
 * 
 */
public interface MyCollection<E> extends Iterable<E> {

	int size();

	boolean add(E e);

	boolean addAll(MyCollection<? extends E> c);

	boolean remove(Object o);

	boolean removeAll(MyCollection<E> c);

	boolean contains(Object o);

	boolean containAll(MyCollection<?> c);

	boolean retainAll(MyCollection<?> c);

	void clean();

	boolean isEmpty();

	Iterator<E> iterator();

	Object[] toArray();

	<T> T[] toArray(T[] a);

	boolean equals(Object o);

	int hasCode();

}
