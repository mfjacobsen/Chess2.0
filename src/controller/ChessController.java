package controller;

import java.util.ArrayList;
import javax.swing.*;
import model.ChessModel;
import view.ChessView;

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
 * 		// https://stackoverflow.com/questions/1097366/java-swing-revalidate-vs-repaint
 *
 * Version: 1
 *
 * Responsibilities of class: Controls the chess game. Passes information between the view and model.
 *
 */
public class ChessController
{	
	private ChessModel model;					// The chess model
	private ChessView view;						// The chess view
	private int[] selectedPieceIndex; 			// Holds the index of the Piece selected by the user
	private ArrayList<int[]> indicesToMoveTo;	// The available moves of the selected Piece
	private ComputerPlayer computerPlayer;		// The computer player of the game
	private String userColor;					// The color of the user for this game
	
	/**
	 * Constructor.
	 * @param model the chess model 
	 * @param view the chess view
	 */
	public ChessController(ChessModel model, ChessView view)
	{
		// Sets the instance variables
		this.model = model;
		this.view = view;
		
		// The default user color is white;
		userColor = "White";
		
		// Initializes the computer player
		computerPlayer = new ComputerPlayer(model, this);
		computerPlayer.start();
		
		// Add action listeners to the menu items
		view.getNewGameMenuItem().addActionListener(new NewGameMenuListener(this));
		view.getWhiteRadio().addActionListener(new ColorRadioListener(model, view, this, "White"));
		view.getBlackRadio().addActionListener(new ColorRadioListener(model, view, this, "Black"));
		
		// Add action listeners to the board buttons
		addBoardListeners();
		
		// Updates the chess view
		updateView();
	}
	
	/**
	 * Updates the icons on the board in the ChessView
	 */
	public void updateView()
	{
		String spaceColor;		// Holds whether the space is a light or dark square
		String iconFileName;	// Holds the icon file name
		
		// Nested for loops iterate through each button on the board
		for(int rank = 7;  rank >= 0; rank --)
		{
			for (int file = 0; file < 8; file ++)
			{		
				// Sets spaceColor to Dark
				if ((file + rank) % 2 == 0)
					spaceColor = "Dark";
					
				// Sets spaceColor to Light
				else
					spaceColor = "Light";
				
				// If there is a Piece in the square
				if (model.getBoard().getPiece(new int[] {file,rank}) != null)
				{					
					// Formats the icon file name based on the space color, piece, and piece color
					iconFileName = String.format("icons/%s%s%s.png",
							spaceColor,
							model.getBoard().getPiece(new int[] {file,rank}).getPlayer().getColor(), 
							model.getBoard().getPiece(new int[] {file,rank}).getClass().getName().replace("model.", ""));
				}
				
				// Else formats the icon file name as an empty square
				else
					iconFileName = String.format("icons/%s.png", spaceColor);

				
				// Creates the icon using the formatted file name
				ImageIcon icon = new ImageIcon(iconFileName);
				
				// Sets the icon in the button
				view.getBoard()[file][rank].setIcon(icon);					
			}
		}
	}

	/**
	 * Moves a piece in the model and view
	 * @param fromIndex the index of the Piece that is moving
	 * @param toIndex the index the Piece is moving to
	 */
	public void makeMove(int[] fromIndex, int[] toIndex)
	{
		// Moves the Piece in the model
		model.makeMove(fromIndex, toIndex);
		
		// If the Piece is promoting
		if (model.getBoard().getPiece(toIndex).isPromoting())
		{
			// Promote the Piece 
			promotePiece(toIndex, model.getBoard().getPiece(toIndex).getPlayer().getColor());
		}
		
		// Moves the Piece in the view
		updateView();
		
		// Clears the board of borders and resets the selected Piece and it's moves
		view.clearBorders();
		selectedPieceIndex = null;
		indicesToMoveTo = null;
		
		// If the game is over, end the game in view
		if (model.isOver())
			view.endGame(model.getGameOutcome());
		
		// Else, set the computer Player's turn to true
		else
			computerPlayer.setTurn(true);
	}
	
	/**
	 * Promotes a pawn
	 * @param index The index of the promoting pawn
	 * @param color The color of the promoting pawn
	 */
	public void promotePiece(int[] index, String color)
	{
		// An int 0-3 to hold the choice of Queen, Rook, Bishop, or Knight
		int choice;
		
		// If the computer is promoting, choose a Queen
		if (computerPlayer.isTurn())
			choice = 0;
		
		// Else, display the promotion dialog in view and save the user's choice
		else
			choice = view.promotePiece(color, index);
		
		// Promotes the Piece to the chosen Piece
		model.promotePiece(choice, index);
	}
	
	/**
	 * Adds action listeners to the board buttons
	 */
	public void addBoardListeners()
	{
		// Nested for loops iterate through each button on the board
		for(int rank = 7;  rank >= 0; rank --)
		{
			for (int file = 0; file < 8; file ++)
			{
				// Adds an action listener to the button
				view.getBoard()[file][rank].addActionListener(new BoardButtonListener(model, view, this, new int[] {file,rank}));
			}
		}
	}
	
	/**
	 * Starts a new game
	 */
	public void newGame()
	{
		// Gets the user color for this game
		userColor = view.getUserColor();
		
		// Starts a new game in the model
		model.newGame();
		
		// Remove and rebuild the board panel in the view
		view.remove(view.getBoardPanel());
		view.buildBoardPanel();
		
		// Revalidate and repaint the view to display the new board panel
		view.revalidate();
		view.repaint();
		
		// Add listeners to the new board 
		addBoardListeners();
		
		// Update the view
		updateView();
		
		// Initializes the computer player
		computerPlayer = new ComputerPlayer(model, this);
		computerPlayer.start();
		
		// If the user is black, set the computer's turn to true
		if (view.getUserColor().equals("Black"))
			computerPlayer.setTurn(true);
	}

	/**
	 * @return the indicesToMoveTo
	 */
	public ArrayList<int[]> getIndicesToMoveTo()
	{
		return indicesToMoveTo;
	}

	/**
	 * @param indicesToMoveTo the indicesToMoveTo to set
	 */
	public void setIndicesToMoveTo(ArrayList<int[]> indicesToMoveTo)
	{
		this.indicesToMoveTo = indicesToMoveTo;
	}

	/**
	 * @return the model
	 */
	public ChessModel getModel()
	{
		return model;
	}

	/**
	 * @return the view
	 */
	public ChessView getView()
	{
		return view;
	}

	/**
	 * @return the selectedPieceIndex
	 */
	public int[] getSelectedPieceIndex()
	{
		return selectedPieceIndex;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(ChessModel model)
	{
		this.model = model;
	}

	/**
	 * @param view the view to set
	 */
	public void setView(ChessView view)
	{
		this.view = view;
	}

	/**
	 * @param selectedPieceIndex the selectedPieceIndex to set
	 */
	public void setSelectedPieceIndex(int[] selectedPieceIndex)
	{
		this.selectedPieceIndex = selectedPieceIndex;
	}

	/**
	 * @return the computerPlayer
	 */
	public Thread getComputerPlayer()
	{
		return computerPlayer;
	}

	/**
	 * @param computerPlayer the computerPlayer to set
	 */
	public void setComputerPlayer(ComputerPlayer computerPlayer)
	{
		this.computerPlayer = computerPlayer;
	}

	/**
	 * @return the userColor
	 */
	public String getUserColor()
	{
		return userColor;
	}

	/**
	 * @param userColor the userColor to set
	 */
	public void setUserColor(String userColor)
	{
		this.userColor = userColor;
	}
}
