package test;

import src.AVLTree;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class Main {
	private static final AVLTree<Integer> tree = new AVLTree<>();

	public static void main(String[] args) {
		tree.clear();
		for (int i = 0; i < 20; i++) {
			tree.add(i);
		}
		System.out.println(tree);
		tree.delete(4);
		tree.delete(9);
		tree.delete(0);
		System.out.println(tree);
	}
}
