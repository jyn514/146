/*
  Copyright © (2018) Joshua Nelson
  Licensed under the GNU Public License
  Essentially, you may modify, copy, and distribute this code frequently,
  but you must preserve this copyright notice and make any changes available as source code to all users.
  Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package src;

/**
 * Rules:
 * <ol><li>All nodes either red or black</li>
 * 		 <li>Root is black</li>
 * 		 <li>Leaves are black</li>
 * 		 <li>If node is red, children must be black</li>
 * 		 <li>Any path from a node to leaf must have same number of blacks</li>
 * </ol>
 *
 * <p>From this, we gain the property that the path
 * from root to farthest leaf is no more than twice that
 * from root to nearest leaf => balanced </p>
 * @param <T> Type of Object stored in tree
 */
public class RedBlackTree<T extends Comparable<T>> extends LinkedBinaryTree<T> implements BinaryTree<T> {

	// Booleans: the poor man's enums
	private static final boolean RED = true, BLACK = false;

	@Override
	public void add(T data) {
		if (data == null)  return; // all nodes must therefore have non-null data
		if (root == null) {
			root = new Node(data);
		} else add(root, data);
		insertBalance(nodeOf(data));	// recolor tree
	}

	private void insertBalance(Node node) {
		Node uncle;
		node.color = RED; // default unless changed
		if (node == root) {
			// required by rule 1
			node.color = BLACK;  // does not violate rules: all paths have same number of nodes
			// tree is still valid
		} else if (((Node) node.parent).color == BLACK) { // from now on we can assume 1. parent is red 2. parent is not root
		} else if ((uncle = (Node) sibling(node.parent)) != null && uncle.color == RED) {
			((Node) node.parent).color = BLACK;
			uncle.color = BLACK;
			((Node) uncle.parent).color = RED; // uncle.parent == node.grandfather == node.parent.parent
			insertBalance((Node) uncle.parent);
		} else { // parent is red, uncle is black
			// check that node is on inside of tree; if so, rotate to outside and use former parent
			LinkedBinaryTree<T>.Node grandparent = node.parent.parent;
			if (grandparent.left.right == node) {
				rotateLeft(node.parent);
				node = (Node) node.left;
			} else if (grandparent.right.left == node) {
				rotateRight(node.parent);
				node = (Node) node.right;
			}

			if (node == node.parent.left) rotateRight(node.parent.parent);
			else rotateLeft(node.parent.parent);
			((Node) node.parent).color = BLACK;
			((Node) node.parent.parent).color = RED;
		}
	}

	@Override
	public void delete(T obj) {
		delete(nodeOf(obj));
	}

	private void delete(Node node) {
		if (node == null) return;
		if (node.left != null && node.right != null) { // two children
			Node smallest = (Node) least(node.right);
			node.data = smallest.data;
			delete(smallest);
			return;
		}
		Node child = (Node)(node.left != null ? node.left : node.right); // can be null, but chooses non-null if available
		if (child != null) moveUp(child); // note we do NOT put node back in the tree - this is not the same as a swap

		if (node.color == RED) { // since red, doesn't affect balance
			assert node.left == null && node.right == null: "must have no children by rule 5";
		} else if (child == null) { // black leaf node
			if (node != root) balanceMultipleBlack((Node) sibling(node)); // root is simple case; set to null at end
		} else {
			assert child.color == RED : "true by rule 5 - remember that node must be black";
			child.color = BLACK;
		}
		// equivalent of free(node) in C
		if (node == root) 								 root = null;
		else if (node.parent.left == node) node.parent.left = null;
		else															 node.parent.right = null;
	}

