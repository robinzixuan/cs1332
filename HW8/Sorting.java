import java.util.Comparator;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;


/**
 * Your implementation of various sorting algorithms.
 *
 * @author robin luo
 * @version 1.0
 * @userid hluo76
 * @GTID 903391803
 *
 * Collaborators: null
 *
 * Resources: null
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("the arr is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("the comparator is null");
        }
        int minindex;
        for (int i = 0; i < arr.length; i++) {
            minindex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[minindex]) < 0) {
                    minindex = j;
                }
            }
            T temp = arr[i];
            arr[i] = arr[minindex];
            arr[minindex] = temp;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("the arr is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("the comparator is null");
        }
        T temp;
        int i;
        for (int j = 1; j < arr.length; j++) {
            i = j;
            while (i > 0 && comparator.compare(arr[i], arr[i - 1]) < 0) {
                temp = arr[i];
                arr[i] = arr[i - 1];
                arr[i - 1] = temp;
                i--;
            }
        }

    }
    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("the arr is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("the comparator is null");
        }
        boolean swapped = true;
        int start = 0;
        int end = arr.length - 1;
        T temp;
        int temps = 0;
        while (start < end && swapped) {
            swapped = false;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                    temps = i;
                }
            }
            end = temps;
            if (swapped) {
                swapped = false;
                for (int i = end; i > start; i--) {
                    if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                        temp = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = temp;
                        swapped = true;
                        temps = i;
                    }
                }
            }
            start = temps;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("the arr is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("the comparator is null");
        }
        if (arr.length > 1) {
            T[] left = (T[]) new Object[arr.length / 2];
            T[] right = (T[]) new Object[arr.length - left.length];
            for (int i = 0; i < left.length; i++) {
                left[i] = arr[i];
            }
            for (int j = left.length; j < arr.length; j++) {
                right[j - left.length] = arr[j];
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            merge(arr, comparator, left, right);
        }
    }

    /**
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @param left       the left part of array
     * @param right      the right part of array
     */
    private static <T> void merge(T[] arr, Comparator<T> comparator,
                                  T[] left, T[] right) {
        int i = 0;
        int j = 0;
        for (int z = 0; z < arr.length; z++) {
            if (j >= right.length || (i < left.length
                    && comparator.compare(left[i], right[j]) <= 0)) {
                arr[z] = left[i];
                i += 1;
            } else {
                arr[z] = right[j];
                j += 1;
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("the arr is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("the comparator is null");
        }
        if (rand == null) {
            throw new IllegalArgumentException("the rand is null");
        }
        quickSort(arr, 0, arr.length - 1, comparator, rand);
    }

    /**
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @param low        the first element index of array
     * @param high       the last element index of index
     */
    private static <T> void quickSort(T[] arr, int low, int high,
                                     Comparator<T> comparator, Random rand) {
        if (low < high) {
            int pivot = rand.nextInt(high - low) + low;
            pivot = partition(arr, low, high, comparator, pivot);
            quickSort(arr, low, pivot - 1, comparator, rand);
            quickSort(arr, pivot + 1, high, comparator, rand);
        }
    }

    /**
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param pivot         the index of pivot
     * @param low        the first element index of array
     * @param high       the last element index of index
     * @return pivot         new pivot index
     */
    private static <T> int partition(T[] arr, int low, int high,
                                     Comparator<T> comparator, int pivot) {
        T temp = arr[pivot];
        T pi;
        pi = temp;
        arr[pivot] = arr[high];
        arr[high] = pi;
        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            if (comparator.compare(arr[j], temp) < 0) {
                i++;
                pi = arr[i];
                arr[i] = arr[j];
                arr[j] = pi;
            }
        }
        pi = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = pi;
        return (i + 1);
    }



    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("the arr is null");
        }
        if (arr.length != 0) {
            int maxValue = arr[0];
            int maxlength = 1;
            for (int i = 0; i < arr.length; i++) {
                if (Math.abs(arr[i]) > maxValue) {
                    maxValue = Math.abs(arr[i]);
                }
            }
            while ((maxValue) >= 10) {
                maxlength++;
                maxValue = maxValue / 10;
            }
            List<Integer>[] buckets = new ArrayList[19];
            for (int i = 0; i < 19; i++) {
                buckets[i] = new ArrayList<Integer>();
            }
            int divnumber = 1;

            for (int i = 0; i < maxlength; i++) {
                for (Integer num : arr) {
                    buckets[((num / divnumber) % 10) + 9].add(num);
                }
                int index = 0;
                for (int k = 0; k < buckets.length; k++) {
                    for (Integer x : buckets[k]) {
                        arr[index] = x;
                        index += 1;
                    }
                    buckets[k].clear();
                }
                divnumber = divnumber * 10;
            }
        }
    }
}
