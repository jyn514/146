import java.util.Random;

import static java.util.Arrays.sort;

public class SearchStats {
	private static final Random rand = new Random();

	public static void main(String[] args) {
		int binary, linear, item;
		int[] list;
		for (int i = 0; i < 20; i++) {
			list = createPositiveArray();
			item = rand.nextInt(1200); // will be in list 5 times out of six
			if (item < 1000) item = list[item];
			binary = binarySearch(list, item);
			linear = linearSearch(list, item);
			System.out.printf("Test %d. Binary checks: %d, linear checks: %d%n", i + 1, binary, linear);
		}
	}

	private static int linearSearch(int[] list, int n) {
		for (int i = 0; i < list.length; i++) {
			if (list[i] == n) return i;
		}
		return list.length;
	}

	private static int binarySearch(int[] list, int n) {
		int min = 0, max = list.length - 1, accesses = 0;
		int current, temp;
		while(true) {
			current = (min + max) / 2;
			if (current == min) break;
			accesses++;
			temp = list[current];
			if (temp > n) {
				max = current;
			} else if (temp < n)  {
				min = current;
			} else {
				return accesses;
			}
		}
		System.out.println(n + " not found.");
		return accesses;
	}
	
	/*private static int binarySearch(int[] list, int n, int min, int max, int accesses) {
		int current = (min + max) / 2;
		int temp = list[current];
		if (temp == n) return current;
		else if (temp < n) {
			
		}
	}*/

	private static int[] createPositiveArray() {
		int[] result = new int[1000];
		int i = 0;
		while (i < result.length) {
			result[i++] = rand.nextInt();
		}
		sort(result);
		return result;
	}

}
