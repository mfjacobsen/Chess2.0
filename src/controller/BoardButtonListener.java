package controller;

import model.ChessModel;
import view.ChessView;
import java.awt.event.*;
import javax.swing.*;

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
 * Responsibilities of class: Defines actions for the board buttons.
 *
 */
public class BoardButtonListener implements ActionListener
{
	private ChessModel model;			// The chess model
	private ChessView view;				// The chess view
	private ChessController controller;	// The chess controller
	private JButton button;				// The button the listener is attached to
	private int[] index;				// The index of the board button

	/**
	 * Constructor.
	 * @param model the chess model
	 * @param view the chess view
	 * @param controller the chess controller
	 * @param index the chess index
	 */
	public BoardButtonListener(ChessModel model, ChessView view, ChessController controller, int[] index)
	{
		// Sets the instance variables
		this.model = model;
		this.view = view;
		this.controller = controller;
		this.index = index;
		
		// Sets the button
		button = view.getBoard()[index[0]][index[1]];
	}

	/**
	 * Defines actions for the button when pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{	
		// Clears the board of borders
		view.clearBorders();
		
		// if the game is over, do nothing
		if (model.isOver())
			return;	
		
		// If the user tries to select an opponent Piece, do nothing
		else if (model.getBoard().getPiece(index) != null &&
				!model.getBoard().getPiece(index).getPlayer().getColor().equals(controller.getUserColor()) &&
				controller.getSelectedPieceIndex() == null)
			return;

		// If there is a Piece at the index and the Piece has available moves
		else if (model.getBoard().getPiece(index) != null && !model.getBoard().getPiece(index).getMoves().isEmpty())
		{
			// Sets a border on the button
			button.setBorderPainted(true);
			
			// Sets this piece as selected in the controller
			controller.setSelectedPieceIndex(index);
			
			// Sets the available moves for this piece in the controller
			controller.setIndicesToMoveTo(model.getBoard().getPiece(index).getMoves());
						
			// Paints the borders of the available spaces to move 
			view.paintBorders(model.getBoard().getPiece(index).getMoves());
		}
		
		// If there is already a Piece selected and the button pressed was one of the available moves
		else if (controller.getSelectedPieceIndex() != null && ChessModel.checkContains(index, controller.getIndicesToMoveTo()))
		{
			// Make the move
			controller.makeMove(controller.getSelectedPieceIndex(), index);
		}
		
		else
		{
			// resets the selected piece and its moves
			controller.setSelectedPieceIndex(null);
			controller.setIndicesToMoveTo(null);
		}		
	}
}
