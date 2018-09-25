package net.bgsystems.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.UnaryOperator;

public class ArrayListWithListener<E> implements List<E> {
	private ArrayList<E> internalList = new ArrayList<E>();
	private UnaryOperator<E> listener;

	public ArrayListWithListener(UnaryOperator<E> listener) {
		this.listener = listener;
	}

	@Override
	public int size() {
		return internalList.size();
	}

	@Override
	public boolean isEmpty() {
		return internalList.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return internalList.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return internalList.iterator();
	}

	@Override
	public Object[] toArray() {
		return internalList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return internalList.toArray(a);
	}

	@Override
	public boolean add(E e) {
		listener.apply(e);
		return internalList.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return internalList.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return internalList.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		c.forEach((element) -> {
			listener.apply(element);
		});
		return internalList.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return internalList.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return internalList.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return internalList.retainAll(c);
	}

	@Override
	public void clear() {
		internalList.clear();
	}

	@Override
	public E get(int index) {
		return internalList.get(index);
	}

	@Override
	public E set(int index, E element) {
		return internalList.set(index, element);
	}

	@Override
	public void add(int index, E element) {
		internalList.add(index, element);
	}

	@Override
	public E remove(int index) {
		return internalList.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return internalList.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return internalList.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return internalList.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return internalList.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return internalList.subList(fromIndex, toIndex);
	}
}
