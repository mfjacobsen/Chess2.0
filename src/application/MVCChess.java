package application;

import model.ChessModel;
import view.ChessView;
import controller.ChessController;

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
 * Responsibilities of class: Runs the chess application
 *
 */
public class MVCChess
{
	/**
	 * Runs the Chess application
	 */
	public static void main(String[] args)
	{
		try 
		{
			// Creates a new Chess model, view, and controller
			ChessModel model = new ChessModel();
			ChessView view = new ChessView();
			ChessController controller = new ChessController(model, view);
		}
		
		// Catches all exceptions
		catch(Exception e)
		{
			System.out.println("Application Error.");
		}		
	}
}
