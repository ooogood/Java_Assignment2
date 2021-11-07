import java.util.Random;

public class Board {
	// counts for small, medium, large battleships on this board
	private static int[] m_pCountOfShips = { 3, 2, 1 };
	private Square[][] m_ppSquares;
	private int m_nShipCount = 0;
	private int m_nSunkShipCount = 0;
	private int m_nRow;
	private int m_nCol;
	// rader mode can show all ships on the board!
	private boolean m_bRaderMode = false;
	
// constructor
	public Board( int row, int col ) {
		m_nRow = row;
		m_nCol = col;
		genSquares();
		genShips();
	}
// is firing at the certain square legal?
	public boolean isLegalFiring( int nRow, int nCol ) {
		return ( nRow >= 0 && nRow < m_nRow ) &&
			   ( nCol >= 0 && nCol < m_nCol ) &&
				!m_ppSquares[ nRow ][ nCol ].getFired();
	}
// fire at a square
// return true if it hit a ship
	public boolean fireAt( int nRow, int nCol ) {
		if( m_ppSquares[ nRow ][ nCol ].fired() ) {
			// hit a ship
			if( m_ppSquares[ nRow ][ nCol ].hasSunkShip() ) 
				// update sunk ship count
				++m_nSunkShipCount;
			return true;
		}
		else
			return false;
	}
// get total ship count
	public int getShipCount() {
		return m_nShipCount;
	}
// get sunk ship count
	public int getSunkShipCount() {
		return m_nSunkShipCount;
	}
// has the game finished?
	public boolean hasFinished() {
		return m_nShipCount == m_nSunkShipCount;
	}
// set rader mode
	public void setRaderMode( boolean bRaderMode ) {
		m_bRaderMode = bRaderMode;
	}
// generate square objects on this board
	private void genSquares() {
		m_ppSquares = new Square[ m_nRow ][ m_nCol ];
		for( int i = 0; i < m_nRow; ++i ) {
			for( int j = 0; j < m_nCol; ++j ) {
				m_ppSquares[ i ][ j ] = new Square( i, j );
			}
		}
	}
// generate battleship objects on this board
	private void genShips() {
		// generate small battleships
		Battleship pShip;
		for( int i = 0; i < m_pCountOfShips[ 0 ]; ++i ) {
			pShip = new SmallBattleship();
			randomlyPlaceAShip( pShip );
			++m_nShipCount;
		}
		// generate medium battleships
		for( int i = 0; i < m_pCountOfShips[ 1 ]; ++i ) {
			pShip = new MediumBattleship();
			randomlyPlaceAShip( pShip );
			++m_nShipCount;
		}
		// generate large battleships
		for( int i = 0; i < m_pCountOfShips[ 2 ]; ++i ) {
			pShip = new LargeBattleship();
			randomlyPlaceAShip( pShip );
			++m_nShipCount;
		}
	}
// randomly place a ship on the board
	private void randomlyPlaceAShip( Battleship pShip ) {
		Random rd = new Random();
		int nSize = pShip.getSize();
		// describing the shape of the ship with dots
		int[] pDotRow = new int[ nSize ];
		int[] pDotCol = new int[ nSize ];
		int nShipHeight, nShipWidth, nShipHeadRow, nShipHeadCol;
		// randomly decide if the ship is vertically or horizontally placed
		if( rd.nextBoolean() ) {
			// the ship will be vertically placed
			// ex:
			//   -----
			//   --*--
			//   --*--
			//   --*--
			//   -----
			for( int i = 0; i < nSize; ++i ) {
				pDotRow[ i ] = i;
				pDotCol[ i ] = 0;
			}
			nShipHeight = nSize;
			nShipWidth = 1;
		}
		else {
			// the ship will be horizontally placed
			// ex:
			//   -------
			//   --***--
			//   -------
			for( int i = 0; i < nSize; ++i ) {
				pDotRow[ i ] = 0;
				pDotCol[ i ] = i;
			}
			nShipHeight = 1;
			nShipWidth = nSize;

		}
		// check if this shape is possible to put on this board
		// if not, then we can assume this board is not big enough and the game is too easy
		if( !checkPossibleShapePlacing( pDotRow, pDotCol, nShipHeight, nShipWidth ) )
			throw new RuntimeException( "The board is too small! Check your game setting!" );
		// randomly choose a location to put this ship into the board
		do {
			nShipHeadRow = rd.nextInt( m_nRow - nShipHeight + 1 );
			nShipHeadCol = rd.nextInt( m_nCol - nShipWidth + 1 );
		} while( !checkSingleSlotFit( nShipHeadRow, nShipHeadCol, pDotRow, pDotCol ) );
		// notify each square in the shape
		for( int i = 0; i < nSize; ++i ) {
			m_ppSquares[ nShipHeadRow + pDotRow[ i ] ]
					   [ nShipHeadCol + pDotCol[ i ] ].Occupied( pShip );
		}
	}
// check if this shape is possible to put on this board
// to prevent infinite loop in randomized shape placing
//   pDotRow, pDotCol: information of dots that form this shape
//                   ( these two array must have the same length )
//   nHeight, nWidth: information of the shape
	private boolean checkPossibleShapePlacing( int[] pDotRow, int[] pDotCol, int nHeight, int nWidth ) {
		for( int i = 0; i < m_nRow - nHeight + 1; ++i ) {
			for( int j = 0; j < m_nCol - nWidth + 1; ++j ) {
				if( checkSingleSlotFit( i, j, pDotRow, pDotCol ) )
					// found a slot that can fit this shape in, return true
					return true;
			}
		}
		// cannot find a slot that can fit this shape in, return false
		return false;
	}
// check if this shape can fit in the slot that represented by a head cell [nRow, nCol]
	private boolean checkSingleSlotFit( int nRow, int nCol, int[] pDotRow, int[] pDotCol ) {
		for( int i = 0; i < pDotRow.length; ++i ) {
			if( m_ppSquares[ nRow + pDotRow[ i ] ][ nCol + pDotCol[ i ] ].getOccupied() ) {
				// one of the squares is occupied, we cannot fit the shape in this slot
				return false;
			}
		}
		return true;
	}
// return the board string
	public String toString() {
		String retVal = "   ";
		// print header
		for( int i = 0; i < m_nCol; ++i ) {
			retVal += String.format( "%3d", i );
		}
		retVal += "\n";
		// print all the squares
		for( int i = 0; i < m_nRow; ++i ) {
			retVal += String.format( "%3d", i );
			for( int j = 0; j < m_nCol; ++j ) {
				retVal += m_ppSquares[ i ][ j ].toString( m_bRaderMode );
			}
			retVal += "\n";
		}
		return retVal;
	}
}
