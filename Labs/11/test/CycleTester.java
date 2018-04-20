package test;

import src.Graph;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class CycleTester {
	public static void main(String[] args) {
		Graph graph = new Graph();
		for(int i = 0; i < 7; i++) graph.addVertex();
		graph.addEdge(0, 4);
		graph.addEdge(4, 1);
		graph.addEdge(4, 2);
		graph.addEdge(4, 6);
		graph.addEdge(1, 0);
		graph.addEdge(2, 0);
		graph.addEdge(6, 3);
		graph.addEdge(6, 5);
		graph.addEdge(3, 1);
		graph.addEdge(5, 2);
		System.out.println(graph);
		System.out.println(graph.cycles());
	}
}
