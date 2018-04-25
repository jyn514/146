/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package src;

import java.util.Random;

public class Main {
	public static void main(String[] args) {
		Heap<Jeep> heap = new ArrayMaxHeap<>();
		Random rand = new Random();
		for (int i = 0; i < 20; i++) heap.add(new Jeep(rand.nextFloat() * 1000));
		for (int i = 0; i < 5; i++) heap.pop();
		for (Jeep j : heap.heapSort()) System.out.println(j);
	}
}
