/**
 * Copyright © 2017 Joshua Nelson
 * Licensed under the GNU GPL; see http://www.gnu.org/licenses/gpl.html for details.
 *
 * Essentially, you may modify, distribute, and use this program freely,
 * but you may not sell it or make changes to the license, and you MUST
 * make any changes available AS SOURCE CODE to end users.
 *
 * Main class. Nothing interesting here.
 */

import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {

    public static void main (String[] args) {
      Scanner keyboard = new Scanner(System.in);
      System.out.println(new Board()); // 10x10x10 is default
      // very ugly if columns > larger than terminal size
      while(!askToQuit(keyboard)) System.out.println(makeBoardFromInput(keyboard));
    }

    private static boolean askToQuit(Scanner keyboard) {
        System.out.println("Make a new board? [yes]");
        String input = keyboard.nextLine().trim().toLowerCase();
        return (input.equals("no") || input.equals("n") || input.equals("cancel")
                || input.equals("quit") || input.equals("exit") || input.equals("q"));
    }

    public static Board makeBoardFromInput(Scanner keyboard) {
      try {
        System.out.println("Enter x dimension");
        int x = Integer.parseInt(keyboard.nextLine());

        System.out.println("Enter a y dimension.");
        int y = Integer.parseInt(keyboard.nextLine());

        System.out.println("Enter number of mines");
        int m = Integer.parseInt(keyboard.nextLine());

        return new Board(x, y, m);
      } catch (IllegalArgumentException | InputMismatchException e) {
          // %n is system-appropriate newline
          System.out.printf("Invalid argument: %s%n", e.getMessage());
          return makeBoardFromInput(keyboard);
      }
    }

}
