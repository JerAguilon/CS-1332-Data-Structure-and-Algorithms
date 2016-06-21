import java.util.NoSuchElementException;

/**
 * Your implementation of a max heap.
 *
 * @author YOUR NAME HERE
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial size of {@code STARTING_SIZE}.
     *
     * Use the constant field in the interface. Do not use magic numbers!
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[STARTING_SIZE];
        size = 0;
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add a null item to the"
                    + " haep");
        }

        if (size == backingArray.length - 1) {
            T[] newArray = (T[]) new Comparable[backingArray.length * 2];

            for (int i = 1; i < backingArray.length; i++) {
                newArray[i] = backingArray[i];
            }

            backingArray = newArray;
        }

        size++;

        backingArray[size] = item;

        int i = size;

        while (i > 1 && backingArray[i].compareTo(backingArray[i / 2]) > 0) {
            swap(i, i / 2);
            i = i / 2;
        }
    }

    @Override
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }

        T removed = backingArray[1];

        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;

        heapify();

        return removed;
    }

    /**
     * Heapifies the heap by sending the item at the root index downards
     * appropriately
     *
     */
    private void heapify() {
        int i = 1; //position of the smallest value in heap


        boolean stillSwitching = true;
        while (hasChildren(i) && stillSwitching) {
            int largest = i * 2;



            if (backingArray[largest + 1] != null) {
                if (backingArray[largest + 1]
                        .compareTo(backingArray[largest]) > 0) {
                    largest++;
                }
            }

            if (backingArray[i].compareTo(backingArray[largest]) < 0) {

                swap(i, largest);
            } else {
                stillSwitching = false;
            }
            i = largest;
        }

    }

    /**
     * Heapifies the heap by sending the item at the root index downards
     * appropriately
     * @param i the location in the array of the item in question
     * @return whether the node has children or not
     */
    private boolean hasChildren(int i) {
        return i * 2 <= size; // && backingArray[i * 2] != null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        backingArray = (T[]) new Comparable[STARTING_SIZE];
    }

    @Override
    public Comparable[] getBackingArray() {
        // DO NOT CHANGE THIS METHOD!
        return backingArray;
    }

    /**
     * Helper method to swap the items in two indices
     * @param i the index that will be switched
     * @param j the other index that will be switched
     */
    private void swap(int i, int j) {
        T temp = backingArray[i];
        backingArray[i] = backingArray[j];
        backingArray[j] = temp;
    }

}
