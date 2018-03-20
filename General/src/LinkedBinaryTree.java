package src;

import java.util.function.Consumer;

public class LinkedBinaryTree<T extends Comparable<T>> implements BinaryTree<T> {
    private Node root;

    public void clear() {
        root = null;
    }

    public boolean contains(T data) {
        return nodeOf(data) != null;
    }

    public void insert(T data) {
        if (root == null) {
            root = new Node(data);
        } else insert(root, data);
    }

    public void remove(T obj) {
        remove(nodeOf(obj)); // first occurrence
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        forEach(t -> {
						result.append(t);
						result.append(' ');
				});
        return result.deleteCharAt(result.length() - 1).toString();
    }

    public void printPreorder () {
        StringBuilder result = new StringBuilder();
        forEach(t -> {
            result.append(t);
            result.append(' ');
        }, true);
        System.out.println(result);
    }

    public int depth(T obj) {
        return depth(obj, root, 0);
    }

    public void forEach(Consumer<? super T> action) {
            forEach(action, false);
    }

    public void forEach(Consumer<? super T> action, boolean preorder) {
        forEach(action, root, preorder);
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

    private void forEach(Consumer<? super T> action, Node start, boolean preorder) {
        if (start == null) return;
        if (preorder) {
            action.accept(start.data);
            forEach(action, start.left, true);
        } else {
            forEach(action, start.left, false);
            action.accept(start.data);
        }
        forEach(action, start.right, preorder);
    }

    private Node nodeOf(T data) {
        return nodeOf(root, data);
    }

    private void remove(Node node) {
        if (node == null) return;

        if (node.left == null && node.right == null) node = null; // at a leaf
        else if (node.left != null && node.right != null) { // two children
            Node smallest = smallest(node.right); // right path has equal values
            node.data = smallest.data;
            remove(smallest);
        } else { // we know exactly one branch is null
            node = (node.left != null ? node.left : node.right);
        }
    }

    private Node smallest(Node start) {
        if (start.left == null) return start;
        return smallest(start.left);
    }

    private void insert(Node start, T data) { // making this recursive allows rotations to be a lot easier
        if (data.compareTo(start.data) <= 0) { // data <= start.data
            if (start.left == null) start.left = new Node(data);
            else insert(start.left, data);
        } else if (start.right == null) {
            start.right = new Node(data);
        } else insert(start.right, data);
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

    private class Node {
        private T data;
        private Node left;
        private Node right;

        Node(T data) {
            this.data = data;
        }

        public String toString() {
        	return data.toString();
				}
    }
}
