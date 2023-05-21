package model;

import java.util.ArrayList;

/**
 * Lead Authors:
 *
 * @author Matthew Jacobsen; 5550026131
 * @author Daniel Blasczyk; 5550063899
 *
 * References:
 * 
 * 		Morelli, R., & Walde, R. (2016). 
 * 		Java, Java, Java: Object-Oriented Problem Solving
 * 		Retrieved from https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *
 * 		Gaddis, T. (2015). Starting Out With Java Myprogramming Lab 
 * 		From Control Structures Through Objects. (6th ed.). Addison-Wesley. 
 *
 * Version: 1
 *
 * Responsibilities of class: Defines a player, white or black.
 *
 */
public class Player
{
	private ChessModel model;							// The chess model
	private String color;								// The color of the player
	private ArrayList<Piece> pieces;					// The Player's Pieces
	private ArrayList<int[]> indicesToMoveFrom;			// List of indices the player has pieces to move from
	private ArrayList<int[]> indicesThreatened;  	    // List of indices threatened by the player
	private Piece king;									// The Player's King
	private Player opponent;							// The opposing Player
	private boolean isInCheck;							// If the Player is in check
	private boolean isInDoubleCheck;					// If two Pieces are checking the King
	private Piece checkedBy;							// The Piece putting the Player in check
	private int[] enPassantIndex;						// An index the Player can move to en passant
	private String castlingRights;						// The castling rights of the Player in FEN notation
	
	/**
	 * Constructor.
	 * @param model
	 * @param color
	 */
	public Player(ChessModel model, String color)
	{
		// Sets the instance variables
		this.color = color;
		this.setModel(model);
		
		// Initializes the ArrayLists
		pieces = new ArrayList<Piece>();
		indicesToMoveFrom = new ArrayList<int[]>();
		indicesThreatened = new ArrayList<int[]>();
		
		// Sets the castling rights of the player
		if (color.equals("White"))
			setCastlingRights("KQ");
		else
			setCastlingRights("kq");
	}
	
	/**
	 * Creates a list of new Pieces.
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

	/**
	 * Determines each Piece's moves.
	 */
	public void determineMoves()
	{		
		// Clear the indices to moveFrom list
		indicesToMoveFrom.clear();
		
		// For each of the Player's Pieces
		for (Piece piece : pieces)
		{
			// Determine the moves available
			piece.determineMoves();
		}
	}
	
	/**
	 * Moves a Piece from one space to another.
	 * @param fromIndex the index of the Piece that is moving
	 * @param toIndex the index the Piece is moving to
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
	 * Reset's the player for a new game.
	 */
	public void resetPlayer()
	{
		// Resets the Player's fields
		indicesToMoveFrom.clear();
		indicesThreatened.clear();
		isInCheck = false;
		isInDoubleCheck = false;
		checkedBy = null;
		enPassantIndex = null;
		
		// Resets the castling rights of the Player
		if (color.equals("White"))
			setCastlingRights("KQ");
		else
			setCastlingRights("kq");
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

	/**
	 * @return the castlingRights
	 */
	public String getCastlingRights()
	{
		return castlingRights;
	}

	/**
	 * @param castlingRights the castlingRights to set
	 */
	public void setCastlingRights(String castlingRights)
	{
		this.castlingRights = castlingRights;
	}
}

