import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.NoSuchElementException;
import java.util.LinkedList;

public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST.
     * YOU DO NOT NEED TO IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot instantiate with a "
                    + "null collection");
        }

        for (T item : data) {
            add(item);
        }
    }

    @Override
    public void add(T data) {

        if (data == null) {
            throw new IllegalArgumentException("Cannot add a null object");
        }

            root = add(data, root);
            size++;

    }

    /**
     * Helper method to add an element to the BST. Called recursively until an
     * appropriate spot is found for the node
     *
     * @return Node to be added to the BST
     * @param data the data to be added
     * @param node the current node that is being indexed in the BST
     */
    private BSTNode<T> add(T data, BSTNode<T> node) {
        //Check to see if you have reached the end of the tree
        if (node == null) {
            return new BSTNode<T>(data);
        }

        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(data, node.getLeft()));
            return node;
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(data, node.getRight()));
            return node;
        } else {
            size--;
            //decrement size since it will be incremented at the end
            // of first recursive call
            return node;
        }
    }

    @Override
    public T remove(T data) {


        if (data == null) {
            throw new IllegalArgumentException("Cannot remove a null element");
        }

        BSTNode<T> removedData = new BSTNode<>(null);
        root = remove(data, root, removedData);
        size--;

        return removedData.getData();
    }


    /**
     * Helper method to add remove an element from a BST. Called recursively
     * until the end condition (when data is equal to data in node) is reached
     *
     * @return Node to be added to the BST after removal
     * @param data the data to be removed
     * @param node the current node that is being indexed in the BST
     * @param removedData a variable that stores the value of node when
     *                    node.getData().equals(data)
     * @throws NoSuchElementException when a null node is reached and nothing
     *         has been removed.recurse
     */
    private BSTNode<T> remove(T data, BSTNode<T> node, BSTNode<T> removedData) {

        if (node == null) {
            throw new NoSuchElementException("Element does not exist in the "
                    + "BST");
        }

        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(remove(data, node.getLeft(), removedData));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(remove(data, node.getRight(), removedData));
        } else {

            //set removedData to the found node's data
            if (removedData != null) {
                removedData.setData(node.getData());
            }

            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }

            //Right MUST be != null now
            if (node.getLeft() == null) {
                return node.getRight();
            }
            //Left MUST be != null now
            if (node.getRight() == null) {
                return node.getLeft();
            }
            successor(node, node.getRight());
