
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author YOUR NAME HERE
 * @version 1.0
 * @userid hluo76
 * @GTID 903391803
 *
 * Collaborators: null
 *
 * Resources: null
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        for (T t: data) {
            if (t == null) {
                throw new IllegalArgumentException("element is null");
            }
            add(t);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data t
     o add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        root = add(root, data);
    }

    /**
     * helper function for add the data to the tree
     * @param data the data to add
     * @param root the root of the tree
     * @return the node of tree root
     */
    private AVLNode<T> add(AVLNode<T> root, T data) {
        if (root == null) {
            size++;
            root = new AVLNode<T>(data);
        }
        if (data.compareTo(root.getData()) < 0) {
            root.setLeft(add(root.getLeft(), data));
            root.setBalanceFactor(getH(root.getLeft())
                    - getH(root.getRight()));
            root = balance(root);
        } else if (data.compareTo(root.getData()) > 0) {
            root.setRight(add(root.getRight(), data));
            root.setBalanceFactor(getH(root.getLeft())
                    - getH(root.getRight()));
            root = balance(root);
        } else if (data.compareTo(root.getData()) == 0) {
            root = root;
        }
        root.setHeight(Math.max(getH(root.getRight()),
                getH(root.getLeft())) + 1);
        root.setBalanceFactor(getH(root.getLeft())
                - getH(root.getRight()));
        return root;
    }


    /**
     * make the balance for tree
     * @param root mean the node
     * @return node of tree
     *
     */
    private AVLNode<T> balance(AVLNode<T> root) {
        if (root.getBalanceFactor() > 1) {
            //left heavy
            if (root.getLeft() != null && root.getLeft().
                    getBalanceFactor() < 0) {
                root.setLeft(rotateLeft(root.getLeft()));
                return rotateRight(root);
            } else {
                return rotateRight(root);
            }
        } else if (root.getBalanceFactor() < -1) {
            if (root.getRight() != null && root.getRight().
                    getBalanceFactor() > 0) {
                root.setRight(rotateRight(root.getRight()));
                return rotateLeft(root);
            } else {
                return rotateLeft(root);
            }
        }
        return root;
    }

    /**
     * left rotate
     * @param root mean the node
     * @return node of tree
     *
     */
    private AVLNode<T> rotateLeft(AVLNode<T> root) {
        AVLNode b = root.getRight();
        root.setRight(b.getLeft());
        b.setLeft(root);
        root.setHeight(Math.max(getH(root.getRight()),
                getH(root.getLeft())) + 1);
        b.setHeight(Math.max(getH(b.getRight()),
                getH(b.getLeft())) + 1);
        root.setBalanceFactor(getH(root.getLeft())
                - getH(root.getRight()));
        b.setBalanceFactor(getH(b.getLeft())
                - getH(b.getRight()));
        return b;
    }

    /**
     * right rotate
     * @param root mean the node
     * @return node of tree
     *
     */
    private AVLNode<T> rotateRight(AVLNode<T> root) {
        AVLNode b = root.getLeft();
        root.setLeft(b.getRight());
        b.setRight(root);
        root.setHeight(Math.max(getH(root.getRight()),
                getH(root.getLeft())) + 1);
        b.setHeight(Math.max(getH(b.getRight()),
                getH(b.getLeft())) + 1);
        root.setBalanceFactor(getH(root.getLeft())
                - getH(root.getRight()));
        b.setBalanceFactor(getH(b.getLeft())
                - getH(b.getRight()));
        return b;
    }



    /**
     * get the Hight of the node
     * @param root mean the node
     * @return the height
     *
     */
    private int getH(AVLNode<T> root) {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data, NOT predecessor. As a reminder, rotations can occur
     * after removing the successor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data is null");
        }
        if (root == null) {
            throw new NoSuchElementException("the data is not found");
        }
        AVLNode<T> node = new AVLNode<>(data);
        root = removeNode(root, data, node);
        if (node != null) {
            size -= 1;
        }
        return node.getData();
    }

    /**
     * remove the element to the tree.
     *
     * @param root the root of tree
     * @param data the data to add
     * @param node the node remove
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     * @return the root node of tree
     */
    private AVLNode<T> removeNode(AVLNode<T> root, T data, AVLNode<T> node) {
        if (root == null) {
            throw new NoSuchElementException("root is empty");
        }
        if (data.compareTo(root.getData()) < 0) {
            root.setLeft(removeNode(root.getLeft(), data, node));
            root.setHeight(Math.max(getH(root.getRight()),
                    getH(root.getLeft())) + 1);
            root.setBalanceFactor(getH(root.getLeft())
                    - getH(root.getRight()));
            root = balance(root);
        } else if (data.compareTo(root.getData()) > 0) {
            root.setRight(removeNode(root.getRight(), data, node));
            root.setHeight(Math.max(getH(root.getRight()),
                    getH(root.getLeft())) + 1);
            root.setBalanceFactor(getH(root.getLeft())
                    - getH(root.getRight()));
            root = balance(root);
        } else if (data.compareTo(root.getData()) == 0) {
            node.setData(root.getData());
            if (root.getLeft() == null) {
                root.setBalanceFactor(getH(root.getLeft())
                        - getH(root.getRight()));
                return root.getRight();
            } else if (root.getRight() == null) {
                root.setBalanceFactor(getH(root.getLeft())
                        - getH(root.getRight()));
                return root.getLeft();
            } else if (root.getLeft() != null && root.getRight() != null) {
                AVLNode<T> child = new AVLNode<T>(null);
                root.setRight(find(root.getRight(), child));
                root.setData(child.getData());
                root.setHeight(Math.max(getH(root.getRight()),
                        getH(root.getLeft())) + 1);
                root.setBalanceFactor(getH(root.getLeft())
                        - getH(root.getRight()));
                root = balance(root);
            } else {
                return null;
            }
        }
        if (root != null) {
            root.setBalanceFactor(getH(root.getLeft())
                    - getH(root.getRight()));
        }
        return root;
    }

    /**
     * find the replace node
     * @param  node the node input
     * @param  child the child of tree
     * @return the data node return
     */
    private AVLNode<T>  find(AVLNode<T> node, AVLNode<T> child) {
        while (node.getLeft() == null) {
            child.setData(node.getData());
            return node.getRight();
        }
        node.setLeft(find(node.getLeft(), child));
        node.setHeight(Math.max(getH(node.getRight()),
                getH(node.getLeft())) + 1);
        node.setBalanceFactor(getH(node.getLeft())
                - getH(node.getRight()));
        node = balance(node);
        return node;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data is null");
        }
        T element = dataget(root, data);
        if (element == null) {
            throw new NoSuchElementException("data is not in the tree");
        }
        return element;
    }



    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data is null");
        }
        return dataget(root, data) != null;
    }

    /**
     * find the element to the tree.
     *
     * @param root the root of tree
     * @param data the data to add
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     * @return the data of tree node
     */
    private T dataget(AVLNode<T> root, T data) {
        T element = null;
        if (root == null) {
            return null;
        }
        if (data.compareTo(root.getData()) == 0) {
            element = root.getData();
            return element;
        } else {
            if (data.compareTo(root.getData()) < 0) {
                return dataget(root.getLeft(), data);
            } else if (data.compareTo(root.getData()) > 0) {
                return dataget(root.getRight(), data);
            }
        }
        return element;
    }


    /**
     * Returns the height of the root of the tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return root.getHeight();
    }




    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * In your BST homework, you worked with the concept of the predecessor, the
     * largest data that is smaller than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data is null");
        }
        if (root == null) {
            throw new NoSuchElementException("the data is not found");
        }
        T element = predecessor(root, data);
        return element;
    }

    /**
     * @param node the node
     * @param data the data do predecessor
     * @return the data for predecessor
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    private T predecessor(AVLNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("the data is not found");
        }
        if (data.compareTo(node.getData()) == 0) {
            if (node.getLeft() != null) {
                return findPredecessor(node.getLeft());
            } else {
                return null;
            }
        } else if (data.compareTo(node.getData()) < 0) {
            return predecessor(node.getLeft(), data);
        } else {
            T result = predecessor(node.getRight(), data);
            System.out.println(result);
            if (result == null) {
                return node.getData();
            } else {
                return result;
            }
        }
    }

    /**
     * find the predecessor
     * @param node the node
     * @return the data for predecessor
     */
    private T findPredecessor(AVLNode<T> node) {
        if (node.getRight() == null) {
            return node.getData();
        } else {
            return findPredecessor(node.getRight());
        }
    }





    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                50
     *              /    \
     *            25      75
     *           /  \     / \
     *          12   37  70  80
     *         /  \    \      \
     *        10  15    40    85
     *           /
     *          13
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("The K < 0 or K > n");
        }
        List<T> array = new ArrayList<>();
        inorder(root, array, k);
        return array;
    }

    /**
     * Generate a in-order traversal of the tree.
     *
     * @param  root the root node
     * @param  array the array that store the data
     * @param  k the k number need add
     */
    private void inorder(AVLNode<T> root, List<T> array, int k) {
        if (root == null || array.size() == k) {
            return;
        } else {
            if (array.size() < k) {
                inorder(root.getLeft(), array, k);
            }
            if (array.size() < k) {
                array.add(root.getData());
            }
            if (array.size() < k) {
                inorder(root.getRight(), array, k);
            }
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
