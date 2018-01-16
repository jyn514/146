import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

	private final String word, gameType;
	private String hiddenWord, input;
	private List<String> guesses;
	private int wrongGuesses;
	private static final Data d = new Data();
	private static Scanner keyboard; // should not be static :/
	private boolean won;

	public static void main(String[] args) {
		keyboard = new Scanner(System.in);
		System.out.println(Data.INTRO);
		do {
			System.out.println("Play against a friend or the computer? [f/c]");
			new Game(getGameType()); // :D
			System.out.println("Play again? (yes or quit)");
		} while (keyboard.next().toLowerCase().contains("yes"));
		System.out.println("Goodbye!");
		keyboard.close();
	}

	private static void hackierClearScreen() {
		for (int i = 0; i < 1000; i++) {
			System.out.println(); // should clear buffer memory
		}
	}

	Game(String s) {
		gameType = s;
		won = false;
		wrongGuesses = 0;
		guesses = new ArrayList<String>();

		if (gameType.equals("computer")) {
			word = d.randWord();
		} else {
			System.out.println("Type a word for your friend to guess. (Spaces aren't supported yet)");
			word = keyboard.next();
			hackierClearScreen();
		}

		hiddenWord = repeat(word.length(), "?");
		while (wrongGuesses < Data.LIVES) {
			System.out.println(Data.PICTURES[wrongGuesses]);
			if (guesses.size() > 0) {
				System.out.println("Guesses so far:");
				for (String c : guesses) {
					System.out.print(c + " ");
				}
				System.out.println();
			}
			System.out.println(hiddenWord);
			input = getInput();
			if (word.contains(input)) {
				hiddenWord = replaceValues();
			} else {
				wrongGuesses++;
			}
			guesses.add(input);
			if (hiddenWord.equals(word)) {
				won = true;
				break;
			}
		}
		if (won) {
			System.out.println(Data.WON);
		} else {
			System.out.println(Data.LOST);
			System.out.println(String.format("The word was %s.", word));
		}

	}

	private static String getGameType() { // really needs not to be static
		String i = keyboard.next().substring(0, 1);
		if (i.equals("c")) {
			return "computer";
		} else if (i.equals("f")) {
			return "friend";
		} else {
			System.out.println("Please enter 'f' for friend or 'c' for computer.");
			return getGameType();
		}
	}

	private String replaceValues() {
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < word.length(); i++) {
			if (hiddenWord.charAt(i) != '?') {
				temp.append(hiddenWord.charAt(i));
			} else if (String.valueOf(word.charAt(i)).equals(input)) {
				temp.append(word.charAt(i));
			} else {
				temp.append('?');
			}
		}
		return temp.toString().toLowerCase();
	}

	private String getInput() { // validates input as well
		String in = keyboard.next();
		if (in.length() != 1) {
			System.out.println("Please only type one letter.");
			return getInput();
		} else if (guesses.contains(in)) {
			System.out.println("You have already guessed that.");
			return getInput();
		} else if (!in.matches("[A-Za-z]*") && gameType.equals("computer")) {
			System.out.println("Must only be letters. Don't worry, you don't need special symbols.");
			return getInput();
		} else {
			return in.toLowerCase();
		}
	}

	private static String repeat(int i, String c) {
		return new String(new char[i]).replace("\0", c);
	}
}