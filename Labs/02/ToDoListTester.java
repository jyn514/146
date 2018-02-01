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
