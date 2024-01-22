package model;

import java.util.ArrayList;

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
 * Responsibilities of class: Defines the Knight.
 *
 */
public class Knight extends Piece
{
	/**
	 * Constructor.
	 * @param player the Player the Piece belongs to
	 */
	public Knight(Player player)
	{
		// Calls the Piece constructor
		super(player);
	}

	/**
	 * Determines the indices the knight is threatening
	 * @return an array list of int[] the knight is threatening
	 */
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
	
	/**
	 * Determines the knight's available moves.
	 */
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

	/**
	 * Determines the line of attack on the opponent king
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
	 * The knight cannot pin pieces.
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
			return "N";
		
		// Else return a lower case letter
		else 
			return  "n";
	}
}
