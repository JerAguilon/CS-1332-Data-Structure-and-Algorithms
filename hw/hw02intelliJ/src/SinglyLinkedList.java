/**
 * Your implementation of a SinglyLinkedList
 *
 * @author Jeremy Aguilon
 * @version 1.0
 */
public class SinglyLinkedList<T> implements LinkedListInterface<T> {

    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;


    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Data is invalid. Data cannot be null");
        }

        if (size == 0) {
            head = new LinkedListNode<T>(data, null);
            tail = new LinkedListNode<T>(data, null);
        } else if (size == 1) {
            head = new LinkedListNode<T>(data, tail);
        } else {
            head = new LinkedListNode<T>(data, head);
        }

        size++;

    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Data is invalid. Data cannot be null");
        }

        if (size == 0) {
            tail = new LinkedListNode<>(data, null);
            head = tail;
        } else if (size == 1) {
            tail = new LinkedListNode<>(data, null);
            head.setNext(tail);
        } else {
            LinkedListNode<T> newTail = new LinkedListNode<T>(data, null);
            tail.setNext(newTail);
            tail = newTail;
        }
        size++;
    }

    @Override
    public T removeFromFront() {
        T removed;
        if (size == 0) {
            return null;
        } else if (size == 1) {
            removed = head.getData();
            clear();
        } else {
            size--;
            removed = head.getData();
            head = head.getNext();

            if (size == 1) {
                head = tail;
            }
        }

        return removed;
    }

    @Override
    public T removeFromBack() {
        T removed;


        if (size == 0) {
            return null;
        } else if (size == 1) {

            if (head != null) {
                removed = head.getData();
            } else {
                removed = tail.getData();
            }
            clear();
        } else {
            LinkedListNode<T> current = head;

            for (int i = 0; i < size - 2; i++) {
                current = current.getNext();
            }

            removed = current.getNext().getData();

            current.setNext(null);
            tail = current;
            size--;

            if (size == 1) {
                tail = head;
            }
        }

        return removed;

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    @Override
    public int size() {
        return size;
    }

    /**
     * Clears the list
     */
    private void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    @Override
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}
