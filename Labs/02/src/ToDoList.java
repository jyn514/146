public class ToDoList {
	ListNode head, current;

	private class ListNode {
		String data;
		ListNode link;
		ListNode previous;

		ListNode() {
			previous = head; // strongly not recommended
		}

		ListNode(ListNode p) {
			previous = p;
		}

		ListNode(String d, ListNode l, ListNode p) {
			data = d;
			link = l;
			previous = p;
		}

	}

	ToDoList() {
		head = new ListNode();
		current = head;
	}

	ToDoList(String... data) {
		head = new ListNode();
		current = head;
		for (int i = 0; i < data.length; i++) {
			setData(data[i]); // sets current data
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

	void goToPrev() {
		current = current.previous;
	}

	String getData() {
		return current.data;
	}

	void setData(String s) {
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

	void insert() {
		current.link = new ListNode(null, current.link, current.previous);
		current.link.link.previous = current.link;
	}

	void insert(String s) {
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
			System.out.println(String.format("%d. %s", i + 1, temp.data));
			temp = temp.link;
			i++;
		}
		System.out.println(String.format("%d. %s", i + 1, temp.data));
	}

}
