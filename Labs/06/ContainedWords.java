/**
 * Copyright (c) 2018 Joshua Nelson
 * Licensed under the GPL 3
 * See https://www.gnu.org/licenses/gpl-3.0.en.html for details
 */

import java.util.Scanner;
import static java.util.Arrays.sort;
import static java.util.Arrays.copyOfRange;

public class ContainedWords {
	public static void main(String[] args) {
		System.out.println("Enter two strings to compare them, or type EOF"
						   + " (ctrl+D on UNIX, ctrl+C on Windows) to exit.");
		Scanner keyboard = new Scanner(System.in);
		String one, two;
		while (true) {
			if (keyboard.hasNext()) {
				one = keyboard.next();
			} else break;
			if (keyboard.hasNext()) {
				two = keyboard.next();
			} else break;
			System.out.println(containsAllLetters(one, two));
		}
	}

	private static boolean containsAllLetters(char[] one, char[] two) {
		return containsAllLetters(new String(one), new String(two));
	}
	
	/**
	 *  @param one string that must be contained
	 *  @param two string to check against
	 *	@return whether <code>two</code> contains all of the letters in <code>one</code>.
	 */
	private static boolean containsAllLetters(String one, String two) {
		if (one == null) 					return two == null;
		if (one.equals(two)) 				return true;
		if (one.length() > two.length()) 	return false;
		if (one.length() == 1) 				return two.indexOf(one) > -1; // same as built-in contains

		char[] oneSorted = one.toCharArray(), twoSorted = two.toCharArray();
		sort(oneSorted); // these return void
		sort(twoSorted);

		// find first occurence of first char in one
		int index = new String(twoSorted).indexOf(oneSorted[0]);
		if (index < 0) return false; // doesn't contain char
		
		// move to next letter in both
		return containsAllLetters(copyOfRange(oneSorted, 1, oneSorted.length),
								  copyOfRange(twoSorted, index + 1, twoSorted.length));
	}
}
