import java.util.Scanner;

public class Main {
	public static void main( String args[] ) {
		Scanner sc = new Scanner( System.in );
		Board brd = new Board( 10, 10 );
		// Board brd = new Board( 4, 4 );
		System.out.print( "Greetings, commander. Please enter your name: ");
		String name = sc.next();
		Player pyr = new Player( brd, name );
		System.out.println( "The time has come, commander " + name +", initiate the attack!");

		// playing the game
		do {
			System.out.println( brd );
		} while( !pyr.takeTurn() );

		// game ended
		System.out.println( "Final result: ");
		System.out.println( brd );
		sc.close();
	}
}
