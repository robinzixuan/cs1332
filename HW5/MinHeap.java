import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Robin Luo
 * @version 1.0
 * @userid hluo76
 * @GTID 903391803
 * Collaborators: null
 *
 * Resources: null
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * <p>
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     * <p>
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     * <p>
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        size = data.size();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("data is null");
            }
            backingArray[i + 1] = data.get(i);
        }
        int small;
        T temp;
        for (int i = data.size() / 2; i >= 1; i--) {
            minHeapify(data, i);
        }
    }

    /**
     * @param data a list of data to initialize the heap with
     * @param index the index of the parent
     */
    private void minHeapify(ArrayList<T> data, int index) {
        int left = 2 * index;
        int right = 2 * index + 1;
        int small;
        T temp;
        if (left <= data.size()  && backingArray[left].
                compareTo(backingArray[index]) < 0) {
            small = left;
        } else {
            small = index;
        }
        if (right <= data.size() && backingArray[right].
                compareTo(backingArray[small]) < 0) {
            small = right;
        }
        if (small != index) {
            temp = backingArray[index];
            backingArray[index] = backingArray[small];
            backingArray[small] = temp;
            index = small;
            minHeapify(data, index);
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (size >= backingArray.length - 1) {
            T[] temp = (T[]) new Comparable[2 * backingArray.length];
            for (int i = 1; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }
        size++;
        backingArray[size] = data;
        int child = size;
        int parent = (int) (child / 2);
        T temp;
        while (child > 1 && backingArray[parent].
                compareTo(backingArray[child]) > 0) {
            temp = backingArray[parent];
            backingArray[parent] = backingArray[child];
            backingArray[child] = temp;
            child = parent;
            parent = (int) (child / 2);
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("heap is empty");
        }
        int small;
        T data = backingArray[1];
        T temp;
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        int index = 1;
        while ((index * 2) <= size) {
            if (backingArray[index * 2 + 1] != null) {
                if (backingArray[index * 2].compareTo(backingArray[index
                        * 2 + 1]) < 0) {
                    small = index * 2;
                } else {
                    small = index * 2 + 1;
                }
            }  else {
                small = index * 2;
            }
            if (backingArray[index].compareTo(backingArray[small])
                    > 0) {
                temp = backingArray[index];
                backingArray[index] = backingArray[small];
                backingArray[small] = temp;
                index = small;
            }
            index = small;
        }
        return data;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("heap is empty");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
