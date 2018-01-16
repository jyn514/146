
public class MineCounter {
	
	public static void main (String[] args) {
	  java.util.Scanner keyboard = new java.util.Scanner(System.in);
		Board board = new Board(10, 10, 10);
		System.out.println(board);
		
		while(true) {
		  System.out.println("making a new board");
		
		  int[] vars = getVariables(keyboard);
		  
		  board = new Board(vars[0], vars[1], vars[2]);
		  System.out.println(board);
		}
	}
	
	public static int[] getVariables(java.util.Scanner keyboard) {
	  int[] vars = new int[3];
	  
	  System.out.println("Enter x dimension");
    vars[0] = keyboard.nextInt();
    System.out.println("Enter a y dimension.");
    vars[1] = keyboard.nextInt();
    
    System.out.println("Enter number of mines");
    vars[2] = keyboard.nextInt();

    if(vars[0] * vars[1] < vars[2]) {
      System.out.println("More mines than squares.");
      return getVariables(keyboard);
    }
    
	  return vars;
	}

}
