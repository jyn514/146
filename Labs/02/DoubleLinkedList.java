/*
 * Copyright (c) 2018 Joshua Nelson
 *
 * This program is licensed under the GNU General Public License.
 * Essentially, you may modify, distribute, and copy this work,
 * but you must preserve this copyright notice and you MUST
 * make any changes available AS SOURCE CODE to the end users.
 *
 * Details available here:
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * Generic doubly linked list. Contains many helpful methods.
 */

import java.util.Iterator;

public class DoubleLinkedList<T> implements Iterable<T> {
	private final ListNode head;
	private transient ListNode current;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (T t: this) sb.append(t).append('\n');
		return sb.toString();
	}

	DoubleLinkedList() {
		head = new ListNode();
		current = head;
	}

	@SafeVarargs
	DoubleLinkedList(T ... data) {
		head = new ListNode();
		current = head;
		for (T t : data) append(t);
		current = head;
	}

	void goToStart() { current = head; }

	void goToNext() {
		if (current == null) current = head;
		else if (current.nextLink != null) current = current.nextLink;
	}

	void goToPrev() {
		if (current == null || current.prevLink == null) return;
		current = current.prevLink;
	}

	void goTo(T data) {
		current = head;
		while (current != null && !current.data.equals(data)) { goToNext(); }
	}

	void setDataAtCurrent(T s) {
		if (s != null) { current.data = s; }
	}

	void delete(T t) {
		if (this.inList(t)) { goTo(t); deleteCurrentNode(); }
	}

	/**
	 * Visible for testing
	 * @return data at current ListNode
	 */
	T getDataAtCurrent() {
		return current.data;
	}

	boolean inList(T data) {
		for (T t : this) if (t.equals(data)) return true;
		return false;
	}

	@SafeVarargs
	final void append(T... data) {
		if (data == null || data.length == 0) return;

		ListNode temp = head;
		for (T t : data) {
			// First empty node
			while (temp.data != null && temp.nextLink != null) {
				temp = temp.nextLink;
			}
			if (temp.data == null) temp.data = t;
			else { // temp.nextLink == null; end of list
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
	final void insert(T... data) {
		if (data == null || data.length == 0) return;
		for (T t : data) {
			current.nextLink = new ListNode(current, t, current.nextLink);
			current.nextLink.nextLink.prevLink = current.nextLink;
			goToNext();
		}
	}

	/**
	 * Don't even ask
	 */
	void deleteCurrentNode() {
		if (current == head) {
			if (head == null) return;
			if (head.data != null) {
				head.data = null;
				goToNext();
			}
		} else if (current.nextLink != null) {
			current.prevLink.nextLink = current.nextLink;
			current.nextLink.prevLink = current.prevLink;
			current = current.prevLink;
		} else {
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

	private class ListNode {
		private T data;
		private ListNode nextLink;
		private ListNode prevLink;
	
		@Override
		public String toString() {
			return data.toString();
		}
	
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
				ListNode next = temp == null ? head : temp.nextLink;
				return next != null && next.data != null;
			}

			public T next() {
				temp = temp == null ? head : temp.nextLink;
				return temp.data;
			}

			public void remove() {
				deleteCurrentNode();
			}
		};
	}
}
