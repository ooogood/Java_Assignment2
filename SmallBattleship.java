public class SmallBattleship extends Battleship {
	private static int m_nMaxSBSCount = 3;
	private static int m_nSBSCount = 0;
	private static int m_nDefaultSize = 1;	
// constructor
	public SmallBattleship() {
		super( m_nDefaultSize );
		if( ++m_nSBSCount > m_nMaxSBSCount ) {
			throw new RuntimeException( "SmallBattleship count exceed max limit! Check your game setting!" );
		}
	}
}
