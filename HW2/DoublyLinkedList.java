/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Robin Luo
 * @version 1.0
 * @userid hluo76
 * @GTID 903391803
 *
 * Collaborators: NULL
 *
 * Resources: NULL
 */
import java.util.NoSuchElementException;

public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        // index < 0 or index > size
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index is small than 0");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("Index is larger than size");
        } else if (data == null) {
            // data is null
            throw new IllegalArgumentException("Data is null");
        }
        DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<>(data);
        if (index == 0) {
            if (head == null) {
                head = temp;
                tail = temp;
            } else {
                temp.setNext(head);
                temp.setPrevious(null);
                head.setPrevious(temp);
                head = temp;
            }
        } else if (index == size) {
            if (tail == null) {
                head = temp;
                tail = temp;
            } else {
                tail.setNext(temp);
                temp.setNext(null);
                temp.setPrevious(tail);
                tail = temp;
            }
        } else {
            DoublyLinkedListNode<T> current;
            if (index <= size / 2) {
                current = head;
                for (int j = 0; j < index; j++) {
                    current = current.getNext();
                }
            } else {
                current = tail;
                for (int j = size - 1; j > index; j--) {
                    current = current.getPrevious();
                }
            }
            temp.setNext(current);
            temp.setPrevious(current.getPrevious());
            (current.getPrevious()).setNext(temp);
            current.setPrevious(temp);
        }
        size += 1;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            // data is null
            throw new IllegalArgumentException("Data is null");
        }
        DoublyLinkedListNode<T> temp = new DoublyLinkedListNode(data);
        if (head == null) {
            head = temp;
            tail = temp;
        } else {
            temp.setNext(head);
            temp.setPrevious(null);
            head.setPrevious(temp);
            head = temp;
        }
        size += 1;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            // data is null
            throw new IllegalArgumentException("Data is null");
        }
        DoublyLinkedListNode<T> temp = new DoublyLinkedListNode(data);
        if (tail == null) {
            head = temp;
            tail = temp;
        } else {
            tail.setNext(temp);
            temp.setNext(null);
            temp.setPrevious(tail);
            tail = temp;
        }
        size += 1;
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        // index < 0 or index >= size
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index is small than 0");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index is larger than size");
        }
        if (index == 0) {
            DoublyLinkedListNode<T> current = head;
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                (head.getNext()).setPrevious(null);
                head = head.getNext();
            }
            size -= 1;
            return current.getData();
        } else if (index == size - 1) {
            DoublyLinkedListNode<T> current = tail;
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                (tail.getPrevious()).setNext(null);
                tail = tail.getPrevious();
            }
            size -= 1;
            return current.getData();
        } else {
            DoublyLinkedListNode<T> current;
            if (index <= size / 2) {
                current = head;
                for (int i = 0; i < index; i++) {
                    current = current.getNext();
                }
            } else {
                current = tail;
                for (int i = size - 1; i > index; i--) {
                    current = current.getPrevious();
                }
            }
            (current.getPrevious()).setNext(current.getNext());
            (current.getNext()).setPrevious(current.getPrevious());
            size -= 1;
            return current.getData();
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty.");
        }
        DoublyLinkedListNode<T> current = head;
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
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty.");
        }
        DoublyLinkedListNode<T> current = tail;
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
     * Returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index is small than 0");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index is larger than size");
        }
        if (index == 0) {
            return head.getData();
        } else if (index == size) {
            return tail.getData();
        } else {
            DoublyLinkedListNode<T> current;
            if (index <= size / 2) {
                current = head;
                for (int i = 0; i < index; i++) {
                    current = current.getNext();
                }
            } else {
                current = tail;
                for (int i = size - 1; i > index; i--) {
                    current = current.getPrevious();
                }
            }
            return current.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is empty");
        }
        if (size == 0) {
            throw new NoSuchElementException("List is empty");
        } else {
            DoublyLinkedListNode<T> current = tail;
            if (current.getData().equals(data)) {
                if (size != 1) {
                    (tail.getPrevious()).setNext(null);
                    tail = tail.getPrevious();
                    size -= 1;
                    return current.getData();
                } else {
                    tail = null;
                    head = null;
                    size -= 1;
                    return current.getData();
                }
            } else {
                for (int i = size; i > 1; i--) {
                    if (current.getData().equals(data)) {
                        (current.getPrevious()).setNext(current.getNext());
                        (current.getNext()).setPrevious(current.getPrevious());
                        size -= 1;
                        return current.getData();
                    }
                    current = current.getPrevious();
                }
                if (current.getData().equals(data)) {
                    (head.getNext()).setPrevious(null);
                    head = head.getNext();
                    size -= 1;
                    return current.getData();
                } else {
                    throw new NoSuchElementException("Data is not exist");
                }
            }
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        if (size == 0) {
            Object[] array = (Object[]) new Object[0];
            return array;
        }
        DoublyLinkedListNode<T> current = head;
        Object[] array = (Object[]) new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = current.getData();
            current = current.getNext();
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
