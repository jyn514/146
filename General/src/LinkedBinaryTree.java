package src;

import java.util.function.Consumer;

public class LinkedBinaryTree<T extends Comparable<T>> implements BinaryTree<T> {

    public int depth(T obj) {
        return depth(obj, root, 0);
    }

    public boolean contains(T data) {
        return nodeOf(data) != null;
    }

    public void add(T data) {
        if (root == null) {
            root = new Node(data);
        } else add(root, data);
    }

    /**
     * Deletes only first occurrence of <code>obj</code>.
     * @param obj Object to delete
     */
    public void delete(T obj) {
        delete(root, obj);
    }

    public void clear() {
        root = null;
    }

    public void printPreorder() {
        System.out.println(toString(ORDER.PREORDER));
    }

    public void printPostorder() {
        System.out.println(toString(ORDER.POSTORDER));
    }

    public String toString() {
        return toString(ORDER.ORDERED);
    }

    public T least() {
        return least(root).data;
    }

    public T greatest() {
        return greatest(root).data;
    }

    protected void forEach(Consumer<? super T> action) {
        forEach(action, root, ORDER.ORDERED);
    }

    protected void forEach(Consumer<? super T> action, ORDER order) {
        forEach(action, root, order);
    }

    protected Node root;

    protected Node nodeOf(T data) {
        return nodeOf(root, data);
    }

    protected int maxDepth(Node node) { return maxDepth(node, 0); }

    private enum ORDER {
        PREORDER, ORDERED, POSTORDER
    }

    private int depth(T obj, Node start, int startDepth) {
        if (start == null) return startDepth - 1;
        if (obj == start.data) return startDepth;
        int tmp;
        if ((tmp = depth(obj, start.left, startDepth + 1)) != startDepth) {
            return tmp;
        } else if ((tmp = depth(obj, start.right, startDepth + 1)) != startDepth) {
            return tmp;
        }
        return startDepth - 1;
    }

    private String toString(ORDER order) {
        StringBuilder result = new StringBuilder();
        forEach(t -> {
            result.append(t);
            result.append(' ');
        }, order);
        return result.deleteCharAt(result.length() - 1).toString();
    }

    private void add(Node start, T data) { // making this recursive allows rotations to be a lot easier
        if (data.compareTo(start.data) <= 0) { // data <= start.data
            if (start.left == null) start.left = new Node(data, start);
            else add(start.left, data);
        } else if (start.right == null) {
            start.right = new Node(data, start);
        } else add(start.right, data);
    }

    private void forEach(Consumer<? super T> action, Node start, ORDER order) {
        if (start == null) return;
        if (order == ORDER.PREORDER) {
            action.accept(start.data);
            forEach(action, start.left, order);
            forEach(action, start.right, order);
        } else if (order == ORDER.ORDERED) {
            forEach(action, start.left, order);
            action.accept(start.data);
            forEach(action, start.right, order);
        } else { // postorder
            forEach(action, start.left, order);
            forEach(action, start.right, order);
            action.accept(start.data);
        }
    }

    private Node least(Node start) {
        if (start.left == null) return start;
        return least(start.left);
    }

    private Node greatest(Node start) {
        if (start.right == null) return start;
        return greatest(start.right);
    }

    private void delete(Node start, T data) {
        if (start == null) return;
        if (start.left == null && start.right == null) start = null; // at a leaf
        else if (start.left != null && start.right != null) { // two children
            Node smallest = least(start.right); // right path has equal values
            start.data = smallest.data;
            delete(smallest, smallest.data);
        } else { // we know exactly one branch is null
            Node next = start.left != null ? start.left : start.right;
            if (start.parent != null) {
							if (start.parent.left == start) start.parent.left = next;
							else start.parent.right = next;
						}
        }
    }

    private int maxDepth (Node node, int current) {
        if (node == null) return current;
        return Math.max(maxDepth(node.left, current), maxDepth(node.right, current)) + current;
    }

    /**
     * Worst case: O(n) (simple linked list)
     * Average case: log_2(n)
     * @param startNode node to start search at
     * @param data data to find
     * @return whether the data is present in this branch of the tree
     */
    private Node nodeOf(Node startNode, T data) {
        if (startNode == null || data.compareTo(startNode.data) == 0) return startNode;
        if (data.compareTo(startNode.data) < 0) return nodeOf(startNode.left, data);
        return nodeOf(startNode.right, data);
    }

    class Node {
        T data;
        Node left, right, parent;

        Node(T data) {
            this.data = data;
        }
        Node(T data, Node parent) {
            this.data = data;
            this.parent = parent;
        }

        public String toString() {
        	return data.toString();
				}
    }
}