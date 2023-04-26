package model;

import java.util.ArrayList;

public abstract class Piece
{
	// Instance variable declarations
	private Player player;					// The Player the Piece belongs to
	private Board board;					// The Board the Piece is on
	private int[] index;					// The index of the Piece on the board
	private boolean hasMoved;				// If the Piece has moved yet
	private boolean isPromoting;			// If the Piece is promoting 
	private Piece pinnedBy;					// The Piece that this Piece is pinned by
	private ArrayList<int[]> moves; 			// List of indices the Piece can move to
	private ArrayList<int[]> threats; 			// List of indices the Piece is threatening
	private ArrayList<int[]> lineOfAttack;	  	// List of indices indicating the line of attack on the opponent King
	private ArrayList<int[]> pinnedLineOfAttack;// List of indices indicating the line of attack on a pinned Piece
	
	/**
	 * Constructor
	 * @param player the Player the piece belongs to
	 */
	public Piece(Player player)
	{
		// Sets the instance variables
		this.setPlayer(player);
		board = player.getModel().getBoard();
		hasMoved = false;
		pinnedBy = null;
		
		// Initializes the ArrayLists
		moves = new ArrayList<int[]>();
		threats = new ArrayList<int[]>();
		lineOfAttack = new ArrayList<int[]>();
		pinnedLineOfAttack = new ArrayList<int[]>();
		
	}

	public void addMove(int[] index)
	{
		// If the Player is in DoubleCheck
		if (player.isInDoubleCheck())
		{
			// Only the King can move
			if (this instanceof King)
			{
				// Confirms the move
				confirmMove(index);
			}
		}
		
		// If the Player is in Check
		else if (player.isInCheck())
		{
			// The King can move
			if (this instanceof King)
				
				//Confirms the move
				confirmMove(index);
			
			// Else if the move blocks the line of attack of the checking Piece and the Piece is not pinned
			else if (ChessModel.checkContains(index, getPlayer().getCheckedBy().getLineOfAttack()) && pinnedBy == null)
				
				//Confirms the move
				confirmMove(index);
		}
		
		// If the Piece is not pinned (and Player not in check)
		else if (pinnedBy == null)
			
			//Confirms the move
			confirmMove(index);

		
		// If the Piece is pinned, but the desired move is in the pinning piece's line of attack
		else if (ChessModel.checkContains(index, pinnedBy.getPinnedLineOfAttack()))
				
			//Confirms the move
			confirmMove(index);
				
	}
	
	public void confirmMove(int[] index)
	{
		// Add the move to the Pieces move
		moves.add(index);
		
		// If the Player doesn't already have the index of this Piece in their list to move
		if(!ChessModel.checkContains(this.index, player.getIndicesToMoveFrom()))
			// Add the index of this Piece to the list
			player.getIndicesToMoveFrom().add(this.index);
	}
	
	public void addThreatened(int[] index)
	{
		// Adds the index to the Piece's list of threats
		threats.add(index);
		
		// If the Player doesn't already have this space in their list of indices threatened
		// Add the index to the list of indices threatened
		if(!ChessModel.checkContains(index, player.getIndicesThreatened()))
			player.getIndicesThreatened().add(index);
		
		// If the space threatened contains the opponent's King
		if (getBoard().getPiece(index) == getPlayer().getOpponent().getKing())
		{
			// If the opponent is not already in check
			if (!getPlayer().getOpponent().isInCheck())
			{
				// Sets the opponent in check
				getPlayer().getOpponent().setInCheck(true);
				
				// Sets this Piece as the checking Piece in the opponent
				getPlayer().getOpponent().setCheckedBy(this);
				
				// Determines the line of attack on the opponent King
				determineLineOfAttack();
			}
			
			// Else the opponent is already in check
			else
				// Sets the opponent in double check
				getPlayer().getOpponent().setInDoubleCheck(true);
		}
	}
	
	/**
	 * Determines the moves the Piece can make
	 */
	public abstract void determineMoves();
	
	/**
	 * Determines the indices the Piece is threatening
	 * @return 
	 */
	public abstract ArrayList<int[]> determineThreats();
	
	/**
	 * Determines the line of attack a Piece has on the opponents King
	 */
	public abstract void determineLineOfAttack();
	
	/**
	 * Determines if the Piece is pinning an opponent Piece
	 */
	public abstract void determinePins();

	/**
	 * @return the player
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * @return the board
	 */
	public Board getBoard()
	{
		return board;
	}

	/**
	 * @return the index
	 */
	public int[] getIndex()
	{
		return index;
	}

	/**
	 * @return  hasMoved
	 */
	public boolean hasMoved()
	{
		return hasMoved;
	}

	/**
	 * @return the pinnedBy
	 */
	public Piece getPinnedBy()
	{
		return pinnedBy;
	}

	/**
	 * @return the moves
	 */
	public ArrayList<int[]> getMoves()
	{
		return moves;
	}

	/**
	 * @return the threats
	 */
	public ArrayList<int[]> getThreats()
	{
		return threats;
	}

	/**
	 * @return the lineOfAttack
	 */
	public ArrayList<int[]> getLineOfAttack()
	{
		return lineOfAttack;
	}

	/**
	 * @return the pinnedLineOfAttack
	 */
	public ArrayList<int[]> getPinnedLineOfAttack()
	{
		return pinnedLineOfAttack;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player)
	{
		this.player = player;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(Board board)
	{
		this.board = board;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int[] index)
	{
		this.index = index;
	}

	/**
	 * @param hasMoved the hasMoved to set
	 */
	public void setMoved(boolean hasMoved)
	{
		this.hasMoved = hasMoved;
	}

	/**
	 * @param pinnedBy the pinnedBy to set
	 */
	public void setPinnedBy(Piece pinnedBy)
	{
		this.pinnedBy = pinnedBy;
	}

	/**
	 * @param moves the moves to set
	 */
	public void setMoves(ArrayList<int[]> moves)
	{
		this.moves = moves;
	}

	/**
	 * @param threats the threats to set
	 */
	public void setThreats(ArrayList<int[]> threats)
	{
		this.threats = threats;
	}

	/**
	 * @param lineOfAttack the lineOfAttack to set
	 */
	public void setLineOfAttack(ArrayList<int[]> lineOfAttack)
	{
		this.lineOfAttack = lineOfAttack;
	}

	/**
	 * @param pinnedLineOfAttack the pinnedLineOfAttack to set
	 */
	public void setPinnedLineOfAttack(ArrayList<int[]> pinnedLineOfAttack)
	{
		this.pinnedLineOfAttack = pinnedLineOfAttack;
	}

	/**
	 * @return the isPromoting
	 */
	public boolean isPromoting()
	{
		return isPromoting;
	}

	/**
	 * @param isPromoting the isPromoting to set
	 */
	public void setPromoting(boolean isPromoting)
	{
		this.isPromoting = isPromoting;
	}
}
