package test;

import src.Graph;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class GraphTester {
	public static void main(String[] args) {
		Graph g = new Graph();
		System.out.println("Adding vertices");
		for (int i = 0; i < 7; i++) {
			g.addVertex();
		}
		System.out.println("Adding edges");
		g.addEdge(0, 1);
		g.addEdge(0, 3);
		g.addEdge(1, 3);
		g.addEdge(2, 0);
		g.addEdge(2, 4);
		g.addEdge(2, 5);
		g.addEdge(3, 2);
		g.addEdge(3, 4);
		g.addEdge(4, 5);
		g.addEdge(4, 6);

		System.out.println("Printing graph");
		System.out.println(g);
	}
}
