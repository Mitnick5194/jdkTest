package com.ajie.collection.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import com.ajie.collection.Map;

/**
 * HashMap的数据结构： 一个连续内存的表格，每一项都存放这一个链表的首地址 存放的对象的key通过hash运算得出一个hash值，
 * 再模上表格的长度（这样得出的值一定是0<值<表格长度）key通过hash运算在模上表格长度后得出的值就是表格的下标（或位置）
 * 如果两个不同的key通过运算得出的表格下标是一样的 那么就将改对象（封装好的entry）放入表格的位置 将原来的链表挂载新entry上 即
 * 链表是从头部插入 头部entry的next指针指向原来的链表的头entry 直接从头部插入的好处有：链表不能通过下标索引直接找到位置 如果要从
 * 后面添加的话 那么每次添加都值能从链表头一个一个next知道链表尾 这样效率太低了 特别是链表很长的时候 所以在头部添加效率会高很多
 * 
 * 注意 不同的key计算出来的hashcode有可能一样 一样的hashcode转换成index后一定一样
 * 而不一样的hashcode转换成index也有可能一样 所以在 数组表格中 同一项的链表hashcode不一定一样 所以在entry需要保存hash的值
 * 
 * capacity始终是2的幂次 这样做是有利于在计算hashcode后计算对应的下标 并且这样做有利于下标的均匀分配（不会使数组某个元素的链表很长
 * 某些元素一个值都没有）
 * 
 * 其实不是用模 用 h& length-1; length-1 可以确保 是数组的下标 如： length=16 -1=1111 任何数 &
 * 1111最大就是 1111=15 即下标最大为 15
 * 
 * @author niezhenjie
 * 
 */
public class HashMap<K, V> implements Map<K, V>, Serializable {
	private static final long serialVersionUID = 1L;

	/** 默认初始化容量 */
	static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
	/** 最大容量 */
	static final int MAXIMUM_CAPACITY = 1 << 30;
	// 因为这个是静态的 所以需要使用?? 如果使用KV会报错 因为KV并非静态
	static final Entry<?, ?>[] EMPTY_TABLE = {};
	/** 连续内存的数组列表 */
	@SuppressWarnings("unchecked")
	transient Entry<K, V>[] table = (Entry<K, V>[]) EMPTY_TABLE;
	/** 默认装载因子 当容量元素的个数超过了 容量（capacity）与装载因子的乘积 就会进行扩容 */
	static final float DEFAULT_LOAD_FACTOR = 0.75f;
	/** 阈值 当元素超过这个值时 开始增容 一般情况这个值为 loadfactor*capacity 或者是MAXIMUM_CAPACITY+1 */
	int threshold;
	/** 装载因子 当容量元素的个数超过了 容量（capacity）与装载因子的乘积 就会进行扩容 */
	final float loadFactor;
	transient int size;
	/** 更改的次数 */
	transient int modCount;

	transient int hashseed = 0;

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
	 * 增加table的容量 注意 为了方便，toSize最终增大的容量是2的幂数 如toSize=3最终是4 9==>16.... 即
	 * roundUpToPowerOf2能确保返回一定是2的幂次 这样做是为了使计算hashmap对应的下标能得到更均匀的分布
	 * 
	 * @param toSize
	 */
	private void inflateTable(int toSize) {
		int capacity = roundUpToPowerOf2(toSize); // roundUpToPowerOf2能确保capacity一定是2的幂次
		// 这里的+1也不太明白 既然MAXIMUM_CAPACITY是最大了 为什么还要+1呢？？？？？？？
		threshold = Math.min((int) (capacity * loadFactor), MAXIMUM_CAPACITY + 1);
	}

	/**
	 * highestOneBit((number - 1) << 1) 向上2的幂 如 9=>16；
	 * number-1是为了防止number刚好是2的幂次 -1后 位数减1 然后左移 位数仍然不变 如 8-1=111<<1=1110=14
	 * highestOneBit(14)=8 9-1=1000<<10000=16 highestOneBit(16)=16
	 * 
	 * 
	 * 
	 */
	private int roundUpToPowerOf2(int number) {
		return number > MAXIMUM_CAPACITY ? MAXIMUM_CAPACITY
				: number > 1 ? highestOneBit((number - 1) << 1) : 1;
	}

	/**
	 * 将i二进制第一位后面的数全都变成0 如：i=9 1001|100=1100|11=1111|0|0|0 = 1111-111 = 1000;
	 * 
	 * 其实这个函数在Integer里有
	 * 
	 * @param i
	 * @return
	 */
	private int highestOneBit(int i) {
		i |= i >> 1;
		i |= i >> 2;
		// 因为上面经历了右移一位和右移两位 这时候前4个已经是1了 那么可以直接再右移4位了 当然 如果位数不够 那么就变成0 0|1最终也为1
		i |= i >> 4;
		i |= i >> 8;
		i |= i >> 16;
		// 上面的几步是将i的二进制格式全部变成1 如 9=>1001=>1111 下面这一步是将i的二进制格式除第一位是1以外 其他变为0
		// 如： 1111=>1000
		return i - (i >>> 1);
	}

