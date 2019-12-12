import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;



/**
 * Your implementation of a LinearProbingHashMap.
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
public class LinearProbingHashMap<K, V> {

    /**
     * The initial capacity of the LinearProbingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the LinearProbingHashMap
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private LinearProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public LinearProbingHashMap() {
        table = (LinearProbingMapEntry<K, V>[])
                new LinearProbingMapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of initialCapacity.
     *
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public LinearProbingHashMap(int initialCapacity) {
        table = (LinearProbingMapEntry<K, V>[])
                new LinearProbingMapEntry[initialCapacity];
        size = 0;
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("the key or value is null");
        }
        if ((double) (size + 1) / ((double)
                (table.length)) > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }
        int index = Math.abs(key.hashCode() % table.length);
        V temp = null;
        boolean done = false;
        int firstDelete = -1;
        boolean getFirstD = false;
        int count = -1;
        while (!done) {
            count++;
            if (table[index] != null) {
                if (count == table.length - 1) {
                    table[firstDelete] = new
                            LinearProbingMapEntry<K, V>(key, value);
                    table[firstDelete].setRemoved(false);
                    done = true;
                    size++;
                } else if (!table[index].isRemoved()) {
                    if (table[index].getKey().equals(key)) {
                        temp = table[index].getValue();
                        table[index].setValue(value);
                        done = true;
                    } else {
                        index = (index + 1) % table.length;
                    }
                } else {
                    if (table[index].getKey().equals(key) && getFirstD) {
                        table[firstDelete] = new
                                LinearProbingMapEntry<K, V>(key, value);
                        table[firstDelete].setRemoved(false);
                        done = true;
                        size++;
                    } else if (table[index].getKey().equals(key)) {
                        table[index].setValue(value);
                        table[index].setRemoved(false);
                        done = true;
                        size++;
                    } else {
                        if (!getFirstD) {
                            firstDelete = index;
                            getFirstD = true;
                        }
                        index = (index + 1) % table.length;
                    }
                }
            } else {
                if (getFirstD) {
                    table[firstDelete].setRemoved(false);
                    table[firstDelete] = new
                            LinearProbingMapEntry<K, V>(key, value);
                    done = true;
                    size++;
                } else {
                    table[index] = new LinearProbingMapEntry<K, V>(key, value);
                    done = true;
                    size++;
                }
            }
        }
        return temp;
    }


    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("the key is null");
        }
        int i = Math.abs(key.hashCode() % table.length);
        V temp = null;
        boolean found = false;
        int count = -1;
        while (!found) {
            count++;
            if (table[i] == null) {
                throw new java.util.NoSuchElementException("The key "
                        + "does not exist.");
            } else if (count == table.length - 1) {
                throw new java.util.NoSuchElementException("The key "
                        + "does not exist.");
            } else {
                if (!table[i].getKey().equals(key)) {
                    i = (i + 1) % table.length;
                } else {
                    if (table[i].isRemoved()) {
                        throw new java.util.NoSuchElementException(
                                "The key does not exist.");
                    } else {
                        temp = table[i].getValue();
                        table[i].setRemoved(true);
                        found = true;
                        size--;
                    }
                }
            }
        }
        return temp;
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("the key is null");
        }
        int index = Math.abs(key.hashCode() % table.length);
        V value = null;
        if (table[index] == null) {
            throw new java.util.NoSuchElementException("error,key not exist");
        }
        if (table[index].getKey().equals(key)) {
            if (table[index].isRemoved()) {
                throw new java.util.
                        NoSuchElementException("error,key not exist");
            }
            value = table[index].getValue();
            return value;
        } else {
            int count = 0;
            LinearProbingMapEntry<K, V> entry;
            index += 1;
            for (int i = index; i < table.length; i++) {
                entry = table[i];
                if (entry != null) {
                    if (entry.getKey().equals(key)) {
                        if (entry.isRemoved()) {
                            throw new java.util.
                                    NoSuchElementException("error,key "
                                    + "not exist");
                        }
                        value = table[i].getValue();
                        return value;
                    }
                }
            }
            for (int j = 0; j < index; j++) {
                entry = table[j];
                if (entry != null) {
                    if (entry.getKey().equals(key)) {
                        if (entry.isRemoved()) {
                            throw new java.util.
                                    NoSuchElementException("error,key "
                                    + "not exist");
                        }
                        value = table[j].getValue();
                        return value;
                    }
                }
            }
        }
        throw new java.util.NoSuchElementException("error,key not exist");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("the key is null");
        }
        int i = Math.abs(key.hashCode() % table.length);
        boolean found = false;
        int count = -1;
        while (!found) {
            count++;
            if (table[i] == null) {
                return false;
            } else if (count == table.length - 1) {
                return false;
            } else {
                if (table[i].getKey().equals(key)
                        && !table[i].isRemoved()) {
                    found = true;
                } else {
                    i = (i + 1) % table.length;
                }
            }
        }
        return found;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<K>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                keys.add(table[i].getKey());
            }
        }
        return keys;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> values = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                values.add(table[i].getValue());
            }
        }
        return values;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     * number of items in the hash map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("length is less than size");
        }
        LinearProbingMapEntry<K, V>[] temp = (LinearProbingMapEntry<K, V>[])
                new LinearProbingMapEntry[length];
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                int index = Math.abs(table[i].getKey().hashCode() % length);
                if (temp[index] == null) {
                    temp[index] = new LinearProbingMapEntry<>(
                            table[i].getKey(), table[i].getValue());
                } else {
                    boolean done = false;
                    while (!done) {
                        index = (index + 1) % length;
                        if (temp[index] == null) {
                            temp[index] = new LinearProbingMapEntry<>(
                                    table[i].getKey(), table[i].getValue());
                            done = true;
                        }
                    }
                }
            }
        }
        table = temp;
    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the initial capacity and resets the
     * size.
     *
     * Must be O(1).
     */
    public void clear() {
        table = (LinearProbingMapEntry<K, V>[])
                new LinearProbingMapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public LinearProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
