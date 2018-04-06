package src;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class Fruit implements Comparable<Fruit> {

	private double weight;
	private String name;

	public Fruit(String name, double weight) {
		this.weight = weight;
		this.name = name;
	}

	public Fruit(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Fruit{" +
				"weight=" + weight +
				", name='" + name + '\'' +
				'}';
	}

	@Override
	public int compareTo(Fruit o) {
		return (int) ((weight - o.weight) * 1000);
	}
}
