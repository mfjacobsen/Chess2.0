package model;

import java.util.Arrays;

public class Board
{
	// Instance variable declarations
	private Piece [][] board;
	
	/**
	 * Constructor
	 */
	public Board()
	{
		// Initializes the board as a 2D array of Spaces
		board = new Piece[8][8];
		
		// Nested for loops iterate through each Space on the board
		for(int rank = 7;  rank >= 0; rank --)
		{
			for (int file = 0; file < 8; file ++)
			{
				// Initializes each Space in the  to null
				board[file][rank] = null;
			}
		}
	}	
	
	/**
	 * Moves the Piece from one Space to another
	 * @param currentIndex the index the Piece is moving from
	 * @param destinationIndex the index the Piece is moving to
	 */
	public void movePiece(int[] fromIndex, int[] toIndex)
	{
		// If the Piece is a king and has not moved
		if (getPiece(fromIndex) instanceof King && !getPiece(fromIndex).hasMoved())
		{
			// If the King is castling Kingside
			if (Arrays.equals(toIndex, new int[] {fromIndex[0] + 2, fromIndex[1]}))
			{
				// Move the rook from the corner to the f file
				movePiece(new int[] {fromIndex[0] + 3, fromIndex[1]}, new int[] {fromIndex[0] + 1, fromIndex[1]});
			}
				
			// Else if the King is castling Queenside
			else if (Arrays.equals(toIndex, new int[] {fromIndex[0] - 2, fromIndex[1]}))
			{
				// Move the rook from the corner to the d file
				movePiece(new int[] {fromIndex[0] - 4, fromIndex[1]}, new int[] {fromIndex[0] - 1, fromIndex[1]});
			}
		}
		
		// If the Piece is a Pawn
		if (getPiece(fromIndex) instanceof Pawn)
		{
			// If the Pawn is moving its initial two step
			if(Math.abs(fromIndex[1] - toIndex[1]) == 2)
			{
				int[] enPassantCaptureIndex;	 // The index an opponent pawn can capture en passant
				
				// If the player is white, set the en passant index
				if (getPiece(fromIndex).getPlayer().getColor().equals("White"))
				{
					enPassantCaptureIndex = new int[] {fromIndex[0], fromIndex[1] + 1};
				}
				
				// Else the player is black, set the en passant index
				else
				{
					enPassantCaptureIndex = new int[] {fromIndex[0], fromIndex[1] - 1};
				}
				
				// Set the en passant index in the opponent
				getPiece(fromIndex).getPlayer().getOpponent().setEnPassantIndex(enPassantCaptureIndex);
			}
			
			// If the Pawn is capturing en passant
			else if (Arrays.equals(toIndex, getPiece(fromIndex).getPlayer().getEnPassantIndex()))
			{
				int[] capturedIndex; // The index of the Piece being captured en passant
				
				// If the player is white, set the capture index
				if (getPiece(fromIndex).getPlayer().getColor().equals("White"))
				{
					capturedIndex = new int[] {toIndex[0], toIndex[1] - 1};
				}
				
				// Else the player is black, set the captured index
				else
				{
					capturedIndex = new int[] {toIndex[0], toIndex[1] + 1};
				}
				
				// Removes the captured Piece from the opponent's Pieces list
				getPiece(capturedIndex).getPlayer().getPieces().remove(getPiece(capturedIndex));
				
				// Removes the Piece from the board
				board[capturedIndex[0]][capturedIndex[1]] = null;
			}
			
			// If the Pawn is promoting
			else if (toIndex[1] == 0 || toIndex[1] == 7)
			{
				// Set's the Piece as a promoting Piece
				getPiece(fromIndex).setPromoting(true);
			}
		}
				
		// Places the Piece in the new Space
		placePiece(board[fromIndex[0]][fromIndex[1]], toIndex);
		
		// Removes the piece from the current space
		board[fromIndex[0]][fromIndex[1]] = null;
		
		// Sets the Piece's moved value to true
		board[toIndex[0]][toIndex[1]].setMoved(true);
	}
	
	/**
	 * Places a Piece on the designated place on the board
	 * @param piece the Piece to place
	 * @param destinationIndex the index of the Piece to place
	 */
	public void placePiece(Piece piece, int[] destinationIndex)
	{
		// if the destination index is occupied, remove the piece from the player's list of pieces
		if (getPiece(destinationIndex) != null)
			getPiece(destinationIndex).getPlayer().getPieces().remove(getPiece(destinationIndex));
		
		// Sets the Piece in the Space
		board[destinationIndex[0]][destinationIndex[1]] = piece;
		
		// Sets the index of the Piece
		piece.setIndex(destinationIndex);
	}
	
	/**
	 * Determines if a given index is on the Board
	 * @param index
	 * @return true if the index is on the Board, otherwise false
	 */
	public boolean onBoard(int[] index)
	{
		return (index[0] >= 0 && index[1] >= 0 && index[0] <= 7 && index[1] <= 7);
	}
	
	/**
	 * Gets the Piece at the given index
	 * @param index the index of the Piece
	 * @return the Piece at the file and rank
	 */
	public Piece getPiece(int[] index)
	{
		return board[index[0]][index[1]];
	}	
	
	/**
	 * Returns the player at the given index on the board
	 * @param index if the space
	 * @return the Player in the space or null if the space is vacant
	 */
	public Player getPlayer(int[] index)
	{
		// If the space is occupied, return the Player
		if (getPiece(index) != null)
			return getPiece(index).getPlayer();
		
		// Else, return null
		else
			return null;
	}
}

