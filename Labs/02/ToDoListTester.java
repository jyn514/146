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
 * Basic tester for ToDoList. More tests available in DoubleLinkedListTest.java.
 */
public class ToDoListTester {
	public static void main(String[] args) {
		System.out.println("To Do List Tester!");
		System.out.println("Adding Five Tasks To Do\n");

		ToDoList<String> t = new ToDoList<>(
				"Buy Ground Beef", "Buy Cheese", "Buy Taco Shells", "Make Tacos", "Eat Tacos");
		t.showList();

		System.out.println("\nForgot salsa. Adding after step 2.");
		t.goTo("Buy Cheese");
		t.insert("Buy Salsa");
		t.showList();

		System.out.println("\nActually I'm changing salsa to hot sauce.");
		t.setDataAtCurrent("Buy Hot Sauce");
		t.showList();

		System.out.println("\nAdding guacamole to tacos.");
		t.insert("Buy Guacamole");
		t.showList();

		System.out.println("\nMaybe not.");
		t.deleteCurrentNode();
		t.showList();
	}
}
