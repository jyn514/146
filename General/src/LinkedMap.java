package src;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.function.Consumer;

public class LinkedMap<Key extends Comparable<Key>, Value> implements Map<Key, Value> {

    public Value get(Key key) {
        return nodeOf(key, root).data;
    }

    public int depth(Key obj) {
        return depth(obj, root, 0);
    }

    public boolean contains(Key key) {
        return nodeOf(key) != null;
    }

    public void put(Key key, Value data) {
        if (root == null) {
            root = new Node(key, data);
        } else put(key, data, root);
    }

    @Override
    public void add(Value data) throws NotImplementedException {
        throw new NotImplementedException();
    }

    /**
     * Deletes only first occurrence of <code>key</code>.
     * @param key Object to delete
     */
    public void delete(Key key) {
        delete(nodeOf(key));
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

    public final Value least() {
        return least(root).data;
    }

    public final Value greatest() {
        return greatest(root).data;
    }

    public final void forEach(Consumer<Node> action) {
        forEach(action, root, ORDER.ORDERED);
    }

    public final void forEach(Consumer<Node> action, ORDER order) {
        forEach(action, root, order);
    }

    protected Node root;

    protected Node nodeOf(Key key) {
        return nodeOf(key, root);
    }

    protected int maxDepth(Node node) { return maxDepth(node, 0); }

    private enum ORDER {
        PREORDER, ORDERED, POSTORDER
    }

    private int depth(Key key, Node start, int startDepth) {
        if (start == null) return startDepth - 1;
        if (key == start.key) return startDepth;
        int tmp;
        if ((tmp = depth(key, start.left, startDepth + 1)) != startDepth) {
            return tmp;
        } else if ((tmp = depth(key, start.right, startDepth + 1)) != startDepth) {
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

    private void put(Key key, Value data, Node start) {
        if (key.compareTo(start.key) == 0) {
            start.data = data;
        } else if (key.compareTo(start.key) < 0) { // data <= start.data
            if (start.left == null) start.left = new Node(key, data, start);
            else put(key, data, start.left);
        } else if (start.right == null) {
            start.right = new Node(key, data, start);
        } else put(key, data, start.right);
    }

    private void forEach(Consumer<? super Node> action, Node start, ORDER order) {
        if (start == null) return;
        if (order == ORDER.PREORDER) {
            action.accept(start);
            forEach(action, start.left, order);
            forEach(action, start.right, order);
        } else if (order == ORDER.ORDERED) {
            forEach(action, start.left, order);
            action.accept(start);
            forEach(action, start.right, order);
        } else { // postorder
            forEach(action, start.left, order);
            forEach(action, start.right, order);
            action.accept(start);
        }
    }

    protected Node least(Node start) {
        if (start.left == null) return start;
        return least(start.left);
    }

    protected Node greatest(Node start) {
        if (start.right == null) return start;
        return greatest(start.right);
    }

    protected void delete(Node start) {
        if (start == null) return;
        if (start.left == null && start.right == null) { // at a leaf
            if (start.parent.left == start) start.parent.left = null;
            else start.parent.right = null;
        }
        else if (start.left != null && start.right != null) { // two children
            Node smallest = least(start.right); // right path has equal values
            start.data = smallest.data;
            delete(smallest);
        } else { // we know exactly one branch is null
            Node next = start.left != null ? start.left : start.right;
            if (start.parent != null) {
							if (start.parent.left == start) start.parent.left = next;
							else start.parent.right = next;
						} else root = next;
        }
    }

    private int maxDepth (Node node, int current) {
        if (node == null) return current;
        return Math.max(maxDepth(node.left, current + 1), maxDepth(node.right, current + 1));
    }

    /**
     * Worst case: O(n) (simple linked list)
     * Average case: log_2(n)
     * @param startNode node to start search at
     * @param key key to find
     * @return whether the data is present in this branch of the tree
     */
    private Node nodeOf(Key key, Node startNode) {
        if (startNode == null || key.compareTo(startNode.key) == 0) return startNode;
        if (key.compareTo(startNode.key) < 0) return nodeOf(key, startNode.left);
        return nodeOf(key, startNode.right);
    }

    class Node {
        Key key;
        Value data;
        Node parent, left, right;

        Node(Key key, Value data) {
            this.data = data;
            this.data = data;
        }

        Node(Key key, Value data, Node parent) {
            this.data = data;
            this.key = key;
            this.parent = parent;
        }
    }
}