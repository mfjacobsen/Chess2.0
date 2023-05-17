package model;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ChessModel
{
	// Instance variable declarations
	private Board board;
	private Player white;
	private Player black;
	private String playerColor;			
	private boolean isOver;        		// Boolean for determining when the game is over	
	private int turnCounter;			// Counter used to determine whose turn it is
	
	
	public ChessModel()
	{		
		// Initializes the Players
		white = new Player(this, "White", playerColor);
		black = new Player(this, "Black", playerColor);
		
		// Sets the opponents against each other
		white.setOpponent(black);
		black.setOpponent(white);
		
		// Starts a new game
		newGame();
	}
	
	/**
	 * Creates a new game
	 */
	public void newGame()
	{
		//Sets the turn counter
		turnCounter = 0;
		
		//Sets isOver to false
		isOver = false;
		
		// Initializes a new board
		board = new Board();
		
		white.resetPlayer();
		black.resetPlayer();
		
		// Creates White's starting Pieces
		white.createNewStartingPieces();
		black.createNewStartingPieces();
		
		// Places the starting Pieces
		placeStartingPieces();
		
		// White determines their starting moves
		white.determineMoves();
		}
	
	public void placeStartingPieces()
	{
		// Places White's pieces along the first rank, then the second rank
		for (int rank = 0; rank < 2; rank++)
		{
			for (int file = 0; file < 8; file ++)
			{
				// Gets the list of pieces in white, then iterates through each piece and places it on the board
				board.placePiece(white.getPieces().get(file + (8 * rank)), new int[] {file, rank});
			}
		}
		
		// Places Black's pieces along the eighth rank, then the seventh rank
		for (int rank = 7; rank > 5; rank--)
		{
			for (int file = 0; file < 8; file ++)
			{
				// Gets the list of pieces in white, then iterates through each pieces and places it on the board
				board.placePiece(black.getPieces().get(file + (8 * (7 - rank))), new int[] {file, rank});
			}
		}	
	}
	
	/**
	 * Moves a Piece from one space to another
	 * @param fromIndex
	 * @param toIndex
	 */
	public void makeMove(int[] fromIndex, int[] toIndex)
	{
		// Declares a local variable player
		Player player;
		
		// Sets player to the white or black based on the turn counter
		if (turnCounter % 2 == 0)
			player = white;
		
		else
			player = black;
		
		// The player makes their move
		player.makeMove(fromIndex, toIndex);
		
		// The opponent determines their moves
		player.getOpponent().determineMoves();
		
		turnCounter++;
		// Check over
	}
	
	public void checkOver()
	{		
		// Declares a local variable player
		Player player;
		
		// Sets player to the white or black based on the turn counter
		if (turnCounter % 2 == 0)
			player = white;
		
		else
			player = black;
				
		
		// If the player has no moves
		if (player.getIndicesToMoveFrom().size() == 0)
		{
			// If the opponent is in check
			if (player.isInCheck())
			{				
				// The opponent wins
				JOptionPane.showMessageDialog(null, (player.getColor() + " is in checkmate."));
				
				// The game is over
				setOver(true);
			}
			
			// Else the opponent is not in check and has no moves
			else
			{
				// It's a stalemate
				JOptionPane.showMessageDialog(null, "Stalemate");
				
				// The game is over
				setOver(true);
			}
		}
	}
	
	public void promotePiece(int pieceChoice, int[] index)
	{
		Player player;  // The Player the Piece belongs to
		Piece piece;	// The promoted Piece
		
		// Set's the Player of the Piece that is promoting
		player = board.getPiece(index).getPlayer();
		
		// Removes the Piece from the Player's Piece list
		player.getPieces().remove(board.getPiece(index));
		
		// Switch statement that creates the Piece and adds it to the Players Piece list
		switch (pieceChoice)
		{
			case 0:
				player.getPieces().add(new Queen(player));
				break;
			
			case 1:
				player.getPieces().add(new Rook(player));
				break;
			
			case 2:
				player.getPieces().add(new Bishop(player));
				break;
				
			case 3:
				player.getPieces().add(new Knight(player));
				break;
				
			default:
				player.getPieces().add(new Queen(player));				
		}
		
		// Places the piece on the board
		board.placePiece(player.getPieces().get(player.getPieces().size() - 1), index);
		
		// Determine the indices the Piece is threatening
		board.getPiece(index).determineThreats();
		
		// Determine if the Piece is pinning an opponent Piece
		board.getPiece(index).determinePins();
		
		// The opponent determines their moves
		player.getOpponent().determineMoves();
	}
	
	/**
	 * Checks if an index is contained in a list of indices
	 * @param index the index to check
	 * @param indices the list of indices the check
	 * @return true if the index is an element of the indices list, otherwise false
	 */
	public static boolean checkContains(int[] index, ArrayList<int[]> indices)
	{
		// For each index in indices
		for (int[] element: indices)
		{
			// If the indices match, return true
			if (element[0] == index[0] && element[1] == index[1])
				return true;
		}
		
		// Return false if no matches found
		return false;
	}

	/**
	 * @return the board
	 */
	public Board getBoard()
	{
		return board;
	}

	/**
	 * @return the white
	 */
	public Player getWhite()
	{
		return white;
	}

	/**
	 * @return the black
	 */
	public Player getBlack()
	{
		return black;
	}

	/**
	 * @return the turnCounter
	 */
	public int getTurnCounter()
	{
		return turnCounter;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(Board board)
	{
		this.board = board;
	}

	/**
	 * @param white the white to set
	 */
	public void setWhite(Player white)
	{
		this.white = white;
	}

	/**
	 * @param black the black to set
	 */
	public void setBlack(Player black)
	{
		this.black = black;
	}

	/**
	 * @param turnCounter the turnCounter to set
	 */
	public void setTurnCounter(int turnCounter)
	{
		this.turnCounter = turnCounter;
	}

	/**
	 * @return the isOver
	 */
	public boolean isOver()
	{
		return isOver;
	}

	/**
	 * @param isOver the isOver to set
	 */
	public void setOver(boolean isOver)
	{
		this.isOver = isOver;
	}
}

