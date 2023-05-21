package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
 * Version: 1
 *
 * Responsibilities of class: Defines actions for the color radio buttons in the ChessView.
 * 			Changes the color of the user for the next game.
 *
 */
public class ColorRadioListener implements ActionListener
{
	private ChessModel model;			 	// The chess model
	private ChessView view;					// The chess view
	private ChessController controller;		// The chess controller
	private String color;					// The color of the radio button
	
	/**
	 * Constructor.
	 * @param model the chess model
	 * @param view the chess view
	 * @param controller the chess controller
	 * @param color the color of the radio button
	 */
	public ColorRadioListener(ChessModel model, ChessView view, ChessController controller, String color)
	{
		// Sets the instance variables
		this.model = model;
		this.view = view;
		this.controller = controller;
		this.color = color;
	}

	/**
	 * Changes the user color for the next game
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// Set the user color
		view.setUserColor(color);
		
		// If the game has not started yet, start a new game
		if (model.getTurnCounter() == 0)
			controller.newGame();
	}
}
