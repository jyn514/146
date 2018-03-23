public class DoubleLinkedListStack<T> extends DoubleLinkedList<T> implements Stack<T> {
	@Override
	public void push(T data) {
		head = new ListNode(null, data, head);
		head.nextLink.prevLink = head;
	}

	@Override
	public void clear() {
		head = null;
	}

	@Override
	public int size() {
		int i = 0;
		for (T t: this) i++;
		return i;
	}

	@Override
	public T peek() {
		return head.data;
	}

	@Override
	public T pop() {
		T temp = head.data;
		head.nextLink.prevLink = null;
		head = head.nextLink;
		return temp;
	}

	@Override
	public String toString() {
		return null;
	}
}
