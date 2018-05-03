package com.ajie.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 链表List 非线程安全 能add null 可重复值 适用于增删操作频繁的场景 不适用与遍历或查询频繁的场景
 * 
 * 注意 这里插入的null 只是内部Node里的E元素为空而已 而不知说整个Node为null 这里需要注意的
 * 
 * @author niezhenjie
 * 
 */
public class LinkedList<E> implements List<E>, java.io.Serializable {
	transient private static final long serialVersionUID = 1L;
	/** 数组链表第一个元素 没有元素 则为null */
	transient private Node<E> first;
	/** 数组链表最后一个元素 如果只有一个 则last == first 如果没有 则为null */
	transient private Node<E> last;
	transient private int modCount;
	transient private int size;

	/**
	 * 
	 */
	public LinkedList() {
	}

	public LinkedList(Collection<E> c) {
		c.addAll(c);
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		return size;
	}

	private void linkFirst(E e) {
		final Node<E> firstNode = first;
		Node<E> newNode = new Node<E>(null, e, firstNode);
		if (null == firstNode)
			last = newNode; // 只有一个 元素时 开始和结束都是同一个
		else
			firstNode.prev = newNode;
		first = newNode;
		size++;
		modCount++;
	}

	private void linkLast(E e) {
		final Node<E> lastNode = last;
		Node<E> newNode = new Node<E>(lastNode, e, null);
		if (null == last)
			first = newNode;
		else
			lastNode.next = newNode;
		last = newNode;
		size++;
		modCount++;

	}

	/**
	 * 将元素e插入到非空节点node前面
	 * 
	 * @param e
	 * @param node
	 */
	private void linkBefore(E e, Node<E> node) {
		final Node<E> prev = node.prev;
		Node<E> newNode = new Node<E>(prev, e, node);
		node.prev = newNode; // 将新的链表链进来
		// 注意这里的prev为空 表示prev是第一个元素 这里的null是指Node 而不是值Node里的E e 元素
		if (null == prev)
			first = newNode; // 其实就是在第一个元素前面插入 被插入的节点自然也就变成了第一个了
		else
			prev.next = newNode;
		size++;
		modCount++;
	}

	/**
	 * 删除传进来的链表第一个元素 判断f是否为null在调用该函数时判断
	 * 
	 * @param f
	 *            链表第一个节点
	 * @return
	 */
	private E unlinkFirst(Node<E> f) {
		final Node<E> next = f.next;
		final E element = f.item;
		first = next; // 老二成功篡位 坐上第一把座椅
		if (null == next)
			last = null;
		else
			next.prev = null;
		f.item = null;// 太狠心了 夺了老大的位置还不甘心 借GC老大之手 借刀杀人 斩草除根 以防后患
		f.next = null;
		size--;
		modCount++;
		return element;
	}

	private E unlinkLast(Node<E> l) {
		final E element = l.item;
		final Node<E> prev = l.prev;
		last = prev;
		if (null == prev)
			first = null;
		else
			prev.next = null;
		l.item = null;
		l.prev = null;
		size--;
		modCount++;
		return element;
	}

	/**
	 * 删除指定的节点
	 * 
	 * @param node
	 * @return
	 */
	private E unlink(Node<E> node) {
		final Node<E> prev = node.prev;
		final Node<E> next = node.next;
		final E element = node.item;
		if (null == prev) {
			first = next;
		} else {
			prev.next = next;
			node.prev = null;
		}
		if (null == next) {
			last = prev;
		} else {
			next.prev = prev;
			node.next = null;
		}
		node.item = null;
		size--;
		modCount++;
		return element;
	}

	public E getFirst() {
		final Node<E> node = first;
		if (null == node)
			throw new NoSuchElementException();
		return node.item;
	}

	public E getLast() {
		final Node<E> node = last;
		if (null == last)
			throw new NoSuchElementException();
		return node.item;
	}

	public E removeFirst() {
		final Node<E> node = first;
		if (null == node)
			throw new NoSuchElementException();
		unlinkFirst(node);
		return first.item;
	}

