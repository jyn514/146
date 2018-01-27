import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.NoSuchElementException;

import static java.nio.file.FileSystems.getDefault;
import static java.nio.file.Files.newBufferedReader;

final class FinalGradeCalculator {

  private enum CATEGORY {
    LABS (20), HOMEWORK (30), EXAM_1 (10), EXAM_2 (10), LAB_EXAM_1 (10), LAB_EXAM_2 (10), FINAL (10),
	EXTRA_CREDIT();

	final int weight;
	CATEGORY() { weight = 0; } // weight is a placeholder, never used
	CATEGORY(int weight) { this.weight = weight; }
  }

  // literally just all categories seperated by literal '|'
  private static final Pattern validCategories = makeValidCategories();
  // modified from StackOverflow: https://stackoverflow.com/a/1102916
  private static final Pattern positiveFloat = Pattern.compile("\\d+(\\.\\d+)?");
  private static final String perfectGradesFile = "perfectGrades.txt";
  private static final String imperfectGradesFile = "grades.txt";

  private FinalGradeCalculator() { throw new IllegalAccessError("No instantiation allowed."); }

  public static void main(String[] args) {
    for (String p : new String[]{perfectGradesFile, imperfectGradesFile}) { handleIOForFile(p); }
    final Scanner keyboard = new Scanner(System.in);
    String input;
    while (true) {
      System.out.println("Enter a file to read, or 'quit' to quit.");
	  try { input = keyboard.nextLine(); } catch (NoSuchElementException e) { break; }
      if (input.equals("quit") || input.equals("exit")) break;
      handleIOForFile(input);
    }
	keyboard.close();
  }

  static void handleIOForFile(String filePath) {
      System.out.printf("Calculating grades for file %s...%n", filePath);
      try { System.out.printf("%c.%n", getFinalGrade(getDefault().getPath(filePath))); }
      catch (IOException e) { System.out.printf(
          "Could not open file `%s' for reading. Please ensure it exists and you have the proper permissions.%n", filePath);
      }
  }

  /**
   * <i>This</i> mess of a class calculates a final grade from a text document.
   * Any field not marked as final is a stateful variable. Please don't mess with the stateful variables.
   * Uses BufferedReader instead of Scanner for efficiency and to avoid exceptions.
   * Prints average for each category as it iterates.
   *
   * @param file Text document containing grades. See grades.txt for an example.
   * @return finalGrade Grade for the class. Letter from A-F, excluding E.
   * @throws IOException if file cannot be accessed
   */
  private static char getFinalGrade(Path file) throws IOException {

    final char finalGrade;
    final float[] gradeTypes = new float[CATEGORY.values().length];
    final BufferedReader reader = newBufferedReader(file); // throws IOException

    // avoids dynamic arrays. I know, it's ugly
    float minHomework = Integer.MAX_VALUE, homeworkSum = 0, labSum = 0, extraCreditSum = 0;
    int numHomeworks = 0, labs = 0;
    String currentInput;
    CATEGORY currentCategory = null;

    // start reading file
    while ((currentInput = reader.readLine()) != null) { // BufferedReader returns null at EOF
      currentInput = currentInput.trim().toUpperCase(); // uppercase does nothing for numbers
      if (currentInput.equals("")) continue;  // ignore blank lines
      if (validCategories.matcher(currentInput).matches()) {
        currentCategory = CATEGORY.valueOf(currentInput.replace(' ', '_'));
        continue;
      }
      if (!isPositiveFloat(currentInput) || currentCategory == null) throw new IllegalArgumentException("Bad file format");

      switch(currentCategory) {
        case EXAM_1:
        case EXAM_2:
        case LAB_EXAM_1:
        case LAB_EXAM_2:
        case FINAL:
          if (gradeTypes[currentCategory.ordinal()] != 0) {
            throw new IllegalArgumentException("More than one exam for type " + currentCategory.name());
          }
          System.out.printf("Your %s average is %s%%.%n",
			currentCategory.name().toLowerCase().replace('_', ' '), currentInput);
          gradeTypes[currentCategory.ordinal()] = Float.parseFloat(currentInput) * currentCategory.weight / 100;
          break;
        case HOMEWORK:
          int grade = Integer.parseInt(currentInput);
          // Find lowest
          if (minHomework == Integer.MAX_VALUE || grade < minHomework) minHomework = grade;
          homeworkSum += grade;
          numHomeworks++;
          break;
        case LABS:
          labSum += Integer.parseInt(currentInput);
          labs++;
          break;
        case EXTRA_CREDIT:
          extraCreditSum += Integer.parseInt(currentInput);
          break;
        default:
          throw new IllegalStateException("WTF is a " + currentCategory.name() + "?!?!?");
      }
    }
    reader.close();

    // get percentage of final grade for homework, dropping lowest
    float homeworkMean = (homeworkSum - minHomework) / (numHomeworks - 1);
    System.out.printf("Your homework average is %.2f%%.%n", homeworkMean);
    gradeTypes[CATEGORY.HOMEWORK.ordinal()] = homeworkMean * CATEGORY.HOMEWORK.weight / 100;

    // get percentage for labs
    float labMean = labSum / labs;
    System.out.printf("Your lab average is %.2f%%.%n", labMean);
    gradeTypes[CATEGORY.LABS.ordinal()] =  labMean * CATEGORY.LABS.weight / 100;

    // add percentage for extra credit
    gradeTypes[CATEGORY.EXTRA_CREDIT.ordinal()] = extraCreditSum / 50; // sum * 2 / 100, each extra credit is worth 2%
    System.out.printf("Your extra credit total is %.2f%%.%n", gradeTypes[CATEGORY.EXTRA_CREDIT.ordinal()]);

    float sum = 0;
    for (float gradePercentage : gradeTypes) sum += gradePercentage; // max of 110
    if (sum < 0 || sum > 110 ) throw new IllegalStateException("WTF why is raw grade " + sum);

    System.out.printf("Your raw grade is %.1f%%.%n", sum);
    sum = (int) Math.ceil(sum); // safe cast, will always be integer
    if (sum < 60) finalGrade = 'F';
    else if (sum < 70) finalGrade = 'D';
    else if (sum < 80) finalGrade = 'C';
    else if (sum < 90) finalGrade = 'B';
    else finalGrade = 'A';
    return finalGrade;
  }

  private static boolean isPositiveFloat (String s) { return positiveFloat.matcher(s).matches(); }

  /**
   * CURSE THEE JAVA<br />
   * no but seriously this is literally <code>'|'.join(CATEGORY)</code> in python.
   * Java 8 ALMOST managed this but enums don't provide an interface for a list of strings.
   * @return the stringbuilder way to do <code>'|'.join(CATEGORY)</code>
   */
  private static Pattern makeValidCategories() {
    final StringBuilder valid = new StringBuilder();
    for (CATEGORY c : CATEGORY.values()) { valid.append(c.name().replace('_', ' ')).append('|'); }
    valid.deleteCharAt(valid.length() - 1);
    return Pattern.compile(valid.toString());
  }

}
