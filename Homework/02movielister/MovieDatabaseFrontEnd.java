import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;

public class MovieDatabaseFrontEnd {

  private static Path DEFAULT_PATH = Paths.get(".").resolve("movies.txt").toAbsolutePath()
          .normalize();

  private static final String INSTRUCTIONS = "Enter 1: To Add a Movie\n"
          + "Enter 2: To Remove a Movie by its Title\n" + "Enter 3: To search for a Title\n"
          + "Enter 4: To search for moves by a Director\n"
          + "Enter 5: To search for movies of a given Year\n"
          + "Enter 6: To search for movies of a certain Rating\n"
          + "Enter 7: To print out all movies\n" + "Enter 8: To print to a database file\n"
          + "Enter 9: To load a database file\n" + "Enter 0: To quit";

  public static void main(String[] args) {
    MovieDatabase database = new MovieDatabase();
    Scanner keyboard = new Scanner(System.in);
    boolean quit = false;
    System.out.println(INSTRUCTIONS);

    while (quit == false) {
      switch (getInput(keyboard)) {
      case '?':
        System.out.println(INSTRUCTIONS);
        break;

      case '0':
        quit = true;
        break;

      case '1':
    	String name = "", director = ""; 
    	long income;
    	int year;
    	
        System.out.println(
                "Enter the following details of the movie you wish to add, seperated by a line break.");
        do {
        System.out.println("Enter the name of the movie.");
        name = keyboard.nextLine();
        } while (name.matches("\\p{Space}*")); // https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
        
        do {
        	System.out.println("Enter the name of the director.");
            director = keyboard.nextLine();
        } while (director.matches("\\p{Space}*"));

        System.out.println("Enter a number for income");
        income = Long.parseLong((getNumericInput(keyboard)));

        do {
          System.out.println("Enter a year between 1874 and the present. ");
          year = Integer.parseInt(getNumericInput(keyboard));
        } while (year < 1874 || year > 2017);

        short rating;
        do {
          System.out.println("Enter a rating between 1 and 5. ");
          rating = Short.parseShort((getNumericInput(keyboard)));
        } while (rating > 5 || rating < 1);

        database.append(new Movie(name, director, income, year, rating));
        break;

      case '2':
        System.out.println("Enter the name of the movie you wish to remove.");
        String message = database.removeMovieTitle(keyboard.nextLine());
        System.out.println(message);
        break;

      case '3':
        System.out.println("Enter the name of the movie you wish to search.");
        print(database.searchByTitle(keyboard.nextLine()), database);
        break;

      case '4':
        System.out.println("Enter the director of the movie you wish to search.");
        print(database.searchByDirector(keyboard.nextLine()), database);
        break;

      case '5':
        System.out.println("Enter the year of the movie you wish to search.");
        print(database.searchByYear(Integer.parseInt(getNumericInput(keyboard))), database);
        break;

      case '6':
        System.out.println("Enter the rating of the movie you wish to search.");
        print(database.searchByRating(Integer.parseInt(getNumericInput(keyboard))), database);
        break;

      case '7':
        database.print();
        break;

      case '8':
        System.out.println("Enter the name of the file you wish to save to.");
        System.out.println("default: " + DEFAULT_PATH);
        Path p = getPath(keyboard, false);
        try {
          database.write(p);
          System.out
                  .println(String.format("Wrote \"%s\" to %s successfully.", database.getAll(), p));
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;
      case '9':
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

  static Path getPath(Scanner keyboard) {
    return getPath(keyboard, true); // default: require path to exist
  }

  static Path getPath(Scanner keyboard, boolean mustExist) {
	Path p;
    String in = keyboard.nextLine().trim();
    if (in.length() == 0) {
      p = DEFAULT_PATH;
    } else {
    	p = Paths.get(in).toAbsolutePath().normalize();
    }
    if (mustExist && !p.toFile().exists()) {
      System.out.println(String.format("File %s does not exist. Please enter a different file.", p));
      return getPath(keyboard, true);
    }
    return p;
  }

  static char getInput(Scanner keyboard) {
    return getNumericInput(keyboard, true, 1).charAt(0); // backwards compatability
  }

  static String getNumericInput(Scanner keyboard) {
    return getNumericInput(keyboard, false, -1); // convienience
  }

  static void print(Set<Movie> set, MovieDatabase database) {
    if (set.size() == 0) {
      System.out.println("No matches found.");
      return;
    }
    for (Movie m : set) {
      System.out.println(m);
    }
  }

  /*
   * if maxLength is negative, length is unlimited requires keyboard in order to
   * be static
   */
  static String getNumericInput(Scanner keyboard, boolean questionMarkAcceptable, int maxLength) {
    String in = keyboard.nextLine();
    String valid = "[0-9]+";
    if (questionMarkAcceptable) {
      valid = "[0-9\\?]*";
    }
    if ((maxLength >= 0 && in.length() > maxLength) || !in.matches(valid) || in.isEmpty()) {
      System.out.println(String.format(
              "Invalid input. Please enter input matching the regex %s and less than %s characters long.",
              valid, maxLength));
      return getNumericInput(keyboard, questionMarkAcceptable, maxLength);
    }
    return in;
  }
}
