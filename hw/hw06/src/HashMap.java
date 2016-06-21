import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * Your implementation of HashMap.
 * 
 * @author JEREMY AGUILON
 * @version 1.3
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code STARTING_SIZE}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(STARTING_SIZE);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
    }

    @Override
    public V add(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Can't add null key"
                    + " or value");
        }

        if ((double) (size + 1) / table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }

        return addHelper(key, value);

    }

    /**
     * Helper method to the add method, used by
     * resizeBackingArray and add by passing in an
     * appropriate object reference of a table
     *
     * @return added value to the HashMap
     * @param key the key to be added
     * @param value the value to associated with the key
     */
    private V addHelper(K key, V value) {
        int position = key.hashCode() % table.length;


        while (table[position] != null
                && !table[position].getKey().equals(key)
                && !table[position].isRemoved()) {
            position = (position + 1) % table.length;

        }

        if (table[position] == null) {
            table[position] = new MapEntry<>(key, value);
            size++;
            return null;
        }

        if (table[position].isRemoved()) {
            table[position].setKey(key);
            table[position].setValue(value);
            table[position].setRemoved(false);
            size++;
            return null;
        }

        V added = table[position].getValue();
        table[position].setKey(key);
        table[position].setValue(value);

        return added;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot search for a"
                    + " null key");
        }

        int position = key.hashCode() % table.length;
        int initial = position;
        boolean notWrapped = true;
        while (table[position] != null
                && !table[position].getKey().equals(key)
                && !table[position].isRemoved()
                && notWrapped) {

            position = (position + 1) % table.length;
            if (position == initial) {
                notWrapped = false;
            }
        }

        if (table[position] == null
                || !notWrapped
                || table[position].isRemoved()) {
            throw new NoSuchElementException("Key not found in hashtable");
        }

        table[position].setRemoved(true);
        size--;

        return table[position].getValue();
    }

    @Override
    public V get(K key) {

        if (key == null) {
            throw new IllegalArgumentException("Cannot get a null key");
        }

        int position = key.hashCode() % table.length;
        int original = position;
        boolean notWrapped = true;

        while (table[position] != null
                && !table[position].getKey().equals(key)
                && !table[position].isRemoved()
                && notWrapped) {
            position = (position + 1) % table.length;
            if (position == original) {
                notWrapped = false;
            }
        }

        if (table[position] == null
                || !notWrapped
                || table[position].isRemoved()) {
            throw new NoSuchElementException("Key does not exist");
        }

        return table[position].getValue();
    }

    @Override
    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot search for a "
                + "null key");
        }

        int position = key.hashCode() % table.length;
        int original = position;
        boolean notWrapped = true;
        while (table[position] != null
                && !table[position].getKey().equals(key)
                && !table[position].isRemoved()
                && notWrapped) {
            position = (position + 1) % table.length;
            if (position == original) {
                notWrapped = false;
            }
        }

        if (table[position] == null || !notWrapped) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        size = 0;
        table = (MapEntry<K, V>[]) new MapEntry[STARTING_SIZE];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> output = new HashSet<>();
        for (MapEntry<K, V> entry : table) {
            if (entry != null && !entry.isRemoved()) {
                output.add(entry.getKey());
            }
        }

        return output;
    }

    @Override
    public List<V> values() {
        ArrayList<V> output = new ArrayList<>(size);

        for (MapEntry<K, V> entry : table) {
            if (entry != null && !entry.isRemoved()) {
                output.add(entry.getValue());
            }
        }

        return output;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void resizeBackingTable(int length) {
        if (length <= 0 || length < size) {
            throw new IllegalArgumentException("Cannot resize to "
                + "a length less than 1 or less than the number "
                + "of items.");
        }

        MapEntry<K, V>[] oldTable = table;

        table = (MapEntry<K, V>[]) new MapEntry[length];

        size = 0;

        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null && !oldTable[i].isRemoved()) {
                addHelper(oldTable[i].getKey(), oldTable[i].getValue());
            }
        }


    }
    
    @Override
    public MapEntry<K, V>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return table;
    }

}
