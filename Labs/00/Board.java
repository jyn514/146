/**
 * Copyright © 2017 Joshua Nelson
 * Licensed under the GNU GPL; see http://www.gnu.org/licenses/gpl.html for details.
 *
 * Essentially, you may modify, distribute, and use this program freely,
 * but you may not sell it or make changes to the license, and you MUST
 * make any changes available AS SOURCE CODE to end users.
 *
 * Board class: Extremely stateful. Anything not marked as static should not be static.
 * All methods O(columns * mines), excluding equalParameters
 */

import java.util.Random;

public class Board {
  public final int rows;
  public final int columns;
  public final int mines;
  public final char[][] board;

  // (1 / probability) gives percent chance a given space will be marked as a mine
  private final int probability;

  Board() { this(10, 10, 10); }

  Board(int rows, int columns, int numberOfMines) throws IllegalArgumentException {
   // set rows
   if (rows <= 0) throw new IllegalArgumentException("Rows must be greater than 0.");
   this.rows = rows;
   // set columns
   if (columns <= 0) throw new IllegalArgumentException("Columns must be greater than 0.");
   this.columns = columns;
   // set mines
   if (numberOfMines < 0) throw new IllegalArgumentException(
      "Mines must be greater than or equal to 0.");
   if (numberOfMines > rows * columns) throw new IllegalArgumentException(
      "Mines must be less than or equal to number of spaces");
   this.mines = numberOfMines;

   // on average, will fill board in two passes
    if (mines > 0) { probability = 2 * (rows * columns / mines);  }
   else { probability = 0; }
    board = new char[rows][columns];
    createMines();
    createNumbers();
  }

  public String toString() {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns - 1; j++) result.append(board[i][j]).append(" | ");
      result.append(board[i][columns - 1]); // don't end line with '|'
      result.append('\n');
    }
    return result.toString();
  }

  public boolean equals(Board b) {
     for (int i = 0; i < b.rows; i++) {
      for (int j = 0; j < b.columns; j++) {
         if (b.board[i][j] != board[i][j]) return false;
      }
     }
     return true;
  }

 // O(k), something very low
  public boolean equalParameters(Board b) {
     return b.rows == rows && b.columns == columns && b.mines == mines;
  }

  private void createMines() {
   int minesPlaced = 0;
    Random random = new Random();
    while (minesPlaced < mines) {
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
          if (minesPlaced >= mines) return; // don't loop through the whole board if it's already full
          else if (board[i][j] != '*' && random.nextInt(probability) == 0) {
             board[i][j] = '*';
             minesPlaced++;
          }
        }
      }
    }
  }

  private void createNumbers() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (board[i][j] != '*') board[i][j] = Integer.toString(findMinesBordering(i, j)).charAt(0);
      }
    }
  }

  private int findMinesBordering(int i, int j) {
    int bordering = 0;
    for (int k = i - 1; k <= i + 1; k++) {
      if (k < 0)     continue;
      if (k >= rows) break;
      for (int l = j - 1; l <= j + 1; l++) {
        if (l < 0)              continue;
        if (l >= columns)       break;
        if (board[k][l] == '*') bordering++;
      }
    }
    return bordering;
  }

}
