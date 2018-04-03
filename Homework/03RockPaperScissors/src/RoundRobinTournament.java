import java.util.Random;
import java.util.Scanner;

public class RoundRobinTournament {
	private static final String[] names = new String[] {
		"Bert", "Ernie", "Piggy", "Kermit", "Fozzie", "Gonzo", "Scooter", "Beaker", "Clifford",
		"Pepe the King Prawn", "Animal", "Walter", "Dr. Bunsen Honeydew", "Camila", "Statler", "Waldorf",
		"Rizzo the Rat", "Swedish Chef", "Sam Eagle", "Dr. Teeth and the Electric Mayham",
		"Rowlf the Dog", "Bobo the Bear", "Crazy Harry" };

	private static final Random rand = new Random();

	private enum OUTCOME {
			ROCK, PAPER, SCISSORS
	}

	private static final CircularDoubleLinkedList<OUTCOME> relationships = new CircularDoubleLinkedList<>(
			OUTCOME.ROCK, OUTCOME.PAPER, OUTCOME.SCISSORS);

	public static void main(String[] args) {
		System.out.println("Welcome to Rock Paper Scissors!");
		new RoundRobinTournament();
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter number of players for a tournament: ");
		String input;
		int toPlay;
		while(!(input = keyboard.next()).equals("quit") && isNumeric(input)) {
			toPlay = Integer.parseInt(input);
			if (toPlay < 0) break;
			new RoundRobinTournament(toPlay);
			System.out.print("Enter number of players for a tournament: ");
		}
		keyboard.close();
	}

	private StringBuilder result = new StringBuilder();

	private RoundRobinTournament() { this(10); }

	private RoundRobinTournament(int players) {
		Player[] playerList = new Player[players];
		final String[] playerNames = randomNames(players);
		for (int i = 0; i < players; i++) {
			playerList[i] = new Player(playerNames[i]);
		}

		for (int i = 0; i < players; i++) {
			for (int j = i + 1; j < players; j++) {
				playGame(playerList[i], playerList[j]);
			}
		}
		for (Player p : playerList) {
			result.append(String.format("Name: %s\tWins: %d\tTies: %d\tLosses: %d%n",
				p.name, p.wins, p.ties, p.losses));
		}
		System.out.print(result);
	}

	private void playGame(Player one, Player two) {
		OUTCOME o1 = OUTCOME.values()[rand.nextInt(3)];
		OUTCOME o2 = OUTCOME.values()[rand.nextInt(3)];
		relationships.goTo(o1);

		if (o1.equals(o2)) {
			one.ties++;
			two.ties++;
			result.append(String.format(
				"%s would have played with %s, but they were too busy eating chicken. TIE%n", one.name, two.name));
		} else if (relationships.current.nextLink.data.equals(o2)) {
			two.wins++;
			one.losses++;
			result.append(String.format("%s got %s %s. LOSS for %s%n",
				one.name, gameResult(o2), two.name, one.name));
		} else {
			two.losses++;
			one.wins++;
			result.append(String.format("%s got %s %s. WIN for %s%n",
				two.name, gameResult(o1), one.name, one.name));
		}
	}

	private static String gameResult(OUTCOME outcome) {
		switch(outcome) {
			case ROCK: return "clobbered by";
			case PAPER: return "smothered by";
			case SCISSORS: return "cut in half by";
			default: return "destroyed by a cosmic neutrino flying through a black hole along with";
		}
	}

	private static String[] randomNames(int length) {
		String[] result = new String[length];
		String currentName;
		int j = 0;
		for (int i = 0; i < length; i++) {
			if (i < names.length) {
				do {
					currentName = randomName();
				} while (contains(result, currentName));
			} else {
				currentName = "Player " + j;
				j++;
			}
			result[i] = currentName;
		}
		return result;
	}

	private static String randomName() {
		return names[rand.nextInt(names.length)];
	}

	private static <T> boolean contains(T[] array, T member) {
		for (T t : array) {
			if (t == null) continue;
			if (t.equals(member)) return true;
		}
		return false;
	}

	private static boolean isNumeric (String input) {
		return !input.matches("[^0-9]+");
	}
}
