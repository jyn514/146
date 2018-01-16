import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ReversePolishCalculator {
  private static final String BAD_EXPRESSION = "Ill formatted expression.\nResult is 0";

  public static void main(String[] args) {
    Path DEFAULT = Paths.get(".").toAbsolutePath().resolve("revPol.txt").normalize();
    LinkedListQueue<Integer> queue = new LinkedListQueue<>();
    Scanner keyboard = new Scanner(System.in);
    List<String> commands;

    System.out.println(String.format("Enter a file path to open (default: %s):", DEFAULT));
    String file = keyboard.nextLine();

    try {
      if (file.length() == 0) {
        commands = getLines();
      } else {
        commands = getLines(Paths.get(file));
      }

      for (String s : commands) {
        System.out.println("Calculating " + s);
        System.out.println(parse(s, queue));
        queue.clear();
        System.out.println();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    keyboard.close();
    System.out.println("Quitting.");
  }

  private static List<String> getLines() throws IOException {
    return getLines(Paths.get(".").toAbsolutePath().resolve("revPol.txt").normalize());
  }

  private static List<String> getLines(Path s) throws IOException {
    return Files.readAllLines(s);
  }

  private static boolean isNumeric(String s) {
    return s != null && s.matches("[-+]?\\d*\\.?\\d+"); // https://stackoverflow.com/questions/14206768/how-to-check-if-a-string-is-numeric#14206803
  }

  private static String parse(String operation, LinkedListQueue<Integer> queue) {
    int second;

    for (String s : operation.split(" ")) {
      if (isNumeric(s)) {
        queue.insert(Integer.parseInt(s));
      } else if (queue.size < 2) {
        return BAD_EXPRESSION
                + String.format(" Tried to use operator %s with less than 2 numbers in queue.", s);
      } else {
        switch (s) {
        case "+":
          second = queue.dequeue();
          queue.insert(queue.dequeue() + second);
          break;
        case "-":
          second = queue.dequeue();
          queue.insert(queue.dequeue() - second);
          break;
        case "*":
          second = queue.dequeue();
          queue.insert(queue.dequeue() * second);
          break;
        case "/":
          second = queue.dequeue();
          if (second == 0) {
            return BAD_EXPRESSION + " (error was division by zero)";
          } else {
            queue.insert(queue.dequeue() / second);
          }
          break;
        default:
          return BAD_EXPRESSION;
        }
      }
    }
    if (queue.size != 1) {
      System.out.println(String.format("Still had %d numbers in queue.", queue.size));
      return BAD_EXPRESSION;
    } else {
      return queue.dequeue().toString();
    }
  }

}
