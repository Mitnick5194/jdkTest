package com.ajie.collection;

import java.util.Iterator;

/**
 * @author niezhenjie
 * 
 */
public abstract class AbstractMyCollection<E> implements MyCollection<E> {

	protected AbstractMyCollection() {

	}

	public abstract int size();

	public abstract Iterator<E> iterator();

	@Override
	public boolean isEmpty() {
		return 0 == size();
	}

	@Override
	public boolean contains(Object o) {
		Iterator<E> it = iterator();
		if (null == o) {
			while (it.hasNext()) {
				if (null == it.next()) {
					return true;
				}
			}
		} else {
			while (it.hasNext()) {
				if (o.equals(it.next())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean containAll(MyCollection<?> c) {
		for (Object o : c) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Object[] toArray() {
		Object[] o = new Object[size()];
		Iterator<E> it = iterator();
		for (int i = 0, len = o.length; i < len; i++) {
			if (!it.hasNext()) {
				return o;
			}
			o[i] = it.next();
		}
		return o;
	}

	@Override
	public boolean remove(Object o) {
		Iterator<E> it = iterator();
		if (null == o) {
			while (it.hasNext()) {
				if (it.next() == null) {
					it.remove();
					return true;
				}
			}
		} else {
			while (it.hasNext()) {
				if (o.equals(it.next())) {
					it.remove();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(MyCollection<E> c) {
		boolean modified = false;
		Iterator<E> it = iterator();
		while (it.hasNext()) {
			if (c.contains(it.next())) {
				it.remove();
				modified = false;
			}
		}
		return modified;
	}

	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(MyCollection<? extends E> c) {
		boolean modified = false;
		for (E e : c) {
			if (add(e)) {
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public void clean() {
		Iterator<E> it = iterator();
		while (it.hasNext()) {
			it.remove();
		}
	}

	@Override
	public boolean retainAll(MyCollection<?> c) {
		boolean modified = false;
		Iterator<E> it = iterator();
		while (it.hasNext()) {
			if (!c.contains(it.next())) {
				it.remove();
				modified = true;
			}
		}
		return modified;
	}
	
	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}
	
	public static void main(String[] args) {
	}

}
