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
    public void addAtIndex(int index, T data) {
        if (index == 0) {
            addToFront(data);
        } else if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(
                    "Invalid index. Index must be in range [0,size]");
        } else if (data == null) {
            throw new IllegalArgumentException(
                    "Invalid data. Data cannot be null.");
        } else if (index == size) {
            addToBack(data);

        } else {
            int i = 0;

            LinkedListNode<T> left = head;
            LinkedListNode<T> right = left.getNext();
            while (i < index - 1) {

                left = right;
                right = left.getNext();
                i++;
            }

            LinkedListNode<T> newNode = new LinkedListNode<>(data, right);
            left.setNext(newNode);
            size++;

        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Invalid index. Index must be greater than"
                    + " or equal to 0 and less than size");
        }

        int i = 0;

        LinkedListNode<T> current = head;

        while (i < index) {
            current = current.getNext();
            i++;
        }

        return current.getData();
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Invalid index. Index must be an int in range [0, size).");
        }

        T removed;

        if (index == 0) {
            removed = head.getData();
            removeFromFront();

        } else if (index == size - 1) {
            removed = tail.getData();
            removeFromBack();
        } else {
            LinkedListNode<T> left = head;
            LinkedListNode<T> right = left.getNext().getNext();

            for (int i = 1; i != index; i++) {
                left = left.getNext();
                right = right.getNext();
            }

            removed = left.getNext().getData();
            left.setNext(right);
            size--;

            if (size == 1) {
                if (index == 0) {
                    head = tail;
                } else {
                    tail = head;
                }
            }
        }


        return removed;
    }

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
            tail = new LinkedListNode<T>(data, null);

            LinkedListNode<T> current = head;

            while (current.getNext() != null) {
                current = current.getNext();
            }

            current.setNext(tail);
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
    public boolean removeAllOccurrences(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }

        int i = 0;
        boolean removed = false;

        LinkedListNode<T> current = head;

        while (i < size) {
            if (current.getData().equals(data)) {
                removeAtIndex(i);
                i--;
                removed = true;
            }
            current = current.getNext();
            i++;
        }

        return removed;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];

        LinkedListNode<T> current = head;

        for (int i = 0; i < size; i++) {
            array[i] = current.getData();
            current = current.getNext();
        }

        return array;
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
