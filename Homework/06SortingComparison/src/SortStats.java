import java.util.concurrent.CountDownLatch; // for sleep sort
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.sort; // for bogo sort

class SortStats {
    static int accesses, writes;
    private static transient int min, temp, iterator;
    int[] sorted;

    SortStats(int[] list, String method) {
        if (list == null) {
            sorted = null;
            accesses = 0;
            writes = 0;
            return;
        }
        sorted = null;
        method = method.trim().toLowerCase();
        min = Integer.MAX_VALUE;
        accesses = 0;
        writes = 0;

        switch (method) {
            case "merge":
                sorted = mergeSort(list);
                break;
            case "bubble":
                sorted = bubbleSort(list);
                break;
            case "quick":
                sorted = quickSort(list);
                break;
            case "selection":
                sorted = selectionSort(list);
                break;
            case "insert":
                sorted = insertSort(list);
                break;
            case "sleep":
                try {
                    sorted = sleepSort(list);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "memory":
                sorted = memorySort(list);
                break;
            default:
                throw new IllegalArgumentException("Please choose one of 'merge', 'bubble', 'quick', 'selection', 'sleep', or 'memory'");
        }
    }

    /**
     * Sort forwards starting from front. Elements behind count are sorted, elements in front are not.
     * O(n^2) time, and read complexity, O(n) for writes`
     * @param list must have length <= Integer.MAX_VALUE
     * @return sorted list
     */
    private static int[] selectionSort(int[] list) {
        for (int count = 0; count < list.length - 1; count++) {
            iterator = count;
            int minIndex = count;
            min = list[count];
            while (++iterator < list.length) {
                temp = list[iterator];
                accesses++;
                if (temp < min) {
                    min = temp;
                    minIndex = iterator;
                }
            }
            if (minIndex != count) {
                list = swap(list, count, minIndex);
            }
        }
        return list;
    }

    /**
     * Similar to selectionSort.
     * Sort backwards starting from the front.
     * Shifts elements forward to make space for temp.
     * Elements *behind* count are always in order, while elements in front are disorganized.
     * O(n^2) time, read and write complexity.
     * @param list length must be <= Integer.MAX_LENGTH
     * @return sorted list
     */
    private static int[] insertSort(int[] list) {
        for (int count = 1; count < list.length; count++) {
            temp = list[count]; accesses++; // keep track of the current element to be sorted
            iterator = count - 1;

            // TODO: only access list[iterator] once
            while (iterator >= 0 && list[iterator] > temp) { // avoid out of bounds and not in order
                list[iterator + 1] = list[iterator]; // move element up; list[iterator + 1] has already been assigned to temp
                accesses+=3; writes++;
                iterator--;
            }

            list[iterator + 1] = temp; writes++; // temp is now discarded; count is incremented
        }
        return list;
    }

    private static int[] bubbleSort(int[] list) {
        for (int i = list.length - 1; i >= 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                accesses += 2;
                if (list[j] > list[i]) {
                    swap(list, j, i);
                }
            }
        }
        return list;
    }

    private static int[] mergeSort(int[] list) {
        if (list.length == 1) return list;

        int middle  = list.length / 2;
        int[] left  = new int[middle];
        int[] right = new int[list.length - middle];

        System.arraycopy(list, 0, left, 0, middle);  // this would be so much easier in python
        System.arraycopy(list, middle, right, 0, list.length - middle);
        accesses += list.length;
        writes += list.length;

        left = mergeSort(left);
        right = mergeSort(right);

        int[] result = new int[list.length];
        int i = 0, j = 0, current = 0; // left and right will always be at least length 1

        while (current < list.length) {
            // deal with out of bounds errors
            if (j >= right.length) {
                for (int temp = i; temp < left.length; temp++) {
                    accesses++;
                    writes++;
                    result[current++] = left[temp];
                }
            } else if (i >= left.length) {
                for (int temp = j; temp < right.length; temp++) {
                    writes++;
                    accesses++;
                    result[current++] = right[temp];
                }
            } else if (left[i] < right[j]) {
                result[current++] = left[i++];
                writes++;
                accesses += 3;
            } else { // left[i] >= right[j]
                accesses += 3; // accessed above and here
                writes++;
                result[current++] = right[j++];
            }
        }
        return result;
    }

    private static int[] quickSort(int[] list) {
        return quickSort(list, 0, list.length - 1);
    }

