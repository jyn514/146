/*
 * Copyright (c) 2018 Joshua Nelson
 *
 *  This program is licensed under the GNU General Public License.
 *  Essentially, you may modify, distribute, and copy this work,
 *  but you must preserve this copyright notice and you MUST
 *  make any changes available AS SOURCE CODE to the end users.
 *
 *  Details available here:
 *  https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * Stack using double linked list. Offloads most work onto linked list.
 */
public class DoubleLinkedListStack<T> extends CircularDoubleLinkedList<T> implements Stack<T> {
	@Override
	public void push(T data) {
		append(data);
	}

	@Override
	public T peek() {
		goToStart();
		goToPrev();
		return getDataAtCurrent();
	}

	@Override
	public T pop() {
		T temp = peek();
		deleteCurrentNode();
		return temp;
	}
}
