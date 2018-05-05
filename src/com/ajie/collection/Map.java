package com.ajie.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author niezhenjie
 * 
 */
public interface Map<K, V> {
	/**
	 * Map的大小
	 * 
	 * @return
	 */
	int size();

	/**
	 * Map是否为空
	 * 
	 * @return
	 */
	boolean isEmpty();

	/**
	 * 是否包含key 为什么这里要用object 我的理解是 既然是判断是否包含
	 * 
	 * 没必要一定要是K类型 只要能在Map中找到这个key就行
	 * 
	 * @param key
	 * @return 是否存在key
	 */
	boolean isContainsKey(Object key);

	/**
	 * 是否存在指定value object类型和上述一样
	 * 
	 * @param value
	 * @return 是否存在value
	 */
	boolean isContainsValue(Object value);

	/**
	 * 根据给定的key返回值 留意一下就会发现所以的集合的非下标get都是使用object 而不是K（E）
	 * 
	 * 所以在调用get的时候并不会去检查类型 任何类型都能传进来 只是返回null而已
	 * 
	 * @param key
	 * @return key对应的值或null
	 */
	V get(Object key);

	/**
	 * 插入值 这里的key和value就要严格按照定义的KV类型了
	 * 
	 * @param key
	 * @param value
	 * @return 操作是否成功
	 */
	boolean put(K key, V value);

	/**
	 * 
	 * @param map
	 * @return 操作是否成功
	 */
	boolean putAll(Map<? extends K, ? extends V> map);

	/**
	 * 根据给定的key删除值
	 * 
	 * @param key
	 * @return 是否成功删除
	 */
	boolean remove(K key);

	/**
	 * 清空Map 此时并不会变成 null 只是size=0 isEmpty返回true
	 */
	void clear();

	/**
	 * 以为set集合结构返回Map所有key
	 * 
	 * @return
	 */
	Set<K> keySet();

	/**
	 * 返回包含所有的值的集合
	 * 
	 * @return
	 */
	Collection<V> values();

	/**
	 * 重写equal方法
	 * 
	 * @return
	 */
	boolean equals();

	/**
	 * 重写hashCode方法
	 * 
	 * @return
	 */
	int hashCode();

	/**
	 * map内部数据结构 只是一部分 还有table呢
	 * 
	 * @author niezhenjie
	 *
	 */
	interface Entry<K, V> {
		K getKey();

		V getValue();

		void setValue();

		boolean equals();

		int hashmap();

	}

}
