package src;

import java.util.Iterator;

public class LinkedListSortedSet<T extends Comparable<T>> extends DoubleLinkedList<T> implements SortedSet<T> {
	@Override
	public void add(T data) {
		goToStart();
		Iterator<T> i = iterator();
		while(i.hasNext() && i.next().compareTo(data) < 0) {
			goToNext();
		}
		if (!getDataAtCurrent().equals(data))	insert(data);
	}

	@Override
	public boolean contains(T data) {
		return inList(data);
	}

	@Override
	public void remove(T data) {
		delete(data);
	}
}
