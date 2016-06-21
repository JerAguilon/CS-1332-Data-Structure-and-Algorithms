public class LinkedList {
    private Node head;

    public LinkedList() {
        head = null;
    }

    public void addToFront(int data) {
        head = new Node(data, head);
    }

    public void addtoBack(int data) {
        Node newNode = new Node(data, null);

        Node current = head;

        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
    }

    @Override
    public String toString() {
        if (head != null) {
            return head.toString();
        } else {
            return "empty";
        }
    }

    private class Node {
        private int data;
        private Node next;

        private Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            if (next != null) {
                return Integer.toString(data) + ", " + next.toString();
            } else {
                return Integer.toString(data);
            }
        }
    }



    public static void main(String[] args) {
        LinkedList ll = new LinkedList();
        System.out.println(ll);
        ll.addToFront(12);

        ll.addToFront(124);
        ll.addToFront(1);
        ll.addtoBack(13);
        System.out.println(ll);

    }
}