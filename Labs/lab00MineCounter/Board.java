import java.util.Random;

public class Board {
	public int x;
	public int y;
	public int mines;
	public char[][] board;

	private transient int minesPlaced;
	private transient int bordering;
	private Random random;
	private int probability;

	Board() {
		new Board(10, 10, 10);
	}

	Board(int xDimension, int yDimension, int numberOfMines) {
		minesPlaced = 0;
		random = new Random();
		this.x = xDimension;
		this.y = yDimension;
		this.mines = numberOfMines;
		probability = 2 * (x * y / mines); // on average, will fill board in two passes
		board = new char[x][y];
		createMines();
		createNumbers();
	}

	public void print() {
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y - 1; j++) {
				System.out.print(String.format(board[i][j] + " | "));
			}
			System.out.println(board[i][y - 1]); // print the last character in board[i]

		}
	}

	private void createMines() {
		while (minesPlaced < mines) {
			for (int i = 0; i < x; i++) {
				for (int j = 0; j < y; j++) {
					if (minesPlaced >= mines) // don't loop through the whole board if it's already full
						return;
					else if (board[i][j] != '*' && random.nextInt(probability) == 0) {
						board[i][j] = '*';
						minesPlaced++;
					}
				}
			}
		}
	}

	private void createNumbers() {
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				if (board[i][j] != '*') {
					board[i][j] = findMinesBordering(i, j);
				}
			}
		}
	}

	private char findMinesBordering(int i, int j) {
		bordering = 0;
		for (int k = i - 1; k <= i + 1; k++) {
			if (k < 0)
				continue;
			if (k >= x)
				break;
			for (int l = j - 1; l <= j + 1; l++) {
				if (l < 0)
					continue;
				if (l >= y)
					break;
				if (board[k][l] == '*') {
					bordering++;
				}
			}
		}
		return Integer.toString(bordering).charAt(0);
	}

}
