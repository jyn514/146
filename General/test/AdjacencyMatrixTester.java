package test;

import src.AdjacencyMatrix;
/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class AdjacencyMatrixTester {
	public static void main(String[] args) {
		AdjacencyMatrix g = new AdjacencyMatrix(7);
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
		System.out.println("Depth First Search");
		System.out.println(g.depthFirstSearch());
		System.out.println("Breadth First Search");
		System.out.println(g.breadthFirstSearch());
	}
}