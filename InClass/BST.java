public class BST {
    private Node root;

    public void add(int data) {
        if (root == null) {
            root = new Node(data);
        } else {
            add(data, root);
        }
    }

    private void add(int data, Node node) {
        if (data < node.data) {
            if (node.left == null) {
                node.left = new Node(data);
            } else {
                add(data, node.left);
            }
        } else if (data > node.data) {
            if (node.right == null) {
                node.right = new Node(data);
            } else {
                add(data, node.right);
            }
        }
    }

    private void remove(int data) {
        if (root.data == data) {
            if (root.left == null && root.right == null) {
                root = null;
            } else if ((root.left == null && root.right != null)
                    || (root.left != null && root.right == null)) {
                if (root.left != null) {
                    root = root.left;
                } else {
                    root = root.right;
                }
            } else if (root.left != null && root.right != null) {

            }
        }
    }

    public void removeRecursive(int data) {

    }

    public Node remove(int data, Node node) {
        if (data == node.data) {
            //removal cases
        } else if (data < node.data) {
            node.left = remove(data, node.left);
        } else if (data > node.data) {
            node.right = remove(data, node.right);
        }

        return null;
    }


    private class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
        }
    }
}