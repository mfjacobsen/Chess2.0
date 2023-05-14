package model;

import java.util.ArrayList;

public class Player
{
	private ChessModel model;
	private String color;
	private ArrayList<Piece> pieces;
	private ArrayList<int[]> indicesToMoveFrom;			// List of indices the player has pieces to move from
	private ArrayList<int[]> indicesThreatened;  	    // A list of indices threatened by the player
	private Piece king;									// The Player's King
	private Player opponent;							// The opposing player
	private boolean isInCheck;							// If the player is in check
	private boolean isInDoubleCheck;					// If two pieces are checking the king
	private Piece checkedBy;							// The Piece putting the Player in check
	private int[] enPassantIndex;						// An index the Player can move to en passant
	private boolean isComputer;
	
	public Player(ChessModel model, String color, String playerColor)
	{
		// Sets the instance variables
		this.color = color;
		this.setModel(model);
		isComputer = !color.equals(playerColor);
		
		// Initializes the ArrayLists
		pieces = new ArrayList<Piece>();
		indicesToMoveFrom = new ArrayList<int[]>();
		indicesThreatened = new ArrayList<int[]>();
	}
	
	/**
	 * Creates a list of new Pieces
	 */
	public void createNewStartingPieces()
	{
		// Clears any Pieces in the list
		if (pieces != null)
			pieces.clear();
		
		// Adds the major pieces to Piece list
		pieces.add(new Rook(this));
		pieces.add(new Knight(this));
		pieces.add(new Bishop(this));
		pieces.add(new Queen(this));
		pieces.add(new King(this));
		pieces.add(new Bishop(this));
		pieces.add(new Knight(this));
		pieces.add(new Rook(this));
		
		// Adds eight pawns to the Piece list
		for (int i = 0; i < 8; i++)
		{
			pieces.add(new Pawn(this));
		}
		
		// sets the players King
		king = pieces.get(4);
	}

	public void determineMoves()
	{		
		// For each of the Player's Pieces
		for (Piece piece : pieces)
		{
			// Determine the moves available
			piece.determineMoves();
		}
	}
	
	/**
	 * Moves a Piece from one space to another
	 * @param fromIndex
	 * @param toIndex
	 */
	public void makeMove(int[] fromIndex, int[] toIndex)
	{
		// Move the Piece on the board
		model.getBoard().movePiece(fromIndex, toIndex);
		
		// Clear possible moves from each Piece
		for (Piece piece: pieces)
			piece.getMoves().clear();
		
		// Clears the coordinates threatened, and indices to move from lists and resets check booleans
		indicesToMoveFrom.clear();
		indicesThreatened.clear();
		isInCheck = false;
		isInDoubleCheck = false;
		enPassantIndex = null;
		
		// For each Piece
		for (Piece piece: pieces)
		{
			// Determine the indices it's threatening
			piece.determineThreats();
			
			// Rest the value of pinned by in the Piece
			piece.setPinnedBy(null);
			
			// Determine of the Piece is pinning an opponent Piece
			piece.determinePins();
		}
	}

	/**
	 * @return the model
	 */
	public ChessModel getModel()
	{
		return model;
	}

	/**
	 * @return the color
	 */
	public String getColor()
	{
		return color;
	}

	/**
	 * @return the pieces
	 */
	public ArrayList<Piece> getPieces()
	{
		return pieces;
	}

	/**
	 * @return the indicesToMoveFrom
	 */
	public ArrayList<int[]> getIndicesToMoveFrom()
	{
		return indicesToMoveFrom;
	}

	/**
	 * @return the indicesThreatened
	 */
	public ArrayList<int[]> getIndicesThreatened()
	{
		return indicesThreatened;
	}

	/**
	 * @return the king
	 */
	public Piece getKing()
	{
		return king;
	}

	/**
	 * @return the opponent
	 */
	public Player getOpponent()
	{
		return opponent;
	}

	/**
	 * @return the isInCheck
	 */
	public boolean isInCheck()
	{
		return isInCheck;
	}

	/**
	 * @return the isInDoubleCheck
	 */
	public boolean isInDoubleCheck()
	{
		return isInDoubleCheck;
	}

	/**
	 * @return the checkedBy
	 */
	public Piece getCheckedBy()
	{
		return checkedBy;
	}

	/**
	 * @return the enPassantIndex
	 */
	public int[] getEnPassantIndex()
	{
		return enPassantIndex;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(ChessModel model)
	{
		this.model = model;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color)
	{
		this.color = color;
	}

	/**
	 * @param pieces the pieces to set
	 */
	public void setPieces(ArrayList<Piece> pieces)
	{
		this.pieces = pieces;
	}

	/**
	 * @param indicesToMoveFrom the indicesToMoveFrom to set
	 */
	public void setIndicesToMoveFrom(ArrayList<int[]> indicesToMoveFrom)
	{
		this.indicesToMoveFrom = indicesToMoveFrom;
	}

	/**
	 * @param indicesThreatened the indicesThreatened to set
	 */
	public void setIndicesThreatened(ArrayList<int[]> indicesThreatened)
	{
		this.indicesThreatened = indicesThreatened;
	}

	/**
	 * @param king the king to set
	 */
	public void setKing(Piece king)
	{
		this.king = king;
	}

	/**
	 * @param opponent the opponent to set
	 */
	public void setOpponent(Player opponent)
	{
		this.opponent = opponent;
	}

	/**
	 * @param isInCheck the isInCheck to set
	 */
	public void setInCheck(boolean isInCheck)
	{
		this.isInCheck = isInCheck;
	}

	/**
	 * @param isInDoubleCheck the isInDoubleCheck to set
	 */
	public void setInDoubleCheck(boolean isInDoubleCheck)
	{
		this.isInDoubleCheck = isInDoubleCheck;
	}

	/**
	 * @param checkedBy the checkedBy to set
	 */
	public void setCheckedBy(Piece checkedBy)
	{
		this.checkedBy = checkedBy;
	}

	/**
	 * @param enPassantIndex the enPassantIndex to set
	 */
	public void setEnPassantIndex(int[] enPassantIndex)
	{
		this.enPassantIndex = enPassantIndex;
	}


	
	
}

