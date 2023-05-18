package application;

import model.ChessModel;
import view.ChessView;
import controller.ChessController;

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
			e.printStackTrace();
		}		
	}
}
