package src;

public interface Set<T> extends Iterable<T> {
	void add(T data);
	void remove(T data);
	boolean contains(T data);
	String toString();
}
