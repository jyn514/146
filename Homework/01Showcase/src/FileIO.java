/*
 * Copyright © 2018 Joshua Nelson
 * This code is licensed under the GNU General Public License.
 * Essentially, you may modify, copy, and distribute this code, but
 * you may not sell it and you must make modifications available
 * AS SOURCE CODE to end users. The full license is located at
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 */

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

/**
 * Entirely static methods. Adapted for Showcase and not suitable for generics.
 */
public class FileIO {
  private static final Pattern DELIMITER = Pattern.compile("\\t");

  // wrappers so I don't have to remember what boolean means
  static void overwriteFile(String filename, Prize[] prizes) { writeFile(filename, prizes, false); }
  static void appendToFile(String filename, Prize[] prizes)  { writeFile(filename, prizes, true); }

  private static void writeFile(String filename, Prize[] prizes, boolean append) {
    try {
      PrintWriter writer = new PrintWriter(new FileOutputStream(filename, append));
      for (Prize p : prizes) { writer.println(p);  }
      writer.close();
    } catch (IOException e) { e.printStackTrace(); }
  }

  public static Prize[] readFile(String filename) {
    if (filename == null) return null;
    Prize[] prizes = null;
    try {
      // assumes no header
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      int lines = (int) reader.lines().count(); // requires Java 1.8+
      prizes = new Prize[lines];

      reader = new BufferedReader(new FileReader(filename)); // reset to start of file

      String current = reader.readLine();
      int i = 0;
      while(!(current == null || current.equals(""))) {
        String[] parts = DELIMITER.split(current);
        // do nothing if no tabs, ignore more than one tab
        if (parts.length >= 2) { prizes[i] = new Prize(parts[0], Integer.parseInt(parts[1])); }
        i++;
        current = reader.readLine();
      }
      reader.close();

    } catch (IOException e) { e.printStackTrace(); }

    return prizes;
  }
}
