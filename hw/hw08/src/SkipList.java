import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
/**
 * Your implementation of a skip list.
 * 
 * @author JEREMY AGUILON
 * @version 1.0
 */
public class SkipList<T extends Comparable<? super T>>
    implements SkipListInterface<T> {
    // Do not add any additional instance variables
    private CoinFlipper coinFlipper;
    private int size;
    private SkipListNode<T> head;

    /**
     * Constructs a SkipList object that stores data in ascending order.
     * When an item is inserted, the flipper is called until it returns a tails.
     * If, for an item, the flipper returns n heads, the corresponding node has
     * n + 1 levels.
     *
     * @param coinFlipper the source of randomness
     */
    public SkipList(CoinFlipper coinFlipper) {
        this.coinFlipper = coinFlipper;
        head = new SkipListNode<>(null, 1, null, null, null, null);

    }

    @Override
    public T first() {
        if (size == 0) {
            throw new NoSuchElementException("Skip list is empty");
        }

        SkipListNode<T> subHead = head;

        while (subHead.getDown() != null) {
            subHead = subHead.getDown();
        }

        return subHead.getNext().getData();

    }

    @Override
    public T last() {
        if (size == 0) {
            throw new NoSuchElementException("Skip list is empty");
        }

        SkipListNode<T> subHead = head;

        while (subHead.getDown() != null) {
            while (subHead.getNext() != null) {
                subHead = subHead.getNext();
            }
            subHead = subHead.getDown();
        }

        while (subHead.getNext() != null) {
            subHead = subHead.getNext();
        }

        return subHead.getData();


    }

    @Override
    public void put(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot put a null element");
        }

        if (head.getLevel() == 1) {
            promoteHead();
        }

        int highestLevel = 1;

        while (coinFlipper.flipCoin().equals(CoinFlipper.Coin.HEADS)) {
            highestLevel++;

            if (head.getLevel() == highestLevel) {
                promoteHead();
            }
        }

        SkipListNode<T> current = head;


        SkipListNode<T> levelUp = null;

        while (current != null) {
            while (current.getNext() != null
                    && data.compareTo(current.getNext().getData()) > 0) {
                current = current.getNext();

            }

            //current is immediately to the left of where it should be inserted
            if (current.getLevel() <= highestLevel) {


                levelUp = new SkipListNode<>(data, current.getLevel(),
                        current, current.getNext(), levelUp, null);
                if (levelUp.getUp() != null) {
                    levelUp.getUp().setDown(levelUp);
                }


                if (current.getNext() != null) {
                    current.getNext().setPrev(levelUp);
                }
                current.setNext(levelUp);

            }
            current = current.getDown();
        }

        size++;

    }

    /**
     * Helper method to promote a head depending on the height of added node
     */
    private void promoteHead() {
        head.setUp(new SkipListNode<>(null, head.getLevel() + 1,
                null, null, null, head));
        head = head.getUp();

    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove a null element");
        }

        SkipListNode<T> current = head;
        T removed = null;

        while (current != null) {


            while (current.getNext() != null
                    && data.compareTo(current.getNext().getData()) >= 0) {
                current = current.getNext();
            }


            if (data.equals(current.getData())) {
                //current.setUp(null);

                removed = current.getData();

                current.getPrev().setNext(current.getNext());

                if (current.getNext() != null) {
                    current.getNext().setPrev(current.getPrev());
                }

                //check to see if the head needs to be reassigned
                if (head.getDown().getNext() == null) {
                    head = head.getDown();
                    head.setUp(null);
                }

            }
            current = current.getDown();

        }

        if (removed == null) {
            throw new NoSuchElementException("Element does "
                    + "not exist in list");
        } else {
            size--;
            return removed;
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot check "
                    + "for a null element");
        }

        SkipListNode<T> current = head;

        while (current != null) {
            while (current.getNext() != null
                    && data.compareTo(current.getNext().getData()) >= 0) {
                current = current.getNext();
            }

            \

            if (data.equals(current.getData())) {
                return true;
            }
            current = current.getDown();
        }
        return false;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot "
                    + "get a null element");
        }

        SkipListNode<T> current = head;

        while (current != null) {
            while (current.getNext() != null
                    && data.compareTo(current.getNext().getData()) >= 0) {
                current = current.getNext();
            }

            if (data.equals(current.getData())) {
                return current.getData();
            }

            current = current.getDown();
        }

        throw new NoSuchElementException("Element does not exist in lists");

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        head = new SkipListNode<T>(null, 1);
    }

    @Override
    public Set<T> dataSet() {
        Set<T> output = new HashSet<>();
        SkipListNode<T> current = head;
        while (current.getLevel() != 1) {
            current = current.getDown();
        }
        current = current.getNext();
        while (current != null) {
            output.add(current.getData());
            current = current.getNext();
        }
        return output;
    }

    @Override
    public SkipListNode<T> getHead() {
        return head;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("**********************\n");
        builder.append(String.format("SkipList (size = %d)\n", size()));
        SkipListNode<T> levelCurr = getHead();

        while (levelCurr != null) {
            SkipListNode<T> curr = levelCurr;
            int level = levelCurr.getLevel();
            builder.append(String.format("Level: %2d   ", level));

            while (curr != null) {
                builder.append(String.format("(%s)%s", curr.getData(),
                            curr.getNext() == null ? "\n" : ", "));
                curr = curr.getNext();
            }
            levelCurr = levelCurr.getDown();
        }
        builder.append("**********************\n");
        return builder.toString();
    }

}
