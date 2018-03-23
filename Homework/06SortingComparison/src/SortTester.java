/*import org.junit.jupiter.api.Test;*/

import java.util.Arrays;
import java.util.Random;

class SortTester {
    private static final Random rand = new Random();
    private static final String[] methods = {"merge", "bubble", "quick", "selection",
            "memory", "insert", "sleep"};

    public static void main(String[] args) throws IllegalStateException {
        System.out.println("Hello! Sorting a new random array just for you.");

        int[] original = createPositiveArray();
        int[] knownGood = original.clone();
        Arrays.sort(knownGood);

        for (String method : methods) {

           SortStats sorted = new SortStats(original.clone(), method);

           if (!equals(sorted.sorted, knownGood)) {
               if (equals(sorted.sorted, original)) {
                   System.out.printf("Method %s is not yet implemented%n", method);
               } else if (equals(sorted.sorted, original)) {
                   throw new IllegalStateException("Modifying by reference, not value");
               } else {
                   String errorMessage = "Method " + method + " did not sort successfully.\n" +
                           "Your sorted array: " + arrayToString(sorted.sorted) + "\n\n" +
                           "Should be: " + arrayToString(knownGood);
                   throw new IllegalStateException(errorMessage);
               }
           } else {
                System.out.printf("Method %s sorted array of size %d in %d accesses and %d writes%n",
                                  method, original.length, sorted.accesses, sorted.writes);
           }
       }
       System.out.println("Finished sorting! Hope you had a good time.");
    }

    /*@Test*/
    static boolean modifyingOriginal(String method) {
        int[] original = createPositiveArray();
        int[] sorted = new SortStats(original.clone(), method).sorted;

        if (equals(original, sorted)) {
            System.err.printf("Original: %s%nSorted: %s%nMethod: %s%n",
                    arrayToString(original), arrayToString(sorted), method);
            return true;
        }
        return false;
    }

    static boolean equals(int[] first, int[] second) {
        if (first == null || second == null) {
            return first == second;
        } else if (first.length != second.length) {
            return false;
        }
        for (int i = 0; i < first.length; i++) {
            if (first[i] != second[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Java doesn't support generics for primitives. Floats, doubles, etc., will also need a seperate function.
     * @param array list to be Stringified
     * @return array as String, exactly as if arrayToString(Integer[] array) were called
     */
    private static String arrayToString (int[] array) {
        Integer[] boxed = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            boxed[i] = array[i];
        }
        return arrayToString(boxed);
    }

    private static <T> String arrayToString(T[] array) {
        StringBuilder result = new StringBuilder();
        for (T i : array) {
            result.append(i);
            result.append(' ');
        }
        result.append('\n');
        return result.toString();
    }

    private static int[] createPositiveArray() {
        int[] result = new int[1000];
        for (int i = 0; i < result.length; i++) {
            result[i] = rand.nextInt(1000);
        }
        return result;
    }
}
