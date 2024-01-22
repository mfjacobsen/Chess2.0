package model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Lead Authors:
 *
 * @author Matthew Jacobsen; 5550026131
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
 * Responsibilities of class: Defines the pawn.
 *
 */
public class Pawn extends Piece
{
	int direction;			// The direction the pawn moves
	
	/**
	 * Constructor.
	 * @param player the Player the Piece belongs to
	 */
	public Pawn(Player player)
	{
		// Calls the Piece constructor
		super(player);
		
		// If the player is white, direction is one
		if (player.getColor().equals("White"))
			direction = 1;
		
		// Else the player is black, direction is negative one
		else
			direction = -1;
		
	}

	/**
	 * Determines the indices the pawn is threatening
	 * @return an array list of int[] the pawn is threatening
	 */
	@Override
	public ArrayList<int[]> determineThreats()
	{
		// A set of two indices the pawn will check for as threats
		int[][] toIndices = new int[2][2];
	
		// Clears old moves from the indices threatened list
		getThreats().clear();
		
		// Sets the index the pawn will check for as a threat
		toIndices = new int[][] {{(getIndex()[0] + 1), (getIndex()[1] + direction)},
								 {(getIndex()[0] - 1), (getIndex()[1] + direction)}};
	
		
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

	/**
	 * Determines the pawn's available moves
	 */
	@Override
	public void determineMoves()
	{
		// Declares and initializes the index the Piece will check for a single space move
		int[] toIndex1 = new int[] {getIndex()[0], getIndex()[1] + direction};
		
		// Clears old moves from the move list
		getMoves().clear();
				
		// For each index the Piece is threatening,
		for (int[] threat : determineThreats())
			// if the index threatened contains an opponents Piece or is eligible for en passant
			if (getBoard().getPlayer(threat) == getPlayer().getOpponent() ||
					Arrays.equals(threat, getPlayer().getEnPassantIndex()))
				// add the index to the move list
				addMove(threat);
		
		// For each index the Piece is threatening,
		// if the index threatened contains an opponents Piece or is eligible for en passant
		// add the index to the move list
		for (int[] threat : determineThreats())	
			if ((getBoard().getPlayer(threat) != getPlayer() && getBoard().getPlayer(threat) != null) ||
					Arrays.equals(threat, getPlayer().getEnPassantIndex()))
				addMove(threat);
		
		// If toIndex is on the board and is unoccupied
		if (getBoard().onBoard(toIndex1) && getBoard().getPiece(toIndex1) == null)
		{
			// add the move
			addMove(toIndex1);
			
			// If the Pawn has not moved
			if (!hasMoved())
			{
				// Declares and initializes the index of a second space ahead to check for a move
				int[] toIndex2 = new int[] {getIndex()[0], getIndex()[1] + (direction * 2)};
				
				// If the space is unoccupied
				if (getBoard().getPiece(toIndex2) == null)
				{
					// add the move
					addMove(toIndex2);
				}
			}			
		}		
	}

	/**
	 * Determines the pawn's line of attack on the opponent king
	 */
	@Override
	public void determineLineOfAttack()
	{
		// Clears the line of attack list
		getLineOfAttack().clear();
				
		// Adds the current index to the line of attack list
		getLineOfAttack().add(getIndex());
		
	}

	/**
	 * The pawn cannot pin other Pieces
	 */
	@Override
	public void determinePins() {}
	
	/**
	 * Gets the value of the piece in FEN notation
	 * @return the value of the piece in FEN notation
	 */
	@Override
	public String toString()
	{
		// If the player is white, return a capital letter
		if (getPlayer().getColor().equals("White"))
			return "P";
		
		// Else return a lower case letter
		else 
			return  "p";
	}

}