	public E removeLast() {
		final Node<E> node = last;
		if (null == node)
			throw new NoSuchElementException();
		unlinkLast(node);
		return node.item;
	}

	public void addFirst(E e) {
		linkFirst(e);
	}

	public void addLast(E e) {
		linkLast(e);
	}

	@Override
	public E get(int index) {
		checkPositionIndex(index);
		/* 这个算法不好
		 int i = 0;
		for(Node<E> n = first ; n !=null ;n=n.next){
			if(i++ == index){
				return n.item;
			}
		}*/
		// 参考jdk算法 非常巧妙
		return node(index).item;
	}

	/**
	 * 获取指定下标的节点 所以说链表的查询效率很低 每次查询只能首或尾巴处一个个走 知道走到指定的位置
	 * 
	 * @param index
	 * @return
	 */
	public Node<E> node(int index) {
		checkPositionIndex(index);
		// 如果下标小于size的一半 从头部开始遍历 速度会快很多 最多遍历 size>>1遍
		if (index < (size >> 1)) {
			Node<E> node = first;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
			return node;
		} else { // 如果下标大于size的一半 从尾部开始遍历 速度会快很多 最多遍历 size>>1遍
			Node<E> node = last;
			for (int i = size - 1; i > index; i--) {
				node = node.prev;
			}
			return node;
		}
	}

	@Override
	public boolean add(E e) {
		linkLast(e);
		return true;
	}

	@Override
	public boolean add(int i, E e) {
		/* not nice and not implement my mine
		 * if(i == size){
			addLast(e);
			return true;
		}
		checkPositionIndex(i);
		Node<E> node = node(i);
		Node<E> prev= node.prev;
		Node<E> next = node.next;
		Node<E> newNode = new Node<E>(null , e , node);
		if(null == next){
			last.next = newNode;
			newNode.prev = last;
			last = newNode;
		}else if(null == prev){
			first.prev = newNode;
			first  = newNode;
		}else{
			newNode.prev = prev;
			prev.next = newNode;
		}
		return true;*/
		// jdk
		checkPositionIndex(i);
		Node<E> node = node(i);
		if (size == i)
			linkLast(node.item);
		else
			linkBefore(e, node);
		return true;

	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		addAll(size, c);
		return false;
	}

	/**
	 * 指定位置插入多元素 算法：
	 * <p>
	 * 如果插入位置是链表的尾部 那么 直接将c每个元素转换成节点追加到当前链表
	 * <p>
	 * <p>
	 * 如果不是 则需要获取index对应的节点 然后在index处将链表斩为两段 第一段的最后一个节点为prev 第二段第一个节点为cur
	 * c每个元素转换成节点后追加到prev上面 prev没追加一个节点就往后移动以为 意思就是prev始终指向第一段链表的最后一个节点
	 * 由此将c全部挂在第一段链表 再将第一段的链表和第二段链表（cur）拼接起来
	 * </p>
	 * 
	 * 
	 * @param index
	 * @param c
	 * @return
	 */
	public boolean addAll(int index, Collection<? extends E> c) {
		checkPositionIndex(index);
		Object[] array = c.toArray();
		if (array.length == 0) {
			return false;
		}
		// prev每挂上一个节点都会向后移动一位 始终指向当前链表（去掉了index后面的链表）的尾部
		Node<E> prev, cur;
		if (size == index) {
			cur = null;
			prev = last;
		} else {
			cur = node(index); // 当前下标的节点 先与链表脱离 最后需要拼接上来
			prev = cur.prev;
		}
		for (Object o : array) {
			@SuppressWarnings("unchecked")
			// 每个对象生成一个节点 然后挂在index节点上
			Node<E> newNode = new Node<E>(prev, (E) o, null);
			if (null == prev) // 前面没有节点了 那么这个新插入的节点就是第一个了
				first = newNode;
			prev.next = newNode; // 指向新的节点
			prev = newNode; // 节点向后移一个 继续挂上其他节点
		}
		if (null == cur) { // 其实就是size==index的情况 直接在尾部追加了 不需要在断开在拼接
			last = prev;
		} else {
			cur.prev = prev;
			prev.next = cur;
		}
		size += array.length;
		modCount++;
		return true;
	}

