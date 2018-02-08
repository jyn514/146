/*
 * Copyright (c) 2018 Joshua Nelson
 *
 * This program is licensed under the GNU General Public License.
 * Essentially, you may modify, distribute, and copy this work,
 * but you must preserve this copyright notice and you MUST
 * make any changes available AS SOURCE CODE to the end users.
 *
 * Details available here:
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * Closest you can get in Java to a duplicate class.
 * Duplicates DoubleLinkedList in every way.
 */

public class ToDoList <T> extends DoubleLinkedList<T> {
	ToDoList() { super(); }
	@SafeVarargs ToDoList(T...data) { super(data); }
}