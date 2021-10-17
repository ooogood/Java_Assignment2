public class LargeBattleship extends Battleship {
	private static int m_nMaxLBSCount = 1;
	private static int m_nLBSCount = 0;
	private static int m_nDefaultSize = 3;	
// constructor
	public LargeBattleship() {
		super( m_nDefaultSize );
		if( ++m_nLBSCount > m_nMaxLBSCount ) {
			throw new RuntimeException( "MediumBattleship count exceed max limit! Check your game setting!" );
		}
	}
}
