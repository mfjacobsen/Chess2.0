package controller;

// https://stackoverflow.com/questions/1097366/java-swing-revalidate-vs-repaint
	
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameMenuListener implements ActionListener
{

	private ChessController controller;
	
	public NewGameMenuListener(ChessController controller) 
	{
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		controller.newGame();
	}
}
