import java.util.Scanner;

class Contains {

	public static void test() {
		System.out.println(contains("i", "i"));  // true
		System.out.println(contains("i", "e"));  // false
		System.out.println(contains("e", "i"));  // false
		System.out.println(contains("hi", "i"));  // true
		System.out.println(contains("i", "hi"));  // true
		System.out.println(contains("hit", "bat"));  // false
		System.out.println(contains("self", "elf"));  // true
		System.out.println(contains("elf", "self"));  // false, should be true
		System.out.println(contains("jerky", "turkey"));  // false
		System.out.println(contains("asdf", "fasted"));  // false, should be true
		System.out.println(contains("fasted", "asdf"));  // true
	}

	public static void main(String[] args) {
		/* System.out.println("Enter two words and I will determine "
						 + "if one contains the other.");
		final Scanner keyboard = new Scanner(System.in);
		System.out.println(contains(keyboard.nextLine(), keyboard.nextLine()));
		keyboard.close(); */
		test();
	}

	/**
	 * If both are null, return true. If only one is null, return false.
	 * Return whether the set of letters in one string is a subset of the other.
	 * @param one Nullable string
	 * @param two Nullable string
	 * @return true if both are null, else whether <i>either</i>
	 * contains the letters of the other. 
	 */
	private static boolean contains(String one, String two) {
		if (one == null) return two == null;
		else if (one.equals(two)) return true;
		else {
			if (one.length() == 1) {
				if (two.length() == 1) return false; // known not equal
				return contains(one, two.substring(0, 1)) || contains(one, two.substring(1));
			}

			return (two.length() <= one.length()
				&& (contains(one.substring(0, 1), two)
					|| contains(one.substring(1), two)));
		}
	}
}
