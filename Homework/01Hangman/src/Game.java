import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Game {

  private Game() throws IllegalAccessException {
    throw new IllegalAccessException("No instantiation allowed. Run main method instead.");
  }

  private enum GAME_TYPE { COMPUTER, FRIEND };

  public static void main(String[] args) {
    final Scanner keyboard = new Scanner(System.in);
    String playAgain;
    GAME_TYPE type = null;

		System.out.println(Data.INTRO);

		do {
			System.out.println("Play against a friend or the computer? [f/c]");
      do {
        String i = keyboard.next().substring(0, 1);
        switch (i) {
          case "c": type = GAME_TYPE.COMPUTER; break;
          case "f": type = GAME_TYPE.FRIEND;  break;
          default:  System.out.println("Please enter 'f' for friend or 'c' for computer.");
        }
      } while (type == null);

      new GameInstance(type, keyboard);
			System.out.println("Play again?");
			playAgain = keyboard.next().toLowerCase();

		} while (playAgain.startsWith("y"));
		System.out.println("Goodbye!");
		keyboard.close();
	}

	private static class GameInstance {
    private final Scanner keyboard;
    private final String word;
    private final List<String> guesses = new ArrayList<>();
    private int wrongGuesses = 0;

    private GameInstance(GAME_TYPE t, Scanner keyboard) {
      this.keyboard = keyboard;

      if ( t == GAME_TYPE.COMPUTER ) { word = Data.randWord();	}
      else {
        String tempWord;
        do {
          System.out.println("Type a word for your friend to guess (spaces aren't supported yet).");
          tempWord = keyboard.next().trim(); // TODO: silently takes first word if 2 given
        } while (!tempWord.matches("[a-zA-Z]+"));

        word = tempWord;
        clearScreen();
      }

      String hiddenWord = repeat(word.length());
      String input;
      while (wrongGuesses < Data.LIVES && !hiddenWord.equals(word)) {
        System.out.println(Data.PICTURES[wrongGuesses]);
        if (guesses.size() > 0) {
          System.out.println("Guesses so far:");
          for (String c : guesses) { System.out.print(c + " "); }
          System.out.println();
        }
        System.out.println(hiddenWord);
        input = getInput();
        if (word.contains(input)) {	hiddenWord = replaceValues(hiddenWord, input);	}
        else { wrongGuesses++; }
        guesses.add(input);
      }
      if (hiddenWord.equals(word)) { System.out.println(Data.WON); }
      else { System.out.printf("%s%n%s%nThe word was %s.%n", Data.PICTURES[wrongGuesses], Data.LOST, word); }
    }

    private String replaceValues(String hiddenWord, String input) {
      StringBuilder temp = new StringBuilder();
      for (int i = 0; i < word.length(); i++) {
        if (hiddenWord.charAt(i) != '?') { temp.append(hiddenWord.charAt(i)); }
        else if (String.valueOf(word.charAt(i)).equals(input)) { temp.append(word.charAt(i)); }
        else { temp.append('?'); }
      }
      return temp.toString().toLowerCase();
    }

    private String getInput() {
      while (true) {
        String in = keyboard.next();
        if (in.length() != 1) { System.out.println("Please only type one letter."); }

        else if (guesses.contains(in)) { System.out.println("You have already guessed that."); }

        else if (!in.matches("[A-Za-z]*")) {
          System.out.println("Must only be letters. Don't worry, you don't need special symbols.");

        } else return in.toLowerCase();
      }
    }
  }

  private static void clearScreen() {
    if (System.getProperty("os.name").contains("Windows")) {
      try { new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); }
      catch (IOException | InterruptedException e) { hackyClearScreen(); }
    } else { hackyClearScreen(); }
  }

  private static void hackyClearScreen() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 1000; i++) { sb.append('\n'); }
    System.out.println(sb);
  }

  private static String repeat(int i) {
		return new String(new char[i]).replace("\0", "?");
	}
}
