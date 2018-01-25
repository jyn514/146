import java.io.*;
import java.util.Scanner;

public class Database {
  private static final String DELIMITER = "\t";

  static void overwriteFile(String filename, Prize[] prizes) { writeFile(filename, prizes, false); }
  static void appendToFile(String filename, Prize[] prizes)  { writeFile(filename, prizes, true); }

  private static void writeFile(String filename, Prize[] prizes, boolean append) {
    try {
      PrintWriter writer = new PrintWriter(new FileOutputStream(filename, append));
      for (Prize p : prizes) {

      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Prize[] readFile(String filename) {
    if (filename == null) return null;
    Prize[] prizes = null;
    try {
      // for efficiency
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      int lines = (int) reader.lines().count(); // requires Java 1.8+

      prizes = new Prize[lines];

      // for convenience
      Scanner fileScanner = new Scanner(reader);
      for(int line = 0; line < lines; line++) {
        prizes[line] = new Prize(fileScanner.next(), fileScanner.nextLong());
      }

      fileScanner.close();
      reader.close();

    } catch (IOException e) { e.printStackTrace(); }

    return prizes;
  }
}
