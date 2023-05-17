package model;

import java.util.ArrayList;

public class Knight extends Piece
{
	/**
	 * Constructor
	 * @param player the Player the Piece belongs to
	 */
	public Knight(Player player)
	{
		super(player);
	}

	@Override
	public ArrayList<int[]> determineThreats()
	{			
		// Sets the indices the Knight will check for a valid move
		int[][] toIndices = new int[][] {{getIndex()[0] + 1, getIndex()[1] + 2},
			 							 {getIndex()[0] + 1, getIndex()[1] - 2},
			 							 {getIndex()[0] - 1, getIndex()[1] + 2},
			 							 {getIndex()[0] - 1, getIndex()[1] - 2},
			 							 {getIndex()[0] + 2, getIndex()[1] + 1},
			 							 {getIndex()[0] + 2, getIndex()[1] - 1},
			 							 {getIndex()[0] - 2, getIndex()[1] + 1},
			 							 {getIndex()[0] - 2, getIndex()[1] - 1}};
		
		// Clears old moves from the indices threatened list
		getThreats().clear();
		
		// For loop goes through each index in the toIndices array					     
		for(int[] toIndex : toIndices)
		{
			// If the index is on the board, add the index to the indices threatened
			if (getBoard().onBoard(toIndex))
				addThreatened(toIndex);	
		}
		
		// Returns the indices threatened list
		return getThreats();
	}
	
	@Override
	public void determineMoves()
	{
		// Clears old moves from the move list
		getMoves().clear();
		
		// For each index the Piece is threatening,
		for (int[] threat : determineThreats())
			// if the index threatened does not contain a Piece from the same team, then
			if (getBoard().getPlayer(threat) != getPlayer())
				// add the index to the move list
				addMove(threat);
	}

	@Override
	public void determineLineOfAttack()
	{
		// Clears the line of attack list
		getLineOfAttack().clear();
		
		// Adds the current index to the line of attack list
		getLineOfAttack().add(getIndex());
	}

	@Override
	public void determinePins()
	{
		// The Knight cannot pin pieces
	}
}
