package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.FileSystems.getDefault;
import static java.nio.file.Files.newBufferedReader;

public final class FinalGradeCalculator {

	private static final String validCategories = "LABS|HOMEWORK|EXAM 1|EXAM 2|LAB EXAM 1|LAB EXAM 2|FINAL|EXTRA CREDIT";
	private static final String perfectGradesFile = "perfectGrades.txt";
  private static final String imperfectGradesFile = "grades.txt";
  private static final int[] gradeWeights = new int[] { 20, 30, 10, 10, 10, 10, 10 }; // extra credit NOT included

  private FinalGradeCalculator() { throw new IllegalStateException("Not allowed." ); } // cannot be initialized

  public static void main(String[] args) {
    for (Path p : new Path[] { getDefault().getPath(perfectGradesFile), getDefault().getPath(imperfectGradesFile) }) {
      System.out.printf("Calculating grades for %s...%n", p.toString());
      try { System.out.printf("%c.%n", getFinalGrade(p)); }
      catch (IOException e) { e.printStackTrace(); }
    }
	}

	public enum CATEGORY {
    LABS, HOMEWORK, EXAM_1, EXAM_2, LAB_EXAM_1, LAB_EXAM_2, FINAL, EXTRA_CREDIT
  }

	private static char getFinalGrade(Path file) throws IOException {

    final char finalGrade;
    final float[] gradeTypes = new float[CATEGORY.values().length];
    final BufferedReader reader = newBufferedReader(file); // throws IOException
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
          gradeTypes[currentCategory.ordinal()] = (int) Math.ceil(
              Float.parseFloat(currentInput) / 100 * gradeWeights[currentCategory.ordinal()]);
          break;
        case LABS:
        case HOMEWORK:
        case EXTRA_CREDIT:
          System.out.printf("%ss are not yet handled.%n", currentCategory.toString());

      }
    }
    reader.close();

    int sum = 0;
    for (int grade : gradeTypes) sum += grade;

    if (sum < 60) finalGrade = 'F';
    else if (sum < 70) finalGrade = 'D';
    else if (sum < 80) finalGrade = 'C';
    else if (sum < 90) finalGrade = 'B';
    else finalGrade = 'A';
    return finalGrade;
  }

  /**
   * @param homeworkGrades List of grades in percentage form (1-100)
   * @return Appropriate overall homework grade in percentage form (1-100)
   */
  private static int calculateHomeworkGrade(int[] homeworkGrades) {
    // Find lowest
    int min = homeworkGrades[0];
    int sum = 0;
    for (int grade : homeworkGrades) {
      if (grade < min) min = grade;
      sum += grade;
    }
    // drop lowest grade, return percentage
    return (int) Math.ceil((sum - min) / (homeworkGrades.length - 1));
  }

  private static int calculateLabGrade(int[] labGrades) {
    int sum = 0;
    for (int grade : labGrades) sum += grade;
    return (int) Math.ceil(sum / labGrades.length);
  }

  /**
   * @param extraCreditGrades List of grades in percentage form (1-100)
   * @return Absolute fraction extra credit will be of overall grade (2% for each original 100%)
   */
  private static int calculateExtraCredit(int[] extraCreditGrades) {
    int sum = 0;
    for (int grade : extraCreditGrades) sum += grade;
    return sum / 50; // sum * 2 / 100
  }

  private static boolean isPositiveFloat (String s) {
    return s.matches("\\d+(\\.\\d+)?"); // modified from StackOverflow: https://stackoverflow.com/a/1102916
  }

}
