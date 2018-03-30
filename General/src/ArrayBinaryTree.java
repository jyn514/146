package src;

import java.util.function.Consumer;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class ArrayBinaryTree<T extends Comparable<T>> implements BinaryTree<T> {

	private final float loadFactor;
	private T[] tree;

	ArrayBinaryTree() {
		this(1024); // kB is nothing
	}

	ArrayBinaryTree(int size) {
		this(size, 2);
	}

	ArrayBinaryTree(int size, float loadFactor) {
		if (loadFactor <= 1) throw new IllegalArgumentException("Size must be greater than 1");
		this.loadFactor = loadFactor;
		init(size);
	}

	@SafeVarargs
	ArrayBinaryTree(T... data) {
		this(data.length, 2);
		for (T t : data) add(t);
	}

	@SafeVarargs
	ArrayBinaryTree(float loadFactor, T... data) {
		this(data.length, loadFactor);
		for (T t : data) add(t);
	}

	private void init(int size) {
		if (size <= 0) throw new IllegalArgumentException("Size must be greater than 0");
		if (tree == null) tree = (T[]) new Comparable[(int) (size * loadFactor)];
		else {
			T[] replacing = (T[]) new Comparable[(int) (size * loadFactor)];
			System.arraycopy(tree, 0, replacing, 0, tree.length);
			tree = replacing;
		}
	}

	@Override
	public void add(T obj) {
		if (tree[0] == null) tree[0] = obj;
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
	}

	@Override
	public void clear() {
		tree = (T[]) new Comparable[tree.length];
	}

	public boolean contains(T obj) {
		return indexOf(0, obj) != 0;
	}

	private void insert(int index, T data) {
		if (tree[index] == null) tree[index] = data;
		else if (data.compareTo(tree[index]) < 0 && leftChild(index) < tree.length) { // less than tree[index] -> go left
			insert(leftChild(index), data);
		} else if (data.compareTo(tree[index]) >= 0) {
			if (rightChild(index) < tree.length) init(rightChild(index));
			insert(rightChild(index), data);
		} else {

		}
	}

	private int indexOf(int index, T data) {
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
	private int leftSibling(int index) { return index - 1; }
	private int leftChild(int index) { return index * 2 + 1; }
	private int rightChild(int index) { return index * 2 + 2; }

	private enum ORDER {
		PREORDER, ORDERED, POSTORDER, BREADTHORDER
	}

	private void forEach(Consumer<? super T> action, int startIndex, ORDER order) {
		switch (order) {
			case BREADTHORDER:
				for (T t : tree) action.accept(t);
				break;
		}
	}
}