package src;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import static src.Vertex.distance;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class DijkstrasAlgorithm {
	/*
	 * works only on undirected graphprivate
	 * uses dynamic programming to remember shortest path from other vertex
	 * updates distances as it finds shortest
	 * models 2d plane of points, each connected to nearest neighbor (including diagonals)
	 *
	 */
	public static final int SIZE = 10;
	public static final int TOTAL_OBSTACLES = SIZE * SIZE / 5;
	public static void main(String[] args) {
		Vertex[][] graph = new Vertex[SIZE][SIZE];
		for (int y = 0; y < graph.length; y++) {
			for (int x = 0; x < graph.length; x++) {
				graph[y][x] = new Vertex(x, y);
			}
		}
		int obstacles = 0;
		Random rand = new Random();

		while (obstacles < TOTAL_OBSTACLES) {
			int i = rand.nextInt(graph.length);
			int j = rand.nextInt(graph.length);
			if (!(graph[i][j] == null || (i == 0 && j == 0) || (i == graph.length - 1 && j == graph.length - 1))) {
				graph[i][j] = null;
				obstacles++;
			}
		}
		Vertex finalVertex = spanningTree(graph, 0, 0);
		if (finalVertex == null) System.out.println("No path found");
		ArrayList<Vertex> path = new ArrayList<>();
		while (finalVertex != null) {
			path.add(finalVertex);
			finalVertex = finalVertex.previous;
		}
		System.out.println(toString(graph, path));

	}

	public static Vertex spanningTree(Vertex[][] graph, int startX, int startY) {
		PriorityQueue<Vertex> queue = new PriorityQueue<>();
		graph[startY][startX].distance = 0;
		queue.add(graph[startY][startX]);
		while (!queue.isEmpty()) {
			Vertex current = queue.remove();
			for (int i = current.y - 1; i <= current.y + 1; i++) {
				for (int j = current.x - 1; j <= current.x + 1; j++) {
					if (isValid(i) && isValid(j) && graph[i][j] != null && i != current.x && j != current.y) {
						Vertex next = graph[i][j];
						if (current.distance == Double.POSITIVE_INFINITY) {
							next.distance = current.distance + distance(current, next);
							next.previous = current;
							// reweight queue
							reheapify(queue, next);
						} else {
							double newDistance = current.distance + Vertex.distance(current, next);
							if (newDistance < next.distance) {
								next.distance = newDistance;
								next.previous = current;
								reheapify(queue, next);
							}
						}
					}
				}
			}
		}
		return graph[graph.length - 1][graph.length - 1];
	}

	public static <T> void reheapify(Queue<T> queue, T obj) {
		 if (queue.contains(obj)) queue.remove(obj);
		 queue.add(obj);
	}

	public static boolean isValid(int i) {
		return i >= 0 && i < SIZE;
	}

	public static String toString(Vertex[][] graph) {
		return toString(graph, null);
	}

	public static String toString(Vertex[][] graph, ArrayList<Vertex> path) {
		StringBuilder sb = new StringBuilder();
		for (Vertex[] row : graph) {
			for (int x = 0; x < graph.length; x++) {
				if (row[x] == null) sb.append('X');
				else if (path != null && path.contains(row[x])) sb.append('O');
				else sb.append('_');
				sb.append('|');
			}
			sb.append('\n');
		}
		return sb.toString();
	}
}
