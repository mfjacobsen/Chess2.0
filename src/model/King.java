package model;

import java.util.ArrayList;

public class King extends Piece
{	
	public King(Player player)
	{
		super(player);
	}

	@Override
	public ArrayList<int[]> determineThreats()
	{
		// Instance Variable Declarations
		int[] toIndex = new int[2];		// The index the Piece will check for a valid threat
		int[][] moveDirections = 		// The directions the Piece will check as a threat
			{{1,1},{1,0},{1,-1},{0,1},{0,-1},{-1,1},{-1, 0},{-1, -1}};

		// Clears old threats from the indices threatened list
		getThreats().clear();
			
		// For the directions of each of the paths
		for (int[] directions: moveDirections)
		{
			// Sets the index the Piece will check as a threat
			toIndex[0] = getIndex()[0] + (directions[0]);
			toIndex[1] = getIndex()[1] + (directions[1]);
			
			// If the index is on the board, add it as a threat
			if (getBoard().onBoard(toIndex))
				addThreatened(new int[] {toIndex[0], toIndex[1]});
		}
		
		// Return the indices threatened list
		return getThreats();
	}
	
	@Override
	public void determineMoves()
	{
		// Clears old moves from the move list
		getMoves().clear();
		
		// For each index the Piece is threatening
		for (int[] threat : determineThreats())
			// If the index threatened does not contain a Piece from the same team,
			// nor is the index threatened by the opponent team, then
			
			if (getBoard().getPlayer(threat) != getPlayer() 
					&& !ChessModel.checkContains(threat, getPlayer().getOpponent().getIndicesThreatened()))
					
				// add the index to the move list
				addMove(threat);
		
		// If the King has not moved and the player is not in check
		if (!hasMoved() && !getPlayer().isInCheck())
		{
			// Holds if the king can castle
			boolean canCastleKingside = true;
			boolean canCastleQueenside = true;
			
			// The index of the kingside rook and queenside rooks
			int[] kingsideRookIndex = new int[] {getIndex()[0] + 3, getIndex()[1]}; 
			int[] queensideRookIndex = new int[] {getIndex()[0] - 4, getIndex()[1]};
			
			// Holds the number of the index being checked for castling
			int indexNum = 0;
			
			// If the Kingside rook has not moved
			if (getBoard().getPiece(kingsideRookIndex) instanceof Rook && !getBoard().getPiece(kingsideRookIndex).hasMoved())
			{
				// Declares and initializes the indices of the kingside castle
				int[][] kingsideCastleIndices = new int[][]{{getIndex()[0] + 1, getIndex()[1]},
														    {getIndex()[0] + 2, getIndex()[1]}};
				
				// Do while canCastleKingside is true and there are more indices to check
				do
				{
					// If the kingside castle index is non empty, or the space is threatened
					if (getBoard().getPiece(kingsideCastleIndices[indexNum]) != null ||
							ChessModel.checkContains(kingsideCastleIndices[indexNum], getPlayer().getOpponent().getIndicesThreatened()))
					{
						// Sets canCastleKingside to false
						canCastleKingside = false;
					}
					
					// increment the index number
					indexNum ++;
					
				} while (canCastleKingside && indexNum < kingsideCastleIndices.length);
				
				// If the King can castle kingside
				if (canCastleKingside)
				{
					// Add the move to the move list
					addMove(new int[] {getIndex()[0] + 2, getIndex()[1]});
				}
			}
			
			//resets the index number
			indexNum = 0;
		
			// If the Queenside rook has not moved and the b file is empty
			if (getBoard().getPiece(queensideRookIndex) instanceof Rook && !getBoard().getPiece(queensideRookIndex).hasMoved() &&
					getBoard().getPiece(new int[] {getIndex()[0] - 3, getIndex()[1]}) == null)
			{				
				// Declares and initializes the indices of the Queenside castle
				int[][] queensideCastleIndices = new int[][]{{getIndex()[0] - 1, getIndex()[1]},
														     {getIndex()[0] - 2, getIndex()[1]}};	
				// Do while canCastleQueenside is true and there are more indices to check
				do
				{
					// If the Queenside castle index is non empty, or the space is threatened
					if (getBoard().getPiece(queensideCastleIndices[indexNum]) != null ||
							ChessModel.checkContains(queensideCastleIndices[indexNum], getPlayer().getOpponent().getIndicesThreatened()))
					{
						// Sets canCastleKingside to false
						canCastleQueenside = false;
					}
					
					// increment the index number
					indexNum ++;
					
				} while (canCastleQueenside && indexNum < queensideCastleIndices.length);
				
				// If the King can castle queenside
				if (canCastleQueenside)
				{
					// Add the move to the move list
					addMove(new int[] {getIndex()[0] - 2, getIndex()[1]});
				}
			}
		}
	}
	
	/**
	 * The King cannot have a line of attack on an opponent King
	 */
	@Override
	public void determineLineOfAttack() {}
	
	/**
	 * The King cannot pin other Pieces
	 */
	@Override
	public void determinePins() {}
	
}
