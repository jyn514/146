package Lab01FinalGradeCalculator.src;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class FinalGradeCalculator {

	private static final String validCategories = "LABS|HOMEWORK|EXAM 1|EXAM 2|LAB EXAM 1|LAB EXAM 2";

	private static Scanner file;

	public static void main(String[] args) throws FileNotFoundException {
		float labAve = 0, firstLabExam = 0, HWAve = 0, firstExam = 0, secondExam = 0, secondLabExam = 0, extraWork = 0,
				finalExam = 0, rawTotal = 0;
		char finalGrade;
		String currentInput;
		file = new Scanner(new File("C:\\Users\\Joshua\\Documents\\Programming\\Java\\CS 146\\Labs\\Lab01FinalGradeCalculator\\perfectGrades.txt"));
		currentInput = file.next();
		
		while (currentInput.matches(validCategories)) {
			System.out.println(currentInput);

			switch (currentInput) {
			case "LABS":
				labAve = averageAllNumbers();
				break;
			case "HOMEWORK":
				HWAve = averageAllNumbers();
				break;
			case "EXAM 1":
				firstExam = averageAllNumbers();
				break;
			case "EXAM 2":
				secondExam = averageAllNumbers();
				break;
			case "LAB EXAM 1":
				firstLabExam = averageAllNumbers();
				break;
			case "LAB EXAM 2":
				secondLabExam = averageAllNumbers();
				break;
			case "FINAL":
				finalExam = averageAllNumbers();
				break;
			case "EXTRA CREDIT":
				extraWork = averageAllNumbers();
				break;
			}
			currentInput = file.nextLine();

		}
		file.close();
		System.out.println(labAve);
		System.out.println(firstExam);
		System.out.println(HWAve);
		System.out.println(secondExam);
		System.out.println(firstLabExam);



	}

	private static float averageAllNumbers() { // this REALLY should not be static
		int sum = 0, count = 0;
		while (file.hasNextInt()) {
			sum += file.nextInt();
			count++;
		}
		file.nextLine(); // hacky

		return (float) sum / (float) count;
	}

}
