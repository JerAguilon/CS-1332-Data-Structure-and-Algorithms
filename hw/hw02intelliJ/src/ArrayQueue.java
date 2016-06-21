import java.util.NoSuchElementException;
/**
 * Your implementation of a Queue backed by an array.
 *
 * @author JEREMY AGUILON
 * @version 1.0
 */
public class ArrayQueue<T> implements QueueInterface<T> {

    // Do not add instance variables.
    private T[] backingArray;
    private int size;
    private int front;
    private int back;


    /**
     * Construct a Queue with an initial capacity of {@code INITIAL_CAPACITY}.
     *
     * Use Constructor Chaining
     */
    public ArrayQueue() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Construct a Queue with the specified initial capacity of
     * {@code initialCapacity}.
     * @param initialCapacity Initial capacity of the backing array.
     */
    @SuppressWarnings("unchecked")
    public ArrayQueue(int initialCapacity) {
        backingArray = (T[]) new Object[initialCapacity];

        size = 0;
        front = 0;
        back = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void enqueue(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot queue a null object");
        }
        if (size == 0) {
            backingArray[front] = item;
            back = 0;
        }
        else if (size == backingArray.length) {
            T[] arr = (T[]) new Object[backingArray.length * 2];
            int j = 0;
            for (int i = 0; i < size; i++) {
                arr[j] = backingArray[(i + front) % backingArray.length];
                j++;
            }

            backingArray = arr;
            back = size;
            arr[back] = item;
            front = 0;
        } else if (back == backingArray.length - 1) {
            back = 0;
            backingArray[back] = item;
        } else {
            back++;
            backingArray[back] = item;
        }
        size++;
    }

    @Override
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot dequeue an empty queue");
        }
        T removed = backingArray[front];

        backingArray[front] = null;

        if (front == backingArray.length - 1) {
            front = 0;
        } else {
            front++;
        }

        size--;
        return removed;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Used for testing your code.
     * DO NOT USE THIS METHOD!
     *
     * @return the backing array of this queue.
     */
    public Object[] getBackingArray() {
        return backingArray;
    }

}
