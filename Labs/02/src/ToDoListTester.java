
public class ToDoListTester {

	public static void main(String[] args) {
		System.out.println("To Do List Tester!");
		System.out.println("Adding Five Tasks To Do\n");
		
		ToDoList t = new ToDoList("Buy Ground Beef", "Buy Cheese", "Buy Taco Shells", "Make Tacos", "Eat Tacos");
		
		t.showList();
		t.goToNext(); // on step 2
		
		System.out.println("\nForgot salsa. Adding after step 2.");
		t.insert("Buy Salsa");
		t.showList();

		t.goToNext(); // on step 3
		System.out.println("\nActually I'm changing salsa to hot sauce.");
		t.setData("Hot Sauce");
		t.showList();

		System.out.println("\nAdding guacamole to tacos.");
		t.insert("Buy Guacamole");
		t.showList();
		
		t.goToNext(); // on step 4
		System.out.println("\nMaybe not.");
		t.deleteCurrent();
		t.showList();
	}

}
