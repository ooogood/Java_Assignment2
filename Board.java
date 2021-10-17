import java.util.Random;

public class Board {
	// counts for small, medium, large battleships on this board
	private static int[] m_pCountOfShips = { 3, 2, 1 };
	private static boolean m_bRaderMode = true;
	private Square[][] m_ppSquares;
	private int m_nRow;
	private int m_nCol;
	
// constructor
	public Board( int row, int col ) {
		m_nRow = row;
		m_nCol = col;
		genSquares();
		genShips();
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
		// todo: change these ships to child classes of BattleShip
		// generate small battleships
		for( int i = 0; i < m_pCountOfShips[ 0 ]; ++i ) {
			BattleShip pShip = new BattleShip( 1 );
			randomlyPlaceAShip( pShip );
		}
		// generate medium battleships
		for( int i = 0; i < m_pCountOfShips[ 1 ]; ++i ) {
			BattleShip pShip = new BattleShip( 2 );
			randomlyPlaceAShip( pShip );
		}
		// generate large battleships
		for( int i = 0; i < m_pCountOfShips[ 2 ]; ++i ) {
			BattleShip pShip = new BattleShip( 3 );
			randomlyPlaceAShip( pShip );
		}
	}
// randomly place a ship on the board
	private void randomlyPlaceAShip( BattleShip pShip ) {
		Random rd = new Random();
		int nSize = pShip.getSize();
		// describing the shape of the ship with dots
		//  ex: vertically placed ship with size = 3
		//  -----
		//  --*--
		//  --*--
		//  --*--
		//  -----
		int[] pDotRow = new int[ nSize ];
		int[] pDotCol = new int[ nSize ];
		int nShipHeight, nShipWidth, nShipHeadRow, nShipHeadCol;
		if( rd.nextBoolean() ) {
			// the ship will be vertically placed
			for( int i = 0; i < nSize; ++i ) {
				pDotRow[ i ] = i;
				pDotCol[ i ] = 0;
			}
			nShipHeight = nSize;
			nShipWidth = 1;
		}
		else {
			// the ship will be horizontally placed
			for( int i = 0; i < nSize; ++i ) {
				pDotRow[ i ] = 0;
				pDotCol[ i ] = i;
			}
			nShipHeight = 1;
			nShipWidth = nSize;

		}
		// check if this shape is possible to put on this board
		// if not, then we can assum this board is not big enough and the game is unfair
		if( !checkPossibleShapePlacing( pDotRow, pDotCol, nShipHeight, nShipWidth ) )
			throw new RuntimeException( "The board is not big enough to put so many ships on! Restart the game!" );
		// randomly put this ship into the board
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
//  pDotRow, pDotCol: information of dots that form this shape
//                  ( these two array must have same length )
//  nHeight, nWidth: information of the shape
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
		String retVal = "";
		if( m_bRaderMode ) {
			// print all the ships
			for( int i = 0; i < m_nRow; ++i ) {
				for( int j = 0; j < m_nCol; ++j ) {
					if( m_ppSquares[ i ][ j ].getOccupied() ) {
						retVal += " * ";
					}
					else {
						retVal += " - ";
					}
				}
				retVal += "\n";
			}
		}
		return retVal;
	}
}
