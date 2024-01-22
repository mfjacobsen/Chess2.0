package view;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;

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
 * Responsibilities of class: Defines a board button in the chess view.
 *
 */
public class BoardButton extends JButton
{	
	/**
	 * Constructor.
	 */
	public BoardButton()
	{
		// Sets the borders on the Buttons
		setBorder(BorderFactory.createLineBorder(Color.ORANGE));
				
		// Hides the button's border
		setBorderPainted(false);
				
		// Sets the button to opaque (necessary for Mac users)
		setOpaque(true);
				
		// Sets the preferred and min/max size of the buttons
		setPreferredSize(new Dimension(90,90));
		setMinimumSize(new Dimension(90,90));
		setMaximumSize(new Dimension(90,90));
	}
}
