public class Square {
	private int m_nRow = 0;
	private int m_nCol = 0;
	private boolean m_bOccupied = false;
	private boolean m_bFired = false;
	private BattleShip m_pShip;
// constructor
	public Square( int nRow, int nCol ) {
		m_nRow = nRow;
		m_nCol = nCol;
	}
// called when a player fire at this square
// return if action is successful
	public boolean fired() {
		// if this square is fired, action fail
		if( m_bFired == true )
			return false;
		// if there is a ship on this square, the ship is hit
		if( m_bOccupied == true )
			m_pShip.hit();
		return true;
	}
// Occupied getter method
	public boolean getOccupied() {
		return m_bOccupied;
	}
// Occupied by a ship
	public void Occupied( BattleShip pShip ) {
		if( m_bOccupied )
			throw new RuntimeException( "Occupying an occupied sqaure!" );
		m_bOccupied = true;
		m_pShip = pShip;
	}
// bFired getter method
	public boolean getFired() {
		return m_bFired;
	}
// bFired setter method
	public void setFired( boolean bFired) {
		m_bFired = bFired;
	}
// pShip getter method
	public BattleShip getShip() {
		return m_pShip;
	}
// pShip setter method
	public void setShip( BattleShip pShip) {
		m_pShip = pShip;
	}
}