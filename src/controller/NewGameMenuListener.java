package controller;
	
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
 * Responsibilities of class: Starts a new game when the button is pressed.
 *
 */
public class NewGameMenuListener implements ActionListener
{

	private ChessController controller;		// The chess controller
	
	/**
	 * Constructor.
	 * @param controller the chess controller
	 */
	public NewGameMenuListener(ChessController controller) 
	{
		this.controller = controller;
	}

	/**
	 * Starts a new game
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		controller.newGame();
	}
}
