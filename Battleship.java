public class Battleship {
	private boolean m_bSunk = false;
	private int m_nHealth = 0;
	private int m_nSize = 0;
// constructor
	public Battleship( int size ) {
		// rule: battleship's health equals to batlleship's size
		m_nSize = size;
		m_nHealth = m_nSize;
	}
// called when this ship get hit
	public void hit() {
		if( m_bSunk == true ) 
			throw new RuntimeException( "Battleship hit after it was sunk" );
		if( --m_nHealth <= 0 )
			m_bSunk = true;
	}
// nSize getter method
	public int getSize() {
		return m_nSize;
	}
// m_bSunk getter method
	public boolean getSunk() {
		return m_bSunk;
	}
}