    /**
     * Space complexity: O(n)
     * Average time complexity: n log(n)
     * Worst case: already sorted O(n**2)
     * @param list list to sort
     * @param left index to start sorting at
     * @param right index to end sorting at
     * @return sorted list
     */
   private static int[] quickSort(int[] list, int left, int right) {
        if (left >= right) return list; // handle it here so we don't have to think too much later

        int pivot  = partition(list, left, right); // list index of pivot
        list = quickSort(list, left, pivot - 1); // sort left side and right side respectively
        return quickSort(list, pivot, right);
    }

    private static int partition(int[] list, int left, int right) {
        int i = left, j = right;
        int pivot = list[(left + right) / 2];
        accesses++;

        while (i <= j) { // haven't already sorted this
            while (list[i] < pivot) {
                i++; // find element on the *left* *greater* than pivot
                accesses++;
            }
            accesses++; // last time accessed, loop not entered
            while (list[j] > pivot) {
                j--; // find element on the *right* *smaller* than pivot
                accesses++;
            }
            accesses++;
            // i and j out of order
            if (i <= j) { // why does this break if changed to i < j ?? (??)
                list = swap(list, i++, j--);
            }
        }
        return i; // i is pivot
    }

    private static int[] heapSort(int[] list) {
        return list; // TODO: https://en.wikipedia.org/wiki/Heapsort
    }

    /** Don't use this method. */
    private static int[] sleepSort(int[] list) throws InterruptedException { // why the hell not

        final int[] result = new int[list.length];
        final AtomicInteger nextIndex = new AtomicInteger(0);
        final CountDownLatch latch = new CountDownLatch(result.length);
        for (final int i : list) {
            accesses++;
            new Thread(() -> {
                try {
                    Thread.sleep(i * 100);
                    result[nextIndex.getAndIncrement()] = i;
                    writes++;
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        latch.await();
        return result;
    }

    /**
     * Constant time sort. Accesses n*2 times. Writes n times.
     * Uses memory equal to max(list). Use only in specialized circumstances.
     * Worst efficiency: one element equal to Integer.MAX_VALUE
     * Worst case scenario: many elements equal to Integer.MAX_VALUE
     * Best case scenario: many elements within a small range of each other
     * @param list list to sort
     * @return sorted list
     */
    private static int[] memorySort(int[] list) { // jeremy's sorter
        int max = 0;
        accesses += list.length;
        for (int i : list) { // find max
            if (i > max) max = i;
        }
        int[] temp = new int[max + 1];

        accesses += list.length;
        for (int i: list) temp[i] += 1;
        int added = 0;

        for (int i = 0; i < temp.length; i++) {
            for (int num = 0; num < temp[i]; num++) {
                list[added++] = i;
                writes++;
            }
        }
        return list;
    }

    private static int[] shellSort(int[] list) { // https://en.wikipedia.org/wiki/Shellsort
        return list; // TODO: https://en.wikipedia.org/wiki/Shell_sort
    }

    /**
     * DO NOT RUN this method it will hang indefinitely
     * @param list an integer list to sort
     * @return the sorted list. probably. sometime before the heat death of the universe.
     */
    private static int[] bogoSort(int[] list) { // bogo is short for bogus
        int[] result = null;
        int current;
        sort(list); // easier than implementing an isSorted method

        while (!SortTester.equals(list, result)) {
            result = new int[list.length];
            current = 0;
            while (current < list.length) {
                result[current] = list[(int) (Math.random() *  list.length)];
            }
        }
        return result;
    }

    private static int[] miracleSort(int[] list) {
        // let's just hope it's already sorted
        return list;
    }

    private static int[] stackSort(int[] list) /* throws MalformedURLException */ { // runs arbitrary code from StackOverflow. Don't try this at home.
        /* final String API = "api.stackexchange.com/2.1/questions?sort=activity&tagged=sort;java&page=";
        final String OPTIONS = "&pagesize=100&order=desc&site=stackoverflow&todate=1363060800";
        final int MAX_TRIES = 7; // arbitrary

        for (int page = 1; page < MAX_TRIES; page++) {
            URL url = new URL(API + page + OPTIONS);
        } */
        return list; // TODO: finish adapting https://github.com/gkoberger/stacksort/blob/master/js/script.js
    }

    /**
     * Swaps two members of a list, irrespective of order they are given.
     * @param list from which to change elements
     * @param first index of an element
     * @param second index of second element
     * @return list with first and second swapped
     */
   private static int[] swap(int[] list, int first, int second) {
        temp = list[first];
        list[first] = list[second];
        list[second] = temp;
        writes += 2;
        accesses += 2;
        return list;
    }
}
