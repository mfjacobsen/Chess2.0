package controller;

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
		
		// Nested for loops iterate through each button on the board
		for(int rank = 7;  rank >= 0; rank --)
		{
			for (int file = 0; file < 8; file ++)
			{
				// Adds an action listener to the button
				view.getBoard()[file][rank].addActionListener(new BoardButtonListener(model, view, this, new int[] {file,rank}));
			}
		}
		
		// Updates the chess view
		updateView();
	}
	
	public void updateView()
	{
		// Nested for loops iterate through each button on the board
		for(int rank = 7;  rank >= 0; rank --)
		{
			for (int file = 0; file < 8; file ++)
			{		
				// If there is a Piece in the square
				if (model.getBoard().getPiece(new int[] {file,rank}) != null)
				{
					// Formats the icon file name based on the piece and color
					String iconFileName = String.format("icons/%s%s.png", 
							model.getBoard().getPiece(new int[] {file,rank}).getPlayer().getColor(), 
							model.getBoard().getPiece(new int[] {file,rank}).getClass().getName().replace("model.", ""));
					
					// Creates the icon
					ImageIcon icon = new ImageIcon(iconFileName);
							
					// Sets the icon in the button
					view.getBoard()[file][rank].setIcon(icon);
				}
				
				// Else remove the icon
				else
					view.getBoard()[file][rank].setIcon(null);
			}
		}
	}
	
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
}
