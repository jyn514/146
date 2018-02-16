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
 * Generic circular doubly linked list. Contains many helpful methods.
 */

import java.util.Iterator;

public class CircularDoubleLinkedList<T> implements Iterable<T> {
	private ListNode head;
	transient ListNode current;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (T t: this) sb.append(t).append('\n');
		return sb.toString();
	}

	CircularDoubleLinkedList() {
		current = head = new ListNode();
	}

	@SafeVarargs
	CircularDoubleLinkedList(T ... data) {
		current = head = new ListNode();
		for (T t : data) append(t);
		goToStart();
	}

	void goToStart() { current = head; }

	void goToNext() { current = current.nextLink; }

	void goToPrev() { current = current.prevLink; }

	void goTo(T data) {
		goToStart();
		for (T t : this) {
			if (t.equals(data)) break;
			goToNext();
		}
	}

	void setDataAtCurrent(T  s) {
		if (s != null) current.data = s;
	}

	void delete(T t) {
		if (this.inList(t)) goTo(t); deleteCurrentNode();
	}

	T getDataAtCurrent() { return current.data; }

	boolean inList(T data) {
		for (T t : this) if (t.equals(data)) return true;
		return false;
	}

	@SafeVarargs
	final void append(T ... data) {
		if (data == null || data.length == 0) return;
		goToStart();
		goToPrev(); // go to tail
		for (T t : data) {
			insertAfterCurrent(t);
			goToNext();
		}
		goToStart();
	}

	/**
	 * Inserts one or more ListNodes after current node
	 * Guarenteed to keep current node the same
	 * @param data list of values to add
	 */
	@SafeVarargs
	final void insertAfterCurrent(T ... data) {
		if (data == null || data.length == 0) return;
		if (current == null) current = head;
		ListNode original = current;
		for (T t : data) {
			if (current.data == null) current.data = t;
			else {
				current.nextLink = new ListNode(current, t, current.nextLink);
				current.nextLink.nextLink.prevLink = current.nextLink;
				goToNext();
			}
		}
		current = original;
	}

	 void deleteCurrentNode() {
		if (head == null || current == null) return;
		current.nextLink.prevLink = current.prevLink;
		current.prevLink.nextLink = current.nextLink;
		if (current == head) {
			head = head.nextLink;
		}
		goToNext();
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
		public String toString() {
			return data.toString();
		}

		ListNode() { // starts new list
			prevLink = nextLink = head = this;
		}

		ListNode(ListNode previous) {
			prevLink = previous;
			this.nextLink = head;
		}

		ListNode(ListNode previous, T data) {
			prevLink = previous;
			this.data = data;
			nextLink = head;
		}

		ListNode(ListNode previous, T data, ListNode nextLink) {
			this.prevLink = previous;
			this.data = data;
			this.nextLink = nextLink;
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			ListNode temp = head;
			boolean firstRun = true;

			public boolean hasNext() {
				return (firstRun || temp != head) && temp.data != null;
			}

			public T next() {
				T result = temp.data;
				temp = temp.nextLink; // checked later by hasNext
				firstRun = false;
				return result;
			}

			public void remove() {
				deleteCurrentNode();
			}
		};
	}
}
