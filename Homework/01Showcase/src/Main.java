import java.util.Random;
import java.util.Scanner;

class Main {
  private static final int ALLOWED_ERROR = 2000, PRIZES_GIVEN = 5;
  private static final String DEFAULT_FILENAME = "prizeList.txt";
  private static final Random random = new Random();

  public static void main(String[] args) {
    String playAgain;
    long guess, sum;
    Scanner keyboard = new Scanner(System.in);

    System.out.println("Welcome to the Showcase Showdown!");

    do {
      Prize[] prizes = randomPrizes();
      System.out.println("Your prizes are:");
      System.out.print(prizeNames(prizes));
      System.out.println("You must guess the total cost of all without going over.");

      System.out.println("Enter a guess.");
      guess = keyboard.nextLong();
      sum = costSum(prizes);
      System.out.printf("You guessed %s and the real cost was %s.%n", guess, sum);
      if (guess <= sum && sum - guess < ALLOWED_ERROR) { System.out.println("You win!"); }
      else { System.out.println("Bad guess. You lose."); }

      System.out.println("Play again? (y/n)");
      playAgain = keyboard.next();
    } while(!playAgain.startsWith("n"));

    System.out.println("Goodbye.");
    keyboard.close();
  }

  private static Prize[] randomPrizes() {
    Prize[] allPrizes = FileIO.readFile(DEFAULT_FILENAME);
    Prize[] prizes = new Prize[PRIZES_GIVEN];

    for (int i = 0; i < PRIZES_GIVEN; i++) {
      prizes[i] = allPrizes[random.nextInt(allPrizes.length)];
    }
    return prizes;
  }

  private static String prizeNames(Prize[] array) {
    StringBuilder sb = new StringBuilder();
    for (Prize p : array) { if (p != null) sb.append(p.name).append('\n'); }
    return sb.toString();
  }

  private static long costSum(Prize[] array) {
    long sum = 0;
    for (Prize p : array) { if (p != null) { sum += p.price; } }
    return sum;
  }
}
