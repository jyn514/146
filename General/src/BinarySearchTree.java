package src;

public interface BinarySearchTree<T extends Comparable<T>> extends Map<T, T> {
	@Override
	default void put(T t, T data) { add(t); };
	default T get(T t) { return t; }
}
