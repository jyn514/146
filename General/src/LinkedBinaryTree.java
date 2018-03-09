package src;

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

    private Node insert(Node node, T data) { // making this recursive allows rotations to be a lot easier
        if (node == null) { // leaf
            node = new Node(data);
        } else if (data.compareTo(node.data) <= 0) { // data < node.data
            node.left = insert(node.left, data);
        } else {
            node.right = insert(node.right, data);
        }
        return node;
    }

    private void print(Node node) {
        if (node == null) return;
        print(node.left);
				System.out.println(node);
        print(node.right);
    }

	/**
     * Worst case: O(n) (unbalanced tree)
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
