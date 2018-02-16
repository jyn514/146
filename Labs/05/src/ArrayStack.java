import java.util.Iterator;

public class ArrayStack<T> implements Stack<T> {
	private T[] array;
	private int end = 0;
	
	ArrayStack() {
		this(20);
	}
	
	@SuppressWarnings("unchecked")
	ArrayStack(int size) {
		array = (T[]) new Object[size];
	}
	
	@SafeVarargs
	ArrayStack(T ... data) {
		array = data;
	}

	ArrayStack(Stack<T> stack) {
		for (T t : stack) push(t);
	}
	
	@Override
	public void push(T data) {
		if (end >= array.length) {
			// TODO: fix this
			throw new IndexOutOfBoundsException("Not enough space in array. Try creating a larger one.");
		}
		array[end++] = data;
	}

	@Override
	public void clear() {
		end = 0;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public T peek() {
		if (end == 0) return null;
		return array[end - 1];
	}

	@Override
	public T pop() {
		return array[--end];
	}


	@Override
	public Iterator<T> iterator() {
		return new Iterator<T> (){
			int current = 0;

			@Override
			public boolean hasNext() {
				return current < end;
			}

			@Override
			public T next() {
				return array[current++];
			}
		};
	}
}
