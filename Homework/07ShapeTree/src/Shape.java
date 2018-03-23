package src;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
abstract class Shape implements Comparable<Shape> {
	final double area;

	private Shape(double area) {this.area = area;}

	public boolean equals(Shape s) {
		return s.getClass() == this.getClass() && s.area == area;
	}

	public int compareTo(Shape s) {
		return (int) ((area - s.area) * 100000);
	}

	@Override
	public String toString() {
		return String.format("%s with area %.3f",
				this.getClass().toString().replaceAll("class src.Shape\\$", ""), area);
	}

	static class Rectangle extends Shape {
		Rectangle(double length, double width) {
			super(length * width);
		}
	}

	static class Circle extends Shape {
		Circle(double radius) {
			super(Math.PI * Math.pow(radius, 2));
		}
	}

	static class Triangle extends Shape {
		Triangle(double base, double height) {
			super(base * height / 2);
		}
	}
}
