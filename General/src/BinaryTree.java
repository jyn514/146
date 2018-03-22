package src;

public interface BinaryTree <T extends Comparable<T>> {
    void add(T obj);
    void delete(T obj);
    void clear();
    boolean contains(T obj);
    int depth(T obj);
    T least();
    T greatest();
}