	private void balanceMultipleBlack(Node sibling) {
		assert sibling != null : "must be non-null by rule 5";

		if (sibling.color == RED) {
			sibling.color = BLACK;
			((Node) (sibling.parent)).color = RED;
			// note this comes after color change so that sibling.parent has expected result
			if (sibling.parent.right == sibling) rotateLeft(sibling);
			else rotateRight(sibling);
		}

		if (((Node) (sibling.parent)).color == BLACK
		    && sibling.color == BLACK
		    && ((Node) (sibling.left)).color == BLACK
		    && ((Node) (sibling.right)).color == BLACK) {
			sibling.color = RED;
			balanceMultipleBlack((Node) sibling.parent);
		} else if (((Node) (sibling.parent)).color == RED
		           && sibling.color == BLACK
		           && ((Node) (sibling.left)).color == BLACK
		           && ((Node) (sibling.right)).color == BLACK) {
			sibling.color = RED;
			((Node) (sibling.parent)).color = BLACK;
		} else {
			assert sibling.color != RED: "Must be true by start of method (always reassigned)";
			if (sibling.parent.right == sibling
			    && ((Node) (sibling.right)).color == BLACK) {
				assert ((Node) (sibling.left)).color == RED: "Wikipedia told me so";
				sibling.color = RED;
				((Node) (sibling.left)).color = BLACK;
				rotateRight(sibling.left);
			} else if (sibling.parent.left == sibling
			           && ((Node) (sibling.left)).color == BLACK) {
				assert ((Node) (sibling.right)).color == RED: "Wikipedia told me so";
				sibling.color = RED;
				((Node) (sibling.right)).color = BLACK;
				rotateLeft(sibling.right);
			}
			sibling.color = ((Node) (sibling.parent)).color;
			((Node) (sibling.parent)).color = BLACK;
			if (sibling == sibling.parent.right) {
				((Node) (sibling.right)).color = BLACK;
				rotateLeft(sibling);
			} else {
				((Node) (sibling.left)).color = BLACK;
				rotateRight(sibling);
			}
		}

	}

	private void add(LinkedBinaryTree<T>.Node start, T data) { // duplicated so that only correct nodes added
		if (data.compareTo(start.data) <= 0) { // data <= start.data
			if (start.left == null) start.left = new Node(data, start);
			else add(start.left, data);
		} else if (start.right == null) {
			start.right = new Node(data, start);
		} else add(start.right, data);
	}

	protected Node nodeOf(T obj) {
		return (Node) super.nodeOf(obj);
	}

	private LinkedBinaryTree<T>.Node sibling(LinkedBinaryTree<T>.Node node) {
		if (node == root) return null;
		return node.parent.left == node ? node.parent.right : node.parent.left;
	}

	/**
	 * <pre>
	 *  a
	 *   \
	 *    b       b
	 *     \ =>  / \
	 *      c   a   c
	 * </pre>
	 * @param a Node a in above diagram
	 */
	private void rotateLeft(LinkedBinaryTree<T>.Node a) {
		LinkedBinaryTree<T>.Node b = a.right;

		a.right = b.left;
		if (a.right != null) a.right.parent = a;

		moveUp(b);

		b.left = a;
		a.parent = b;
	}

	/**
	 * <pre>
	 *     a      b
	 *    / \    / \
	 *   b  i   c  a
	 *  / \  =>   / \
	 * c  1      1  i
	 * </pre>
	 * @param a Node a in above diagram
	 */
	private void rotateRight(LinkedBinaryTree<T>.Node a) {
		LinkedBinaryTree<T>.Node b = a.left;

		a.left = b.right; // tree 1
		if (a.left != null) a.left.parent = a;

		moveUp(b);

		b.right = a;
		a.parent = b;
	}

	private void moveUp(LinkedBinaryTree<T>.Node start) {
		if (start.parent.parent != null) {
			if (start.parent.parent.left == start.parent) {
				start.parent.parent.left = start;
			} else {
				start.parent.parent.right = start;
			}
		} else {
			root = (Node) start;
		}
		start.parent = start.parent.parent;
	}

	private class Node extends LinkedBinaryTree<T>.Node {
		boolean color;

		Node(T data) {
			super(data);
			this.color = RED;
		}

		Node(T data, LinkedBinaryTree<T>.Node parent) {
			this(data);
			this.parent = parent;
		}
	}
}
