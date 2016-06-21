import java.util.NoSuchElementException;
/**
 * Your implementation of a Queue backed by a LinkedList.
 *
 * @author JEREMY AGUILON
 * @version 1.0
 */
public class LinkedListQueue<T> implements QueueInterface<T> {

    // Do not add new instance variables.
    // Do not modify this variable.
    private LinkedListInterface<T> backingList;

    /**
     * Initialize the Queue.
     */
    public LinkedListQueue() {
        backingList = new SinglyLinkedList<>();
    }

    @Override
    public void enqueue(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot queue a null item.");
        }

        backingList.addToBack(item);
    }

    @Override
    public T dequeue() {
        if (backingList.isEmpty()) {
            throw new NoSuchElementException("Cannot dequeue an empty list.");
        }

        return backingList.removeFromFront();
    }

    @Override
    public int size() {
        return backingList.size();
    }

    @Override
    public boolean isEmpty() {
        return backingList.isEmpty();
    }

    /**
     * Used for testing your code.
     * DO NOT USE THIS METHOD!
     *
     * @return the backing list of this queue.
     */
    public LinkedListInterface<T> getBackingList() {
        return backingList;
    }

}
