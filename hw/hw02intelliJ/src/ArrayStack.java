import java.util.NoSuchElementException;

/**
 * Your implementation of a Stack backed by an array.
 *
 * @author Jeremy Aguilon
 * @version 1.0
 */
public class ArrayStack<T> implements StackInterface<T> {
    // Do not add any new instance variables!
    private T[] backingArray;
    private int size;

    /**
     * Construct a Stack with an initial capacity of {@code INITIAL_CAPACITY}.
     *
     * Use constructor chaining.
     */
    public ArrayStack() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Construct a Stack with the specified initial capacity of
     * {@code initialCapacity}.
     * @param initialCapacity Initial capacity of the backing array.
     */
    @SuppressWarnings("unchecked") // You don't have to suppress the warning
    public ArrayStack(int initialCapacity) {
        backingArray = (T[]) new Object[initialCapacity];
        size = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void push(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot push null"
                    + "objects to stack");
        }
        if (size == backingArray.length - 1) {
            T[] newBackingArray = (T[]) new Object[backingArray.length * 2];

            for (int i = 0; i < backingArray.length; i++) {
                newBackingArray[i] = backingArray[i];
            }

            backingArray = newBackingArray;
        }

        backingArray[size] = item;

        size++;
    }

    @Override
    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException("Stack is empty.");
        }

        T popped = backingArray[size - 1];

        backingArray[size - 1] = null;
        size--;

        return popped;
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