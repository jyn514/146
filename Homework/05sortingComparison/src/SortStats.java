import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

@SuppressWarnings("SpellCheckingInspection")
class SortStats {
    static int accesses, writes;
    private static transient int min, minIndex = 0, temp, iterator;
    @Nullable

    int[] sorted;

    SortStats(@Nullable int[] list, String method) {
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
            case "succ":
                sorted = succSort(list);
                break;
            default:
                throw new IllegalArgumentException("Please choose one of 'merge', 'bubble', 'quick', 'selection', 'sleep', or 'succ'");
        }
    }

    /**
     * Sort forwards starting from front. Elements behind count are sorted, elements in front are not.
     * O(n^2) time, and read complexity, O(n) for writes`
     * @param list must have length <= Integer.MAX_VALUE
     * @return sorted list
     */
    private static int[] selectionSort(@NotNull int[] list) {
        for (int count = 0; count < list.length - 1; count++) {
            iterator = count;
            min = Integer.MAX_VALUE;
            while (iterator < list.length) {
                temp = list[iterator]; accesses++;
                if (temp < min) {
                    min = temp;
                    minIndex = iterator;
                }
                iterator++;
            }
            list = swap(list, count, minIndex);
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
    private static int[] insertSort(@NotNull int[] list) {
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

    @NotNull
    private static int[] bubbleSort(int[] list) {
        return list; // TODO
    }

    @NotNull
    private static int[] mergeSort(int[] list) {
        return list; // TODO
    }

    @NotNull
    private static int[] quickSort(int[] list) {
        return list; // TODO
    }

    @NotNull
    private static synchronized int[] sleepSort(@NotNull int[] list) throws InterruptedException { // why the hell not

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

        try {
            latch.await();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return result;
    }

    @NotNull
    private static int[] succSort(@NotNull int[] list) { // jeremy's sorter
        int max = 0;
        for (int i : list) {
            accesses++;
            if (i > max) {
                max = i;
            }
        }
        int[] temp = new int[max + 1];
        for (int i: list) {
            accesses++;
            temp[i] += 1;
        }
        int added = 0;

        for (int i = 0; i < temp.length; i++) {
            for (int num = 0; num < temp[i]; num++) {
                list[added] = i;
                added++;
                writes++;
            }
        }
        return list;
    }

    @NotNull
    private static int[] shellSort(int[] list) { // https://en.wikipedia.org/wiki/Shellsort
        return list; // TODO
    }

    /**
     * Swaps two members of a list, irrespective of order they are given.
     * @param list from which to change elements
     * @param first index of an element
     * @param second index of second element
     * @return list with first and second swapped
     */
    @NotNull
    private static int[] swap(@NotNull int[] list, int first, int second) {
       // System.err.printf("Swapping %d at index %d with %d at index %d\n", list[first], first, list[second], second);
        int temp = list[first];
        list[first] = list[second];
        list[second] = temp;
        writes += 2;
        accesses += 2;
        return list;
    }
}