	private void putAllForCreate(Map<? extends K, ? extends V> m) {
		for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
			putForCreate(e.getKey(), e.getValue());
		}
	}

	/**
	 * 创建一个Entry 并不会添加上去 只是创建而已 不会造成table的大小变化
	 * 
	 * @param key
	 * @param value
	 */
	private void putForCreate(K key, V value) {
		int hash = null == key ? 0 : hash(key); // 根据key计算出hash值
		int i = indexFor(hash, hash); // 根据hash值计算出下标的位置
		// 遍历计算出的下标位置的链表
		for (Entry<K, V> e = table[i]; e != null; e = e.next) {
			Object k;
			// hash相等（hash相等 那么key在表格的位置一定一样）并且key有在链表里找到 直接将原本key的值覆盖
			if (e.hash == hash && (key == e.key || (null != e.key && key.equals(e.key)))) {
				e.value = value;
				return;
			}
		}
		createEntry(key, value, hash, i);
	}

	/**
	 * 创建一个entry实体 注意 该方法并不会导致table增容
	 * 
	 * @param key
	 * @param value
	 * @param hash
	 * @param i
	 */
	private void createEntry(K key, V value, int hash, int i) {
		// 根据下标取得对应的链表
		Entry<K, V> entry = table[i];
		// 将新添加的entry直接放入头部 将原来的链表挂载新entry的next指针
		table[i] = new Entry<>(key, value, entry, hash);
		size++;
	}

	@Override
	public V put(K key, V value) {
		if (table == EMPTY_TABLE)
			// 其实我不明白 jdk为什么在下面的方法传入的不是DEFAULT_INITIAL_CAPACITY 而是threshold
			// 虽然在初始化时或这说在没有调用inflateTable时
			// threshold的值确实等于DEFAULT_INITIAL_CAPACITY 但是这样做我感觉不太好 容易造成混淆
			inflateTable(DEFAULT_INITIAL_CAPACITY);
		if (null == key)
			return putForNullKey(value);
		int hash = hash(key);
		int index = indexFor(hash, table.length);
		for (Entry<K, V> e = table[index]; e != null; e = e.next) {
			// 下面的判断是判断传入的key是否已经存在了 hash值一样 不一定就是形同的key 但是相同呢的key hash一定一样 所以在
			// 判断hash相等的同时 也要满足key相等
			if (hash == e.hash && (key == e.key || (null != key && key.equals(e.key)))) {
				V old = e.value;
				e.value = value;
				return old; // 返回key原来对应的值
			}
		}
		modCount++;
		addEntry(key, value, hash, index);
		return null; // 新添加并没有什么东西可返回
	}

	/**
	 * 这个方法会导致数组表格增容
	 * 
	 * @param key
	 * @param value
	 * @param hash
	 * @param index
	 */
	private void addEntry(K key, V value, int hash, int index) {
		// 只有size大于阈值 并且table[index]存在才做增容操作
		// 其实我不太明白 为什么需要判断null != table[index] 难道null != table[index]不存在就不能做增容了吗
		// 回答上面的疑问：只有size大于阈值threshold时 并且由key计算出的下标处有元素了 即出现了hash冲突了 才会增容
		// 如果不出现hash冲突 即下标处为空 直接插入就行了 不用增容 如果size==length时
		// 那么table[index]一定不会出现null的情况 所以会进入if
		if ((size >= threshold) && (null != table[index])) {
			resize(table.length * 2);
			// 不明白这里为什么还要再求一次hash 传进来的hash不是已经是key的hash运算结果了吗 这里还要再求一次 why???
			hash = null == key ? 0 : hash(key);
			// 这里重新计算还能理解 因为table的大小变了 所以下标也随着变化
			index = indexFor(hash, table.length);
		}
		createEntry(key, value, hash, index);
	}

	private void resize(int newCapacity) {
		Entry<K, V>[] oldTable = table;
		int oldCapacity = oldTable.length;
		if (oldCapacity > MAXIMUM_CAPACITY) {
			// 这里也不是很明白 为什么要吧threadshold设为Integer.MAX_VALUE
			// 难道是这样size就不会大于Integer.MAX_VALUE 然后就不会进入再进入这个方法了？ 我猜是这样子
			threshold = Integer.MAX_VALUE;
			return;
		}
		// 泛型不能定义数组？？ Entry<K,V>[] newTable = new Entry<K,V>[newCapacity]; 会报错
		Entry[] newTable = new Entry[newCapacity];

	}

	/**
	 * key为null的情况 注意 key为null只能添加到数组table[0]位置上的链表
	 * 
	 * @param value
	 * @return
	 */
	private V putForNullKey(V value) {
		for (Entry<K, V> e = table[0]; e != null; e = e.next) {
			if (null == e.key) {
				V old = e.value;
				e.value = value;
				return old;
			}
		}
		return null;
	}

	@Override
	public boolean putAll(Map<? extends K, ? extends V> map) {
		// TODO Auto-generated method stub
		return false;
	}

	final private int hash(Object k) {
		int h = hashseed;
		/*	if(0!=h && k instanceof String){
				//do not understanding
				return sun.misc.Hashing.stringHash32((String) k);
			}*/
		h ^= k.hashCode();
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	final private int indexFor(int hash, int length) {
		return hash & (length - 1); // 这样下标最大只能是length-1
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
	public Set<com.ajie.collection.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals() {
		// TODO Auto-generated method stub
		return false;
	}

	static class Entry<K, V> implements Map.Entry<K, V> {
		final K key;
		V value;
		Entry<K, V> next;
		int hash; // key对应的hash值

		Entry(K key, V value, Entry<K, V> next, int hash) {
			this.key = key;
			this.value = value;
			this.next = next;
			this.hash = hash;
		}

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
		java.util.HashMap<String, String> map = new java.util.HashMap<String, String>();
		map.put("price", "24");
		map.put("orderId", "axdf12304");
		map.put("passenger	", "LockLee");
		java.util.HashMap<String, String> m = new java.util.HashMap<String, String>(map);
	}

	private static int roundUpToPowerOf(int number) {
		// assert number >= 0 : "number must be non-negative";
		return number >= MAXIMUM_CAPACITY ? MAXIMUM_CAPACITY : (number > 1) ? Integer
				.highestOneBit((number - 1) << 1) : 1;
	}

}