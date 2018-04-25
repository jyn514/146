package src;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class ArrayBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {

	private final float loadFactor;
	private int size;
	private T[] tree;

	ArrayBinarySearchTree() {
		this(1024); // kB is nothing
	}

	ArrayBinarySearchTree(int size) {
		this(size, 2);
	}

	ArrayBinarySearchTree(int size, float loadFactor) {
		if (loadFactor <= 1) throw new IllegalArgumentException("Size must be greater than 1");
		this.loadFactor = loadFactor;
		tree = (T[]) new Comparable[size];
	}

	@SafeVarargs
	ArrayBinarySearchTree(T... data) {
		this(data.length, 2);
		for (T t : data) add(t);
	}

	ArrayBinarySearchTree(Iterable<T> it) {
		this();
		for (T t : it) add(t);
	}

	@SafeVarargs
	ArrayBinarySearchTree(float loadFactor, T... data) {
		this(data.length, loadFactor);
		for (T t : data) add(t);
	}

	private void init() {
		if (tree == null) tree = (T[]) new Comparable[(int) (tree.length * loadFactor)];
		else {
			T[] replacing = (T[]) new Comparable[(int) (tree.length * loadFactor)];
			System.arraycopy(tree, 0, replacing, 0, tree.length);
			tree = replacing;
		}
	}

	@Override
	public void add(T obj) {
		if (tree[0] == null) {
			tree[0] = obj;
			size++;
		}
		else insert(0, obj);
	}

	public void delete(T obj) {
		int index = indexOf(0, obj);
		if (index == -1) return;
		if (rightChild(index) >= tree.length || tree[rightChild(index)] == null) { // no right child
			moveLeftSubtree(index);
		} else if (tree[index * 2 + 1] == null) { // right child but no left
			moveRightSubtree(index);
		} else { // two children
			// TODO
		}
		size--;
	}

	@Override
	public void clear() {
		tree = (T[]) new Comparable[tree.length];
	}

	public boolean contains(T obj) {
		return indexOf(0, obj) != 0;
	}

	@Override
	public int size() {
		return size;
	}

	private void insert(int index, T data) {
		if (index >= tree.length) init();
		if (tree[index] == null) tree[index] = data;
		else if (data.compareTo(tree[index]) < 0 && leftChild(index) < tree.length) { // less than tree[index] -> go left
			insert(leftChild(index), data);
		} else if (data.compareTo(tree[index]) >= 0) {
			insert(rightChild(index), data);
		}
		size++;
	}

	protected int indexOf(int index, T data) {
		if (index >= tree.length || tree[index] == null) return -1;
		if (data.compareTo(tree[index]) == 0) return index;
		if (data.compareTo(tree[index]) < 0) return indexOf(leftChild(index), data);
		return indexOf(rightChild(index), data);
	}

	private void moveLeftSubtree(int index) {
		if (index >= tree.length) return;
		// right children are ALWAYS even -> all left children are odd
		if (index % 2 != 0 || index == 0) { // left child or root
			if (index * 2 + 1 < tree.length) {
				tree[index] = tree[index * 2 + 1];
			} else { // already shifted
				tree[index] = null;
				return;
			}
		} else { // right child
			if ((index - 1) * 2 + 2 < tree.length) {
				tree[index] = tree[(index - 1) * 2 + 2];
			} else {
				tree[index] = null;
				return;
			}
		}
		// move left and right children
		moveLeftSubtree(leftChild(index));
		moveLeftSubtree(rightChild(index));
	}

	private void moveRightSubtree(int index) {
		if (index >= tree.length) return;
		if (index % 2 == 0 || index == 0) { // right child or root
			if (rightChild(index) < tree.length) {
				tree[index] = tree[rightChild(index)];
			} else {
				tree[index] = null;
				return;
			}
		} else {
			if(leftChild(rightSibling(index)) < tree.length) {
				tree[index] = tree[leftChild(rightSibling(index))];
			} else {
				tree[index] = null;
				return;
			}
		}
		// move left and right children
		moveRightSubtree(leftChild(index));
		moveRightSubtree(rightChild(index));
	}

	// for my own sanity
	private int rightSibling(int index) { return index + 1; }
	private int leftChild(int index) { return index * 2 + 1; }
	private int rightChild(int index) { return index * 2 + 2; }


	public void forEach(Consumer<? super T> action, ORDER order) {
		switch (order) {
			case BREADTHORDER:
				for (T t : tree) action.accept(t);
				break;
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			int traversed = 0, real = 0;

			@Override
			public boolean hasNext() {
				return traversed < size;
			}

			@Override
			public T next() {
				while (tree[real] == null) {
					real++;
				}
				traversed++;
				return tree[real++];
			}
		};
	}
}