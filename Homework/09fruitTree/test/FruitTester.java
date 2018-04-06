package test;

import src.BinaryTree;
import src.Fruit;
import src.LinkedBinaryTree;
import src.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class FruitTester {
	public static void main(String[] args) throws IOException {
		BinaryTree<Fruit> tree = new LinkedBinaryTree<Fruit>();
		System.out.println("Welcome to the fruit tree!");

		System.out.println("Populating the tree file");
		BufferedReader reader = new BufferedReader(new FileReader("fruitFile.txt"));
		String line;
		String[] tmp = null;
		while ((line = reader.readLine()) != null) {
			tmp = line.split("\t");
			tree.add(new Fruit(tmp[0], Double.parseDouble(tmp[1])));
		}

		System.out.println("Printing in-order traversal");
		System.out.println(tree);

		System.out.println("Printing pre-order traversal");
		System.out.println(tree.toString(Map.ORDER.PREORDER));

		System.out.println("Printing post-order traversal");
		System.out.println(tree.toString(Map.ORDER.POSTORDER));

		System.out.println("Deleting element");
		if (tmp != null) tree.delete(new Fruit(Double.parseDouble(tmp[1])));
		System.out.println(tree);
	}
}
