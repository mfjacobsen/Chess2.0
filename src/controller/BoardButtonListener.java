package controller;

import model.ChessModel;
import view.ChessView;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

public class BoardButtonListener implements ActionListener
{
	private ChessModel model;
	private ChessView view;
	private ChessController controller;
	private int[] index;
	private JButton button;
	
	public BoardButtonListener(ChessModel model, ChessView view, ChessController controller, int[] index)
	{
		// Sets the instance variables
		this.model = model;
		this.view = view;
		this.controller = controller;
		this.index = index;
		button = view.getBoard()[index[0]][index[1]];
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{	
		// Clears the board of borders
		view.clearBorders();
		
		// if the game is over, do nothing
		if (model.isOver())
			return;	
		
		// If there is a Piece at the index and the Piece has available moves
		else if (model.getBoard().getPiece(index) != null && !model.getBoard().getPiece(index).getMoves().isEmpty())
		{
			// Sets a border on the button
			button.setBorderPainted(true);
			
			// Sets this piece as selected in the controller
			controller.setSelectedIndex(index);
			
			// Sets the available moves for this piece in the controller
			controller.setIndicesToMoveTo(model.getBoard().getPiece(index).getMoves());
						
			// Paints the borders of the available spaces to move 
			view.paintBorders(model.getBoard().getPiece(index).getMoves());
		}
		
		// If there is already a Piece selected and the button pressed was one of the available moves
		else if (controller.getSelectedIndex() != null && ChessModel.checkContains(index, controller.getIndicesToMoveTo()))
		{
			// Moves the Piece in the model
			model.makeMove(controller.getSelectedIndex(), index);
			
			// If the Piece is promoting
			if (model.getBoard().getPiece(index).isPromoting())
			{
				// Promote the Piece 
				controller.promotePiece(index, model.getBoard().getPiece(index).getPlayer().getColor());
			}
			
			// Moves the Piece in the view
			controller.updateView();
			
			// Clears the board of borders and resets the selected Piece and it's moves
			view.clearBorders();
			controller.setSelectedIndex(null);
			controller.setIndicesToMoveTo(null);
		}
		
		else
		{
			// resets the selected piece and its moves
			controller.setSelectedIndex(null);
			controller.setIndicesToMoveTo(null);
		}
		
		// Check if the game is over
		model.checkOver();
	}
}
