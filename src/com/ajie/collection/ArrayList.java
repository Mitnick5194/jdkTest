package com.ajie.collection;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author niezhenjie
 * 
 */
public class ArrayList<E> implements List<E>, Serializable {

	private static final long serialVersionUID = 1L;

	/** 集合大小 */
	private int size;
	/** 集合元素 */
	private transient Object[] elementData;
	/** 空集合 */
	private final static Object[] EMPTY_ELEMENTDATA = {};
	/** 集合修改次数 */
	private transient int modCount = 0;
	/** 集合默认大小 */
	private static final int DEFAULT_CAPACITY = 10;
	/** 集合的最大容量 */
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	/**
	 * 使用默认大小构造集合 这里没有调用this(int capcity)是因为每次add的时候都会去判断大小 如果快满了 会增大容量
	 * 如果这里初始化为了默认的大小 那么不管集合用不用 已创建就会占用了一定的内存
	 * 
	 */
	public ArrayList() {
		elementData = EMPTY_ELEMENTDATA;
		// this(DEFAULT_CAPACITY);
	}

	/**
	 * 构造指定大小的集合
	 * 
	 */
	public ArrayList(int capcity) {
		if (0 > capcity) {
			throw new IllegalArgumentException("集合初始容量小于0 ==>" + capcity);
		}
		elementData = new Object[capcity];

	}

