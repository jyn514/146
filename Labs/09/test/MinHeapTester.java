package test;

import src.ArrayMinHeap;
import src.Heap;

import java.util.Arrays;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class MinHeapTester {
	public static void main(String[] args) {
		Heap<Integer> heap = new ArrayMinHeap<>();
		System.out.println("Min heap tester");
		System.out.println("Populating with values");
		for (Integer i : new int[]{21, 37, 49, 11, 23, 1, 13, 16, 33, 17}) {
			System.out.println(i);
			heap.add(i);
		}
		System.out.println("Printing heap");
		System.out.println(heap);
		System.out.println("Testing heap sort");

		 Integer[] l = heap.heapSort();
		for (int i : l) {
			System.out.println(i);
		}

		System.out.println("Removing 1 from heap");
		heap.pop();
		System.out.println(heap);
	}
}
