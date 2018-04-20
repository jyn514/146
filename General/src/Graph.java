package src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * Can add but not remove
 */
public class Graph implements Iterable<Graph.Vertex> {
	private int current = 0; // used for UID
	private Vertex origin; // similar to root in linked list; arbitrary
	private final DoubleLinkedList<Vertex> vertices = new DoubleLinkedList<>();
	private Queue<Vertex> queue = new PriorityQueue<>();

	public void addVertex() {
		Vertex v = new Vertex();
		vertices.append(v);
		if (origin == null) origin = v;
	}

	public void addEdge(int fromUID, int toUID) { addEdge(fromUID, toUID, 1); }

	public void addEdge(int fromUID, int toUID, double weight) {
		Vertex from = getVertex(fromUID);
		Vertex to = getVertex(toUID);
		if (from == null || to == null) throw new IllegalArgumentException("from and to must already be in graph");
		from.neighbors.append(new Edge(weight, to));
	}

	@Override
	public String toString() {
		return depthFirstSearch();
	}

	@Override
	public Iterator<Vertex> iterator() {
		return vertices.iterator();
	}

	public String cycles() { // this is an NP hard problem: https://stackoverflow.com/a/15983910
		if (getVertex(0) != null) {
			DoubleLinkedList<CircularDoubleLinkedList<Vertex>> allCycles = new DoubleLinkedList<>();
			List<Vertex> visited = new ArrayList<>();
			cycles(getVertex(0), visited, allCycles);
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

	private void cycles(Vertex v, List<Vertex> visited, DoubleLinkedList<CircularDoubleLinkedList<Vertex>> allCycles) {
		visited.add(v);
		for (Edge next : v.neighbors) {
			if (visited.contains(next.to)) {
				CircularDoubleLinkedList<Vertex> cycle = new CircularDoubleLinkedList<>(visited.subList(visited.indexOf(next.to), visited.size()));
				allCycles.append(cycle);
			} else {
				cycles(next.to, new ArrayList<>(visited), allCycles);
			}
		}
	}

	public String depthFirstSearch() {
		DoubleLinkedList<Vertex> markedVertices = new DoubleLinkedList<>();
		return depthFirstSearch(origin, markedVertices);
	}

	public String depthFirstSearch(Vertex v, DoubleLinkedList<Vertex> markedVertices) {
		if (markedVertices.inList(v)) return "";
		markedVertices.append(v);
		StringBuilder sb = new StringBuilder(v.toString()).append('\n');
		for (Edge next : v.neighbors) {
			sb.append(depthFirstSearch(next.to, markedVertices));
		}
		return sb.toString();
	}

	public String breadthFirstSearch() {
		queue.clear();
		return breadthFirstSearch(origin);
	}

	private String breadthFirstSearch(Vertex start) {
		final DoubleLinkedList<Vertex> markedVertices = new DoubleLinkedList<>();
		StringBuilder sb = new StringBuilder(start.toString()).append('\n');
		queue.add(start);
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



	public class Vertex implements Comparable<Vertex> {
		final int uid;
		final DoubleLinkedList<Edge> neighbors = new DoubleLinkedList<>();

		private Vertex() {
			this.uid = current++;
		}

		@Override
		public String toString() {
			return String.valueOf(uid);
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

	private class Edge {
		Graph.Vertex to;
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

	private Vertex getVertex(int uid) {
		for (Vertex v : vertices) if (v.uid == uid) return v;
		return null;
	}
}
