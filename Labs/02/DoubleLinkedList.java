/*
 * Copyright (c) 2018 Joshua Nelson
 *
 *  This program is licensed under the GNU General Public License.
 *  Essentially, you may modify, distribute, and copy this work,
 *  but you must preserve this copyright notice and you MUST
 *  make any changes available AS SOURCE CODE to the end users.
 *
 *  Details available here:
 *  https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * Generic doubly linked list. Contains many helpful methods.
 */

import java.util.Iterator; // allows foreach loops

public class DoubleLinkedList<T> implements Iterable<T> {
	ListNode head;
	private transient ListNode current; // transient: don't save to disk when dumping class

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(); // StringBuilders are more efficient than Strings in loops
		for (T t: this) sb.append(t).append('\n');
		return sb.toString();
	}

	DoubleLinkedList() {
		head = new ListNode();
		current = head;
	}

	@SafeVarargs
	DoubleLinkedList(T ... data) {
		current = head = new ListNode(); // dual assignment: current points to head
		for (T t : data) append(t);
		goToStart();
	}

	DoubleLinkedList(Iterable<T> data) {
		current = head = new ListNode(); // dual assignment: current points to head
		for (T t : data) append(t);
		goToStart();
	}

	void goToStart() { current = head; }

	void goToNext() {
		if (current == null) current = head; // edge case, perhaps current node was deleted?
		else if (current.nextLink != null) current = current.nextLink; // if at end of list, do nothing
	}

	void goToPrev() {
		if (current == null || current.prevLink == null) return; // not on a node or at start
		current = current.prevLink; // yay for double links
	}

	/**
	 * Go to the first occurence of <code>data</code> in list
	 * @param data Data to search for
	 */
	void goTo(T data) {
		current = head;
		while (current != null && !current.data.equals(data)) { goToNext(); }
	}

	void setDataAtCurrent(T s) {
		if (s != null) { current.data = s; }
	}

	/**
	 * Delete the first occurence of <code>t</code> in list
	 * @param data Data point to delete
	 */
	void delete(T data) {
		if (this.inList(data)) { goTo(data); deleteCurrentNode(); }
	}

	/**
	 * Visible for testing
	 * @return data at current ListNode
	 */
	T getDataAtCurrent() { return current.data;	}

	boolean inList(T data) {
		for (T t : this) if (t.equals(data)) return true;
		return false;
	}

	/**
	 * Inserts an arbitrary number of data points at the first empty node in the list.
	 * If no nodes are empty, creates a new node at the end of the list.
	 */
	@SafeVarargs // compiler complains otherwise
	final void append(T ... data) {
		if (data == null || data.length == 0) return;

		ListNode temp = head;
		for (T t : data) {
			// Go to first empty node
			while (temp.data != null && temp.nextLink != null) {
				temp = temp.nextLink;
			}
			if (temp.data == null) temp.data = t;  // empty node in middle
			else { // temp.nextLink == null (by while condition above); end of list
				temp.nextLink = new ListNode(temp, t);
				temp = temp.nextLink;
			}
		}
	}

	/**
	 * Inserts one or more ListNodes after current node
	 * @param data list of values to add
	 */
	@SafeVarargs
	final void insert(T ... data) {
		if (data == null || data.length == 0) return;
		if (current == null) current = head;
		for (T t : data) {
			current.nextLink = new ListNode(current, t, current.nextLink);
			// set prevlink if next is not null
			if (current.nextLink.nextLink != null) current.nextLink.nextLink.prevLink = current.nextLink;
			goToNext();
		}
	}

	 // Don't even ask
	void deleteCurrentNode() {
		if (current == head) {
			if (head == null) return;
			if (head.data != null) {
				head.data = null; // head is final; cannot be deleted
				goToNext();
			}
		} else if (current.nextLink != null) {
			current.prevLink.nextLink = current.nextLink;
			current.nextLink.prevLink = current.prevLink;
			current = current.prevLink;
		} else { // end of list
			current.prevLink.nextLink = null; // hooray for garbage collectors!
			current = current.prevLink;
		}
	}

	// only here because it's a requirement
	final void showList() {
		StringBuilder sb = new StringBuilder("Printing list.\n");
		int i = 0;
		for (T t : this) sb.append(String.format("%d. %s%n", ++i, t));
		System.out.print(sb);
	}

	class ListNode {
		T data;
		ListNode nextLink;
		ListNode prevLink;

		@Override
		public String toString() { return data.toString(); }

		ListNode() { // strongly not recommended
			prevLink = head;
			data = null;
			nextLink = null;
		}

		ListNode(ListNode previous) {
			this.prevLink = previous;
			data = null;
			nextLink = null;
		}

		ListNode(ListNode previous, T data) {
			this.prevLink = previous;
			this.data = data;
			nextLink = null;
		}

		ListNode(ListNode previous, T data, ListNode nextLink) {
			this.data = data;
			this.nextLink = nextLink;
			this.prevLink = previous;
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private ListNode temp = null;

			public boolean hasNext() {
				// ternary: <condition> ? <command if true> : <command if false>
				ListNode next = (temp == null ? head : temp.nextLink);
				return next != null && next.data != null;
			}

			public T next() {
				temp = (temp == null ? head : temp.nextLink);
				return temp.data;
			}
			// need to test this
			public void remove() { deleteCurrentNode(); }
		};
	}
}