/*            node.setData(getSuccessor(node.getRight()));

            //Pass in a null BSTNode since the item has already been found,
            //We don't want to reassign anything
            node.setRight(remove(node.getData(), node.getRight(), null));*/
        }

        return node;
    }

    private void successor(BSTNode<T> root, BSTNode<T> successor) {
        BSTNode<T> parent = root;

        while (successor.getLeft() != null) {
            parent = successor;
            successor = successor.getLeft();
        }
        root.setData(successor.getData());
        if (root != parent) {
            parent.setLeft(successor.getRight());
        }

        if (root == parent) {
            parent.setRight(successor.getRight());
        }
    }

    /**
     * Helper method to find successor when the node being removed has two
     * children. Called recursively to account for children of the successor.
     *
     * @return data contained in the successor node to be added in place of
     * the removed node
     * @param root The node being indexed, starts with the node being removed
     */
    private T getSuccessor(BSTNode<T> root) {
        BSTNode<T> successor = root;

        while (successor.getLeft() != null) {
            successor = successor.getLeft();
        }

        return successor.getData();
    }


    @Override
    public T get(T data) {
        T result = null;

        if (data == null) {
            throw new IllegalArgumentException("Cannot search for a null "
                    + "entry");
        }

        result = get(data, root);

        if (result == null) {
            throw new NoSuchElementException("Element does not exist in the "
                    + "BST");
        }

        return result;
    }

    /**
     * Helper method to recursively get the data from the BST, starting with
     * the root.
     *
     * @return data found in the BST
     * @param data data to be searched for in  the BST
     * @param node The node being indexed, starts with the root
     * @throws NoSuchElementException thrown when the data is not found in
     * the tree
     */
    private T get(T data, BSTNode<T> node) {
        T result = null;

        if (node == null) {
            return null;
        }

        if (data.equals(node.getData())) {
            result = node.getData();
        } else if (data.compareTo(node.getData()) < 0) {
            result = get(data, node.getLeft());
        } else {
            result = get(data, node.getRight());
        }

        return result;

    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot check for a null item");
        }

        boolean result = false;

        result = contains(data, root);

        return result;

    }

    /**
     * Helper method to check if an item is in the tree
     *
     * @return output containing the data of the BST
     * @param node the current node being indexed, starts with the root and
     *             called recursively
     * @param data the data that is being searched for in the BST
     */
    public boolean contains(T data, BSTNode<T> node) {
        boolean result = false;

        if (node == null) {
            return false;
        }

        if (node.getData().equals(data)) {
            return true;
        }

        if (data.compareTo(node.getData()) < 0) {
            result = contains(data, node.getLeft());
        } else if (data.compareTo(node.getData()) > 0) {
            result = contains(data, node.getRight());
        }

        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        ArrayList<T> output = new ArrayList<T>();

        getPreOrder(root, output);
        return output;
    }

    /**
     * Helper method add the pre-order data to the output
     *
     * @return output containing the data of the BST
     * @param node the current node being indexed, starts with the root and
     *             called recursively
     * @param output the List that stores the data of the BST
     */
    public ArrayList<T> getPreOrder(BSTNode<T> node, ArrayList<T> output) {
        if (node != null) {
            output.add(node.getData());
            getPreOrder(node.getLeft(), output);
            getPreOrder(node.getRight(), output);
        }
        return output;
    }

    @Override
    public List<T> postorder() {
        ArrayList<T> output = new ArrayList<>();

        getPostOrder(root, output);

        return output;
    }

    /**
     * Helper method add the post-order data to the output
     *
     * @return output containing the data of the BST
     * @param node the current node being indexed, starts with the root and
     *             called recursively
     * @param output the List that stores the data of the BST
     */
    private ArrayList<T> getPostOrder(BSTNode<T> node, ArrayList<T> output) {
        if (node != null) {
            getPostOrder(node.getLeft(), output);
            getPostOrder(node.getRight(), output);

            output.add(node.getData());
        }

        return output;
    }

    @Override
    public List<T> inorder() {
        ArrayList<T> output = new ArrayList<>();

        getInOrder(root, output);

        return output;
    }

    /**
     * Helper method get the in-order data to the output
     *
     * @return output containing the data of the BST
     * @param node the current node being indexed, starts with the root and
     *             called recursively
     * @param output the List that stores the data of the BST
     */
    private ArrayList<T> getInOrder(BSTNode<T> node, ArrayList<T> output) {


        if (node != null) {
            getInOrder(node.getLeft(), output);

            output.add(node.getData());
            getInOrder(node.getRight(), output);
        }

        return output;
    }


    @Override
    public List<T> levelorder() {
        ArrayList<T> output = new ArrayList<T>();
        if (root != null) {
            getLevelOrder(root, output);
        }

        return output;
    }

    /**
     * Helper method add the level-order data to the output
     *
     * @return output containing the data of the BST
     * @param node the current node being indexed, starts with the root and
     *             called recursively
     * @param output the List that stores the data of the BST
     */
    private ArrayList<T> getLevelOrder(BSTNode<T> node, ArrayList<T> output) {
        Queue<BSTNode<T>> queue = new LinkedList<>();

        queue.add(node);

        while (!queue.isEmpty()) {
            BSTNode<T> tempNode = queue.poll();
            output.add(tempNode.getData());

            if (tempNode.getLeft() != null) {
                queue.add(tempNode.getLeft());
            }

            if (tempNode.getRight() != null) {
                queue.add(tempNode.getRight());
            }
        }
        return output;
    }


    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {

        int treeHeight = findHeight(root);

        return treeHeight;
    }

    /**
     * Helper method find the height of the BST. Called recusrively to until
     * the bottom of the tree is found.
     *
     * @return the height of the tree from the current node to the bottom of
     * the tree
     *
     * @param node the current node being indexed, starts with the root and
     *             called recursively
     */
    private int findHeight(BSTNode<T> node) {
        if (node != null) {
            int heightLeft = findHeight(node.getLeft());
            int heightRight = findHeight(node.getRight());

            if (heightLeft > heightRight) {
                return heightLeft + 1;
            } else {
                return heightRight + 1;
            }
        }
        return -1;
    }

    /**
     * Compares two BSTs and checks to see if the trees are the same.  If
     * the trees have the same data in a different arrangement, this method
     * should return false.  This will only return true if the tree is in the
     * exact same arrangement as the other tree.
     *
     * You may assume that you won't get a BST with a different generic type.
     * For example, if this BST holds Strings, then you will not get as an input
     * a BST that holds Integers.
     *
     * Be sure to also implement the other general checks that .equals() should
     * check as well.
     *
     * Should have a running time of O(n).
     *
     * @param other the Object we are comparing this BST to
     * @return true if other is equal to this BST, false otherwise.
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        boolean result = false;

        if (other instanceof BST) {
            BST<T> that = (BST<T>) other;

            result = equals(this.root, that.root);
        }
        return result;
    }

    /**
     * Helper method to check for equality between two BSTs
     *
     * @return boolean of whether the BSTs from a given root are equal
     * @param thisNode the node that is being checked
     * @param thatNode The node that this is being compared to
     */
    private boolean equals(BSTNode<T> thisNode, BSTNode<T> thatNode) {
        boolean result1 = false;
        boolean result2 = false;

        if (thisNode == null && thatNode == null) {
            return true;
        }

        if (thisNode == null && thatNode != null) {
            return false;
        }

        if (thisNode != null && thatNode == null) {
            return false;
        }


        if (!(thisNode == null && thatNode == null)) {
            if (thisNode.getData().equals(thatNode.getData())) {
                result1 = equals(thisNode.getLeft(), thatNode.getLeft());
                result2 = equals(thisNode.getRight(), thatNode.getRight());
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
    public BSTNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
