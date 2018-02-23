import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ReversePolishCalculator extends DoubleLinkedListStack<Float> {
  private static final String BAD_EXPRESSION = "Ill formatted expression: Result is 0";
  private static final String DEFAULT = "revPol.txt";

  private ReversePolishCalculator(String command) {
    float second;

    for (String s : command.split(" ")) {
      if (isNumeric(s)) {
        push(Float.parseFloat(s));
      } else if (size() < 2) {
        System.out.printf("Tried to use operator %s with less than 2 numbers in stack. %s%n", s, BAD_EXPRESSION);
        return;
      } else {
        second = pop();
        switch (s.charAt(0)) {
          case '+':
            push(pop() + second);
            break;
          case '-':
            push(pop() - second);
            break;
          case '*':
            push(pop() * second);
            break;
          case '/':
            if (second != 0) push(pop() / second);
            else System.out.print("Error: Division by zero. " + BAD_EXPRESSION);
            return;
          default:
            System.out.printf("Bad operator %s. %s", s, BAD_EXPRESSION);
            return;
        }
      }
    }
    if (size() != 1) {
      System.out.printf("Still had %d numbers in stack. ", size());
      System.out.println(BAD_EXPRESSION);
    } else System.out.println(pop());
  }

  public static void main(String[] args) {
    Scanner keyboard = new Scanner(System.in);
    String file, current;

    System.out.printf("Enter a file path to open (default: %s): ", DEFAULT);
    String fileInput = keyboard.nextLine();

    try {
      if (fileInput.length() == 0) file = DEFAULT;
      else                         file = fileInput;
      BufferedReader reader = new BufferedReader(new FileReader(file));

      while ((current = reader.readLine()) != null) {
        System.out.println("Calculating " + current);
        new ReversePolishCalculator(current);
      }

      keyboard.close();
      reader.close();
      System.out.println("\nQuitting.");
    } catch (IOException e) { e.printStackTrace(); }
  }

  private static boolean isNumeric(String s) {
    return s != null && s.matches("[-+]?\\d*\\.?\\d+"); // https://stackoverflow.com/a/14206803
  }

}
