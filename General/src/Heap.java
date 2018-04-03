package src;

import java.lang.reflect.Array;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public interface Heap<T extends Comparable<T>> extends BinaryTree<T>, Iterable<T> {

	void add(T t);
	boolean isEmpty();
	int size();
	T peek();
	T pop();
	Heap<T> clone();

	static <T extends Comparable<T>> T[] heapSort(Heap<T> heap) {
		int size = heap.size();
		if (size == 0) return null;
		T first = heap.pop();
		T[] result = (T[]) Array.newInstance(first.getClass(), size);
		result[0] = first;

		Heap<T> clone = heap.clone();

		int current = 1;
		T tmpObj;
		while((tmpObj = clone.pop()) != null) {
			result[current++] = tmpObj;
		}
		return result;
	}
}
