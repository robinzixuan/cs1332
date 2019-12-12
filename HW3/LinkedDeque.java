/**
 * Your implementation of a LinkedDeque.
 *
 * @author Robin Luo
 * @version 1.0
 * @userid hluo76
 * @GTID 903391803
 *
 * Collaborators: null
 *
 * Resources: null
 */

import java.util.NoSuchElementException;
public class LinkedDeque<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the front of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        LinkedNode<T> add = new LinkedNode<>(data);
        if (size == 0) {
            head = add;
            tail = add;
        } else {
            add.setNext(head);
            add.setPrevious(null);
            head.setPrevious(add);
            head = add;
        }
        size += 1;
    }

    /**
     * Adds the element to the back of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        LinkedNode<T> add = new LinkedNode<>(data);
        if (size == 0) {
            head = add;
            tail = add;
        } else {
            add.setPrevious(tail);
            add.setNext(null);
            tail.setNext(add);
            tail = add;
        }
        size += 1;
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty.");
        }
        LinkedNode<T> current = head;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            (head.getNext()).setPrevious(null);
            head = head.getNext();
        }
        size -= 1;
        return current.getData();
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty.");
        }
        LinkedNode<T> current = tail;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            (tail.getPrevious()).setNext(null);
            tail = tail.getPrevious();
        }
        size -= 1;
        return current.getData();
    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty.");
        }
        return head.getData();
    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty.");
        }
        return tail.getData();
    }

    /**
     * Returns the head node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
