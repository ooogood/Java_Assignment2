import java.util.Scanner;

public class Player {
	private Scanner m_pScan = new Scanner( System.in );
	private Board m_pBoard;
	private String m_sName;
	private int m_nScore = 0;
// constructor
	public Player( Board pBoard, String sName ) {
		m_pBoard = pBoard;
		m_sName = sName;

	}
// take turn in the battleship game
// return if the game is over
	public boolean takeTurn() {
		// prompt player to input their guess
		System.out.print( "Commander " + m_sName + ", choose a target to hit(row col):" );
		String userInput = m_pScan.nextLine();
		// bonus: cheat code
		if( userInput.equals( "marco polo" ) ) {
			m_pBoard.setRaderMode( true );
			System.out.println( "(Commander " + m_sName + " sacrificed his dignity and summoned the hero spirit of the almighty Marco Polo.)" );
			return false;
		}
		// parse user input to row and col
		// we can assume the commander is smart enough to input in correct form and within bounds
		int nRow, nCol;
		try {
			String[] sInput = userInput.split( " " );
			nRow = Integer.parseInt( sInput[ 0 ] );
			nCol = Integer.parseInt( sInput[ 1 ] );
		}
		catch ( Exception e ) {
			System.out.println( "Input format wrong..." );
			return false;
		}

		// check if it's a legal firing
		if( !m_pBoard.isLegalFiring( nRow, nCol ) ) {
			// case: invalid input
			System.out.println( "[Invalid] Commander " + m_sName + " fired at an illegal location!" );
			System.out.println( "(Commander " + m_sName + " is about to be demoted.)" );
			return false;
		}
		// successful firing
		return onFiring( nRow, nCol );
	}
// actions after the player successfully execute a firing
// return whether the game is over
	private boolean onFiring( int nRow, int nCol ) {
		int nSunkShipCountBefore = m_pBoard.getSunkShipCount();
		if( m_pBoard.fireAt( nRow, nCol ) ) {
			// case: hit
			System.out.println( "[Hit] Commander " + m_sName + " hit a ship at ( " + nRow + ", " + nCol + " )! " );
			System.out.println( "(Blood and wreckage rising to the blue sea surface.)");
			// update player's score
			m_nScore += ( m_pBoard.getSunkShipCount() - nSunkShipCountBefore );
			// check if the game is over
			if( m_pBoard.hasFinished() ) {
				// case: game over
				System.out.println( "[Finished] Commander " + m_sName + " has sunk the last ship!" );
				return true;
			}
		}
		else {
			// case: miss
			System.out.println( "[Missed] Commander " + m_sName + " hit nothing at ( " + nRow + ", " + nCol + " )" );
			System.out.println( "(Enemy soldiers' laughter coming through our sonar.)");
		}
		return false;
	}
// score getter method
	public int getScore() {
		return m_nScore;
	}
}
