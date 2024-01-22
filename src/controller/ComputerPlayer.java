package controller;

import java.util.Random;

import model.ChessModel;

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
 * Responsibilities of class: Makes moves against the user. 
 *
 */
public class ComputerPlayer extends Thread
{
	private ChessModel model;			// The chess model
	private ChessController controller; // The chess controller
	private boolean isTurn;				// If it is the computer player's turn

	/**
	 * Constructor.
	 * @param model the chess model
	 * @param controller the chess controller
	 */
	public ComputerPlayer(ChessModel model, ChessController controller)
	{
		// Sets the instance variables
		this.model = model;
		this.controller = controller;
	}
	/**
	 * Runs the thread while the game is not over
	 */
	@Override
	public void run()
	{
		// While the game is not over
		while (!model.isOver())
		{					
			// Put the thread to sleep for 1 second
			try
			{
				Thread.sleep(1000);
			}
			
			// Catch interrupted exceptions
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			// If it's the computer's turn
			if (isTurn)
			{									
				// Make a move
				makeMove();
			}
		}
	}

	/**
	 * Chooses and makes a move
	 */
	private void makeMove()
	{
		// Creates a new Random object
		Random random = new Random();
		
		// Declares local variables to hole the Piece and move indices
		int[] fromIndex;
		int[] toIndex = null;
		
		// If it's White's turn, get a random white Piece that is eligible to move
		if (model.getTurnCounter() % 2 == 0)
			fromIndex = model.getWhite().getIndicesToMoveFrom()
						.get(random.nextInt(model.getWhite().getIndicesToMoveFrom().size()));
		
		// Else it's Black's turn, get a random black Piece that is eligible to move
		else
			fromIndex = model.getBlack().getIndicesToMoveFrom()
						.get(random.nextInt(model.getBlack().getIndicesToMoveFrom().size()));
		
		// Get a random move from the Piece's move list
		toIndex = model.getBoard().getPiece(fromIndex).getMoves()
				.get(random.nextInt(model.getBoard().getPiece(fromIndex).getMoves().size()));
		
		// Make the move
		controller.makeMove(fromIndex, toIndex);
		
		// Sets isTurn to false
		isTurn = false;
	}

	/**
	 * @return the isTurn
	 */
	public boolean isTurn()
	{
		return isTurn;
	}

	/**
	 * @param isTurn the isTurn to set
	 */
	public void setTurn(boolean isTurn)
	{
		this.isTurn = isTurn;
	}
	
}
