package src;

import java.util.PriorityQueue;
import java.util.Queue;

import static java.util.Arrays.fill;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class AdjacencyMatrix {
	private double[][] matrix;
	private transient boolean[] marked;
	private transient Queue<Integer> queue = new PriorityQueue<>();

	public AdjacencyMatrix() {
		this(10);
	}

	public AdjacencyMatrix(int size) {
		if (size <= 0) throw new IllegalArgumentException("Must be positive");
		matrix = new double[size][size]; // defaults to all zeros
		marked = new boolean[size];
	}

	public void addEdge(int from, int to, double weight) {
		if (from < 0 || to < 0) throw new IllegalArgumentException("Must be non-negative");
		matrix[from][to] = weight;
	}

	public void addEdge(int from, int to) {
		addEdge(from, to, 1);
	}

	public String depthFirstSearch() {
		fill(marked, false); // reset
		String tmp = depthFirstSearch(0);
		return "0|" + tmp.substring(0, tmp.length() - 1);
	}

	public String depthFirstSearch(int start) {
		marked[start] = true;
		return range(0, matrix.length)
		       .filter(i -> !(matrix[start][i] == 0 || marked[i]))
		       .mapToObj(i -> String.valueOf(i) + '|' + depthFirstSearch(i))
		       .collect(joining());
	}

	public String breadthFirstSearch() {
		queue.clear();
		queue.add(0);
		fill(marked, false);
		StringBuilder sb = new StringBuilder().append('0').append('|');
		int current;
		while (!queue.isEmpty()) {
			current = queue.remove();
			marked[current] = true;
			for (int i = 0; i < matrix.length; i++) {
				if (!(marked[i] || queue.contains(i) || matrix[current][i] == 0)) {
					sb.append(i).append('|');
					queue.add(i);
				}
			}
		}
		return sb.deleteCharAt(sb.length() - 1).toString(); // remove trailing |
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (double[] from : matrix) {
			for (int to = 0; to < matrix.length; to++) {
				sb.append(from[to]).append(' ');
			}
			sb.append('\n');
		}
		return sb.toString();
	}
}
