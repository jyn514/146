package src;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class Vertex implements Comparable<Vertex> {
	public double distance = Double.POSITIVE_INFINITY;
	public Vertex previous;
	final int x;
	final int y;

	public Vertex(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Vertex{" +
				"distance=" + distance +
				", previous=" + previous +
				", x=" + x +
				", y=" + y +
				'}';
	}

	@Override
	public int compareTo(Vertex v) { // what's this doing?
		if (distance < v.distance) return -1;
		else if (distance > v.distance) return 1;
		return 0;
	}

	public static double distance(Vertex one, Vertex two) {
		return Math.sqrt(Math.pow(one.x - two.x, 2) + Math.pow(one.y - two.y, 2));
	}
}
