import java.util.Iterator;

public class ToDoList<T> implements Iterable<T> {
	private final ListNode head;
	private transient ListNode current;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (T t: this) { sb.append(t).append('\n'); }
		return sb.toString();
	}

	ToDoList() {
		head = new ListNode();
		current = head;
	}

	@SafeVarargs
	ToDoList(T ... data) {
		head = new ListNode();
		current = head;
		for (T t : data) {
			setData(t);
			current.link = new ListNode(current);
			goToNext();
		}
		goToNext();
		deleteCurrent(); // why is this here?
		current = head;
	}

	private void goToNext() {
		if (current == null) current = head;
		else if (current.link != null) {
			current = current.link;
		}
	}

	void goTo(T data) {
		current = head;
		while (current != null && !current.data.equals(data)) { goToNext(); }
	}

	void setData(T s) {
		if (s != null) { current.data = s; }
	}

	void addItem() {
		ListNode temp = current;
		while (temp.link != null) {
			temp = temp.link;
		}
		temp.link = new ListNode(temp);
	}

	void insert(T s) {
		current.link = new ListNode(s, current.link, current);
		current.link.link.previous = current.link;
	}

	void deleteCurrent() {
		if (current.link != null) {
			current.previous.link = current.link;
			current.link.previous = current.previous;
		} else {
			current.previous.link = null; // hooray for garbage collectors!
		}
		current = current.previous;
	}

	// only here because it's a requirement
	final void showList() {
		StringBuilder sb = new StringBuilder("Printing list.\n");
		int i = 0;
		for (T t : this) {
			sb.append(String.format("%d. %s%n", ++i, t));
		}
		System.out.print(sb);
	}

	private class ListNode {
		private T data;
		private ListNode link;
		private ListNode previous;
	
		@Override
		public String toString() {
			return data.toString();
		}
	
		protected ListNode() {
			previous = head; // strongly not recommended
		}
	
		ListNode(ListNode p) {
			previous = p;
		}
	
		ListNode(T d, ListNode l, ListNode p) {
			data = d;
			link = l;
			previous = p;
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			ListNode temp = null;
			@Override
			public boolean hasNext() {
				if (temp == null) return head != null;
				return temp.link != null;
			}

			@Override
			public T next() {
				if (temp == null) { temp = head; }
				else { temp = temp.link; }
				return temp.data;
			}

			@Override
			public void remove() {
				if (temp.link != null) {
					temp.previous.link = temp.link;
					temp.link.previous = temp.previous;
				} else {
					temp.previous.link = null; // hooray for garbage collectors!
				}
				temp = temp.link;
			}
		};
	}
}
