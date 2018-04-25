package src;

import java.util.Arrays;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class ArrayTree<T> {

	protected T[] array;
	private float loadFactor;

	public static int parent(int index) {
		return (index - 1) / 2; // rounds down if floating
	}

	public static int child(int index) {
		return index * 2 + 1;
	}

	public void clear() {
		Arrays.fill(array, null);
	}

	protected ArrayTree() {
		this(1024); // kB is nothing
	}

	protected ArrayTree(int size) {
		this(size, 2);
	}

	protected ArrayTree(int size, float loadFactor) {
		if (loadFactor <= 1) throw new IllegalArgumentException("Load factor must be greater than 1");
		if (size <= 0) throw new IllegalArgumentException("Size must be greater than 0");
		this.loadFactor = loadFactor;
		array = (T[]) new Comparable[(int) (size * loadFactor)];

	}

	protected void initArray() {
		T[] replacing = (T[]) new Comparable[(int) (array.length * loadFactor)];
		System.arraycopy(array, 0, replacing, 0, array.length);
		array = replacing;
	}
}
