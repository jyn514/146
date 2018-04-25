package src;

public interface Stack<T> extends Iterable<T> {
	void push(T data);
	void clear();
	int size();
	T peek();
	T pop();
	String toString();
}
