package src;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public interface Map<Key extends Comparable<Key>, Value> {
	void put(Key key, Value data);
	void add(Value data);
	void delete(Key key);
	void clear();
	boolean contains(Key key);
	int size();
	Value get(Key key);

	default boolean isEmpty() {
		return size() != 0;
	}

	enum ORDER {
		PREORDER, INORDER, POSTORDER, BREADTHORDER
	}

}
