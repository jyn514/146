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
 * Stack interface. Nothing interesting here.
 */
public interface Stack<T> extends Iterable<T> {
	void push(T data);
	void clear();
	int size();
	T peek();
	T pop();
	String toString();
}
