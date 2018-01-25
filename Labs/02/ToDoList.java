public class ToDoList<T> {
	private ListNode head, current;

	private class ListNode {
		T data;
		ListNode link;
		ListNode previous;

		ListNode() {
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

	ToDoList() {
		head = new ListNode();
		current = head;
	}

	ToDoList(T ... data) {
		head = new ListNode();
		current = head;
		for (T aData : data) {
			setData(aData); // sets current data
			current.link = new ListNode(current);
			goToNext();
		}
		goToNext();
		deleteCurrent();
		current = head;
	}

	void goToNext() {
		if (current.link != null) {
			current = current.link;
		}
	}

	private void goToPrev() {
		current = current.previous;
	}

	T getData() {
		return current.data;
	}

	void setData(T s) {
		if (s != null) {
			current.data = s;
		}
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
			current.previous.link = null;
		}
		goToPrev();
	}

	void showList() {
		System.out.println("Printing list");
		ListNode temp = head;
		int i = 0;
		while (temp.link != null) {
			System.out.printf("%d. %s%n", i + 1, temp.data);
			temp = temp.link;
			i++;
		}
		System.out.printf("%d. %s%n", i + 1, temp.data);
	}

}
