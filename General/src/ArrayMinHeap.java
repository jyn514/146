package src;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class ArrayMinHeap<T extends Comparable<T>> extends ArrayTree<T> implements Heap<T> {

	private int last; // first null element (in breadth order)

	public ArrayMinHeap() {
		this(1024); // kB is nothing
	}

	public ArrayMinHeap(int size) {
		this(size, 2);
	}

	public ArrayMinHeap(int size, float loadFactor) {
		super(size, loadFactor);
		last = 0;
	}

	@SafeVarargs
	public ArrayMinHeap(T ... data) {
		this(data.length);
		for (T t : data) add(t);
	}

	@SafeVarargs
	public ArrayMinHeap(float loadFactor, T... data) {
		this(data.length, loadFactor);
		for (T t : data) add(t);
	}

	@Override
	public void add(T obj) {
		// if (array == null) array = (T[]) Array.newInstance(obj.getClass(), 1);
		if (last == array.length) initArray(array.length);
		array[last] = obj;
		int current = last++; // current points to last NON-null element in array
		T temp;
		while (current > 0 && (temp = array[parent(current)]).compareTo(obj) > 0) { // bubbleUp
			array[current] = temp;
			current = parent(current);
			array[current] = obj;
		}
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
			if (child + 1 < last && array[child].compareTo(array[child + 1]) > 0) { // right smaller than left
				child++;
			}
			temp = array[child];
			if (array[current].compareTo(temp) > 0) { // out of order, swap
				array[child] = array[current];
				array[current] = temp;
			}
			current = child;
			child = child(child);
		}
		return result;
	}

	public T peek() {
		return array[0];
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (T t : this) {
			result.append(t);
			result.append('\n');
		}
		return result.toString();
	}

	public T[] heapSort() { // destructive operation
		if (last == 0) return null;
		T[] result = (T[]) Array.newInstance(array[0].getClass(), last);

		Heap<T> clone = new ArrayMinHeap<>(array.clone(), last);

		int current = 0;
		T tmp = clone.pop();
		while(tmp != null) {
			result[current++] = tmp;
			tmp = clone.pop();
		}
		System.out.println(result[0]);
		System.out.println(result[0].getClass());
		System.out.println(result.getClass());
		return result;
	}

	private ArrayMinHeap(T[] array, int last) {
		this.array = array;
		this.last = last;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private int i = 0;

			@Override
			public boolean hasNext() {
				return i < last;
			}

			@Override
			public T next() {
				return array[i++];
			}
		};
	}
}