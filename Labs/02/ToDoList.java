public class ToDoList <T> extends DoubleLinkedList<T> {
	ToDoList() { super(); }
	@SafeVarargs ToDoList(T...data) { super(data); }
}