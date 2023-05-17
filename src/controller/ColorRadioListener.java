package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ChessModel;
import view.ChessView;

public class ColorRadioListener implements ActionListener
{
	private ChessModel model;
	private ChessView view;
	private ChessController controller;
	private String color;
	
	public ColorRadioListener(ChessModel model, ChessView view, ChessController controller, String color)
	{
		this.model = model;
		this.view = view;
		this.controller = controller;
		this.color = color;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		view.setUserColor(color);
		
		if (model.getTurnCounter() == 0)
		{
			controller.newGame();
		}
	}

}