	public ArrayList(Collection<? extends E> c) {
		if (null == c || 0 > c.size()) {

		}
		int len = c.size();
		elementData = new Object[len];
		Iterator<?> iterator = c.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			Object o = iterator.next();
			elementData[i++] = o;
		}
		// jdk实现：
		/*elementData = c.toArray();
			size = c.size();
			 这里其实不是很清楚为什么要这样做 看注释说c.toArray不一定返回的是Object[] 纳闷!!
			 终于知道了     // c.toArray might (incorrectly) not return Object[] (see 6260652) 原来 6260652是一个jdk bug的编码
			 可以去官网查看bug的详情：
			  http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6260652
			  */
		/*	if (elementData.getClass() != Object[].class) {
				elementData = Arrays.copyOf(elementData, size, Object[].class);
			}*/
	}

	/**
	 * 集合是否包含对象
	 * 
	 * @param o
	 * @return
	 */
	@Override
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	/**
	 * 迭代器
	 * 
	 * @return
	 */
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// 我的写法
		if (null == c) {
			return false;
		}
		Iterator<?> iterator = c.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (!contain(next)) {
				return false;
			}
		}
		return true;
		// jdk写法 不会判断c是否为空或null如果是 抛空指针异常
		/*for (Object object : c) {
			if (!contain(object))
				return false;
		}
		return true;*/
	}

	@Override
	public void clear() {
		// 这是我的做法 （不好）
		/*elementData = EMPTY_ELEMENTDATA;
		size = 0;
		modCount++;*/
		// jdk
		modCount++;
		for (int i = 0, len = elementData.length; i < len; i++)
			elementData[i] = null; // 可以使GC更快地回收
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E get(int i) {
		rangeCheck(i);
		return (E) elementData[i];
	}

	/**
	 * 添加元素
	 * 
	 * @param e
	 * @return
	 */
	@Override
	public boolean add(E e) {
		ensureCapacityInternal(size + 1);
		elementData[size++] = e;
		return true;
	}

	/**
	 * 从指定位置添加
	 * 
	 * @param i
	 * @param e
	 * @return
	 */
	@Override
	public boolean add(int i, E e) {
		rangeCheckForAdd(i);
		ensureCapacityInternal(size + 1);
		System.arraycopy(elementData, i, elementData, i + 1, size - i);
		elementData[i] = e;
		size++;
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		// 需要将c转换成object[]
		Object[] array = c.toArray(); // c的实现是当前的arraylist类型 所以需要先实现toArray方法
		int len = array.length;
		ensureCapacityInternal(size + len);
		System.arraycopy(array, 0, elementData, size, len);
		size += len;
		// 如果c转换为数组后的长度不为0 返回true
		return len != 0;
	}

	/**
	 * 参考了jdk 将需要删除的元素后面的元素整体向前移动一位 达到覆盖了该元素的目的
	 * 
	 * 最后还需要将最后的元素置为 null 有利于gc回收
	 */
	@Override
	public E remove(int i) {
		rangeCheck(i);
		E old = get(i);
		// 计算出删除元素后面的元素个数
		int moveNum = size - i - 1;
		if (moveNum > 0) // 如果删除的是最后一个 没必要做下步操作
			System.arraycopy(elementData, i + 1, elementData, i, moveNum);
		elementData[--size] = null;
		modCount++;
		return old;
	}

	/**
	 * 注释了的是我的做法 没有考虑到null的情况 虽然也可以 但是总感觉不好 也说不上哪里不好
	 */
	@Override
	public boolean remove(Object o) {
		/*for (int i = 0; i < size; i++) {
			Object obj = get(i);
			if (o == obj) {
				remove(i);
				return true;
			}
		}*/
		if (null == o) {
			for (int i = 0; i < size; i++) {
				if (null == elementData[i]) {
					// jdk这里不是调用这个方法 而是单独的一个方法fastRemove() 感觉没这个必要
					// 直接调用remove(i)就好了
					remove(i);
					return true;
				}
			}
		} else {
			for (int i = 0; i < size; i++) {
				if (o == elementData[i]) {
					remove(i);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		Object[] array = c.toArray();
		return false;
	}

	@Override
	public E update(int i, E e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contain(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object[] toArray() {
		// 我的做法 这个做法不好 因为在调用elementData.length时 会返回这个值的长度 而不是size 在一定成都上造成内存浪费
		// return elementData;
		// jdk做法
		return Arrays.copyOf(elementData, size);
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 检查下标是否合法
	 * 
	 * @param i
	 */
	private void rangeCheck(int i) {
		if (i < 0 || i >= elementData.length)
			throw new IndexOutOfBoundsException("下标越界：" + i);
	}

	/**
	 * 检查下标是否合法 用于添加时校验 和rangeCheck区别在与添加时下标可以是length
	 * 
	 * @param i
	 */
	private void rangeCheckForAdd(int i) {
		if (i < 0 || i > elementData.length)
			throw new IndexOutOfBoundsException("下标越界：" + i);
	}

	/**
	 * 检查或改变集合的大小 调用这个方法的方法最终都会调用ensureExplicitCapacity方法 ensureExplicitCapacity
	 * ensureExplicitCapacity方法里有modCount 所以调用了这个方法的方法都不需要modCount++了
	 * 
	 * @param i
	 */
	private void ensureCapacityInternal(int minCapacity) {
		if (elementData == EMPTY_ELEMENTDATA) {
			minCapacity = Math.max(minCapacity, DEFAULT_CAPACITY);
		}
		ensureExplicitCapacity(minCapacity);
	}

	/**
	 * 
	 * @param minCapacity
	 */
	private void ensureExplicitCapacity(int minCapacity) {
		modCount++;
		// 新的容量必须大于现在的集合大小
		if (minCapacity - elementData.length > 0) {
			grow(minCapacity);
		}
	}

	/**
	 * 扩大集合的容量大小
	 * 
	 * @param minCapacity
	 */
	private void grow(int minCapacity) {
		int oldCapacity = elementData.length;
		// 这里不需要检验minCapacity 如果minCapacity小于newCapacity 直接使用newCapacity了
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		if (newCapacity - minCapacity < 0)
			newCapacity = minCapacity;
		// 牛逼 比整形的最大值-8还大 还能怎样 直接给你整形最大值
		if (newCapacity - MAX_ARRAY_SIZE > 0)
			newCapacity = Integer.MAX_VALUE;
		elementData = Arrays.copyOf(elementData, newCapacity);
	}

	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("h");
		list.add("e");
		list.add("l");
		list.add("l");
		list.add("o");
		list.add(null);
		list.remove("h");
		for (int i = 0, len = list.size(); i < len; i++) {
			System.out.println(list.get(i));
		}
		/*	list.add(2, "j");

		for (int i = 0, len = list.size(); i < len; i++) {
			System.out.println(list.get(i));
		}
		System.out.println("============================");
		ArrayList<String> list2 = new ArrayList<String>();
		list2.add("1");
		list2.add("2");
		list2.add("3");
		list.addAll(list2);
		list2.add("5");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}*/

	}
}
