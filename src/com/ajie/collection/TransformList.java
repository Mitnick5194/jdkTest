package com.ajie.collection;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * 将v列表转换成e列表
 * 
 * @author daibo
 * 
 * @param <E>
 * @param <V>
 */
public abstract class TransformList<E, V> extends AbstractList<E> {
	/** 原数据 */
	protected List<V> m_Originals;
	/** 缓存数据 */
	protected Object[] m_Caches;
	/** 空数据 */
	protected static Object EMPTY_OBJECT = new Object();

	public TransformList(List<V> originals) {
		this(originals, true);
	}

	public TransformList(List<V> originals, boolean useCaches) {
		if (null == originals || originals.isEmpty()) {
			m_Originals = Collections.emptyList();
		} else {
			m_Originals = originals;
			if (useCaches) {
				m_Caches = new Object[m_Originals.size()];
			} else {
				m_Caches = null;
			}
		}

	}

	public abstract E transform(V v);

	@SuppressWarnings("unchecked")
	@Override
	public E get(int index) {
		if (null != m_Caches) {
			Object e = m_Caches[index];
			if (null == e) {
				e = transform(m_Originals.get(index));
				if (null == e) {
					m_Caches[index] = EMPTY_OBJECT;
				} else {
					m_Caches[index] = e;
				}
			}
			return e == EMPTY_OBJECT ? null : (E) e;
		} else {
			return transform(m_Originals.get(index));
		}
	}

	@Override
	public int size() {
		return m_Originals.size();
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("111");
		list.add("222");
		List<String> news = new TransformList<String, String>(list) {

			@Override
			public String transform(String v) {
				System.out.println(v);
				return v;
			}
		};
		for (String s : news) {
			System.out.println(s);
		}
/*		for (String s : news) {
			System.out.println(s);
		}*/
	}

}
