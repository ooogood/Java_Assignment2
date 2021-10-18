import java.util.Scanner;

public class Main {
	public static void main( String args[] ) {
		Scanner sc = new Scanner( System.in );
		Board brd = new Board( 10, 10 );
		// initialize player no.1
		System.out.print( "Greetings, commander no.1. Please enter your name: ");
		String name1 = sc.next();
		Player pyr1 = new Player( brd, name1 );
		// initialize player no.2
		System.out.print( "Greetings, commander no.2. Please enter your name: ");
		String name2 = sc.next();
		Player pyr2 = new Player( brd, name2 );
		System.out.println( "The time has come, commander " + name1 + " and commander " + name2 +
							", initiate the attack!");

		// playing the game
		int nPlayerTurn = 0;
		do {
			System.out.println( brd );
			if( nPlayerTurn == 0 ) {
				if( pyr1.takeTurn() )
					break;
			}
			else {
				if( pyr2.takeTurn() )
					break;
			}
			nPlayerTurn = ( nPlayerTurn + 1 ) % 2;
		} while( true );

		// game ended, show the result
		System.out.println( "Final result: ");
		System.out.println( "Commander " + name1 + ", " + pyr1.getScore() + " points" );
		System.out.println( "Commander " + name2 + ", " + pyr2.getScore() + " points" );
		if( pyr1.getScore() == pyr2.getScore() ) {
			System.out.println( "Draw! There is no winner in wars" );
		}
		else {
			System.out.println( "The winner is: Commander " + 
				( pyr1.getScore() > pyr2.getScore() ? name1 : name2 ) + "!" );
		}
		System.out.println( brd );
		sc.close();
	}
}