	private void checkPositionIndex(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
	}

	@Override
	public E remove(int i) {
		checkPositionIndex(i);
		Node<E> node = node(i);
		E e = unlink(node);
		return e;

	}

	/**
	 * 原本我的想法是找到o的下标 然后根据下标大于还是小于size的一半决定是从开始处寻找还是从后面寻找回来 找到就remove
	 * 
	 * 后来想想 找到o的下标已经遍历一遍了 都已经找到了这个值了 再找一遍 亏了
	 * 所以上述的根据size>>1决定从前面找还是后面找起适用于根据传进来的下标进行操作的场景
	 * 
	 * @param o
	 * @return
	 */
	@Override
	public boolean remove(Object o) {

		// 为什么null需要特殊处理呢 因为对象对比需要使用equals()函数 如果o是null 而取出来的也为null
		// 不管用o.equal还是n.item.equal()都会出错
		if (null == o) { // null 情况
			for (Node<E> n = first; null != n.next; n = n.next) {
				if (null == n.item) {
					unlink(n);
					return true;
				}
			}
		} else {
			for (Node<E> n = first; null != n.next; n = n.next) {
				if (o.equals(n.item)) {
					unlink(n);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		Iterator<E> iterator = iterator();
		while (iterator.hasNext()) {
			E next = iterator.next();
			remove(next);
		}
		return true;
	}

	@Override
	public E update(int i, E e) {
		add(i, e);
		return e;
	}

	@Override
	public boolean contains(Object o) {
		return indexOf(o) > -1;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o))
				return false;
		}
		return true;
	}

	@Override
	public void clear() {
		for (Node<E> node = first; node != null;) {
			Node<E> next = node.next;
			node.prev = null;
			node.item = null;
			node.next = null;
			node = next;
		}
		size = 0;
		modCount++;
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
		Object object[] = new Object[size];
		int index = 0;
		for (Node<E> node = first; node != null; node = node.next) {
			object[index++] = node.item;
		}
		return object;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		Object[] array = toArray();
		if (a.length < size)
			return (T[]) Arrays.copyOf(array, size, a.getClass());
		System.arraycopy(array, 0, a, 0, size);
		if(a.length > size){
			a[size] = null;
		}
		return a;
	}

	@Override
	public int indexOf(Object o) {
		int index = 0;
		if (null == o) {
			for (Node<E> node = first; node != null; node = node.next) {
				if (null == node.item) {
					return index;
				}
				index++;
			}
		} else {
			for (Node<E> node = first; node != null; node = node.next) {
				if (o.equals(node.item)) {
					return index;
				}
				index++;
			}
		}
		return -1;
	}

	private static class Node<E> {
		E item;
		Node<E> prev;
		Node<E> next;

		Node(Node<E> pre, E item, Node<E> next) {
			this.item = item;
			this.prev = pre;
			this.next = next;
		}

	}

	public static void main(String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		list.add("h");
		list.add("e");
		list.add("l");
		list.add("l");
		list.add("o");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.addFirst("ajie");
		list.addFirst("Mitnick");
		list.addLast("haha");
		list.removeFirst();
		list.removeLast();
		list.remove("ajie");
		list.add(5, "f");
	//	list.clear();
		// list.remove(9);
		List<String> otherList = new ArrayList<String>() {
			private static final long serialVersionUID = 1L;
			{
				add("Kobe");
				add("Curry");
				add("Durant");
			}
		};
		list.addAll(otherList);
		// list.remove("Kobe");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		
		System.out.println(list.contains("Kobe"));
		System.out.println(list.containsAll(otherList));
		System.out.println("============楚河====================");
		Object[] array = list.toArray();
		for(Object o : array){
			System.out.println(o);
		}
		System.out.println("================汉界===================");
		Integer[] str = new Integer[array.length];
		Integer[] array2 = list.toArray(str);
		for(Integer s : array2){
			System.out.println(s);
		}
	}
}
