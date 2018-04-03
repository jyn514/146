package src;

import java.util.Iterator;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class ArrayMaxHeap<T extends Comparable<T>>extends ArrayTree<T> implements Heap<T> {

	private int last = 0; // first null element (in breadth order)

	public ArrayMaxHeap() {
		this(1024); // kB is nothing
	}

	public ArrayMaxHeap(int size) {
		this(size, 2);
	}

	public ArrayMaxHeap(int size, float loadFactor) {
		super(size, loadFactor);
	}

	@SafeVarargs
	public ArrayMaxHeap(T... data) {
		this(data.length);
		for (T t : data) add(t);
	}

	@SafeVarargs
	public ArrayMaxHeap(float loadFactor, T... data) {
		this(data.length, loadFactor);
		for (T t : data) add(t);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (T t : this) sb.append(t).append(',').append(' ');
		return sb.append('\n').toString();
	}

	@Override
	public void add(T obj) {
		if (last == array.length) initArray(array.length);
		array[last] = obj;
		int current = last++; // current points to last NON-null element in array
		T temp;
		while (current > 0 && (temp = array[parent(current)]).compareTo(obj) < 0) { // bubbleUp
			array[current] = temp;
			current = parent(current);
			array[current] = obj;
		}
	}

	@Override
	public boolean isEmpty() {
		return last == 0;
	}

	@Override
	public int size() {
		return last;
	}

	@Override
	public T pop() {
		if (last == 0) return null;
		T result = array[0];
		array[0] = array[--last];
		array[last] = null;
		// bubble down
		int current = 0;
		int child = child(current);
		T temp;
		while(child < last) {
			if (child + 1 < last && array[child].compareTo(array[child + 1]) < 0) { // right larger than left
				child++;
			}
			temp = array[child];
			if (array[current].compareTo(temp) < 0) { // out of order, swap
				array[child] = array[current];
				array[current] = temp;
			}
			current = child;
			child = child(child);
		}
		return result;
	}

	@Override
	public Heap<T> clone() {
		return new ArrayMaxHeap<>(array, last);
	}

	ArrayMaxHeap(T[] array, int last) {
		this.array = array;
		this.last = last;
	}

	public T peek() {
		return array[0];
	}

	public Iterator<T> iterator() {
		return new Iterator<T>() {
			public boolean hasNext() {
				return last != 0;
			}

			public void remove() { pop(); }

			public T next() {
				return pop();
			}
		};
	}
}