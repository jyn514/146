package src;

public interface BinaryTree <T extends Comparable<T>> {
    void insert(T obj);
    void remove(T obj);
    void clear();
    boolean contains(T obj);
    int depth(T obj);
}
