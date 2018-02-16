public interface Queue<T> {
	void enqueue(T data);
	T dequeue();
	T peek();
	String toString();
}
