package com.ajie.collection.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.ajie.collection.List;

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
	 * 迭代器
	 * 
	 * @return
	 */
	@Override
	public Iterator<E> iterator() {
		return new ListItr();
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

	@Override
	public boolean containsAll(Collection<?> c) {
		// 我的写法 使用jdk的写法吧 不要整天抱着个迭代器 效率低知道吗
		/*	if (null == c) {
				return false;
			}
			Iterator<?> iterator = c.iterator();
			while (iterator.hasNext()) {
				Object next = iterator.next();
				if (!contains(next)) {
					return false;
				}
			}
			return true;*/
		// jdk写法 不会判断c是否为空或null如果是 抛空指针异常
		for (Object object : c) {
			if (!contains(object))
				return false;
		}
		return true;
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
		elementData[--size] = null; // 利于gc回收
		modCount++;
		return old;
	}

	/**
	 * 注释了的是我的做法 没有考虑到null的情况 虽然也可以 但是总感觉不好 也说不上哪里不好
	 * 
	 * 回答上述问题 大错特错啦 傻缺了吧 对象对比还用== 脑袋被驴踢了吗
	 * 
	 * 如果o为null的情况不分开处理 那么在调用对比equal 不管是o.equal还是elementData[i].equal都会出错
	 * 以为o是null 而elementData[i]有可能是null
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
					// 直接调用remove(i)就好啦
					remove(i);
					return true;
				}
			}
		} else {
			for (int i = 0; i < size; i++) {
				/*	if (o == elementData[i]) {  整天想什么呢 这么基础 这么低级的错误都犯*/
				if (o.equals(elementData[i])) {
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
		boolean iscontain = false;
		for (int i = 0, len = array.length; i < len; i++) {
			Object o = array[i];
			if (contains(o)) {
				remove(i);
				iscontain = true;
			}
		}
		return iscontain;
	}

	@Override
	public E update(int i, E e) {
		rangeCheck(i);
		E old = get(i);
		elementData[i] = e;
		return old;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		rangeCheckForSubList(fromIndex, toIndex);
		rangeCheck(toIndex);
		Object[] cpobject = Arrays.copyOfRange(elementData, fromIndex, toIndex);
		ArrayList<Object> arr = new ArrayList<Object>(cpobject.length);
		for (Object object : cpobject) {
			arr.add(object);
		}
		return (List<E>) arr;
	}

	public void rangeCheckForSubList(int fromIndex, int toIndex) {
		if (fromIndex < 0)
			throw new IndexOutOfBoundsException("fromIndex => " + fromIndex);
		if (toIndex >= size)
			throw new IndexOutOfBoundsException("toIndex >= " + toIndex);
		if (fromIndex > toIndex)
			throw new IllegalArgumentException("fromIndex > toIndex fromIndex=>" + fromIndex
					+ " toIndex=>" + toIndex);
	}

	/**
	 * 其实就是求两个集合的交集
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		/*
			这是我的方法 不可行  因为每次remove后 都会改变数组的大小 所以当i的值大于实际数组的大小时 就会抛异常
		Object[] elementData = this.elementData; // 对象 引用赋值
		boolean modified = false;
		int temp = 0;
		try {
			for (int i = 0, len = size; i < len; i++) {
				temp++;
				if (!c.contains(elementData[i])) {
					remove(elementData[i]);
					modified = true;
				}
			}
		} catch (Exception e) {
			System.out.println(elementData[temp]);
			System.out.println("下标 " +temp);
		}

		return modified;*/

		// 以下是jdk的实现方法
		// 对象 引用赋值 注意 一切对elementData的操作都会影响this.elementData
		Object[] elementData = this.elementData;
		boolean modified = false;
		int r = 0, w = 0;
		try {
			for (; r < size; r++) {
				if (c.contains(elementData[r])) {
					elementData[w++] = elementData[r]; // 直接根据数组下标操作数据 简单粗暴
				}
			}
		} finally { // 确保不管有没有异常 都要进入这里
			if (r != size) { // 这是因为出现了异常 才会导致r ！= size 否则的话 r一定等于size
				// 将异常处往后的元素直接拼接到elementData w位置后面
				System.arraycopy(elementData, r, elementData, w, size - r);
			}
			if (w != size) { // 表示c集合存在元素和this.elementData的元素不一致
				// 将w后面的元素全部设为null
				// 利于gc回收最终this.elementData的值就是elementData从0-w的元素
				for (int i = w; i < size; i++)
					elementData[i] = null;
				modified = true;
				modCount = size - w; // 将size-w个元素设为null 即进行了size-w此操作
				size = w;
			}
		}
		return modified;
	}

	@Override
	public Object[] toArray() {
		// 我的做法 这个做法不好 因为在调用elementData.length时 会返回这个值的长度 而不是size 在一定成都上造成内存浪费
		// return elementData;
		// jdk做法
		return Arrays.copyOf(elementData, size);
	}

	/**
	 * 参考了jdk实现
	 * 
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length < size)
			return (T[]) Arrays.copyOf(elementData, size, a.getClass());
		System.arraycopy(elementData, 0, a, 0, size);
		if (a.length > size) {
			// 这一步不明白 为什么需要将a[size]置为null 上面的arraycopy返回size-1以后不都是null了吗
			a[size] = null;
		}
		return a;
	}

	@Override
	public int indexOf(Object o) {
		if (null == o) {
			for (int i = 0; i < size; i++) {
				if (null == elementData[i]) {
					return i;
				}
			}
		}
		for (int i = 0; i < size; i++) {
			if (o.equals(elementData[i])) {
				return i;
			}
		}
		return -1;
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
	 * 这里的变量有点误导人 因为这里在使用的时候一般是传当前元素个数加上准备添加的元素个数（如add 就是 size+i）所以判断
	 * minCapacity - elementData.length > 0
	 * 判断其实就是判断当前数组的空余位置能否还能装进minCapacity个元素 如果可以（即minCapacity -
	 * elementData.length<0） 就不必要增容
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
		/*	ArrayList<String> list = new ArrayList<String>();
			list.add("h");
			list.add("e");
			list.add("l");
			list.add("l");
			Iterator<String> it = list.iterator();
			String s1 = it.next();
			String s2 = it.next();
			String s3 = it.next();
			System.out.println(s1 + "  " + s2 + "  " + s3+"  "+list.size());
			while(it.hasNext()){
				it.next();
				it.remove();
			}
			System.out.println(list.size());*/
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("a");
		arr.add("b");
		arr.add("c");
		arr.add("d");
		/*	Iterator<String> iterator = arr.iterator();
			while (iterator.hasNext()) {
				iterator.remove();
			}*/
		List<String> retain = new ArrayList<String>();
		retain.add("a");
		retain.add("2");
		retain.add("g");
		arr.retainAll(retain);
		for (int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i));
		}

	}

	/**
	 * 内部迭代器
	 * 
	 * @author niezhenjie
	 *
	 */
	private class ListItr implements Iterator<E> {
		private int cursor; // 游标 指向下个元素的位置
		// 返回最后被读取的元素的下标（其实就是cursor-1） 如果没有元素 则返回-1
		private int lastRef = -1;
		// 需要和ArraList里的modcount做比较
		// 如果读取时(next方法)检查到（checkExpectedModCount方法）这两个数不一致 表示应该有其他线程对集合进行了修改
		// 需要抛异常
		private int expectedModCoun = modCount;

		@Override
		public boolean hasNext() {
			return cursor != size;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			checkExpectedModCount();
			int i = cursor;
			if (i >= size)
				throw new NoSuchElementException();
			// jdk这样写的 不太懂 备忘录模式？？？不像吧
			Object[] elementDate = ArrayList.this.elementData;
			if (i >= elementDate.length)
				throw new ConcurrentModificationException();
			// 游标向下移动一位
			cursor = i + 1;
			return (E) elementDate[lastRef = i];
		}

		@Override
		public void remove() {
			// 在remove之前 指向 取（next()）一次 这样lastRef就会指向当前取的那个数 remove掉就可以了
			if (lastRef < 0)
				throw new IllegalStateException();
			checkExpectedModCount();
			try { // 其实不是很懂这里为什么需要捕抓异常呢？？？
				ArrayList.this.remove(lastRef);
				// 将游标指向当前lastRef处（以为在next时last在cursor的前一位）
				cursor = lastRef;
				lastRef = lastRef - 1;
				expectedModCoun = modCount;
			} catch (IndexOutOfBoundsException e) {
				throw new ConcurrentModificationException();
			}

		}

		private final void checkExpectedModCount() {
			// expectedModCoun的值为获取迭代器时的那个值 在此之后 任何对集合的修改都会使modCount发生变化 导致两者不一致
			// 最终跑出异常
			if (expectedModCoun != modCount)
				throw new ConcurrentModificationException();
		}

	}
}
