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
 */

import java.io.IOException;
import java.nio.file.Path;
import java.time.Year;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.nio.file.Paths.get;

public class MovieDatabaseFrontEnd {

  private static final Path DEFAULT_PATH = get(".").resolve("movies.txt").toAbsolutePath().normalize();
  private static final int PRESENT = Year.now().getValue();

  private enum Input {
    QUIT("exit the program, successfully"),
    ADD("add a movie"), REMOVE("remove a movie by its title"),
    SEARCH_TITLE("search for a title"), SEARCH_DIRECTOR("search for a director"),
    SEARCH_YEAR("search for a year"), SEARCH_RATING("search for a rating"),
    PRINT("print all movies"), WRITE("write database to a file"), LOAD("load database from a file"),
    HELP("print these instructions");

    final String instructions;
    Input(String i) { this.instructions = i; }
  }

  private static void printInstructions() {
    for (Input i : Input.values()) {
      System.out.printf("Enter %d to %s.%n", i.ordinal(), i.instructions);
    }
  }

  public static void main(String[] args) {
    MovieDatabase database = new MovieDatabase();
    Scanner keyboard = new Scanner(System.in);
    boolean quit = false;
    printInstructions();

    while (!quit) {
      switch (getInput(keyboard)) {
      case HELP:
        printInstructions();
        break;

      case QUIT:
        quit = true;
        break;

      case ADD:
        String name, director;
        long income;
        int year;
        short rating;

      System.out.println("Enter the details of the movie you wish to add, separated by a line break.");
      do {
      System.out.println("Enter the name of the movie.");
      name = keyboard.nextLine();
      } while (name.matches("\\p{Space}*")); // https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html

      do {
        System.out.print("Enter the name of the director: ");
        director = keyboard.nextLine();
      } while (director.matches("\\p{Space}*"));

      System.out.print("Enter a number for income: ");
      income = getLong(keyboard,0, Long.MAX_VALUE);

      System.out.print("Enter a year between 1874 and the present: ");
      year = (int) getLong(keyboard, 1874, PRESENT);

      System.out.println("Enter a rating between 1 and 5. ");
      rating = (short) getLong(keyboard, 1, 5);

      database.append(new Movie(name, director, income, year, rating));
      break;

      case REMOVE:
      System.out.println("Enter the name of the movie you wish to remove.");
      String message = database.removeMovieTitle(keyboard.nextLine());
      System.out.println(message);
      break;

      case SEARCH_TITLE:
      System.out.println("Enter the name of the movie you wish to search.");
      for (Movie m : database.searchByTitle(keyboard.nextLine())) System.out.println(m);
      break;

      case SEARCH_DIRECTOR:
      System.out.print("Enter the director of the movie you wish to search: ");
      for (Movie m : database.searchByDirector(keyboard.nextLine())) System.out.println(m);
      break;

      case SEARCH_YEAR:
      System.out.print("Enter the year of the movie you wish to search: ");
      for (Movie m : database.searchByYear(getInt(keyboard,1874, PRESENT))) System.out.println(m);
      break;

      case SEARCH_RATING:
      System.out.println("Enter the rating of the movie you wish to search.");
      for (Movie m : database.searchByRating(getShort(keyboard, (short)1, (short)5))) System.out.println(m);
      break;

      case PRINT:
      database.print();
      break;

      case WRITE:
      System.out.println("Enter the name of the file you wish to save to.");
      System.out.println("default: " + DEFAULT_PATH);
      Path p = getPath(keyboard, false);
      try {
        database.write(p);
        System.out.println(String.format("Wrote \"%s\" to %s successfully.", database, p));
      } catch (IOException e) {
        e.printStackTrace();
      }
      break;

      case LOAD:
      System.out.println("Enter the name of the file you wish to load.");
      System.out.println("default: " + DEFAULT_PATH);
      Path rp = getPath(keyboard);
      try {
        database = database.read(rp);
      } catch (IOException e) {
        e.printStackTrace();
      }
      break;
      }
    }
    keyboard.close();

  }

  private static Path getPath(Scanner keyboard) {
    return getPath(keyboard, true); // default: require path to exist
  }

  private static Path getPath(Scanner keyboard, boolean mustExist) {
    Path p;
    String in;
    boolean found = false;
    do {
      in = keyboard.nextLine().trim();
      p = in.length() == 0 ? DEFAULT_PATH : get(in).toAbsolutePath().normalize();
      if (mustExist && !p.toFile().exists()) {
        System.out.println(String.format("File %s does not exist. Please enter a different file.", p));
      } else found = true;
    } while (!found);
    return p;
  }

  private static short getShort(Scanner keyboard, short min, short max) {
    return (short) getLong(keyboard, min, max);
  }

  private static int getInt(Scanner keyboard, int min, int max) {
    return (int) getLong(keyboard, min, max);
  }

  private static long getLong(Scanner keyboard, long min, long max) {
   long result = 0; // compiler complains otherwise
   boolean good = false;
   while (!good) {
     System.out.printf("Please enter an integer between %d and %d: ", min, max);
     try {
       result = Long.parseLong(keyboard.next());
       if (result >= min && result <= max) {
        good = true;
       }
     } catch (NumberFormatException ignored){}
   }
   keyboard.nextLine();
   return result;
  }

  private static Input getInput(Scanner keyboard) {
    short result = -1;
    while (result > 10 || result < 0) {
      System.out.print("Please enter a number between 0 and 10: ");
      try { result = Short.parseShort(keyboard.nextLine());  }
      catch (NoSuchElementException | NumberFormatException ignored) {}
    }
    return Input.values()[result];
  }

}
