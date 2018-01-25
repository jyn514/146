import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.FileSystems.getDefault;
import static java.nio.file.Files.newBufferedReader;

final class FinalGradeCalculator {

  private enum CATEGORY {
    LABS, HOMEWORK, EXAM_1, EXAM_2, LAB_EXAM_1, LAB_EXAM_2, FINAL, EXTRA_CREDIT
  }

  private static final String validCategories = "LABS|HOMEWORK|EXAM 1|EXAM 2|LAB EXAM 1|LAB EXAM 2|FINAL|EXTRA CREDIT";
  private static final String perfectGradesFile = "perfectGrades.txt";
  private static final String imperfectGradesFile = "grades.txt";
  // same order as CATEGORY enum. Extra credit NOT included, since weight is dependent on number of assignments
  private static final int[] gradeWeights = new int[] { 20, 30, 10, 10, 10, 10, 10 };

  private FinalGradeCalculator() { throw new IllegalAccessError("Not allowed." ); }

  public static void main(String[] args) {
    for (Path p : new Path[] { getDefault().getPath(perfectGradesFile), getDefault().getPath(imperfectGradesFile) }) {
      System.out.printf("Calculating grades for %s...%n", p.toString());
      try { System.out.printf("%c.%n", getFinalGrade(p)); }
      catch (IOException e) { e.printStackTrace(); }
    }
  }

  /**
   * <i>This</i> mess of a class calculates a final grade from a text document.
   * Any field not marked as final is a stateful variable. Please don't mess with the stateful variables.
   * Uses BufferedReader instead of Scanner for efficiency and to avoid exceptions.
   * 
   *
   * @param file Text document containing grades. See grades.txt for an example.
   * @return final grade for the class. Letter from A-F, excluding E.
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
      if (currentInput.matches(validCategories)) {
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
          System.out.printf("Your %s average is %s.%n", currentCategory.name(), currentInput);
          gradeTypes[currentCategory.ordinal()] = Float.parseFloat(currentInput) * gradeWeights[currentCategory.ordinal()] / 100;
          break;
        case HOMEWORK:
          int grade = Integer.parseInt(currentInput);
          // Find lowest
          if (minHomework == Integer.MAX_VALUE) minHomework = grade;
          else if (grade < minHomework) minHomework = grade;
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
    System.out.printf("Your homework average is %.2f.%n", homeworkMean);
    gradeTypes[CATEGORY.HOMEWORK.ordinal()] = homeworkMean * gradeWeights[CATEGORY.HOMEWORK.ordinal()] / 100;

    // get percentage for labs
    float labMean = labSum / labs;
    System.out.printf("Your lab average is %.2f.%n", labMean);
    gradeTypes[CATEGORY.LABS.ordinal()] =  labMean * gradeWeights[CATEGORY.LABS.ordinal()] / 100;

    // add percentage for extra credit
    gradeTypes[CATEGORY.EXTRA_CREDIT.ordinal()] = extraCreditSum / 50; // sum * 2 / 100, each extra credit is worth 2%
    System.out.printf("Your extra credit total is %.2f.%n", gradeTypes[CATEGORY.EXTRA_CREDIT.ordinal()]);

    float sum = 0;
    for (float gradePercentage : gradeTypes) sum += gradePercentage; // max of 110
    if (sum < 0 || sum > 110 ) throw new IllegalStateException("WTF why is sum " + sum);

    System.out.printf("Your raw grade is %.1f%%.%n", sum);
    sum = (int) Math.ceil(sum);
    if (sum < 60) finalGrade = 'F';
    else if (sum < 70) finalGrade = 'D';
    else if (sum < 80) finalGrade = 'C';
    else if (sum < 90) finalGrade = 'B';
    else finalGrade = 'A';
    return finalGrade;
  }

  // modified from StackOverflow: https://stackoverflow.com/a/1102916
  private static boolean isPositiveFloat (String s) { return s.matches("\\d+(\\.\\d+)?"); }

}
