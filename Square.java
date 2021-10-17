public class Square {
	private int m_nRow = 0;
	private int m_nCol = 0;
	private boolean m_bOccupied = false;
	private boolean m_bFired = false;
	private Battleship m_pShip;
// constructor
	public Square( int nRow, int nCol ) {
		m_nRow = nRow;
		m_nCol = nCol;
	}
// bFired getter method
	public boolean getFired() {
		return m_bFired;
	}
// called when a player fire at this square
// return true if hit a ship
	public boolean fired() {
		// for now it is impossible to hit a sunk ship again
		//   because player have to hit every squares of a ship to sunk it
		if( m_bFired || hasSunkShip() )
			throw new RuntimeException( "A square was illegally fired!" );
		// update fired flag
		m_bFired = true;
		// if there is a ship on this square, the ship is hit
		if( m_bOccupied == true ) {
			m_pShip.hit();
			return true;
		}
		else
			return false;
	}
// Occupied getter method
	public boolean getOccupied() {
		return m_bOccupied;
	}
// Occupied by a ship
	public void Occupied( Battleship pShip ) {
		if( m_bOccupied )
			throw new RuntimeException( "Occupying an occupied sqaure!" );
		m_bOccupied = true;
		m_pShip = pShip;
	}
// if this square has a sunk ship
	public boolean hasSunkShip() {
		return m_bOccupied && m_pShip.getSunk();
	}
// default toString method
	public String toString() {
		return toString( false );
	}
// toString( is rader mode on ? )
	public String toString( boolean bRaderMode ) {
		if( m_bFired ) {
			if( m_bOccupied )
				return String.format( "%3c", 'x' );
			else
				return String.format( "%3c", 'o' );
		}
		else {
			if( bRaderMode && m_bOccupied )
				return String.format( "%3c", '*' );
			else
				return String.format( "%3c", '-' );
		}
	}
}