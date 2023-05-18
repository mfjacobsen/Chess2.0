package controller;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.*;

import model.ChessModel;
import view.ChessView;

public class ChessController
{	
	private ChessModel model;
	private ChessView view;
	private int[] selectedPieceIndex; 
	private ArrayList<int[]> indicesToMoveTo;
	
	public ChessController(ChessModel model, ChessView view)
	{
		// Sets the instance variables
		this.model = model;
		this.view = view;
		
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
		String spaceColor;
		
		// Declares an iconFileName
		String iconFileName;
		
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
				{
					iconFileName = String.format("icons/%s.png", spaceColor);
				}
				
				// Creates the icon using the formatted file name
				ImageIcon icon = new ImageIcon(iconFileName);
				
				
				// Sets the icon in the button
				view.getBoard()[file][rank].setIcon(icon);
					
			}
		}
	}
	
	/**
	 * Promotes a pawn
	 * @param index The index of the promoting pawn
	 * @param color The color of the promoting pawn
	 */
	public void promotePiece(int[] index, String color)
	{
		// Displays the promotion dialog in view and saves the user's choice
		int choice = view.promotePiece(color);
		
		// Promotes the Piece to the chosen Piece
		model.promotePiece(choice, index);
		
		// Updates the view
		updateView();
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
		// Starts a new game in the model
		model.newGame();
		
		// Remove and rebuild the board panel in the view
		view.remove(view.getBoardPanel());
		view.buildBoardPanel();
		
		// Revalidate and repaint the view to display the new panel
		view.revalidate();
		view.repaint();
		
		// Add action listeners to the new board 
		addBoardListeners();
		
		// Update the view
		updateView();
	}

	/**
	 * @return the selectedIndex
	 */
	public int[] getSelectedIndex()
	{
		return selectedPieceIndex;
	}

	/**
	 * @return the indicesToMoveTo
	 */
	public ArrayList<int[]> getIndicesToMoveTo()
	{
		return indicesToMoveTo;
	}

	/**
	 * @param selectedIndex the selectedIndex to set
	 */
	public void setSelectedIndex(int[] selectedIndex)
	{
		this.selectedPieceIndex = selectedIndex;
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
}
