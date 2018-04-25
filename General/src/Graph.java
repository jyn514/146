package src;

import java.util.*;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * Can add but not remove
 */
public class Graph<T> {
	private int size = 0; // used for UID
	private Vertex origin; // similar to root in linked list; arbitrary
	private final Queue<Vertex> queue = new PriorityQueue<>();
	private final HashSet<Vertex> vertices = new HashSet<>();

	public int size() { return size; }

	public void addVertex() {
		addVertex(null);
	}

	public void addVertex(T data) {
		Vertex v = new Vertex(data);
		vertices.add(v);
		if (origin == null) origin = v;
	}

	public void addEdge(int fromUID, int toUID) { addEdge(fromUID, toUID, 1); }

	public void addEdge(int fromUID, int toUID, double weight) {
		Vertex from = getVertex(fromUID);
		Vertex to = getVertex(toUID);
		if (from == null || to == null) {
			throw new IllegalArgumentException(String.format(
					"from and to must already be in graph (given %d and %d)", fromUID, toUID));
		}
		from.neighbors.append(new Edge(weight, to));
	}

	@Override
	public String toString() {
		return depthFirstSearch().toString();
	}

	public String cycles() {
		return cycles(Integer.MAX_VALUE);
	}

	public String cycles(int maxDepth) { // this is an NP hard problem: https://stackoverflow.com/a/15983910
		if (getVertex(0) != null) {
			DoubleLinkedList<CircularDoubleLinkedList<Vertex>> allCycles = new DoubleLinkedList<>();
			List<Vertex> visited = new ArrayList<>();
			cycles(getVertex(0), visited, allCycles, maxDepth);
			StringBuilder sb = new StringBuilder();
			for (CircularDoubleLinkedList<Vertex> cycle : allCycles) {
				for (int i = 0; i < cycle.size(); i++) {
					for (int j = 0; j <= cycle.size(); j++) {
						sb.append(cycle.getDataAtCurrent()).append(' ');
						cycle.goToNext();
					}
					sb.append('\n');
					cycle.goToNext();
				}
			}
			return sb.toString();
		} else return "";
	}

	private void cycles(Vertex v, List<Vertex> visited, DoubleLinkedList<CircularDoubleLinkedList<Vertex>> allCycles, int depth) {
		if (depth < 0) return;
		visited.add(v);
		for (Edge next : v.neighbors) {
			if (visited.contains(next.to)) {
				CircularDoubleLinkedList<Vertex> cycle = new CircularDoubleLinkedList<>(visited.subList(visited.indexOf(next.to), visited.size()));
				allCycles.append(cycle);
			} else cycles(next.to, new ArrayList<>(visited), allCycles, depth - 1);
		}
	}

	public List<Vertex> depthFirstSearch() {
		List<Vertex> markedVertices = new ArrayList<>();
		depthFirstSearch(origin, markedVertices);
		return markedVertices;
	}

	private void depthFirstSearch(Vertex v, List<Vertex> markedVertices) {
		if (markedVertices.contains(v)) return;
		markedVertices.add(v);
		for (Edge next : v.neighbors) {
			depthFirstSearch(next.to, markedVertices);
		}
	}

	private String breadthFirstSearch() {
		queue.clear();
		final DoubleLinkedList<Vertex> markedVertices = new DoubleLinkedList<>();
		StringBuilder sb = new StringBuilder(origin.toString()).append('\n');
		queue.add(origin);
		while (!queue.isEmpty()) {
			Vertex v = queue.remove();
			markedVertices.append(v);
			for (Edge e : v.neighbors) if (!queue.contains(e.to) && !markedVertices.inList(e.to)) {
				sb.append(e.to.toString()).append('\n');
				queue.add(e.to);
			}
		}
		return sb.toString();
	}

	private Vertex getVertex(int uid) {
		return vertices.parallelStream().filter(v -> v.uid == uid).findFirst().orElse(null);
	}

	public class Vertex implements Comparable<Vertex> {
		final int uid;
		final DoubleLinkedList<Edge> neighbors = new DoubleLinkedList<>();
		T data;

		private Vertex(T data) {
			this.data = data;
			this.uid = size++;
		}

		@Override
		public String toString() {
			if (data == null)	return String.valueOf(uid);
			return data.toString();
		}

		@Override
		public boolean equals(Object o) {
			return this == o || (o != null && getClass() == o.getClass() && uid == ((Vertex) o).uid);
		}

		@Override
		public int hashCode() {
			return uid;
		}

		@Override
		public int compareTo(Vertex v) {
			return uid - v.uid;
		}
	}

	class Edge {
		Graph<T>.Vertex to;
		final double weight;

		private Edge() {
			this(1);
		}

		private Edge(double weight) {
			this(weight, null);
		}

		@Override
		public String toString() {
			return "Edge{" +
					"to=" + to.uid +
					", weight=" + weight +
					'}';
		}

		private Edge(double weight, Vertex v) {
			this.to = v;
			this.weight = weight;
		}
	}
}
