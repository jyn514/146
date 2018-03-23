package src;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class AVLTree<T extends Comparable<T>> extends LinkedBinaryTree<T> implements BinaryTree<T> {

	@Override
	public void add(T obj) {
		super.add(obj);
		rotate(nodeOf(obj).parent);
	}

	@Override
	public void delete(T obj) {
		Node n = nodeOf(obj).parent;
		if (n != null) {
			super.delete(obj);
			rotate(n);
		}
	}

	/**
	 * The balance factor for the current node.
	 * Difference between the height of the right and left subtrees.
	 * If positive, right tree is heavier than left.
	 * If <code>Math.abs(balanceFactor(node)) == 2</code>, tree needs to be rebalanced.
	 * @return an integer in [-2, 2]
	 */
	private short balanceFactor(Node node) {
		if (node == null) return 0;
		return (short) (maxDepth(node.right) - maxDepth(node.left));
	}
	/**
	 * Note that unlike most recursive algorithms for trees, this one recurses upward towards the root,
	 * not downwards towards the leaves
	 * @param start starting node
	 */
	private void rotate(Node start) {
		if (start == null) return;
		short balance = balanceFactor(start);
		// if (Math.abs(balance) < 2) return; - need to rebalance parent

		if (balance == 2) {
			if (maxDepth(start.right.right) >= maxDepth(start.right.left)) {
				rotateLeft(start);
			} else {
				rotateRightLeft(start);
			}
		} else if (balance == -2) {
			if (maxDepth(start.left.left) >= maxDepth(start.left.right)) {
				rotateRight(start);
			} else {
				rotateLeftRight(start);
			}
		}

		if (start.parent != null) {
			rotate(start.parent);
		} else {
			root = start;
		}
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
	private void rotateLeft(Node a) {
		Node b = a.right;

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
	private void rotateRight(Node a) {
		Node b = a.left;

		a.left = b.right; // tree 1
		if (a.left != null) a.left.parent = a;

		moveUp(b);

		b.right = a;
		a.parent = b;
	}

	/**
	 * i means ignored subtrees<br>
	 * 1 and 2 represent arbitrary subtrees
	 * <pre>
	 *   x   =>   x     =>    y
	 *  / \      / \         /\
	 * i   z    i   y       x  z
	 *    / \      / \     /\  /\
	 *   y   i    1   z   i 1 2  i
	 *  / \          / \
	 * 1  2         2   i
	 * </pre>
	 * @param top x in above diagram
	 */
	private void rotateRightLeft(Node top) {
		rotateRight(top.right);
		rotateLeft(top);
	}

	/**
	 * i means ignored subtrees<br>
	 * 1 and 2 represent arbitrary subtrees
	 * <pre>
	 *     x   =>    x     =>   y
	 *    / \       / \        / \
	 *   z   i     y   i      z   x
	 *  / \       / \        /\  / \
	 * i   y     z   2      i 1 2   i
	 *    / \   / \
	 *   1  2  i  1
	 * </pre>
	 * @param top x in above diagram
	 */
	private void rotateLeftRight(Node top) {
		rotateLeft(top.left);
		rotateRight(top);
}

	private void moveUp(Node start) {
		if (start.parent.parent != null) {
			if (start.parent.parent.left == start.parent) {
				start.parent.parent.left = start;
			} else {
				start.parent.parent.right = start;
			}
		} else {
			root = start;
		}
		start.parent = start.parent.parent;
	}
}
