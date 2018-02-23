import java.util.Scanner;

public class ContainedWords {
	public static void main(String[] args) {
		System.out.println("Enter two strings to compare them, or type EOF (ctrl+D) to exit.");
		Scanner keyboard = new Scanner(System.in);
		String one, two;
		while (true) {
			if (keyboard.hasNext()) {
				one = keyboard.next();
			} else return;
			if (keyboard.hasNext()) {
				two = keyboard.next();
			} else return;
			System.out.println(contains(one, two));
		}
	}

	/**
	 * Convenience method for contains
	 */
	private static boolean contains(String one, String two) {
		return contains(one, two, false);
	}

	private static boolean contains(String one, String two, boolean switched) {
		if (one == null) return two == null;
		if (one.length() == 1) { // iterate through string two, return whether one is present
			// avoid OOB exception   and  first character of two is one   or rest of two contains one
			return two.length() != 0 && ((two.charAt(0) == one.charAt(0)) || contains(one, two.substring(1)));
		}
		// split one into component letters; this is usually run first
		if (contains(one.substring(0, 1), two) && contains(one.substring(1), two)) return true;
		// switched parameter is necessary to avoid infinite recursion
		if (!switched) return contains(two, one, true);
		return false;
	}
}
