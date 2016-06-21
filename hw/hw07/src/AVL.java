import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Collection;


/**
 * Your implementation of an AVL Tree.
 *
 * @author JEREMY AGUILON
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {

    // Do not make any new instance variables.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add a null element");
        }

        for (T i : data) {
            add(i);

        }
    }

    /**
     * Helper method to rebalance the AVL
     *
     * @param root the root to be rebalanced
     * @return the new root after rebalancing
     */
    private AVLNode<T> rebalance(AVLNode<T> root) {
        if (root.getBalanceFactor() == 2) {
            if (root.getLeft().getBalanceFactor() == 1
                    || root.getLeft().getBalanceFactor() == 0) {
                root = rotateRight(root);
            } else if (root.getLeft().getBalanceFactor() == -1) {
                root = rotateLeftRight(root);
            }
        }

        if (root.getBalanceFactor() == -2) {
            if (root.getRight().getBalanceFactor() == -1
                    || root.getRight().getBalanceFactor() == 0) {
                root = rotateLeft(root);
            } else if (root.getRight().getBalanceFactor() == 1) {
                root = rotateRightLeft(root);
            }
        }

        return root;
    }


    /**
     * Helper method to update balance factor after changes
     * in AVL have been made
     *
     * @param root the root to be updated
     */
    private void updateBalanceFactor(AVLNode<T> root) {
        if (root.getLeft() != null && root.getRight() != null) {
            root.setBalanceFactor(root.getLeft().getHeight()
                    - root.getRight().getHeight());
        } else if (root.getLeft() != null) {
            root.setBalanceFactor(1 + root.getLeft().getHeight());
        } else if (root.getRight() != null) {
            root.setBalanceFactor(-1 - root.getRight().getHeight());
        } else {
            root.setBalanceFactor(0);
        }
    }

    /**
     * Helper method to rotate counter-clockwise around a node
     *
     * @param root the root to to be updated
     */
    private void updateHeight(AVLNode<T> root) {
        if (root == null) {
            return;
        }

        int heightLeft = -1;
        int heightRight = -1;

        if (root.getLeft() != null) {
            heightLeft = root.getLeft().getHeight();
        }
        if (root.getRight() != null) {
            heightRight = root.getRight().getHeight();
        }

        root.setHeight(1 + Math.max(heightLeft, heightRight));
    }

    /**
     * Helper method to rotate clockwise around a node
     *
     * @param root the root to be rotated right
     * @return the new parent node after the rotation
     */
    private AVLNode<T> rotateRight(AVLNode<T> root) {
        AVLNode<T> newParent = root.getLeft();

        root.setLeft(newParent.getRight());
        newParent.setRight(root);

        updateHeight(root);
        updateBalanceFactor(root);

        if (newParent.getLeft() != null) {
            updateBalanceFactor(newParent.getLeft());
            updateHeight(newParent.getLeft());
        }

        updateHeight(newParent);
        updateBalanceFactor(newParent);

        return newParent;
    }

    /**
     * Helper method to rotate counter-clockwise around a node
     *
     * @param root the root to be rotated left
     * @return the new parent node after the rotation
     */
    private AVLNode<T> rotateLeft(AVLNode<T> root) {
        AVLNode<T> newParent = root.getRight();

        root.setRight(newParent.getLeft());


        newParent.setLeft(root);

        updateHeight(root);
        updateBalanceFactor(root);

        if (newParent.getRight() != null) {
            updateHeight(newParent.getRight());
            updateBalanceFactor(newParent.getRight());
        }


        updateHeight(newParent);
        updateBalanceFactor(newParent);

        return newParent;
    }

    /**
     * Helper method to rotate left and then right
     * about a node
     *
     * @param root the root to be rotated right
     * @return the new parent node after the rotation
     */
    private AVLNode<T> rotateLeftRight(AVLNode<T> root) {
        root.setLeft(rotateLeft(root.getLeft())); //rotate the child
        return rotateRight(root);
    }

    /**
     * Helper method to rotate right and then left
     * about a node
     *
     * @param root the root to be rotated right
     * @return the new parent node after the rotation
     */
    private AVLNode<T> rotateRightLeft(AVLNode<T> root) {
        root.setRight(rotateRight(root.getRight()));
        return rotateLeft(root);
    }



    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add a null element");
        }
        root = add(data, root);
    }


    /**
     * Helper method to add a node to the AVL
     *
     * @param data the data to be added to the tree
     * @param root the root to compare data to
     * @return A node representing the recursive state of the new tree
     */
    private AVLNode<T> add(T data, AVLNode<T> root) {
        if (root == null) {
            root = new AVLNode<>(data);
            root.setHeight(1);
            root.setBalanceFactor(0);
            size++;
        }

        if (data.compareTo(root.getData()) < 0) {
            root.setLeft(add(data, root.getLeft()));
        }

        if (data.compareTo(root.getData()) > 0) {
            root.setRight(add(data, root.getRight()));
        }

        updateHeight(root);
        updateBalanceFactor(root);

        root = rebalance(root);

        return root;
    }



    @Override
    public T remove(T data) {

        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data");
        }
        AVLNode<T> tracker = new AVLNode<>(null);

        root = remove(data, root, tracker);
        size--;

        return tracker.getData();
    }

    /**
     * Helper method to remove an element from the tree
     *
     * @param data the data to be removed from the tree
     * @param root the node being comapred to data
     * @param tracker a dummy node that stores the removed node's data
     * @return A node that displays the recursive state of tree after removal
     * @throws NoSuchElementException when the data does not exist in the tree
     */
    private AVLNode<T> remove(T data,
            AVLNode<T> root, AVLNode<T> tracker) {
        if (root == null) {
            throw new NoSuchElementException("Element does not exist in AVL");
        }
        if (data.compareTo(root.getData()) < 0) {
            //go to the left
            root.setLeft(remove(data, root.getLeft(), tracker));

        } else if (data.compareTo(root.getData()) > 0) {
            //go to the right
            root.setRight(remove(data, root.getRight(), tracker));

        } else {
            //must be equal here
            if (tracker != null) {
                tracker.setData(root.getData());
            }

            if (root.getLeft() == null && root.getRight() == null) {
                root = null;
            } else if (root.getLeft() == null) {
                root = root.getRight();
            } else if (root.getRight() == null) {
                root = root.getLeft();
            } else {
                root = succeed(root, root, root.getRight());
                /*AVLNode<T> successor = root.getRight();


                while (successor.getLeft() != null) {
                    successor = successor.getLeft();
                }


                root.setData(successor.getData());
                root.setRight(remove(successor.getData(),
                        root.getRight(), null));
                updateHeight(root);
                updateBalanceFactor(root);
                root = rebalance(root);*/

                updateHeight(root);
                updateBalanceFactor(root);
                root = rebalance(root);
            }
        }

        if (root != null) {
            updateHeight(root);
            updateBalanceFactor(root);
            root = rebalance(root);

        }

        return root;
    }

    /**
     * Helper method to remove an element when there are two children
     *
     * @param parent parent of the successor node
     * @param successor successor of the root node
     * @param root root, the node being removed
     * @return A node that displays the state of the tree after removal
     */
    private AVLNode<T> succeed(AVLNode<T> parent,
                               AVLNode<T> root, AVLNode<T> successor) {
        if (successor.getLeft() != null) {
            if (parent != root) {
                parent.setLeft(succeed(successor, root, successor.getLeft()));
            } else {
                parent.setRight(succeed(successor, root, successor.getLeft()));
            }
        } else {
            root.setData(successor.getData());
            if (parent != root) {
                parent.setLeft(successor.getRight());
            } else {
                parent.setRight(successor.getRight());
            }
        }

        updateHeight(parent);
        updateBalanceFactor(parent);

        parent = rebalance(parent);

        return parent;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get a null element");
        }
        return get(data, root);
    }

    /**
     * Helper method to find data in an AVL
     *
     * @param data the data being searched for
     * @param root the node being compared to data
     * @return the data of the node that matches data
     */
    private T get(T data, AVLNode<T> root) {
        if (root == null) {
            throw new NoSuchElementException("Element does not exist in "
                    + "the tree.");
        }

        if (data.compareTo(root.getData()) < 0) {
            return get(data, root.getLeft());
        }

        if (data.compareTo(root.getData()) > 0) {
            return get(data, root.getRight());
        }

        return root.getData();
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot search for a null element.");
        }

        return contains(data, root);
    }

    /**
     * Helper method to validate if a data is in the AVL
     *
     * @param data the data being validated in the AVL
     * @param root the node being compared to data
     * @return boolean of whether data is in the AVL
     */
    private boolean contains(T data, AVLNode<T> root) {
        if (root == null) {
            return false;
        }

        if (data.compareTo(root.getData()) < 0) {
            return contains(data, root.getLeft());
        }

        if (data.compareTo(root.getData()) > 0) {
            return contains(data, root.getRight());
        }

        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        ArrayList<T> output = new ArrayList<>();

        preorder(root, output);

        return output;
    }

    /**
     * Helper method to get the preorder of the AVL
     *
     * @param root the root being added to the output
     * @param output the list that stores the AVL's elements
     */
    private void preorder(AVLNode<T> root, List<T> output) {
        if (root != null) {
            output.add(root.getData());
            preorder(root.getLeft(), output);
            preorder(root.getRight(), output);
        }

    }

    @Override
    public List<T> postorder() {
        ArrayList<T> output = new ArrayList<>();
        postorder(root, output);
        return output;
    }

    /**
     * Helper method to get the postorder of the AVL
     *
     * @param root the root being added to the output
     * @param output the list that stores the AVL's elements
     */
    private void postorder(AVLNode<T> root, List<T> output) {
        if (root != null) {
            postorder(root.getLeft(), output);
            postorder(root.getRight(), output);
            output.add(root.getData());
        }
    }

    @Override
    public List<T> inorder() {
        ArrayList<T> output = new ArrayList<>();
        inorder(root, output);
        return output;
    }

    /**
     * Helper method to get the inorder of the AVL
     *
     * @param root the root being added to the output
     * @param output the list that stores the AVL's elements
     */
    private void inorder(AVLNode<T> root, List<T> output) {
        if (root != null) {
            inorder(root.getLeft(), output);
            output.add(root.getData());
            inorder(root.getRight(), output);
        }
    }

    @Override
    public List<T> levelorder() {
        ArrayList<T> output = new ArrayList<T>();
        if (root != null) {
            levelorder(root, output);
        }

        return output;
    }

    /**
     * Helper method to get the levelorder of the AVL
     *
     * @param node the node being added to the output
     * @param output the list that stores the AVL's elements
     * @return list of the elements in the AVL
     */
    private List<T> levelorder(
            AVLNode<T> node, ArrayList<T> output) {
        Queue<AVLNode<T>> queue = new LinkedList<>();

        queue.add(node);

        while (!queue.isEmpty()) {
            AVLNode<T> current = queue.poll();
            output.add(current.getData());
            if (current.getLeft() != null) {
                queue.add(current.getLeft());
            }

            if (current.getRight() != null) {
                queue.add(current.getRight());
            }

        }

        return output;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }


    /**
     * Compares two AVLs and checks to see if the trees are the same.  If
     * the trees have the same data in a different arrangement, this method
     * should return false.  This will only return true if the tree is in the
     * exact same arrangement as the other tree.
     *
     * You may assume that you won't get an AVL with a different generic type.
     * For example, if this AVL holds Strings, then you will not get as an input
     * an AVL that holds Integers.
     *
     * Be sure to also implement the other general checks that .equals() should
     * check as well.
     *
     * @param other the Object we are comparing this AVL to
     * @return true if other is equal to this AVL, false otherwise.
     */
    public boolean equals(Object other) {
        if (!(other instanceof AVL)) {
            return false;
        }
        if (this == other) {
            return true;
        }

        AVL<T> that = (AVL<T>) other;

        return equals(this.root, that.root);

    }

    /**
     * Helper method to check equality in the AVL
     *
     * @param thisRoot the root in this AVL
     * @param thatRoot the root in the other AVL
     * @return boolean of whether the trees are equal
     */
    private boolean equals(AVLNode<T> thisRoot, AVLNode<T> thatRoot) {
        boolean result1 = false;
        boolean result2 = false;

        if (thisRoot == null && thatRoot == null) {
            return true;
        }

        if (thisRoot == null && thatRoot != null) {
            return false;
        }

        if (thisRoot != null && thatRoot == null) {
            return false;
        }

        if (!(thisRoot == null && thatRoot == null)) {
            if (thisRoot.getData().equals(thatRoot.getData())) {
                result1 = equals(thisRoot.getLeft(), thatRoot.getLeft());
                result2 = equals(thisRoot.getRight(), thatRoot.getRight());
            } else {
                return false;
            }
        }

        if (result1 && result2) {
            return true;
        }

        return false;
    }

    @Override
    public AVLNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
