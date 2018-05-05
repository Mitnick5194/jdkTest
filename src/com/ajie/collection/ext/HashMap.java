package com.ajie.collection.ext;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import com.ajie.collection.Map;

/**
 * @author niezhenjie
 * 
 */
public class HashMap<K, V> implements Map<K, V>, Serializable {
	private static final long serialVersionUID = 1L;

	/** 默认初始化容量 */
	static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
	/** 最大容量 */
	static final int MAXIMUM_CAPACITY = 1 << 30;
	static final Entry<?, ?>[] EMPTY_TABLE = {};
	/** 连续内存的列表 */
	transient Entry<?, ?>[] table = EMPTY_TABLE;
	/** 默认装载因子 当容量元素的个数超过了 容量（capacity）与装载因子的乘积 就会进行扩容 */
	static final float DEFAULT_LOAD_FACTOR = 0.75f;
	/** 阈值 当元素超过这个值时 开始增容 一般情况这个值为 loadfactor*capacity 或者是MAXIMUM_CAPACITY+1 */
	int threshold;
	/** 装载因子 当容量元素的个数超过了 容量（capacity）与装载因子的乘积 就会进行扩容 */
	final float loadFactor;
	transient int size;
	/** 更改的次数 */
	transient int modCount;

	/**
	 * 构造一个指定初始化容量 指定装载因子的HashMap
	 * 
	 * @param capcity
	 * @param loadFactor
	 */
	public HashMap(int initialCapacity, float loadFactor) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("illegal initail capacity: " + initialCapacity);
		if (initialCapacity > MAXIMUM_CAPACITY)
			initialCapacity = MAXIMUM_CAPACITY;
		if (loadFactor <= 0 || Float.isNaN(loadFactor))
			throw new IllegalArgumentException("illegal load factor: " + loadFactor);
		this.loadFactor = loadFactor;
		threshold = initialCapacity;
		// jdk在这里还会调用一个init函数 但是init方法是空的 好像是留给子类覆盖的
		// 然后在hashmap任何一个构造都会调用到子类重写的这个方法 这个函数在linkedHashMap里有用到

	}

	public HashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR);
	}

	/**
	 * 构造一个默认大小 默认loadFactor的HashMap
	 */
	public HashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}

	/**
	 * 构造一个指定map的hashMap
	 * 
	 * @param m
	 */
	public HashMap(Map<? extends K, ? extends V> m) {
		// 不太明白这里为什么需要+1
		this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR + 1), DEFAULT_INITIAL_CAPACITY),
				DEFAULT_LOAD_FACTOR);
		inflateTable(threshold); // 增容
		putAllForCreate(m);
	}

	/**
	 * 增加table的容量 注意 为了方便 toSize最终增大的容量是2的幂数 如toSize=3最终是4 9==>16....
	 * 
	 * @param toSize
	 */
	private void inflateTable(int toSize) {
		int capacity = roundUpToPowerOf2(toSize);
		// 这里的+1也不太明白 既然MAXIMUM_CAPACITY是最大了 为什么还要+1呢？？？？？？？
		threshold = Math.min((int) (capacity * loadFactor), MAXIMUM_CAPACITY + 1);
	}

	private int roundUpToPowerOf2(int number) {
		return 0;
	}

	private void putAllForCreate(Map<? extends K, ? extends V> m) {

	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean isContainsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isContainsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean put(K key, V value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean putAll(Map<? extends K, ? extends V> map) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(K key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals() {
		// TODO Auto-generated method stub
		return false;
	}

	static class Entry<K, V> implements Map.Entry<K, V> {

		@Override
		public K getKey() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public V getValue() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setValue() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean equals() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int hashmap() {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	public static void main(String[] args) {
		int bigNum = 0xFFFFFFF + 1;
		System.out.println(bigNum);
		int ret = HashMap.roundUpToPowerOf(9);
		System.out.println(ret);
		System.out.println(Integer.highestOneBit(bigNum));
	}

	private static int roundUpToPowerOf(int number) {
		// assert number >= 0 : "number must be non-negative";
		return number >= MAXIMUM_CAPACITY ? MAXIMUM_CAPACITY : (number > 1) ? Integer
				.highestOneBit((number - 1) << 1) : 1;
	}
}
