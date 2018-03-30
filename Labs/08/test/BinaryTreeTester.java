package test;

import src.LinkedBinaryTree;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class BinaryTreeTester {
	public static void main(String[] args) {
		LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<>();
		test1(tree);
		tree.clear();
		givenTest(tree);
	}

	private static void givenTest(LinkedBinaryTree<Integer> tree) {
		for (int i : new int[] { 8, 13, 3, 4, 18, 19, 10, 1, 9, 2 }) {
			System.out.println("Inserting " + i);
			tree.add(i);
		}
		System.out.println("Printing pre-order");
		tree.printPreorder();

		System.out.println("Removing 4");
		tree.delete(4);
		tree.printPreorder();
	}

	private static void test1(LinkedBinaryTree<Integer> tree) {
		for (int i : new int[] {4, 3, 15, 13, 31}) {
			System.out.println("Inserting " + i);
			tree.add(i);
		}
		System.out.println(tree);

		System.out.print("\nPrinting preorder: ");
		tree.printPreorder();

		System.out.println("\nDepth of 13 is " + tree.depth(13));
		System.out.println("Depth of 4 is " + tree.depth(4));
		System.out.println("Depth of 32 is " + tree.depth(32));
		System.out.println("\nRemoving 4");
		tree.delete(4);
		System.out.println(tree);
	}
}