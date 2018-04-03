package test;

import src.RedBlackTree;

import java.util.Random;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class RedBlackTreeTester {
	public static void main(String[] args) {
		final RedBlackTree<Integer> tree = new RedBlackTree<>();
		final Random rand = new Random();
		for (int i = 0; i < 20; i++) {
			tree.add(rand.nextInt());
		}
		System.out.println(tree);
	}
}
