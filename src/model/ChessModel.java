package model;

import java.util.ArrayList;

import javax.swing.JOptionPane;

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
 * Responsibilities of class: The chess model. Contains all game information. 
 *
 */
public class ChessModel
{
	// Instance variable declarations
	private Board board;				// The board or Pieces
	private Player white;				// White's Pieces and data
	private Player black;				// Black's Pieces and data
	private boolean isOver;        		// Boolean for determining when the game is over	
	private int turnCounter;			// Counter used to determine whose turn it is
	private int halfMoveCounter;		// Holds number of half moves since the last pawn advance or piece capture
	private String gameOutcome;			// Holds the outcome of the game
	
	
	/**
	 * Constructor.
	 */
	public ChessModel()
	{		
		// Initializes the Players
		white = new Player(this, "White");
		black = new Player(this, "Black");
		
		// Sets the opponents against each other
		white.setOpponent(black);
		black.setOpponent(white);
		
		// Starts a new game
		newGame();
	}
	
	/**
	 * Creates a new game state.
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
	
	/**
	 * Places the Pieces in their starting position on the board.
	 */
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
	 * Moves a Piece from one space to another.
	 * @param fromIndex the index of the Piece that is moving
	 * @param toIndex the index the Piece is moving to
	 */
	public void makeMove(int[] fromIndex, int[] toIndex)
	{
		// Declares a local variable player
		Player player;
		
		// Sets player to white or black based on the turn counter
		if (turnCounter % 2 == 0)
			player = white;
		
		else
			player = black;
		
		// Increment the turn counter and half move counter
		turnCounter++;
		halfMoveCounter ++;
		
		// If a pawn is moving or a piece is being captured, reset the half move counter
		if ((board.getPiece(fromIndex) instanceof Pawn) || board.getPiece(toIndex) != null)
			halfMoveCounter = 0;
		
		// The player makes their move
		player.makeMove(fromIndex, toIndex);
		
		// The opponent determines their moves
		player.getOpponent().determineMoves();

		// Checks if the game is over
		checkOver();
	}
	
	/**
	 * Checks if the game is over.
	 */
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
				gameOutcome = player.getColor() + " is in checkmate.";
				
				// The game is over
				isOver = true;
			}
			
			// Else the opponent is not in check and has no moves
			else
			{
				// It's a stalemate
				gameOutcome = "Stalemate.";
				
				// The game is over
				isOver = true;
			}
		}
		
		// Else if it's been 50 moves since the last pawn advance or piece capture
		else if (halfMoveCounter == 50)
		{
			// It's a draw
			gameOutcome = "Draw by 50 move rule.";
			
			// The game is over
			isOver = true;
		}
	}
	
	/**
	 * Promotes a pawn that reaches the 1st or 8th rank
	 * @param pieceChoice and integer 0-3 for Queen, Rook, Bishop, or Knight
	 * @param index the index of the pawn that is promoting
	 */
	public void promotePiece(int pieceChoice, int[] index)
	{
		Player player;  // The Player the Piece belongs to
		Piece piece;	// The promoted Piece
		
		// Set's the Player of the Piece that is promoting
		player = board.getPiece(index).getPlayer();
		
		// Removes the Piece from the Player's Piece list
		player.getPieces().remove(board.getPiece(index));
		
		// Switch statement that creates the Piece and adds it to the Player's Piece list
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
		
		// Reset's the opponent move list
		player.getOpponent().getIndicesToMoveFrom().clear();
		
		// For each Piece
		for (Piece playerPiece: player.getPieces())
		{
			// Determine the indices it's threatening
			playerPiece.determineThreats();
			
			// Rest the value of pinned by in the Piece
			playerPiece.setPinnedBy(null);
			
			// Determine of the Piece is pinning an opponent Piece
			playerPiece.determinePins();
		}
		
		// The opponent determines their moves
		player.getOpponent().determineMoves();
		
		// Check if the game is over
		checkOver();
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
	 * Returns the game state in FEN notation
	 * @return the game state in FEN notation
	 */
	public String toString()
	{
		String fenNotation = "";						 // Holds the game state in FEN notation
		String activeColor;								 // The Player who's turn it is
		String castlingRights;							 // The castling rights of each player
		String enPassantTarget = "";					 // The square eligible for en passant
		String halfMoveCount = " " + halfMoveCounter; 	 // The half move counter
		String turnCount = " " + ((turnCounter / 2) + 1);// The number of full turns taken
		
		// Sets the active color as w or b based on the turn counter
		if (turnCounter % 2 == 0)
		{
			activeColor = " w";
			
			// Gets the en passant target square
			if (white.getEnPassantIndex() != null)
				enPassantTarget = " " + convertToAlgebraic(white.getEnPassantIndex());
			else 
				enPassantTarget = " -";
		}
		else
		{
			activeColor = " b";
			
			// Gets the en passant target square
			if (black.getEnPassantIndex() != null)
				enPassantTarget = " " + convertToAlgebraic(black.getEnPassantIndex());
			else 
				enPassantTarget = " -";
		}
		
		// Get's the castling rights of white and black
		castlingRights = " " + white.getCastlingRights() + black.getCastlingRights();
		if (castlingRights.equals(" "))
			castlingRights = " -";		
		
		// Builds and returns the game in FEN notation
		fenNotation += board.toString() + activeColor + castlingRights + enPassantTarget + halfMoveCount + turnCount;
		return fenNotation;
	}
	
	/**
	 *  Converts the array index of a space in the board to its algebraic notation.
	 *  @param index is a two element int array that contains the array index of a space in the chess board
	 *  @return a two character string of the coordinates of the space
	 */
	public static String convertToAlgebraic(int[] index)
	{
		// Returns a two character String
		return Character.toString(97 + index[0]) + Integer.toString(index[1] + 1);
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

	/**
	 * @return the halfMoveCounter
	 */
	public int getHalfMoveCounter()
	{
		return halfMoveCounter;
	}

	/**
	 * @param halfMoveCounter the halfMoveCounter to set
	 */
	public void setHalfMoveCounter(int halfMoveCounter)
	{
		this.halfMoveCounter = halfMoveCounter;
	}

	/**
	 * @return the gameOutcome
	 */
	public String getGameOutcome()
	{
		return gameOutcome;
	}

	/**
	 * @param gameOutcome the gameOutcome to set
	 */
	public void setGameOutcome(String gameOutcome)
	{
		this.gameOutcome = gameOutcome;
	}
}

