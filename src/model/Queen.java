package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Queen extends Piece 
{

	public Queen(Player player)
	{
		super(player);
	}

	@Override
	public ArrayList<int[]> determineThreats()
	{
		// Instance Variable Declarations
		int distance;						// The distance away from the Piece's current index
		boolean isClearPath;				// A boolean that hold whether each movement path is clear
		int[] toIndex = new int[2];			// The index the Piece will check for a valid threat
		int[][] pathDirections = 			// An array holding the directions for each path
				new int[][] {{1,1},{1,0},{1,-1},{0,1},{0,-1},{-1,1},{-1, 0},{-1, -1}};

		// Clears old threats from the indices threatened list
		getThreats().clear();
			
		// For the directions of each of the paths
		for (int[] directions: pathDirections)
		{
			// initializes the path as clear, and distance as one
			isClearPath = true;
			distance = 1;
			
			// Do while the path is clear
			do
			{
				// Sets the index the Piece will check as a threat
				toIndex[0] = getIndex()[0] + (directions[0] * distance);
				toIndex[1] = getIndex()[1] + (directions[1] * distance);
				
				// If the index is on the board, 
				if (getBoard().onBoard(toIndex))
				{					
					// Add the index to the indices threatened
					addThreatened(new int[] {toIndex[0], toIndex[1]});

					// If there is a Piece at the index, set isClearPath to false
					if (getBoard().getPiece(toIndex)!= null)
						isClearPath = false;
				}
				
				// Else the index is off the board, set isClearPath to false
				else
					isClearPath = false;
			
				// Increment the distance counter
				distance ++;
	
			} while (isClearPath);
		}
		
		// Return the indices threatened list
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
		int distance = 1;				// The distance away from the Piece's current index
		boolean foundKing = false; 		// Whether the Piece has found the opponent King yet
		int[] directions = new int[2];	// The direction of the opponent King
		int[] toIndex = new int[2];		// The index the Piece will check for the King
		int[] kingIndex = 				// The index of the opponent King
				getPlayer().getOpponent().getKing().getIndex();
		
		// Clears the line of attack
		getLineOfAttack().clear();
		
		// This for loop finds the direction of the King
		// For each direction (file or rank)
		for(int direction = 0; direction < 2; direction ++)
		{
			// If the Piece and the King are on the same file/rank, set the direction element to zero
			if ((kingIndex[direction] - getIndex()[direction]) == 0)
				directions[direction] = 0;
			
			// Else set direction[0] to one or negative one
			else
				directions[direction] = (kingIndex[direction] - getIndex()[direction]) / 
					Math.abs((kingIndex[direction] - getIndex()[direction]));
		}
		
		// Add the current index to the line of attack
		getLineOfAttack().add(getIndex());
		
		// Do while the King has not been found
		do
		{
			// Sets the index the Piece will check as a threat
			toIndex[0] = getIndex()[0] + (directions[0] * distance);
			toIndex[1] = getIndex()[1] + (directions[1] * distance);
			
			//System.out.println (Integer.toString(toIndex[0]) + "," + Integer.toString(toIndex[1]));
			
			// If the toIndex is not the King index
			if (!Arrays.equals(toIndex, kingIndex))
			{
				// Add the index to the indices threatened
				getLineOfAttack().add(new int[] {toIndex[0], toIndex[1]});
			}
			
			// Else, set foundKing to true
			else
				foundKing = true;
			
			// Increments the distance
			distance ++;
			
		} while (!foundKing);
	}

	@Override
	public void determinePins()
	{
		// Instance Variable Declarations
		int distance = 1;					// The distance away from the piece
		int numPiecesInPath = 0;			// The number of Pieces in the pinned path
		boolean isKingOnPath = false;		// A boolean that holds if the King is on the Pieces movement path
		boolean isPinnablePath = true;      // A boolean that hold whether each movement path is clear
		boolean foundKing = false;			// A boolean that holds whether the king has been found
		int[] pathIndex = new int[2];		// The index the Piece will check for a pin
		int[] directions = new int[2];		// The direction of the opponent King
		int[] pinnedIndex = new int[2];		// The index of a potentially pinned Piece
		int[] kingIndex =					// The index of the opponent King
				getPlayer().getOpponent().getKing().getIndex();
		ArrayList<int[]> path =				// An array list holding the indices of the pinned path
				new ArrayList<>();
		

		// Clears old threats from the indices threatened list
		getPinnedLineOfAttack().clear();
		
		// This for loop finds the direction of the King
		// For each direction (file or rank)
		for (int direction = 0; direction < 2; direction++)
		{
			// If the Piece and the King are on the same file/rank
			if ((kingIndex[direction] - getIndex()[direction]) == 0)
			{
				// Set the direction as zero
				directions[direction] = 0;
				
				// The king is on the same path as the Piece
				isKingOnPath = true;
			}
			
			// Else set direction to one or negative one
			else
				directions[direction] = (kingIndex[direction] - getIndex()[direction]) / 
					Math.abs((kingIndex[direction] - getIndex()[direction]));			
		} 
		
		// Checks if the King is on the same diagonal as the Piece
		if (Math.abs(kingIndex[0] - getIndex()[0]) - Math.abs((kingIndex[1] - getIndex()[1])) == 0)
			// The king is on the same path as the Piece
			isKingOnPath = true;
		
		// If the King has been found on a path, determine of there is a pinned Piece on the path
		while (isKingOnPath && !foundKing && isPinnablePath && numPiecesInPath < 2)
		{
			// Sets the path index equal to the Piece index plus the distance in the direction of the King
			pathIndex[0] = getIndex()[0] + (directions[0] * distance);
			pathIndex[1] = getIndex()[1] + (directions[1] * distance);
			
			// If the index is empty
			if (getBoard().getPiece(pathIndex) == null)
			{
				// Add the index to the path
				path.add(new int[] {pathIndex[0], pathIndex[1]});
				distance++;
				
			}
			
			// If the index is occupied by the opposing player
			else if (getBoard().getPiece(pathIndex).getPlayer() == getPlayer().getOpponent())
			{
				// If it's the King
				if ((getBoard().getPiece(pathIndex)) instanceof King)
				{
					// If there are zero pieces in the path
					if (numPiecesInPath == 0)
					{
						// It's not a pinnable path
						isPinnablePath = false;
					}
					
					// Else it's a valid pin
					else
					{
						// Pins the Piece at the index
						getBoard().getPiece(pinnedIndex).setPinnedBy(this);
						
						// Adds the Pinning Pieces current coordinates to the line of attack
						getPinnedLineOfAttack().add(getIndex());
						
						// For each index in the path
						for (int[] index: path)
						{
							// Add the element to the pinnedLineOfAttack
							getPinnedLineOfAttack().add(index);
						}
					}
					
					// Sets foundKing to true
					foundKing = true;	
				}
				
				// Sets the index as a pinned Piece
				pinnedIndex[0] = pathIndex[0];
				pinnedIndex[1] = pathIndex[1];
				
				// increments the number of pieces in the path
				numPiecesInPath ++;
				distance ++;
			}
			
			// Else the space is occupied by the same player
			else
			{
				isPinnablePath = false;
			}
		}
	}
}
