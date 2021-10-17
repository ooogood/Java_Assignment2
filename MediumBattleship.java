public class MediumBattleship extends Battleship {
	private static int m_nMaxMBSCount = 2;
	private static int m_nMBSCount = 0;
	private static int m_nDefaultSize = 2;	
// constructor
	public MediumBattleship() {
		super( m_nDefaultSize );
		if( ++m_nMBSCount > m_nMaxMBSCount ) {
			throw new RuntimeException( "MediumBattleship count exceed max limit! Check your game setting!" );
		}
	}
}