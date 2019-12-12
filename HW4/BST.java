import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
 *
 * @author ROBIN LUO
 * @version 1.0
 * @userid hluo76
 * @GTID 903391803
 *
 * Collaborators: null
 *
 * Resources: https://www.geeksforgeeks.org/print-path-between-any-two-nodes
 * -in-a-binary-tree/
 */
public class BST<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null!");
        }
        for (T t: data) {
            if (t == null) {
                throw new IllegalArgumentException("element is null!");
            }
            add(t);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        root = addNode(root, data);
    }

    /**
     * Adds the element to the tree.
     *
     * @param root the root of tree
     * @param data the data to add
     * @return the root node of tree
     */
    private BSTNode<T> addNode(BSTNode<T> root, T data) {
        BSTNode<T> node = new BSTNode<>(data);
        if (root == null) {
            size += 1;
            return node;
        }
        if (data.compareTo(root.getData()) == 0) {
            return root;
        }
        if (data.compareTo(root.getData()) < 0) {
            root.setLeft(addNode(root.getLeft(), data));
        } else if (data.compareTo(root.getData()) > 0) {
            root.setRight(addNode(root.getRight(), data));
        }
        return root;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its
     * child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data.
     * You MUST use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        BSTNode<T> node = new BSTNode<>(data);
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
    private BSTNode<T> removeNode(BSTNode<T> root, T data, BSTNode<T> node) {
        if (root == null) {
            throw new NoSuchElementException("root is empty");
        }
        if (data.compareTo(root.getData()) < 0) {
            root.setLeft(removeNode(root.getLeft(), data, node));
        } else if (data.compareTo(root.getData()) > 0) {
            root.setRight(removeNode(root.getRight(), data, node));
        } else if (data.compareTo(root.getData()) == 0) {
            node.setData(root.getData());
            if (root.getLeft() == null) {
                return root.getRight();
            } else if (root.getRight() == null) {
                return root.getLeft();
            } else if (root.getLeft() != null && root.getRight() != null) {
                BSTNode<T> child = new BSTNode<T>(null);
                root.setLeft(find(root.getLeft(), child));
                root.setData(child.getData());
            } else {
                return null;
            }
        }
        return root;
    }

    /**
     * find the replace node
     * @param  node the node input
     * @param  child the child of tree
     * @return the data node return
     */
    private BSTNode<T>  find(BSTNode<T> node, BSTNode<T> child) {
        while (node.getRight() == null) {
            child.setData(node.getData());
            return node.getLeft();
        }
        node.setRight(find(node.getRight(), child));
        return node;
    }


    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
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
            throw new IllegalArgumentException("data is null");
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
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
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
    private T dataget(BSTNode<T> root, T data) {
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
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> array = new ArrayList<>();
        preorder(root, array);
        return array;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     * @param  root the root node
     * @param  array the array that store the data
     *
     */
    private void preorder(BSTNode<T> root, List<T> array) {
        if (root == null) {
            return;
        }
        array.add(root.getData());
        preorder(root.getLeft(), array);
        preorder(root.getRight(), array);
    }

    /**
     * Generate a in-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> array = new ArrayList<>();
        inorder(root, array);
        return array;
    }


    /**
     * Generate a in-order traversal of the tree.
     *
     * Must be O(n).
     * @param  root the root node
     * @param  array the array that store the data
     *
     */
    private void inorder(BSTNode<T> root, List<T> array) {
        if (root == null) {
            return;
        }
        inorder(root.getLeft(), array);
        array.add(root.getData());
        inorder(root.getRight(), array);
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> array = new ArrayList<>();
        postorder(root, array);
        return array;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     * @param  root the root node
     * @param  array the array that store the data
     *
     */
    private void postorder(BSTNode<T> root, List<T> array) {
        if (root == null) {
            return;
        }
        postorder(root.getLeft(), array);
        postorder(root.getRight(), array);
        array.add(root.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> array = new ArrayList<>();
        Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
        if (root == null) {
            return new ArrayList<T>(0);
        }
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> node = queue.poll();
            array.add(node.getData());
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
        return array;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child should be -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     *  function for tree hight
     *
     * @param root mean the node
     * @return the height
     *
     */
    private int height(BSTNode<T> root) {
        if (root == null) {
            return -1;
        } else {
            return 1 + max(height(root.getLeft()),
                    height(root.getRight()));
        }
    }

    /**
     *  function for max
     *
     * @param a first int
     * @param b second int
     * @return larger int
     *
     */
    private int max(int a, int b) {
        if (a >= b) {
            return a;
        }
        return b;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     **
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only need to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *                 50
     *             /        \
     *           25         75
     *         /    \
     *        12    37
     *       /  \    \
     *     10   15   40
     *         /
     *       13
     * findPathBetween(13, 40) should return the list [13, 15, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (root == null) {
            throw new NoSuchElementException("tree is empty");
        }
        LinkedList<T> list = new LinkedList<>();
        list = findPathBetween2(data1, data2, root, list);
        return list;
    }


    /**
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @param node the nearest node for both
     * @param array the array for insert
     * @return the unique path list between the two elements
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    private LinkedList<T> findPathBetween2(T data1, T data2, BSTNode<T> node,
                                    LinkedList<T> array) {
        if (node == null) {
            throw new NoSuchElementException("tree is empty");
        }
        if (data1.compareTo(node.getData()) < 0
                && data2.compareTo(node.getData()) < 0) {
            return findPathBetween2(data1, data2, node.getLeft(), array);
        } else if  (data1.compareTo(node.getData()) > 0
                && data2.compareTo(node.getData()) > 0) {
            return findPathBetween2(data1, data2, node.getRight(), array);
        } else {
            array = pathFromRootarray1(data1, node, array);
            array.removeLast();
            array = pathFromRootarray2(data2, node, array);
            return array;
        }
    }


    /**
     * @param data data to end the path on
     * @param node the nearest node for both
     * @param array the array for insert
     * @return the unique path between the  elements and root
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    private LinkedList<T> pathFromRootarray2(T data, BSTNode<T> node,
                                             LinkedList<T> array) {
        if (node == null) {
            throw new NoSuchElementException("no element find");
        }
        if (data.compareTo(node.getData()) == 0) {
            array.add(node.getData());
            return array;
        } else {
            if (data.compareTo(node.getData()) < 0) {
                array.add(node.getData());
                return pathFromRootarray2(data, node.getLeft(), array);
            } else if (data.compareTo(node.getData()) > 0) {
                array.add(node.getData());
                return pathFromRootarray2(data, node.getRight(), array);
            }
        }
        return null;
    }


    /**
     * @param data data to end the path on
     * @param node the nearest node for both
     * @param array the array for insert
     * @return the unique path between the  elements and root
     * @throws NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    private LinkedList<T> pathFromRootarray1(T data, BSTNode<T> node,
                                             LinkedList<T> array) {
        if (node == null) {
            throw new NoSuchElementException("no element find");
        }
        if (data.compareTo(node.getData()) == 0) {
            array.addFirst(node.getData());
            return array;
        } else {
            if (data.compareTo(node.getData()) < 0) {
                array.addFirst(node.getData());
                return pathFromRootarray1(data, node.getLeft(), array);
            } else if (data.compareTo(node.getData()) > 0) {
                array.addFirst(node.getData());
                return pathFromRootarray1(data, node.getRight(), array);
            }
        }
        return null;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
