package src;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public abstract class ArrayTree<T> {

	protected T[] array;
	private float loadFactor;

	public static int parent(int index) {
		return (index - 1) / 2; // rounds down if floating
	}

	public static int child(int index) {
		return index * 2 + 1;
	}

	public void clear() {
		array = (T[]) new Comparable[array.length];
	}

	protected abstract void add(T obj);

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

	@SafeVarargs
	protected ArrayTree(T... data) {
		this(data.length, 2);
		for (T t : data) add(t);
	}

	@SafeVarargs
	protected ArrayTree(float loadFactor, T... data) {
		this(data.length, loadFactor);
		for (T t : data) add(t);
	}

	protected void initArray(int size) {
		T[] replacing = (T[]) new Comparable[(int) (size * loadFactor)];
		System.arraycopy(array, 0, replacing, 0, array.length);
		array = replacing;
	}
}
