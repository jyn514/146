package src;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class LinkedBinarySearchTree<T extends Comparable<T>> extends LinkedMap<T, T> implements BinarySearchTree<T> {
	@Override
	public void add(T data) {
		super.put(data, data);
	}
}